package com.example.bahnify.bahnify_stats.DataInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationsQuery {

    private static final String query = "select station.description as station from station";

    public static List<String> getStations(DataInfo dataInfoClass) {
        System.out.println("Running stations query");
        Connection con = dataInfoClass.getDbCon().getConnection();
        List<String> stations = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String station = rs.getString("station");
                stations.add(station);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return stations;
    }

}
