package uk.co.n3fs.mc.haroldplugin.platform.sponge;

import com.google.inject.Inject;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import uk.co.n3fs.mc.haroldplugin.HaroldPlugin;

import java.util.Optional;

/**
 * Bootstraps Harold on Sponge platforms.
 */
@Plugin(
    id = "n3fs-harold",
    name = "Harold",
    description = "A moderation plugin, built for the N3FS",
    url = "https://n3fs.co.uk",
    authors = {
        "md678685"
    },
    dependencies = {
        @Dependency(id = "LuckPerms")
    }
)
public class Bootstrap {

    private HaroldPlugin instance;
    private SchedulerImpl scheduler = new SchedulerImpl(this);
    private UserManagerImpl userManager = new UserManagerImpl(this);
    @Inject private Game game;
    @Inject private Logger logger;

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        if (instance != null) {
            logger.error("Something went horribly wrong and the game is imploding!");
            logger.error("No, but seriously: the game has pre-initialised more than once! This is BAD!",
                new RuntimeException("Pre-init event unexpectedly fired twice"));
        }

        instance = new HaroldPlugin(scheduler, userManager, logger);
    }

    Optional<Server> getServer() {
        return game.isServerAvailable() ? Optional.of(game.getServer()) : Optional.empty();
    }
}
