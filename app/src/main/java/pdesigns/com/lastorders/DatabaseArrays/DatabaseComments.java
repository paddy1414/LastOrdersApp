package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Patrick on 01/04/2016.
 */
public class DatabaseComments {

    /**
     * The Comment list.
     */
    ArrayList<String> commentList;

    /**
     * Instantiates a new Database comments.
     *
     * @param commentList the comment list
     */
    public DatabaseComments(ArrayList<String> commentList) {
        this.commentList = commentList;
    }

    /**
     * Instantiates a new Database comments.
     */
    public DatabaseComments() {
        this.commentList = new ArrayList<String>();
    }


    /**
     * Gets comment list.
     *
     * @return the comment list
     */
    public ArrayList<String> getCommentList() {
        return commentList;
    }

    /**
     * Sets comment list.
     *
     * @param commentList the comment list
     */
    public void setCommentList(ArrayList<String> commentList) {
        this.commentList = commentList;
    }


    /**
     * Add comment.
     *
     * @param postion     the postion
     * @param commentText the comment text
     */
    public void addComment(int postion, String commentText) {


        commentList.add(postion, commentText);
    }

}
