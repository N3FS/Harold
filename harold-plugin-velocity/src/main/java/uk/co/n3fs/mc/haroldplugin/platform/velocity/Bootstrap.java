package uk.co.n3fs.mc.haroldplugin.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.slf4j.Logger;
import uk.co.n3fs.mc.haroldplugin.HaroldPlugin;

/**
 * Bootstraps Harold on the Velocity platform.
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

    @Inject private ProxyServer proxy;

    private HaroldPlugin instance;
    private SchedulerImpl scheduler = new SchedulerImpl(this, proxy.getScheduler());
    private UserManagerImpl userManager = new UserManagerImpl(this);
    @Inject private Logger logger;

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent event) {
        if (instance != null) {
            logger.error("Something went horribly wrong and the proxy is imploding!");
            logger.error("No, but seriously: the proxy has initialised more than once! This is BAD!",
                new RuntimeException("Proxy init event unexpectedly fired twice"));
        }

        instance = new HaroldPlugin(scheduler, userManager, logger);
    }

    public ProxyServer getProxy() {
        return proxy;
    }
}
