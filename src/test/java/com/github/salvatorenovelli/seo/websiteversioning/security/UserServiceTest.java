package com.github.salvatorenovelli.seo.websiteversioning.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(SecurityContextHolder.getContext());
    }

    @Test
    @WithMockUser(username = "salvatore")
    public void shouldGetTheCurrentUser() {
        assertThat(userService.getCurrentUserName(), is("salvatore"));
    }
}