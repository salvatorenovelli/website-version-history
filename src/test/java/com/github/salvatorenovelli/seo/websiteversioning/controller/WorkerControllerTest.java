package com.github.salvatorenovelli.seo.websiteversioning.controller;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class WorkerControllerTest {

    public static final String TEST_URL = "http://example.com";
    private MockMvc mvc;
    private WorkerManager workerManager = new WorkerManager(() -> Mockito.spy(new Worker(UUID.randomUUID().toString())));
    @Mock private Principal currentPrincipal;
    private Worker worker1;
    private Worker worker2;

    @Before
    public void setup() {
        when(currentPrincipal.getName()).thenReturn("testUser");
        worker1 = workerManager.createWorkerForUser("testUser");
        worker2 = workerManager.createWorkerForUser("testUser");

        mvc = MockMvcBuilders
                .standaloneSetup(new WorkerController(workerManager))
                .build();
    }


    @Test
    public void shouldListAllUserWorkers() throws Exception {
        mvc.perform(get("/api/worker/list").principal(currentPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(worker1.getId())))
                .andExpect(jsonPath("$[1]", is(worker2.getId())));
    }

    @Test
    public void shouldAllowStartingOfValidWorker() throws Exception {
        mvc.perform(
                put("/api/worker/" + worker1.getId() + "/start")
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
        mvc.perform(get("/api/worker/" + worker1.getId() + "/status").principal(currentPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(worker1.getId())));
    }


    @Test
    public void shouldReturn404IfWorkerIsNotPresent() throws Exception {
        mvc.perform(get("/api/worker/" + "WRONG_ID" + "/status").principal(currentPrincipal))
                .andExpect(status().isNotFound());
    }
}