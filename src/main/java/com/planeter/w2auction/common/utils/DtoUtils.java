package com.planeter.w2auction.common.utils;

import com.planeter.w2auction.dao.ItemDao;
import com.planeter.w2auction.dao.UserDao;
import com.planeter.w2auction.dto.ItemFront;
import com.planeter.w2auction.dto.OrderFront;
import com.planeter.w2auction.dto.UserFront;
import com.planeter.w2auction.entity.Item;
import com.planeter.w2auction.entity.OrderEntity;
import com.planeter.w2auction.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Planeter
 * @description jpa多表复杂关系难以维护,jpql @query(new dto)与 dao耦合,
 *              暂且使用工具类手动转换 dto
 *              TODO 整合mybatis-plus,直接映射为dto
 * @date 2021/4/27 13:02
 * @status dev
 */
@Component
public class DtoUtils {
    @Resource
    ItemDao itemDao;
    @Resource
    UserDao userDao;
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

    public static UserFront toUserFront(User user) {
        return new UserFront(user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getStatus(),
                user.getCreateTime(),
                user.getGender(),
                user.getImageId());
    }

    public OrderFront toOrderFront(OrderEntity order){
        return new OrderFront(order.getId(),
                order.getAddress(),
                order.getDeliverTime(),
                order.getPhone(),
                toItemFront(itemDao.getOne(order.getItemId())),
                order.getBuyerId(),
                order.isComplete());
    }
    public static OrderEntity toOrder(OrderFront front){
        return new OrderEntity(front.getId(),
                front.getAddress(),
                front.getDeliverTime(),
                front.getPhone(),
                front.getItem().getId(),
                front.getBuyerId(),
                front.isComplete()
                );
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
