package com.planeter.w2auction.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.planeter.w2auction.common.utils.ESUtils;
import com.planeter.w2auction.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/5 11:05
 * @status dev
 */
@Service
@Slf4j
public class ESItemService {
    @Resource
    ESUtils esUtils;
    public List<Map<String, Object>> search(String key) throws IOException {
        MultiMatchQueryBuilder matchQuery= QueryBuilders.multiMatchQuery(key, "name", "detail", "tags").analyzer("ik_max_word");
        TermQueryBuilder termQuery = QueryBuilders.termQuery("verified", true);
        QueryBuilder totalFilter = QueryBuilders.boolQuery()
                .filter(matchQuery)
                .must(termQuery);
        return esUtils.searchListData("item",new SearchSourceBuilder().query(totalFilter),100,-1,"","name","name");
    }
    public String add(Item item) throws IOException {
         return esUtils.addData(JSON.parseObject(JSONObject.toJSONString(item)),"item",String.valueOf(item.getId()));
    }
    public void delete(Long id) throws IOException {
        esUtils.deleteDataById("item",String.valueOf(id));
    }
    public void update(Item item) throws IOException {
        esUtils.updateDataById(item, "item", String.valueOf(item.getId()));
    }
}
