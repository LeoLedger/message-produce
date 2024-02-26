package hw.topevery.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import hw.topevery.enums.SourceTypeEnum;
import hw.topevery.enums.TargetTypeEnum;

import java.lang.reflect.Type;

public class SourceTypeSerializer implements ObjectSerializer, ObjectDeserializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        TargetTypeEnum type =  (TargetTypeEnum) object;
        serializer.out.writeInt(type.getValue());
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        Integer value = defaultJSONParser.parseObject(Integer.TYPE);
        for (SourceTypeEnum val : SourceTypeEnum.values()) {
            if (val.getValue().equals(value)) {
                // 成功匹配，返回实例
                return (T) val;
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
