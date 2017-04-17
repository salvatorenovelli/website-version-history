package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerDTO;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class WorkerManagerController {


    private final WorkerManager workerManager;

    public WorkerManagerController(WorkerManager workerManager) {
        this.workerManager = workerManager;
    }

    @PostMapping("/create-worker")
    public WorkerDTO createWorker(@RequestParam String user) {
        return new WorkerDTO(workerManager.createWorkerForUser(user));
    }
}
