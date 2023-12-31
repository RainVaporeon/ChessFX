package io.github.rainvaporeon.chessfx.async;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncTaskThread extends Thread {
    private static final AsyncTaskThread MAIN = new AsyncTaskThread(1);

    static {
        MAIN.start();
    }

    /**
     * The polling rate of requests, in milliseconds.
     */
    private final long pollRate;

    private final Queue<AsyncRequest> requestQueue = new ArrayBlockingQueue<>(16);

    private static int threadId = 1;
    private AsyncTaskThread(long pollRate) {
        this.setName(STR."ChessFX/Async Task Thread #\{threadId++}");
        this.pollRate = pollRate;
    }

    public static void init() {}

    public static void submitTask(AsyncRequest request) {
        MAIN.requestQueue.offer(request);
    }

    public void supply(AsyncRequest request) {
        requestQueue.add(request);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void run() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::poll, 0, pollRate, TimeUnit.MILLISECONDS);
    }

    private void poll() {
        if(requestQueue.isEmpty()) return;
        requestQueue.poll().run();
    }
}
