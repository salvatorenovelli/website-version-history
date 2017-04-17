package com.github.salvatorenovelli.seo.websiteversioning.controller;

import com.github.salvatorenovelli.seo.websiteversioning.config.WebSecurityConfig;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.Crawler;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebSecurityConfig.class})
@WebAppConfiguration
public class WorkerManagerControllerTest {

    public static final String USER_NAME = "piero";
    @Autowired
    FilterChainProxy springSecurityFilterChain;
    private MockMvc mvc;
    @Mock private WorkerManager workerManager;
    @Mock private Crawler crawler;

    @Before
    public void setup() {

        when(workerManager.createWorkerForUser(USER_NAME)).thenReturn(new Worker("TEST_ID", crawler));

        mvc = MockMvcBuilders
                .standaloneSetup(new WorkerManagerController(workerManager))
                // .alwaysDo(MockMvcResultHandlers.print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }


    @Test
    @WithMockUser(username = "salvatore")
    public void shouldAllowOnlyAdmin() throws Exception {
        mvc.perform(post("/admin/api/create-worker").param("user", USER_NAME))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "salvatore", roles = {"ADMIN"})
    public void shouldAllowAdmin() throws Exception {
        mvc.perform(post("/admin/api/create-worker").param("user", USER_NAME))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "salvatore", roles = {"ADMIN"})
    public void shouldCreateWorker() throws Exception {
        mvc.perform(post("/admin/api/create-worker").param("user", USER_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("TEST_ID")))
                .andDo(MockMvcResultHandlers.print());
    }
}