package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import com.github.salvatorenovelli.seo.websiteversioning.WorkerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class WorkerManager {


    private final Map<String, List<Worker>> userWorkers = new ConcurrentHashMap<>();
    private final WorkerFactory workerFactory;

    public WorkerManager(WorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }

    public List<Worker> getWorkersFor(@NotNull String username) {
        return userWorkers.computeIfAbsent(username, s -> new ArrayList<>());
    }

    public Worker createWorkerForUser(String user) {
        Worker worker = workerFactory.createWorker();
        addWorkerToUser(user, worker);
        return worker;
    }

    public List<Worker> getWorkersFor(Principal principal) {
        return getWorkersFor(principal.getName());
    }

    private void addWorkerToUser(String username, Worker worker) {
        userWorkers.computeIfAbsent(username, s -> new ArrayList<>())
                .add(worker);

    }
}
