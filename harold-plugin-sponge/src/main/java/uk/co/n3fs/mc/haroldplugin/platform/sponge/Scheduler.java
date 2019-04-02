package uk.co.n3fs.mc.haroldplugin.platform.sponge;

import org.spongepowered.api.scheduler.Task;
import uk.co.n3fs.mc.haroldplugin.platform.Scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Scheduler implements uk.co.n3fs.mc.haroldplugin.platform.Scheduler {
    private Bootstrap plugin;
    private Map<Integer, Task> tasks;

    Scheduler(Bootstrap plugin) {
        this.plugin = plugin;
        this.tasks = new HashMap<>();
    }

    @Override
    public int runLater(Runnable task, long delay) {
        Task schedulerTask = Task.builder()
            .async()
            .delay(delay, TimeUnit.MILLISECONDS)
            .execute(spongeTask -> {
                task.run();
                tasks.remove(idFromTask(spongeTask));
            })
            .submit(plugin);

        int id = idFromTask(schedulerTask);
        tasks.put(id, schedulerTask);
        return id;
    }

    @Override
    public int runRepeating(Runnable task, long delay, long interval) {
        Task schedulerTask = Task.builder()
            .async()
            .delay(delay, TimeUnit.MILLISECONDS)
            .interval(interval, TimeUnit.MILLISECONDS)
            .execute(task)
            .submit(plugin);

        int id = idFromTask(schedulerTask);
        tasks.put(id, schedulerTask);
        return id;
    }

    @Override
    public int runLaterSync(Runnable task, long delay) {
        Task schedulerTask = Task.builder()
            .delay(delay, TimeUnit.MILLISECONDS)
            .execute(spongeTask -> {
                task.run();
                tasks.remove(idFromTask(spongeTask));
            })
            .submit(plugin);

        int id = idFromTask(schedulerTask);
        tasks.put(id, schedulerTask);
        return id;
    }

    @Override
    public int runRepeatingSync(Runnable task, long delay, long interval) {
        Task schedulerTask = Task.builder()
            .delay(delay, TimeUnit.MILLISECONDS)
            .interval(interval, TimeUnit.MILLISECONDS)
            .execute(task)
            .submit(plugin);

        int id = idFromTask(schedulerTask);
        tasks.put(id, schedulerTask);
        return id;
    }

    @Override
    public boolean cancel(int id) {
        if (tasks.containsKey(id)) {
            tasks.get(id).cancel();
            return true;
        }

        return false;
    }

    private int idFromTask(Task task) {
        return task.getUniqueId().hashCode();
    }
}
