package dk.dmi.lib.db;

import dk.dmi.lib.location.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

public class StatDB extends Database {
    /**
    * Constructor by default it connects to ClimaDB with Clipac settings
    */
    public StatDB() {
        super("nanoqT.dmi.dk:5432/statdb", "thn", "thndmi", Database.POSTGRES);
    }

    /**
     * Constructor which can connect to ClimaDB with settings provided as
     * arguments
     *
     * @param serverName server name. eg. nanow.dmi.dk:5432
     * @param databaseName database name. eg. climadb
     * @param userName user name. eg. kev
     * @param password password. eg. pass
     */
    public StatDB(String serverName, String databaseName, String userName, String password) {
        super(serverName + "/" + databaseName, userName, password, Database.POSTGRES);
    }

    public Map<Integer, Location> getStatPos(ZonedDateTime date) {
        Map<Integer, Location> locations = new HashMap<>();
        try {
            String sel = "select statid, easting, northing from position_utm_wgs84_z32 where "
                    + "? between start_time and end_time";

            PreparedStatement ps = getConnection().prepareStatement(sel);

            ps.setTimestamp(1, new Timestamp(date.toInstant().toEpochMilli()));

            ResultSet r = ps.executeQuery();

            while (r.next()) {
                int statid = r.getInt("statid");
                int eastings = Math.round(r.getFloat("easting"));
                int northings = Math.round(r.getFloat("northing"));
                locations.put(statid, new Location(statid, eastings, northings));
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return locations;
    }

    public Map<Integer, Integer> getLeeIndex(ZonedDateTime date) {
        Map<Integer, Integer> leeIndexStations = new HashMap<>();
    	/*
    	 SELECT distinct(a.statid), a.elem_val, a.label, a.end_hour FROM
                (SELECT statid, elem_val, label, end_hour from basis_daily_dk where
                the_date = '2017-04-29' and elem_no = 601 order by statid) a ;
    	 */
        String leeIndexStatement = "select statid, start_time, end_time, index from leeindex where start_time <= ? and end_time > ? order by start_time;";

        try {
            PreparedStatement ps = getConnection().prepareStatement(leeIndexStatement);
            ps.setTimestamp(1, new Timestamp(date.toInstant().toEpochMilli()));
            ps.setTimestamp(2, new Timestamp(date.toInstant().toEpochMilli()));

            ResultSet r = ps.executeQuery();
            while (r.next()) {
                int statid = r.getInt("statid");
                int value = r.getInt("index");

                leeIndexStations.put(statid, value);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return leeIndexStations;
    }

}