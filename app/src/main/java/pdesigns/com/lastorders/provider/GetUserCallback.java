package pdesigns.com.lastorders.provider;

import pdesigns.com.lastorders.DTO.User;

/**
 * The interface Get user callback.
 */
public interface GetUserCallback {

    /**
     * Invoked when background task is completed
     *
     * @param returnedUser the returned user
     */
    void done(User returnedUser);
}
