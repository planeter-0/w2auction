package com.planeter.w2auction.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/5 13:48
 * @status dev
 */
@Configuration
public class EsRestConfig {

    String ipPort = "127.0.0.1:9200";

    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(makeHttpHost(ipPort));
    }


    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }


    private HttpHost makeHttpHost(String s) {
        String[] address = s.split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);
        return new HttpHost(ip, port, "http");
    }
}
