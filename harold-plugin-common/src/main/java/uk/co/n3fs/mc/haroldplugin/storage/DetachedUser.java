package uk.co.n3fs.mc.haroldplugin.storage;

import uk.co.n3fs.mc.haroldplugin.platform.User;

import java.util.UUID;

/**
 * A platform-agnostic representation of a user not attached to a base.
 */
public class DetachedUser implements User<Object> {

    private final String name;
    private final UUID uniqueId;

    public DetachedUser(UUID uniqueId) {
        this(null, uniqueId);
    }

    public DetachedUser(String name, UUID uniqueId) {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public boolean isAuthorized(String permission) {
        return false;
    }

    @Override
    public Object getBase() {
        return null;
    }
}
