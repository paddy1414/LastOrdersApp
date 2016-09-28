package pdesigns.com.lastorders.DTO;

import java.io.Serializable;

/**
 * Created by Patrick on 01/04/2016.
 */
public class Comments implements Serializable {

    private static int commentId;
    private String fname, lname;
    private String commentText;

    /**
     * Instantiates a new Comments.
     */
    public Comments() {
        commentId = 0;
        this.fname = "";
        this.lname = "";
        this.commentText = "";
    }

    /**
     * Instantiates a new Comments.
     *
     * @param fname       the fname
     * @param lname       the lname
     * @param commentText the comment text
     */
    public Comments(String fname, String lname, String commentText) {
        commentId++;
        this.fname = fname;
        this.lname = lname;
        this.commentText = commentText;
    }

    /**
     * Gets comment id.
     *
     * @return the comment id
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Sets comment id.
     *
     * @param commentId the comment id
     */
    public void setCommentId(int commentId) {
        Comments.commentId = commentId;
    }

    /**
     * Gets fname.
     *
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets fname.
     *
     * @param fname the fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Gets lname.
     *
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets lname.
     *
     * @param lname the lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Gets comment text.
     *
     * @return the comment text
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Sets comment text.
     *
     * @param commentText the comment text
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comments)) return false;

        Comments comments = (Comments) o;

        if (getCommentId() != comments.getCommentId()) return false;
        if (!getFname().equals(comments.getFname())) return false;
        if (!getLname().equals(comments.getLname())) return false;
        return getCommentText().equals(comments.getCommentText());

    }

    @Override
    public int hashCode() {
        int result = getCommentId();
        result = 31 * result + getFname().hashCode();
        result = 31 * result + getLname().hashCode();
        result = 31 * result + getCommentText().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
