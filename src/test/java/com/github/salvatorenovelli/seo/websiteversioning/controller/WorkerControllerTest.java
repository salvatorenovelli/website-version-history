package com.github.salvatorenovelli.seo.websiteversioning.controller;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class WorkerControllerTest {

    private static final String WORKER_ID1 = "ABCD1";
    private static final String WORKER_ID2 = "ABCD2";
    private MockMvc mvc;
    @Mock private WorkerManager workerManager;
    private Worker worker1 = new Worker(WORKER_ID1);
    private Worker worker2 = new Worker(WORKER_ID2);

    @Before
    public void setup() {

        mvc = MockMvcBuilders
                .standaloneSetup(new WorkerController(workerManager))
                .build();
    }


    @Test
    public void shouldListAllUserWorkers() throws Exception {

        when(workerManager.getUserWorkers()).thenReturn(Arrays.asList(worker1, worker2));

        mvc.perform(get("/api/worker/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(WORKER_ID1)))
                .andExpect(jsonPath("$[1].id", is(WORKER_ID2)));

    }

    @Test
    public void shouldAllowStartingOfWorker() throws Exception {
        mvc.perform(put("api/worker/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(WORKER_ID1)));
    }
}