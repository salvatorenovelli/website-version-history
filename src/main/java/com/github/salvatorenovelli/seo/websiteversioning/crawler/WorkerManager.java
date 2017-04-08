package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;


@Component
public class WorkerManager {


    private final WorkerRepository repository;

    public WorkerManager(WorkerRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Optional<Worker> getWorker(String workerId) {
        try {
            WorkerDTO one = repository.getOne(workerId);
            return Optional.of(new Worker(one.getId()));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}
