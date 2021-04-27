package com.planeter.w2auction.common.utils;

import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.entity.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Planeter
 * @description jpa表关系炸了,jpql select不到,手动new dto TODO mybatis
 * @date 2021/4/27 13:02
 * @status dev
 */
public class DtoUtils {
    public static ItemFront toItemFront(Item item){
        return new ItemFront(item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDetail(),
                item.getCreated(),
                item.getUsername(),
                stringParser(item.getTags()),
                item.isVerified(),
                item.isSold(),
                longParser(item.getImageIds()));
    }
    public static Item toItem(ItemFront item){
        return new Item(item.getId(),
                item.getName(),
                item.getPrice(),
                item.getDetail(),
                item.getCreated(),
                item.getUsername(),
                stringListParser(item.getTags()),
                item.isVerified(),
                item.isSold(),
                longListParser((item.getImageIds())));
    }
    public static List<Long> longParser(String s){
        List<Long> ret = new ArrayList<>();

        for (String val : s.split(",")) {
            ret.add(Long.parseLong(val));
        }
        return ret;
    }
    public static String longListParser(List<Long> list){
        StringBuilder builder = new StringBuilder();
        for (Long val : list) {
            builder.append(val).append(",");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }
    public static List<String> stringParser(String s){
        return Arrays.asList(s.split(","));
    }
    public static String stringListParser(List<String> list){
        StringBuilder builder = new StringBuilder();
        for (String val : list) {
            builder.append(val).append(",");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }
}
