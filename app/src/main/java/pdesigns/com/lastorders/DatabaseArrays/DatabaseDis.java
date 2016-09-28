package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Patrick on 14/03/2016.
 */
public class DatabaseDis {
    /**
     * The constant fbList.
     */
    public static final ArrayList<String> fbList = new ArrayList<String>();
    private ArrayList<Integer> dists;

    /**
     * Instantiates a new Database dis.
     */
    public DatabaseDis() {
        dists = new ArrayList<Integer>();
    }

    /**
     * Gets dists.
     *
     * @return the dists
     */
    public ArrayList<Integer> getDists() {
        return dists;

    }

    /**
     * Sets dists.
     *
     * @param dists the dists
     */
    public void setDists(ArrayList<Integer> dists) {
        this.dists = dists;
    }

    /**
     * Add dists.
     *
     * @param postion the postion
     * @param item    the item
     */
    public void addDists(int postion, Integer item) {
        dists.add(postion, item);
    }

    /**
     * Gets item.
     *
     * @param postion the postion
     * @return the item
     */
    public Integer getItem(int postion) {
        return dists.get(postion);
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return dists.size();
    }
}
