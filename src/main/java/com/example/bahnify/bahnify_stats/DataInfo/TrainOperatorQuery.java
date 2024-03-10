package com.example.bahnify.bahnify_stats.DataInfo;

import com.example.bahnify.bahnify_stats.Resources.TrainOperator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainOperatorQuery {
    private static final String query = """
            select operator, travel_type, description
            from operators""";

    public static List<TrainOperator> getTrainOperators (DataInfo dataInfoClass) {
        Connection con = dataInfoClass.getDbCon().getConnection();
        List<TrainOperator> trainOperators = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String abbreviation = rs.getString("operator");
                String type = rs.getString("travel_type");
                String description = rs.getString("description");
                trainOperators.add(new TrainOperator(abbreviation, description, type));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return trainOperators;
    }
}

/*public class StationsQuery {

    private static final String query = "select station.description as station from station";

    public static List<String> getStations(DataInfo dataInfoClass) {
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

}*/