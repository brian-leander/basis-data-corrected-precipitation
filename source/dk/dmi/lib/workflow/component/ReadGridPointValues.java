package dk.dmi.lib.workflow.component;

import dk.dmi.lib.db.ClimaDB;
import dk.dmi.lib.location.GridPointValue;
import dk.dmi.lib.workflow.common.BaseComponent;
import dk.dmi.lib.workflow.common.WorkflowAnnotations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@WorkflowAnnotations.Component(
        name = "Read Grid Point Values",
        category = "Basis Data",
        description = "reads",
        version = 1)
public class ReadGridPointValues extends BaseComponent {

    @WorkflowAnnotations.ExecuteMethod()
    public Map<Integer, GridPointValue> readGridPointValues(ZonedDateTime date) {
//        NovanaDB ndb = new NovanaDB("wwwdb.dmi.dk:3306", "novadba", "hmjkir", "novana2DB");
//        ndb.open();
        ClimaDB ndb = new ClimaDB();

        Map<Integer, GridPointValue> gridPointValues = new HashMap<>();

        try {
//            String sel = "select field_id, elem_no, grid_value from grid_daily_20km where "
//                    + "dato = ? and (elem_no = 101 or elem_no = 301);";
            String sel = "select name as field_id, element_number as elem_no, value as grid_value from polygon.view_20x20_daily where "
                    + "the_date = ? and (element_number = 101 or element_number = 301);";

            PreparedStatement ps = ndb.getConnection().prepareStatement(sel);
            ps.setTimestamp(1, new Timestamp(date.toInstant().toEpochMilli()));

            ResultSet r = ps.executeQuery();
            while (r.next()) {
                Double temp = null, wind = null;

                int fieldId = r.getInt("field_id");
                int elemNo = r.getInt("elem_no");
                if (elemNo == 101) {
                    temp = r.getDouble("grid_value");
                } else {
                    wind = r.getDouble("grid_value");
                }

                GridPointValue gridPointValue;
                if (gridPointValues.containsKey(fieldId)) {
                    gridPointValue = gridPointValues.get(fieldId);
                } else {
                    gridPointValue = new GridPointValue();
                }

                if (elemNo == 101) {
                    gridPointValue.setTemp(temp);
                } else {
                    gridPointValue.setWind(wind);
                }

                gridPointValues.put(fieldId, gridPointValue);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            ndb.close();
        }

        return gridPointValues;
    }
}
