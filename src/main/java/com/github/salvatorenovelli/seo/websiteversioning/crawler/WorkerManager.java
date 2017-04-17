package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Collections;
import java.util.List;


@Component
public class WorkerManager {
    public List<Worker> getWorkersFor(@NotNull Principal principal) {
        System.out.println(principal.getName());
        return Collections.emptyList();
    }
}
