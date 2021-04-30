package com.planeter.w2auction.common.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

/**
 * @author Planeter
 * @description: JSON字符串响应结果处理
 * @date 2021/4/30 10:28
 * @status dev
 */
@JsonSerialize
public class XSSJacksonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(HtmlUtils.htmlEscape(s));
    }

}
