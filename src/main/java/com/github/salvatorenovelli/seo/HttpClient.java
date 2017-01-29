package com.github.salvatorenovelli.seo;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;

public class HttpClient {


    public Document get(URI path) throws IOException {

//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://targethost/homepage");
//        CloseableHttpResponse response1 = httpclient.execute(httpGet);


        try {
            Connection connect = Jsoup.connect(path.toASCIIString());
            return connect.get();

        } finally {

        }


    }
}
