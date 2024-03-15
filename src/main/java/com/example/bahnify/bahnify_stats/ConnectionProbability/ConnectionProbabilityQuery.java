package com.example.bahnify.bahnify_stats.ConnectionProbability;

import com.example.bahnify.bahnify_stats.DatabaseConnection;
import com.example.bahnify.bahnify_stats.HourlyDelays.HourlyDelaysQuery;
import com.example.bahnify.bahnify_stats.Resources.ChangeProbability;
import com.example.bahnify.bahnify_stats.Resources.Direction;
import com.example.bahnify.bahnify_stats.Resources.IIdentifiable;
import com.example.bahnify.bahnify_stats.Resources.TrainStopStats;
import org.checkerframework.checker.units.qual.C;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionProbabilityQuery {
    private final static String queryArriving = """
            select t.alias as alias, t.last_station as last_station,
            COUNT(case when (time_to_sec(timediff(d.current_arrival, t.scheduled_arrival)) / 60 < 6 and d.status = 1) then 1 else null end) as punctual,
            count(*) as train_count,
            sum(time_to_sec(timediff(d.current_arrival, t.scheduled_arrival)) / 60) / (COUNT(*) -  sum(case when d.status = 2 then 1 else 0 end)) as average_delay,
            sum(case when d.status = 2 then 1 else 0 end) as canceled
            from train t
            join delay d on d.train = t.id
            join station s on t.station = s.eva
            where t.train_number = ?
            and s.description = ?
            and t.direction <> 2
            and t.`type` = ?
            and d.status <> 2
            and time(t.scheduled_arrival) = ?""";

    private final static String queryDeparting = """
            select t.alias as alias, t.last_station as last_station,
            COUNT(case when (time_to_sec(timediff(d.current_departure , t.scheduled_departure)) / 60 < 6 and d.status = 1) then 1 else null end) as punctual,
            count(*) as train_count,
            sum(time_to_sec(timediff(d.current_departure, t.scheduled_departure)) / 60) / (COUNT(*) -  sum(case when d.status = 2 then 1 else 0 end)) as average_delay,
            sum(case when d.status = 2 then 1 else 0 end) as canceled
            from train t
            join delay d on d.train = t.id
            join station s on t.station = s.eva
            where t.train_number = ?
            and s.description = ?
            and t.direction <> 1
            and t.`type` = ?
            and TIME(t.scheduled_departure) = ?""";

    private final static String changeQuery = """
            select sum(case when (da.status = 1 and B.status_dep = 2) or (da.current_arrival > B.current_dep) then 1 else 0 end) as failed, sum(case when da.status = 1 and B.status_dep is not null then 1 else 0 end) as all_trains
            from train ta
            left join delay da on ta.id = da.train
            left join (
            	select d.status as status_dep, d.current_departure as current_dep, t.scheduled_departure as scheduled_dep
            	from train t
            	left join delay d on t.id = d.train
            	left join station s on s.eva = t.station
            	where t.train_number = ?
            	and s.description = ?
            	and t.`type`= ?
            	and time(t.scheduled_departure) = ?
            ) as B on DATE(B.scheduled_dep) = DATE(ta.scheduled_arrival)
            left join station sa on sa.eva = ta.station
            where ta.train_number = ?
            and sa.description = ?
            and ta.`type` = ?
            and time(ta.scheduled_arrival) = ?""";

    public static ChangeProbability getChangeProbability(IIdentifiable arriving, IIdentifiable departing, DatabaseConnection dbCon) {
        Connection con = dbCon.getConnection();
        ChangeProbability ret = null;
        try (PreparedStatement stmt = con.prepareStatement(changeQuery)) {
            ResultSet rs;
            int paramIndex = 1;
            stmt.setString(paramIndex, departing.getTrainNum());
            paramIndex++;
            stmt.setString(paramIndex, departing.getCurrentStation());
            paramIndex++;
            stmt.setString(paramIndex, departing.getOperator());
            paramIndex++;
            stmt.setString(paramIndex, departing.getTime().toString());
            paramIndex++;
            stmt.setString(paramIndex, arriving.getTrainNum());
            paramIndex++;
            stmt.setString(paramIndex, arriving.getCurrentStation());
            paramIndex++;
            stmt.setString(paramIndex, arriving.getOperator());
            paramIndex++;
            stmt.setString(paramIndex, arriving.getTime().toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                ret = new ChangeProbability(rs.getInt("failed"), rs.getInt("all_trains"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static TrainStopStats getStats(IIdentifiable train, DatabaseConnection dbCon) {
        String query = train.getDirection() == Direction.arriving ? queryArriving : queryDeparting;
        Connection con = dbCon.getConnection();
        TrainStopStats ret = null;
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs;
            int paramIndex = 1;
            stmt.setString(paramIndex, train.getTrainNum());
            paramIndex++;
            stmt.setString(paramIndex, train.getCurrentStation());
            paramIndex++;
            stmt.setString(paramIndex, train.getOperator());
            paramIndex++;
            stmt.setString(paramIndex, train.getTime().toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                String alias = rs.getString("alias");
                String lastStation = rs.getString("last_station");
                int punctual = rs.getInt("punctual");
                int trainCount = rs.getInt("train_count");
                double averageDelay = rs.getDouble("average_delay");
                int canceled = rs.getInt("canceled");
                ret = new TrainStopStats(train, lastStation, alias, query.equals(queryDeparting),
                                        averageDelay, trainCount - punctual, punctual,
                                (double) punctual / trainCount, canceled, trainCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
