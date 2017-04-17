package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import com.github.salvatorenovelli.seo.websiteversioning.domain.DefaultWorkerFactory;
import com.github.salvatorenovelli.seo.websiteversioning.domain.WorkerFactory;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class WorkerManagerTest {


    private WorkerFactory workerFactory = new DefaultWorkerFactory();
    private WorkerManager workerManager = new WorkerManager(workerFactory);

    @Test
    public void shouldCreateWorker() throws Exception {
        Worker worker = workerManager.createWorkerForUser("user");
        assertNotNull(worker);
    }


    @Test
    public void workersShouldBeCreatedPerUser() throws Exception {
        Worker worker = workerManager.createWorkerForUser("testUser");
        List<Worker> testUser = workerManager.getWorkersFor("testUser");

        assertThat(testUser, Matchers.hasSize(1));
        assertThat(testUser, hasItems(worker));
    }

    @Test
    public void shouldNotReturnWorkersForOtherUsers() throws Exception {
        Worker workerForuser1 = workerManager.createWorkerForUser("user1");
        Worker workerForuser2 = workerManager.createWorkerForUser("user2");

        List<Worker> workers1 = workerManager.getWorkersFor("user1");
        List<Worker> workers2 = workerManager.getWorkersFor("user2");

        assertThat(workers1, Matchers.hasSize(1));
        assertThat(workers1, hasItems(workerForuser1));

        assertThat(workers2, Matchers.hasSize(1));
        assertThat(workers2, hasItems(workerForuser2));
    }

}