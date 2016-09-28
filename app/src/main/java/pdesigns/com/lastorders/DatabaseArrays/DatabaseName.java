package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Patrick on 01/04/2016.
 */
public class DatabaseName {

    private ArrayList<String> names;

    /**
     * Instantiates a new Database name.
     */
    public DatabaseName() {
        this.names = new ArrayList<String>();
    }

    /**
     * Instantiates a new Database name.
     *
     * @param names the names
     */
    public DatabaseName(ArrayList<String> names) {
        this.names = names;
    }

    /**
     * Gets names.
     *
     * @return the names
     */
    public ArrayList<String> getNames() {
        return names;
    }

    /**
     * Sets names.
     *
     * @param names the names
     */
    public void setNames(ArrayList<String> names) {
        this.names = names;
    }


    /**
     * Add name.
     *
     * @param postion the postion
     * @param item    the item
     */
    public void addName(int postion, String item) {
        names.add(postion, item);
    }

    /**
     * Gets item.
     *
     * @param postion the postion
     * @return the item
     */
    public String getItem(int postion) {
        return names.get(postion);
    }
}
