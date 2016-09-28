package pdesigns.com.lastorders.provider;

import android.content.Context;
import android.content.SharedPreferences;

import pdesigns.com.lastorders.DTO.User;

public class UserLocalStore {

    /**
     * The constant SP_NAME.
     */
    public static final String SP_NAME = "userDetails";

    /**
     * The User local database.
     */
    SharedPreferences userLocalDatabase;

    /**
     * Instantiates a new User local store.
     *
     * @param context the context
     */
    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    /**
     * Store user data.
     *
     * @param user the user
     */
    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putInt("id", user.getId());
        userLocalDatabaseEditor.putString("fName", user.getfName());
        userLocalDatabaseEditor.putString("lName", user.getlName());
        userLocalDatabaseEditor.putString("email", user.getEmail());
        userLocalDatabaseEditor.putString("uPassword", user.getuPassword());
        userLocalDatabaseEditor.commit();
    }

    /**
     * Sets user logged in.
     *
     * @param loggedIn the logged in
     */
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    /**
     * Clear user data.
     */
    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    /**
     * Gets logged in user.
     *
     * @return the logged in user
     */
    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        int id = userLocalDatabase.getInt("id", -1);
        String fName = userLocalDatabase.getString("fName", "");
        String lName = userLocalDatabase.getString("lName", "");
        String email = userLocalDatabase.getString("email", "");
        String uPassword = userLocalDatabase.getString("uPassword", "");


        User user = new User(id, fName, lName, email, uPassword);
        return user;
    }
}
