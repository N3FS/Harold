package uk.co.n3fs.mc.haroldplugin.platform.spigot;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.n3fs.mc.haroldplugin.HaroldPlugin;

import java.util.Optional;

/**
 * Starts Harold on the Spigot platform.
 */
public final class Bootstrap extends JavaPlugin {

    private HaroldPlugin instance;
    private final SchedulerImpl scheduler = new SchedulerImpl(this);
    private final UserManagerImpl userManager = new UserManagerImpl(this);
    private final Logger logger = LoggerFactory.getLogger("Harold");

    @Override
    public void onEnable() {
        if (instance != null) {
            logger.error("Harold did not disable properly before being re-enabled! This is BAD!",
                new RuntimeException("Plugin was not cleanly disabled previously"));
        }

        instance = new HaroldPlugin(scheduler, userManager, logger);
    }

    @Override
    public void onDisable() {
        // TODO: instance.shutdown(); instance = null;
    }
}
