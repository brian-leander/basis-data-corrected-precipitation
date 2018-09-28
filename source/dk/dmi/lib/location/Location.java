package dk.dmi.lib.location;

import java.util.logging.Logger;

/**
 *
 * @author hmj
 */
public class Location {
    private int id;
    private int eastings;
    private int northings;

    public Location(int id, int eastings, int northings) {
        this.id = id;
        this.eastings = eastings;
        this.northings = northings;
    }

    public int getId() {
        return id;
    }

    public int getEastings() {
        return eastings;
    }

    public int getNorthings() {
        return northings;
    }

    public void setEastings(int eastings) {
        this.eastings = eastings;
    }

    public void setNorthings(int northings) {
        this.northings = northings;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", eastings=" + eastings + ", northings=" + northings + '}';
    }
}
