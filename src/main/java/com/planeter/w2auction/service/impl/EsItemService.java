package com.planeter.w2auction.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.planeter.w2auction.common.utils.ESUtils;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
public class EsItemService {
    @Resource
    ESUtils esUtils;
    public List<Map<String, Object>> search(String key,Integer size, Integer from,String sortField,String sortOrder) throws IOException {
        MultiMatchQueryBuilder matchQuery= QueryBuilders.multiMatchQuery(key, "name", "detail", "tags").analyzer("ik_max_word");
        TermQueryBuilder termQuery = QueryBuilders.termQuery("verified", false);
        TermQueryBuilder terQuery_ = QueryBuilders.termQuery("verified", false);
        QueryBuilder totalFilter = QueryBuilders.boolQuery()
                .filter(matchQuery)
                .must(termQuery);
        SortOrder order = null;
        if(sortOrder.equals("ASC")){
            order = SortOrder.ASC;
        }else if(sortOrder.equals("DESC")){
            order = SortOrder.DESC;
        }
        return esUtils.searchListData("item",
                new SearchSourceBuilder().query(totalFilter),
                size,
                from,
                "",
                sortField,
                order,
                "");
    }
    public String add(ItemFront item){
        String ret = "";
        try {
            ret = esUtils.addData(JSON.parseObject(JSONObject.toJSONString(item)), "item", String.valueOf(item.getId()));
        } catch(Exception e){
            log.warn("Elasticsearch ADD failed",e);
        }
        return ret;
    }
    public void delete(Long id)  {
        try {
            esUtils.deleteDataById("item", String.valueOf(id));
        } catch (Exception e){
            log.warn("Elasticsearch DELETE failed",e);
        }
    }
    public void update(ItemFront item) {
        try {
            esUtils.updateDataById(item, "item", String.valueOf(item.getId()));
        } catch (Exception e){
            log.warn("Elasticsearch DELETE failed",e);
        }
    }
}
