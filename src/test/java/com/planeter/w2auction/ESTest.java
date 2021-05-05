package com.planeter.w2auction;

import com.planeter.w2auction.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/5 11:32
 * @status dev
 */
@Slf4j
@SpringBootTest
public class ESTest {

    @Resource
    private ElasticsearchRestTemplate template;


    @Test
    public void testCreateIndexAndDoc() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        String index = "user";
        //1.创建索引请求
        org.elasticsearch.client.indices.CreateIndexRequest request = new CreateIndexRequest(index);
        //2.执行客户端请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        log.info("创建索引{}成功",index);
        System.out.println(response.isAcknowledged());
    }

//    @Test
//    public void saveDoc() throws IOException {
//        String index = "1";
//
//        Item item = new Item();
//        item.setName("树莓派");
//        item.setDetail("这是一条详情");
//        item.setTags("数码,电子,计算机");
//        //创建请求
//        IndexRequest request = new IndexRequest("item");
//
//        //规则 put /test_index/_doc/1
//        request.id(index);
//        request.timeout(TimeValue.timeValueSeconds(1));
//        //将数据放入请求 json
//        request.source(item, XContentType.JSON);
//        //客户端发送请求
//        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
//        log.info("添加数据成功 索引为: {}, response 状态: {}, id为: {}", index, response.status().getStatus(), response.getId());
//        log.info("=========={}", response.getId());
//    }
}

