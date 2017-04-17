package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerDTO;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.springframework.http.HttpStatus;
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
    public List<String> listAvailable(Principal principal) {
        return workerManager.getWorkersFor(principal).stream().map(Worker::getId).collect(Collectors.toList());
    }

    @GetMapping("{workerId}/status")
    public WorkerDTO getWorkerStatus(Principal principal, @PathVariable String workerId) {
        return workerManager.getWorkersFor(principal).stream().filter(worker -> worker.getId().equals(workerId))
                .findFirst()
                .map(WorkerDTO::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping("{workerId}/start")
    public void startWorker(Principal principal, @PathVariable String workerId, @RequestParam String url) {
        List<Worker> workersFor = workerManager.getWorkersFor(principal);
        workersFor.stream().filter(worker -> worker.getId().equals(workerId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new)
                .startCrawling(url);
    }

    @PutMapping("{workerId}/stop")
    public void stopWorker(@PathVariable String workerId, @RequestParam String url) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class ResourceNotFoundException extends RuntimeException {
    }
}
