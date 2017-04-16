package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class WorkerManagerTest {

    @Autowired
    WorkerManager workerManager;

    @Test
    @WithMockUser(username = "salvatore", authorities = {"USER"})
    public void shouldGetCurrentUserWorkers() throws Exception {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}