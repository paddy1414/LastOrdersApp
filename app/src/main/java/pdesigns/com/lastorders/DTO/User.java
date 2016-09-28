package pdesigns.com.lastorders.DTO;

import java.io.Serializable;

/**
 * Created by Patrick on 12/12/2015.
 */
public class User implements Serializable {

    /**
     * The F name.
     */
    String fName, /**
     * The L name.
     */
    lName, /**
     * The Email.
     */
    email, /**
     * The U password.
     */
    uPassword, /**
     * The Uid.
     */
    uid;
    /**
     * The Id.
     */
    int id;

    /**
     * Instantiates a new User.
     *
     * @param id        the id
     * @param fName     the f name
     * @param lName     the l name
     * @param email     the email
     * @param uPassword the u password
     */
    public User(String id, String fName, String lName, String email, String uPassword) {
        this.uid = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.uPassword = "";
    }

    /**
     * Instantiates a new User.
     *
     * @param id    the id
     * @param fName the f name
     * @param lName the l name
     * @param email the email
     */
    public User(String id, String fName, String lName, String email) {
        this.uid = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.uPassword = "";
    }

    /**
     * Instantiates a new User.
     *
     * @param id        the id
     * @param fName     the f name
     * @param lName     the l name
     * @param email     the email
     * @param uPassword the u password
     */
    public User(int id, String fName, String lName, String email, String uPassword) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.uPassword = uPassword;
    }


    /**
     * Instantiates a new User.
     */
    public User() {
        this.id = 0;
        this.fName = "";
        this.lName = "";
        this.email = "";
        this.uPassword = "";
    }

    /**
     * Instantiates a new User.
     *
     * @param email     the email
     * @param uPassword the u password
     */
    public User(String email, String uPassword) {
        this(-1, "", "", email, uPassword);
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getfName() {
        return fName;
    }

    /**
     * Sets name.
     *
     * @param fName the f name
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getlName() {
        return lName;
    }

    /**
     * Sets name.
     *
     * @param lName the l name
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getuPassword() {
        return uPassword;
    }

    /**
     * Sets password.
     *
     * @param uPassword the u password
     */
    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (!getfName().equals(user.getfName())) return false;
        if (!getlName().equals(user.getlName())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        return getuPassword().equals(user.getuPassword());

    }

    @Override
    public int hashCode() {
        int result = getfName().hashCode();
        result = 31 * result + getlName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getuPassword().hashCode();
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uid='" + uid + '\'' +
                ", id=" + id +
                '}';
    }
}

