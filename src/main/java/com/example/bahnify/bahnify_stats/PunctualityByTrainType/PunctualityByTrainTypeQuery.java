package com.example.bahnify.bahnify_stats.PunctualityByTrainType;

import com.example.bahnify.bahnify_stats.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PunctualityByTrainTypeQuery {
    public static class QueryResult {
        public QueryResult(String type, double punctuality, double avgDelay, int maxDelay, int count) {
            this.type = type;
            this.punctuality = punctuality;
            this.avgDelay = avgDelay;
            this.maxDelay = maxDelay;
            this.count = count;
        }

        public String type;
        public double punctuality;
        public double avgDelay;
        public int maxDelay;
        public int count;

        public String toString() {
            return type + "\t\t" + punctuality + "\t\t" + avgDelay + "\t\t" + maxDelay + "\t\t" + count;

        }
    }

    private static final String queryExcludeCanceled = """
                select t.`type` as train_type,
                sum(IF((time_to_sec(timediff(d.current_arrival , t.scheduled_arrival)) / 60 < ? and d.status = 1), 1, 0)) / COUNT(*) * 100 as punctuality,
                sum(time_to_sec(timediff(d.current_arrival, t.scheduled_arrival)) / 60) / COUNT(*) as avg_delay,
                max(time_to_sec(timediff(d.current_arrival, t.scheduled_arrival)) / 60) as max_delay,
                Count(*) as train_count
                from train t
                join delay d on d.train = t.id
                join station s on t.station = s.eva
                where not t.direction = 2
                and not d.status = 2
                and s.description = ?
                group by t.`type`
                order by punctuality desc""";

    private static final String queryIncludeCanceled = """
                select t.`type` as train_type,
                sum(IF((time_to_sec(timediff(d.current_arrival , t.scheduled_arrival)) / 60 < ? and d.status = 1), 1, 0)) / COUNT(*) * 100 as punctuality,
                sum(time_to_sec(timediff(d.current_arrival, t.scheduled_arrival)) / 60) / (COUNT(*) - sum(IF((d.status = 2), 1, 0))) as avg_delay,
                max(time_to_sec(timediff(d.current_arrival, t.scheduled_arrival)) / 60) as max_delay,
                Count(*) as train_count
                from train t
                join delay d on d.train = t.id
                join station s on t.station = s.eva
                where not t.direction = 2
                and s.description = ?
                group by t.`type`
                order by punctuality desc""";


    private static PreparedStatement createPreparedStatement(PunctualityByTrainType punctualityByTrainType) throws SQLException
    {
        String query;
        if (punctualityByTrainType.getCountCanceledTrains()) query = queryIncludeCanceled;
        else query = queryExcludeCanceled;
        DatabaseConnection dbCon = punctualityByTrainType.getDbCon();
        Connection con = dbCon.getConnection();
        PreparedStatement statement;

        if (punctualityByTrainType.getStation() == null) {
            query = query.replace("and s.description = ?\n", "");
            statement = con.prepareStatement(query);
            statement.setInt(1, punctualityByTrainType.getMaxDelay());
        }
        else {
            statement = con.prepareStatement(query);
            statement.setInt(1, punctualityByTrainType.getMaxDelay());
            statement.setString(2, punctualityByTrainType.getStation());
        }

        return statement;
    }

    private static int getRowCount(ResultSet rs) throws SQLException {
        if (rs != null)
        {
            rs.last();
            int c = rs.getRow();
            rs.beforeFirst();
            return c;
        }
        return 0;
    }
    public static QueryResult[] getPunctualityByTrainType(PunctualityByTrainType punctualityByTrainType){
        QueryResult[] result;
        try (PreparedStatement statement = createPreparedStatement(punctualityByTrainType)){
            System.out.println(statement);
            ResultSet rs = statement.executeQuery();
            result = new QueryResult[getRowCount(rs)];
            int index = 0;
            while (rs.next()) {
                String type = rs.getString("train_type");
                double punctuality = rs.getDouble("punctuality");
                double avgDelay = rs.getDouble("avg_delay");
                int maxDelay = rs.getInt("max_delay");
                int count = rs.getInt("train_count");
                result[index] = new QueryResult(type, punctuality, avgDelay, maxDelay, count);
                index++;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
