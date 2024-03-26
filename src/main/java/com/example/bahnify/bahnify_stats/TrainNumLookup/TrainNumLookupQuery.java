package com.example.bahnify.bahnify_stats.TrainNumLookup;

import com.example.bahnify.bahnify_stats.DatabaseConnection;
import com.example.bahnify.bahnify_stats.Resources.Direction;
import com.example.bahnify.bahnify_stats.Resources.IIdentifiable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainNumLookupQuery {

    private final static String queryDeparting = """
            select t.train_number as num
            from train t
            join station s on t.station = s.eva
            where t.alias = ?
            and s.description = ?
            and t.`type` = ?
            and time(t.scheduled_departure) = ?
            limit 1""";

    private final static String queryArriving = """
            select t.train_number as num
            from train t
            join station s on t.station = s.eva
            where t.alias = ?
            and s.description = ?
            and t.`type` = ?
            and time(t.scheduled_arrival) = ?
            limit 1""";

    public static String getTrainNum(TrainNumLookup t_class) {
        IIdentifiable train = t_class.getTrain();
        DatabaseConnection dbCon = t_class.getDbCon();
        String query = train.getDirection() == Direction.arriving ? queryArriving : queryDeparting;
        System.out.println("Running TrainNumLookup query");
        String ret = "0";
        Connection con = dbCon.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            int paramIndex = 1;
            stmt.setString(paramIndex, train.getTrainNum());
            paramIndex++;
            stmt.setString(paramIndex, train.getCurrentStation());
            paramIndex++;
            stmt.setString(paramIndex, train.getOperator());
            paramIndex++;
            stmt.setString(paramIndex, train.getTime().toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ret = rs.getString("num");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
