package com.cn.rmq.api.utils.json.serializer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * <p>Title: DateTimeSerializer</p>
 * <p>Description: 自定义返回JSON时间格式化处理 </p>
 */
public class DateTimeSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (date != null) {
            jsonGenerator.writeString(DateUtil.formatDateTime(date));
        } else {
            jsonGenerator.writeString("");
        }
    }
}
