package uk.co.n3fs.mc.haroldplugin;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import org.slf4j.Logger;
import uk.co.n3fs.mc.haroldplugin.platform.Scheduler;

import java.util.Optional;
import java.util.UUID;

/**
 * The main plugin class.
 */
public class HaroldPlugin {
    private static final UUID dataUUID = UUID.nameUUIDFromBytes("n3fs-harold:global_plugin_data".getBytes());

    private final LuckPermsApi lpApi;
    private final Scheduler scheduler;
    private final Logger logger;
    private User dataUser;

    public HaroldPlugin(LuckPermsApi lpApi, Scheduler scheduler, Logger logger) {
        this.lpApi = lpApi;
        this.scheduler = scheduler;
        this.logger = logger;

        // TODO: storage
        scheduler.runLater(() -> {
            User user = getGlobalDataUser();
            logger.info(user.getName());
        }, 0);
    }

    // TODO: storage
    private User getGlobalDataUser() {
        Optional<User> userOpt = lpApi.getUserSafe(dataUUID);
        userOpt.ifPresent(user -> dataUser = user);
        return dataUser;
    }

}