package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class WorkerManagerTest {

    @Autowired
    WorkerManager workerManager;

    @Bean
    WorkerManager getBean(){
        return new WorkerManager();
    }

    @Test
    @WithMockUser(username = "salvatore", authorities = {"USER"})
    public void shouldGetCurrentUserWorkers() throws Exception {



        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Test
    public void shouldNotAllowNull() throws Exception {
        workerManager.getWorkersFor(null);
    }
}