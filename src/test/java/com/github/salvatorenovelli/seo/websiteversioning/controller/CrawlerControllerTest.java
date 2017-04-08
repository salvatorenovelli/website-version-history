package com.github.salvatorenovelli.seo.websiteversioning.controller;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
public class CrawlerControllerTest {

    private MockMvc mvc;
    @Mock private WorkerManager workerManager;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(new CrawlerController(workerManager))
                .build();
    }


    @Test
    public void startCrawling() throws Exception {
        mvc.perform(put("/crawler/abcd/start?url=snf"))
                .andExpect(status().isOk());
    }

}