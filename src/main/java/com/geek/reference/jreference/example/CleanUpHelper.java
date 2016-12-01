package com.geek.reference.jreference.example;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CleanUpHelper {

    private CleanUpHelper(){}

    private static volatile boolean started = false;

    private static final int SLEEP_TIME = 10;

    private static final Map<Reference<Object>, CleanUp> MAPS = new ConcurrentHashMap<Reference<Object>, CleanUp>();

    private static final ReferenceQueue<Object> REFERENCE_QUEUE = new ReferenceQueue<Object>();

    public static void register(Object watcher, CleanUp cleanUp) {
        init();
        MAPS.put(new PhantomReference<Object>(watcher, REFERENCE_QUEUE), cleanUp);
    }

    private static void init() {
        if (!started) {
            synchronized (CleanUpHelper.class) {
                if (!started) {
                    CLEAN_UP_THREAD.setName("CleanUpThread");
                    CLEAN_UP_THREAD.setDaemon(true);
                    CLEAN_UP_THREAD.start();
                    started = true;
                }
            }
        }
    }
    
    private static final Thread CLEAN_UP_THREAD = new Thread(new Runnable() {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Object target = REFERENCE_QUEUE.poll();
                    if (target != null) {
                        CleanUp cleanUp = MAPS.remove(target);
                        if (cleanUp != null) {
                            cleanUp.cleanUp();
                            continue;
                        }
                    }
                } catch (RuntimeException ignore) {
                    //add logs
                }

                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    });
}