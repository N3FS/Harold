package uk.co.n3fs.mc.haroldplugin.platform;

import java.util.UUID;

/**
 * A user to whom punishments can apply.
 *
 * @param <T> The base class for the user on the current platform
 */
public interface User<T> {
    String getName();

    UUID getUniqueId();

    boolean isAuthorized(String permission);

    T getBase();
}
