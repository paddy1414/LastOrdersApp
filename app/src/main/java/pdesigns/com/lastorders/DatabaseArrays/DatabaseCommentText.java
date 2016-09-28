package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Patrick on 01/04/2016.
 */
public class DatabaseCommentText {

    private ArrayList<String> comments;


    /**
     * Instantiates a new Database comment text.
     */
    public DatabaseCommentText() {
        this.comments = new ArrayList<String>();
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public ArrayList<String> getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    /**
     * Add fb list.
     *
     * @param postion the postion
     * @param item    the item
     */
    public void addFbList(int postion, String item) {
        comments.add(postion, item);
    }
}
