package com.github.salvatorenovelli.seo;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HtmlReaderTest {


    public static final String TEST_TITLE = "Test title";
    private HtmlReader sut;

    @Before
    public void setUp() throws Exception {
        sut = new HtmlReader();


    }

    @Test
    public void shouldRetrieveTitle() throws Exception {

        givenAWebsite()
                .havingPage("/")
                .withTitle(TEST_TITLE)
                .run();


        PageSnapshot snapshot = sut.snapshotPage(testUri("/"));
        assertThat(snapshot.getTitle(), is(TEST_TITLE));

    }

    private String testUri(String s) {
        return null;
    }

    private TestWebsiteBuilder givenAWebsite() {
        return new TestWebsiteBuilder();
    }


    private static class PageFields {
        private final String pagePath;
        private String title;


        public PageFields(String pagePath) {
            this.pagePath = pagePath;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPagePath() {
            return pagePath;
        }
    }

    private class TestWebsiteBuilder {

        Map<String, PageFields> pages = new HashMap<>();
        PageFields curPage;

        public TestWebsiteBuilder havingPage(String pagePath) {
            curPage = new PageFields(pagePath);
            return this;
        }

        public TestWebsiteBuilder havingRootPage() {
            return havingPage("/");
        }

        public TestWebsiteBuilder withTitle(String s) {
            curPage.setTitle(s);
            return this;
        }

        public void run() {
            pages.putIfAbsent(curPage.getPagePath(), curPage);
        }
    }


}