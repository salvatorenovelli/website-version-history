package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WorkerTest {

    Worker worker;
    @Mock private Crawler crawler;

    @Before
    public void setUp() throws Exception {
        worker = new Worker("1234", crawler);
    }

    @Test
    public void getId() throws Exception {
        assertThat(worker.getId(), is("1234"));
    }

    @Test
    public void startCrawling() throws Exception {
        worker.startCrawling("testUrl");
        verify(crawler).startCrawling("testUrl");
    }

}