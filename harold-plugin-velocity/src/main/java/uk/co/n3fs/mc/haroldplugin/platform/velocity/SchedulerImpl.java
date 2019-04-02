package uk.co.n3fs.mc.haroldplugin.platform.velocity;

import com.velocitypowered.api.scheduler.ScheduledTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SchedulerImpl implements uk.co.n3fs.mc.haroldplugin.platform.Scheduler {
    private Bootstrap plugin;
    private final com.velocitypowered.api.scheduler.Scheduler velocityScheduler;
    private final Map<Integer, ScheduledTask> tasks = new HashMap<>();

    SchedulerImpl(Bootstrap plugin, com.velocitypowered.api.scheduler.Scheduler velocityScheduler) {
        this.plugin = plugin;
        this.velocityScheduler = velocityScheduler;
    }

    @Override
    public int runLater(Runnable task, long delay) {
        ScheduledTask schedulerTask = velocityScheduler.buildTask(plugin, task)
            .delay(delay, TimeUnit.MILLISECONDS)
            .schedule();

        int id = nextId();
        tasks.put(id, schedulerTask);
        return id;
    }

    @Override
    public int runRepeating(Runnable task, long delay, long interval) {
        ScheduledTask schedulerTask = velocityScheduler.buildTask(plugin, task)
            .delay(delay, TimeUnit.MILLISECONDS)
            .repeat(interval, TimeUnit.MILLISECONDS)
            .schedule();

        int id = nextId();
        tasks.put(id, schedulerTask);
        return id;
    }

    @Override
    public int runLaterSync(Runnable task, long delay) {
        return runLater(task, delay);
    }

    @Override
    public int runRepeatingSync(Runnable task, long delay, long interval) {
        return runRepeating(task, delay, interval);
    }

    @Override
    public boolean cancel(int id) {
        if (tasks.containsKey(id)) {
            tasks.get(id).cancel();
            return true;
        }

        return false;
    }

    private int nextId() {
        int id;
        do {
            id = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        } while (!tasks.containsKey(id));
        return id;
    }
}
