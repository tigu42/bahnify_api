package com.example.bahnify.bahnify_stats.HourlyDelays;
import com.example.bahnify.bahnify_stats.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HourlyDelaysQuery {
    public static class QueryResult {
        public int hour;
        public double averageDelay;
        public int count;

        public QueryResult(int hour, double averageDelay, int count) {
            this.hour = hour;
            this.averageDelay = averageDelay;
            this.count = count;
        }
        public String toString() {
            return this.hour + "\t" + this.averageDelay + "\t" + this.count;
        }
    }
    private static String getQuery(HourlyDelays delayClass) {
        StringBuilder query = new StringBuilder("""
                select HOUR(t.scheduled_arrival) as hour_of_day, SUM(abs(time_to_sec(timediff(d.current_arrival, t.scheduled_arrival))) / 60) / COUNT(*) as avg_delay, COUNT(*) as count
                from train t
                join delay d on t.id = d.train
                join station s on t.station = s.eva
                join operators o on t.`type` = o.operator
                where t.direction <> 2
                """);
        if (delayClass.getStation() != null) {
            query.append("and s.description = ?\n");
        }
        String[] operators = delayClass.getRequestedOperators();
        if (operators != null) {
            query.append("and (");
            for (String operator: operators) {
                query.append("t.`type` = ").append("\"").append(operator).append("\"").append(" or ");
            }
            query.append("1 = 0)\n");
        }
        if (delayClass.getStartTime() != null) {
            query.append("AND (t.scheduled_arrival >= ? AND t.scheduled_arrival < ?)\n");
        }
        if (delayClass.getOperatorType() != null) {
            query.append("and o.travel_type  = ?\n");
        }
        query.append("GROUP BY hour_of_day");
        return query.toString();
    }

    public static QueryResult[] getHourlyDelays(HourlyDelays delayClass) {
        String query = getQuery(delayClass);
        DatabaseConnection dbCon = delayClass.getDbCon();
        Connection con = dbCon.getConnection();
        QueryResult[] result = new QueryResult[24];
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs;
            int parameterIndex = 1;
            if (delayClass.getStation() != null) {
                stmt.setString(parameterIndex, delayClass.getStation());
                parameterIndex++;
            }
            if (delayClass.getStartTime() != null) {
                stmt.setString(parameterIndex, delayClass.getStartTime().toString());
                parameterIndex++;
                stmt.setString(parameterIndex, delayClass.getEndTime().toString());
                parameterIndex++;
            }
            if (delayClass.getOperatorType() != null) {
                stmt.setString(parameterIndex,delayClass.getOperatorType());
            }
            System.out.println(stmt);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt("hour_of_day");
                double avg_delay = rs.getDouble("avg_delay");
                int count = rs.getInt("count");
                result[hour] = new QueryResult(hour, avg_delay, count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
