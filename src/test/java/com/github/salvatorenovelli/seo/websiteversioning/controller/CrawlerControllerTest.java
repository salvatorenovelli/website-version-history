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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class CrawlerControllerTest {

    public static final String VALID_KEY = "465783snsdujnis";
    private static final String WORKER_ID = "ABCD";
    private MockMvc mvc;
    @Mock private WorkerManager workerManager;
    private Worker worker = new Worker(WORKER_ID);

    @Before
    public void setup() {
        when(workerManager.createWorker()).thenReturn(worker);
        mvc = MockMvcBuilders
                .standaloneSetup(new CrawlerController(workerManager, VALID_KEY))
                .build();
    }

    @Test
    public void shouldAllowCreationWithRightSecretKey() throws Exception {
        mvc.perform(put("/worker/" + VALID_KEY + "/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(WORKER_ID)));
    }

    @Test
    public void shouldNotAllowCreationWithWrongSecretKey() throws Exception {
        mvc.perform(put("/worker/+" + "wrongKey" + "/create"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void startCrawling() throws Exception {
        mvc.perform(put("/worker/" + WORKER_ID + "/start?url=snf"))
                .andExpect(status().isOk());
    }

}