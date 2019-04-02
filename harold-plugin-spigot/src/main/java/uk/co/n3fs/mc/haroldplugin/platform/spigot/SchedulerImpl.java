package uk.co.n3fs.mc.haroldplugin.platform.spigot;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SchedulerImpl implements uk.co.n3fs.mc.haroldplugin.platform.Scheduler {
    private final Plugin plugin;
    private final BukkitScheduler scheduler;

    SchedulerImpl(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    public int runLater(Runnable task, long delay) {
        return scheduler.runTaskLaterAsynchronously(plugin, task, msToTicks(delay)).getTaskId();
    }

    @Override
    public int runRepeating(Runnable task, long delay, long interval) {
        return scheduler.runTaskTimerAsynchronously(plugin, task, msToTicks(delay), msToTicks(interval)).getTaskId();
    }

    @Override
    public int runLaterSync(Runnable task, long delay) {
        return scheduler.runTaskLater(plugin, task, msToTicks(delay)).getTaskId();
    }

    @Override
    public int runRepeatingSync(Runnable task, long delay, long interval) {
        return scheduler.runTaskTimer(plugin, task, msToTicks(delay), msToTicks(interval)).getTaskId();
    }

    @Override
    public boolean cancel(int id) {
        boolean present = scheduler.getPendingTasks().stream().anyMatch(task -> task.getTaskId() == id);
        if (present) {
            scheduler.cancelTask(id);
            return true;
        }

        return false;
    }

    private static long msToTicks(long ms) {
        return (long) Math.ceil(ms / 50);
    }
}
