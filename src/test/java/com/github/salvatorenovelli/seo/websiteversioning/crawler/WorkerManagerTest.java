package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
public class WorkerManagerTest {

    @Autowired
    WorkerManager workerManager;




    @Test
    @WithMockUser(username = "salvatore", authorities = {"USER"})
    public void shouldGetCurrentUserWorkers() throws Exception {



        WorkerManager workerManager = new WorkerManager();
        List<Worker> userWorker = workerManager.getUserWorker();

        assertThat(userWorker, Matchers.hasItems(new Worker("sdf")));

    }
}