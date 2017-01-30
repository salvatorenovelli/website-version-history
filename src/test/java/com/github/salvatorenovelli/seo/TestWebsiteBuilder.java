package com.github.salvatorenovelli.seo;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

class TestWebsiteBuilder {


    private static final Logger logger = LoggerFactory.getLogger(TestWebsiteBuilder.class);
    private static Server server;
    Map<String, PageFields> pages = new HashMap<>();
    PageFields curPage = null;

    private TestWebsiteBuilder(Server server) {
        TestWebsiteBuilder.server = server;
    }

    public static TestWebsiteBuilder givenAWebsite() {
        if (TestWebsiteBuilder.server != null) {
            throw new IllegalStateException("Please use tearDownCurrentServer() in @After/ tearDown() for test isolation.");
        }
        return new TestWebsiteBuilder(new Server(0));
    }

    public static URI testUri(String url) throws URISyntaxException {
        if (!server.isStarted()) {
            throw new IllegalStateException("Sorry, you'll need to run the scenario before asking for URI. (At the moment the server port is not known)");
        }
        int localPort = ((ServerConnector) server.getConnectors()[0]).getLocalPort();
        return new URI("http://localhost:" + localPort + url);
    }


    public static void tearDownCurrentServer() throws Exception {
        server.stop();
        server = null;
    }

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

    public void run() throws Exception {
        if (curPage != null) {
            pages.putIfAbsent(curPage.getPagePath(), curPage);
        }
        server.setHandler(new Handler());
        server.start();
        logger.info("Test server listening on http://localhost:{}", ((ServerConnector) server.getConnectors()[0]).getLocalPort());
    }

    private static class PageFields {
        private final String pagePath;
        private String title;


        public PageFields(String pagePath) {
            this.pagePath = pagePath;
        }

        public String getPagePath() {
            return pagePath;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private class Handler extends AbstractHandler {
        @Override
        public void handle(String s, Request request,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) throws IOException, ServletException {

            logger.info("Received request for path '{}'. {}", s, request);
            PageFields pageFields = pages.get(s);
            if (pageFields != null) {
                servePage(pageFields, httpServletResponse);
            }


        }

        private void servePage(PageFields pageFields, HttpServletResponse response) {
            response.setStatus(HttpServletResponse.SC_OK);

            try (OutputStream outputStream = response.getOutputStream()) {
                IOUtils.write(renderPage(pageFields), outputStream, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private StringBuffer renderPage(PageFields pageFields) {
            StringBuffer sb = new StringBuffer();

            sb.append("<HTML>");
            sb.append("    <HEAD>");
            sb.append("        <TITLE>").append(pageFields.getTitle()).append("</TITLE>");
            sb.append("    </HEAD>");
            sb.append("    <BODY>");
            sb.append("        <H1>").append(pageFields.getTitle()).append("</H1>");
            sb.append("    </BODY>");
            sb.append("</HTML>");

            return sb;

        }
    }
}
