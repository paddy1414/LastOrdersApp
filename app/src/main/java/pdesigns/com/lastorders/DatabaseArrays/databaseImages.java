package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Paddy on 30/05/2015.
 */
public class databaseImages {

    private ArrayList<String> images;

    /**
     * Instantiates a new Database images.
     */
    public databaseImages() {
        images = new ArrayList<String>();
    }

    /**
     * Gets images.
     *
     * @return the images
     */
    public ArrayList<String> getImages() {
        return images;

    }

    /**
     * Sets images.
     *
     * @param images the images
     */
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    /**
     * Add images.
     *
     * @param postion the postion
     * @param item    the item
     */
    public void addImages(int postion, String item) {
        images.add(postion, item);
    }

    /**
     * Gets item.
     *
     * @param postion the postion
     * @return the item
     */
    public String getItem(int postion) {
        return images.get(postion);
    }


}
