package uk.co.n3fs.mc.haroldplugin;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import org.slf4j.Logger;
import uk.co.n3fs.mc.haroldplugin.platform.Scheduler;
import uk.co.n3fs.mc.haroldplugin.platform.UserManager;

import java.util.Optional;
import java.util.UUID;

/**
 * The main plugin class.
 */
public class HaroldPlugin {
    private static final UUID dataUUID = UUID.nameUUIDFromBytes("n3fs-harold:global_plugin_data".getBytes());

    private final LuckPermsApi lpApi;
    private final Scheduler scheduler;
    private final UserManager userManager;
    private final Logger logger;
    private User dataUser;

    public HaroldPlugin(Scheduler scheduler, UserManager userManager, Logger logger) {
        this.lpApi = LuckPerms.getApi();
        this.scheduler = scheduler;
        this.userManager = userManager;
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