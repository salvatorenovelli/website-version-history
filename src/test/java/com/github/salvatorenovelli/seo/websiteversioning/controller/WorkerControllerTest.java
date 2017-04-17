package com.github.salvatorenovelli.seo.websiteversioning.controller;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class WorkerControllerTest {

    public static final String TEST_URL = "http://example.com";
    private static final String WORKER_ID1 = "ABCD1";
    private static final String WORKER_ID2 = "ABCD2";
    private MockMvc mvc;
    @Mock private WorkerManager workerManager;
    @Mock private Principal currentPrincipal;
    @Spy private Worker worker1 = new Worker(WORKER_ID1);
    @Spy private Worker worker2 = new Worker(WORKER_ID2);

    @Before
    public void setup() {
        when(workerManager.getWorkersFor(currentPrincipal)).thenReturn(Arrays.asList(worker1, worker2));
        mvc = MockMvcBuilders
                .standaloneSetup(new WorkerController(workerManager))
                .build();
    }


    @Test
    public void shouldListAllUserWorkers() throws Exception {
        mvc.perform(get("/api/worker/list").principal(currentPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(WORKER_ID1)))
                .andExpect(jsonPath("$[1]", is(WORKER_ID2)));
    }

    @Test
    public void shouldAllowStartingOfValidWorker() throws Exception {
        mvc.perform(
                put("/api/worker/" + WORKER_ID1 + "/start")
                        .principal(currentPrincipal)
                        .param("url", TEST_URL))
                .andExpect(status().isOk());
        verify(worker1).startCrawling(TEST_URL);
    }

    @Test
    public void shouldReturn404InCaseWorkerIsNotFoundForCurrentUser() throws Exception {
        mvc.perform(
                put("/api/worker/" + "WRONG_ID" + "/start")
                        .principal(currentPrincipal)
                        .param("url", TEST_URL))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetWorkerStatus() throws Exception {
        mvc.perform(get("/api/worker/" + WORKER_ID1 + "/status").principal(currentPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(WORKER_ID1)));
    }


    @Test
    public void shouldReturn404IfWorkerIsNotPresent() throws Exception {
        mvc.perform(get("/api/worker/" + "WRONG_ID" + "/status").principal(currentPrincipal))
                .andExpect(status().isNotFound());
    }
}