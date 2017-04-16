package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Collections;
import java.util.List;


@Component
public class WorkerManager {

    public List<Worker> getWorkersFor(Principal principal) {
        return Collections.emptyList();
    }
}
