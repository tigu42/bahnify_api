package com.example.bahnify.bahnify_stats.DataInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NATrainQuery {
    private static final String query = """
            SELECT t.`type` as op
            FROM train t
            GROUP BY t.`type`
            HAVING COUNT(DISTINCT t.alias) = 1 AND MAX(t.alias) = '[NA]'""";

    public static List<String> getNATrainTypes(DataInfo dataInfoClass) {
        System.out.println("Running NATrains query");
        Connection con = dataInfoClass.getDbCon().getConnection();
        List<String> ops = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String op = rs.getString("op");
                ops.add(op);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return ops;
    }
}
