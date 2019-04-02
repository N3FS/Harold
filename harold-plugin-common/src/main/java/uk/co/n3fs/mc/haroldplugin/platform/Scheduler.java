package uk.co.n3fs.mc.haroldplugin.platform;

public interface Scheduler {
    int runLater(Runnable task, long delay);

    int runRepeating(Runnable task, long delay, long interval);

    int runLaterSync(Runnable task, long delay);

    int runRepeatingSync(Runnable task, long delay, long interval);

    boolean cancel(int id);
}
