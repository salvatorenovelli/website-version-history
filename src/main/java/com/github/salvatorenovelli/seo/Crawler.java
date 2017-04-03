package com.github.salvatorenovelli.seo;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class Crawler {

    private final Map<String, Integer> consumedLinks = new ConcurrentHashMap<>();


    public void crawl(URI uri, Consumer<URI> uriMapper) throws InterruptedException {

        ForkJoinPool pool = new ForkJoinPool(8);

        pool.invoke(new PageCrawler(uri, uriMapper));

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        //System.out.println("Took " + (System.currentTimeMillis() - start) + " millis to complete!");

    }

    class PageCrawler extends RecursiveTask<Void> {

        private final URI uri;
        private final Consumer<URI> uriMapper;

        public PageCrawler(URI uri, Consumer<URI> uriMapper) {
            this.uri = uri;
            this.uriMapper = uriMapper;
        }

        @Override
        protected Void compute() {
            try {
                uriMapper.accept(uri);
                StreamSupport.stream(Jsoup.connect(uri.toString()).get().select("a").spliterator(), true)
                        .map(element -> element.attr("href"))
                        .filter(this::isCurrentDomain)
                        .forEach(s -> consumedLinks.computeIfAbsent(s, s1 -> {
                            System.out.println("Discovered:" + s + " in " + uri);
                            new PageCrawler(URI.create(s1), uriMapper).fork();
                            return 1;
                        }));
            } catch (IOException e) {
                System.out.println("Error while crawling: " + uri + ": " + e.toString());
            }
            return null;
        }

        private boolean isCurrentDomain(String s) {
            return URI.create(s).getHost().equals(uri.getHost());
        }

        private boolean isCurrentDomain(URI element) {
            return false;
        }
    }
}
