package dk.dmi.lib.location;

import dk.dmi.lib.gauges.Station;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hmj
 */
public class GridClassifier {

    private List<GridPoint> gridPoints = null;

    public GridClassifier(List<GridPoint> gridPoints) {
        this.gridPoints = gridPoints;
    }

    public int getGridPoint(Location s, int statid) {
        for (GridPoint p : gridPoints) {

            boolean x = Math.abs(p.getEastings() - s.getEastings()) <= p.getSize() / 2.0;
            boolean y = Math.abs(p.getNorthings() - s.getNorthings()) <= p.getSize() / 2.0;

            if (x && y) {
                return p.getId();
            }
        }


        switch (statid) {
            // Skagen ligger lige uden for cellen
            case 2000050:
            case 604100:
                return 20098;
            case 2703050:   // Hesselø ligger lige uden for cellen
                return 20152;
            case 611900:    //Kegnæs Fyr
                return 20097;
        }
        return 0;
    }
}
