package dk.dmi.lib.location;

import dk.dmi.lib.location.Location;

/**
 *
 * @author hmj
 */
public class GridPoint extends Location {

    private int size;

    public GridPoint(int id, int eastings, int northings, int size) {
        super(id, eastings, northings);
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "" + getId() + ", " + getEastings() + ", " + getNorthings() + ", " + getSize() / 2.0;
    }
}
