package pdesigns.com.lastorders.DatabaseArrays;

import java.util.ArrayList;

import pdesigns.com.lastorders.DTO.User;

/**
 * Created by Patrick on 14/12/2015.
 */
public class DatabaseUsers {

    private ArrayList<User> usersList;

    /**
     * Instantiates a new Database users.
     *
     * @param usersList the users list
     */
    public DatabaseUsers(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    /**
     * Instantiates a new Database users.
     */
    public DatabaseUsers() {
        this.usersList = new ArrayList<User>();
    }


    /**
     * Gets users list.
     *
     * @return the users list
     */
    public ArrayList<User> getUsersList() {
        return usersList;
    }

    /**
     * Sets users list.
     *
     * @param usersList the users list
     */
    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }


    /**
     * Add users.
     *
     * @param postion the postion
     * @param u       the u
     */
    public void addUsers(int postion, User u)

    {
        usersList.add(postion, u);
    }
}
