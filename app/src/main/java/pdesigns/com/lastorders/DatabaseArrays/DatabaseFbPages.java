package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Patrick on 12/12/2015.
 */
public class DatabaseFbPages {

    /**
     * The constant fbList.
     */
    public static final ArrayList<String> fbList = new ArrayList<String>();
    private ArrayList<String> fBs;

    /**
     * Instantiates a new Database fb pages.
     */
    public DatabaseFbPages() {
        fBs = new ArrayList<String>();
    }

    /**
     * Gets fb list.
     *
     * @return the fb list
     */
    public ArrayList<String> getFbList() {
        return fBs;

    }

    /**
     * Sets fb list.
     *
     * @param images the images
     */
    public void setFbList(ArrayList<String> images) {
        this.fBs = images;
    }

    /**
     * Add fb list.
     *
     * @param postion the postion
     * @param item    the item
     */
    public void addFbList(int postion, String item) {
        fBs.add(postion, item);
    }

    /**
     * Gets item.
     *
     * @param postion the postion
     * @return the item
     */
    public String getItem(int postion) {
        return fBs.get(postion);
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return fBs.size();
    }
}
