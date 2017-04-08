package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


@DataJpaTest
@RunWith(SpringRunner.class)
public class WorkerManagerTest {


    private static final String CRAWLER_ID = "ABCD";
    @Autowired
    private WorkerRepository repository;
    private WorkerManager workerManager;

    @Before
    public void setUp() throws Exception {
        repository.save(new WorkerDTO(CRAWLER_ID));
        workerManager = new WorkerManager(repository);
    }

    @Test
    public void shouldReturnTheCrawlerIfItExist() throws Exception {
        Optional<Worker> crawler = workerManager.getWorker(CRAWLER_ID);
        assertTrue(crawler.isPresent());
        assertThat(crawler.get().getId(), is(CRAWLER_ID));
    }

    @Test
    public void shouldReturnNoneIfTheCrawlerDoesNotExist() throws Exception {
        Optional<Worker> crawler = workerManager.getWorker("ZZZZZZZ");
        assertFalse(crawler.isPresent());
    }

    @Test
    public void onceCreatedItShouldAlwaysRetrieveTheSameInstance() throws Exception {
        Optional<Worker> worker1 = workerManager.getWorker(CRAWLER_ID);
        Optional<Worker> worker2 = workerManager.getWorker(CRAWLER_ID);

        assertEquals(worker1, worker2);

    }
}