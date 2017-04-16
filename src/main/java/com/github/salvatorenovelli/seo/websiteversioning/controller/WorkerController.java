package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerDTO;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/worker")
public class WorkerController {


    private final WorkerManager workerManager;

    public WorkerController(WorkerManager workerManager) {
        this.workerManager = workerManager;
    }

    @GetMapping("/list")
    public List<WorkerDTO> listAvailable(Principal principal) {
        System.out.println("principal = " + principal);
        return Collections.emptyList();
    }

    @PutMapping("/create")
    public ResponseEntity<WorkerDTO> createWorker() {
        return ResponseEntity.ok(new WorkerDTO(workerManager.createWorker().getId()));
    }

    @PutMapping("{workerId}/start")
    public CrawlStartResponse startWorker(Principal principal, @PathVariable String workerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting worker " + workerId + " " + url);
    }

    @PutMapping("{workerId}/stop")
    public CrawlStartResponse stopWorker(@PathVariable String workerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting worker " + workerId + " " + url);
    }
}
