package com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j;

import com.github.salvatorenovelli.seo.websiteversioning.domain.PatternGenerator;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

class DefaultWebCrawler extends WebCrawler {

    private final Pattern patter;
    private final String id;

    DefaultWebCrawler(String baseDomain, String id) {
        patter = PatternGenerator.generatePatternFor(baseDomain);
        this.id = id;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return patter.matcher(url.getURL()).matches();
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);
//
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//
//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());
//        }
    }

    public String getId() {
        return id;
    }

}
