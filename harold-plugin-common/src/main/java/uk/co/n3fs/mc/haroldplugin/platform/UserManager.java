package uk.co.n3fs.mc.haroldplugin.platform;

import java.util.UUID;

/**
 * A user manager for the platform.
 *
 * @param <T> The platform's player type
 */
public interface UserManager<T> {

    User<T> getUser(T base);

    User<T> getUser(UUID uniqueId);

    User<T> getOnlineUser(String username);

}
