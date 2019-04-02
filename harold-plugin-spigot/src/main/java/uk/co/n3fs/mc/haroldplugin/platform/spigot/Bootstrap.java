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

        Optional<LuckPermsApi> lpOpt = LuckPerms.getApiSafe();
        if (lpOpt.isPresent()) {
            LuckPermsApi lpApi = lpOpt.get();
            instance = new HaroldPlugin(lpApi, scheduler, userManager, logger);
        } else {
            // It's technically possible for LuckPerms to load after us, though it shouldn't
            getLogger().warning("LuckPerms wasn't loaded before Harold. Waiting...");
            getLogger().warning("Harold will not work until LuckPerms is available.");
            this.loadLate();
        }

    }

    private void loadLate() {
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            public void onPluginEnable(PluginEnableEvent event) {
                if (event.getPlugin().getName().equals("LuckPerms")) {
                    event.getHandlers().unregister(this);
                    instance = new HaroldPlugin(LuckPerms.getApi(), scheduler, userManager, logger);
                }
            }
        }, this);
    }

    @Override
    public void onDisable() {
        // TODO: instance.shutdown(); instance = null;
    }
}
