package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class WorkerManager {


    private final WorkerRepository repository;

    private final Map<String, Worker> instances = new ConcurrentHashMap<>();

    public WorkerManager(WorkerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Worker createWorker() {
        WorkerDTO save = repository.save(new WorkerDTO(UUID.randomUUID().toString()));
        return getWorker(save.getId()).get();
    }

    @Transactional
    public Optional<Worker> getWorker(String workerId) {
        if (repository.exists(workerId)) {
            return Optional.of(instances.computeIfAbsent(workerId, Worker::new));
        } else {
            return Optional.empty();
        }
    }
}
