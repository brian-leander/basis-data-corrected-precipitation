package dk.dmi.lib.correctedprecipitation;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for storing rainintensity for each month.
 * See IR11-10 page 8
 * 
 */
public class RainIntensityI {

    static final Map<Month, Double> intensity ;
    static {
        intensity = new HashMap<>(12);
        intensity.put(Month.JANUARY, 1.12);
        intensity.put(Month.FEBRUARY, 1.21);
        intensity.put(Month.MARCH, 1.18);
        intensity.put(Month.APRIL, 1.38);
        intensity.put(Month.MAY, 2.01);
        intensity.put(Month.JUNE, 2.46);
        intensity.put(Month.JULY, 3.01);
        intensity.put(Month.AUGUST, 2.90);
        intensity.put(Month.SEPTEMBER, 2.26);
        intensity.put(Month.OCTOBER, 1.71);
        intensity.put(Month.NOVEMBER, 1.37);
        intensity.put(Month.DECEMBER, 1.26);
    }

    /**
     * Returns the climatological precipitation intensity for a specific month
     * @param month a 0-indexed month (0 = january, 11=december)
     * @return The climatological precipitation intensity in mm/hour 
     */
    public static double getI(Month month) {
        
        return intensity.get(month);
    }
}
