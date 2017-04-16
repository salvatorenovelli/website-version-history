package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerDTO;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    private final WorkerManager workerManager;

    public WorkerController(WorkerManager workerManager) {
        this.workerManager = workerManager;
    }

    @GetMapping("/list")
    public List<WorkerDTO> listAvailable() {
        return workerManager.getUserWorkers().stream().map(WorkerDTO::new).collect(Collectors.toList());
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
