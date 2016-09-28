package pdesigns.com.lastorders.DatabaseArrays;

import android.util.Log;

import java.util.ArrayList;

import pdesigns.com.lastorders.DTO.Bar;

/**
 * Created by Paddy on 30/05/2015.
 */
public class databaseBars {


    /**
     * The Name list.
     */
    ArrayList<String> nameList;
    /**
     * The B.
     */
    Bar b;
    private ArrayList<Bar> bars;

    /**
     * Instantiates a new Database bars.
     */
    public databaseBars() {
        bars = new ArrayList<Bar>();
    }

    /**
     * Gets bar list.
     *
     * @return the bar list
     */
    public ArrayList<Bar> getBarList() {
        return bars;

    }

    /**
     * Sets bars.
     *
     * @param images the images
     */
    public void setBars(ArrayList<Bar> images) {
        this.bars = images;
    }

    // public void addBars(int postion, int id, String name, String fb, String locale, String url, Integer distanceInMeters) {
    //      b = new Bar(id, name, fb, locale, url);


    //  System.out.println("from the dbb addBit" + b.toString());
    //      bars.add(postion, b);
    //  nameList.add(postion, name);


    //   }

    /**
     * Add bars.
     *
     * @param id               the id
     * @param name             the name
     * @param fb               the fb
     * @param locale           the locale
     * @param url              the url
     * @param distanceInMeters the distance in meters
     */
    public void addBars(int id, String name, String fb, String locale, String url, Integer distanceInMeters) {
        b = new Bar(id, name, fb, locale, url, distanceInMeters);
        Log.d("b", b.toString());

        bars.add(b);

    }

    public String toString() {

        return bars.toString();
    }

    /**
     * Gets item.
     *
     * @param postion the postion
     * @return the item
     */
    public Bar getItem(int postion) {
        return bars.get(postion);
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return bars.size();
    }
}
