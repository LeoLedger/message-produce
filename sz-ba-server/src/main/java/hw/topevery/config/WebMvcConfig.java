package hw.topevery.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import hw.topevery.converter.UniversalEnumConverter;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.enums.SourceTypeEnum;
import hw.topevery.enums.TargetTypeEnum;
import hw.topevery.framework.serializer.SqlDateSerializer;
import hw.topevery.framework.serializer.SqlTimeSerializer;
import hw.topevery.framework.serializer.SqlTimestampSerializer;
import hw.topevery.parser.DataTypeSerializer;
import hw.topevery.parser.SourceTypeSerializer;
import hw.topevery.parser.TargetTypeSerializer;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ：陆韦里
 * @date ：Created in 2019/11/20 11:26
 * @description：
 * @modified By：
 * @version:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("*")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new UniversalEnumConverter());
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2.添加fastjson的配置信息，比如是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializerFeature[] serializerFeatures = {
                SerializerFeature.PrettyFormat,
                // 是否输出为null的字段,若为null 则显示该字段
                SerializerFeature.WriteMapNullValue,
                // 禁止循环引用
                SerializerFeature.DisableCircularReferenceDetect
        };
        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        //3.在convert中添加配置信息

        fastJsonConfig.getSerializeConfig().put(java.sql.Date.class, new SqlDateSerializer());
        fastJsonConfig.getParserConfig().putDeserializer(java.sql.Date.class, new SqlDateSerializer());

        fastJsonConfig.getSerializeConfig().put(java.sql.Timestamp.class, new SqlTimestampSerializer());
        fastJsonConfig.getParserConfig().putDeserializer(java.sql.Timestamp.class, new SqlTimestampSerializer());

        fastJsonConfig.getSerializeConfig().put(java.sql.Time.class, new SqlTimeSerializer());
        fastJsonConfig.getParserConfig().putDeserializer(java.sql.Time.class, new SqlTimeSerializer());

        fastJsonConfig.getSerializeConfig().put(DataTypeEnum.class, new DataTypeSerializer());
        fastJsonConfig.getParserConfig().putDeserializer(DataTypeEnum.class, new DataTypeSerializer());

        fastJsonConfig.getSerializeConfig().put(SourceTypeEnum.class, new SourceTypeSerializer());
        fastJsonConfig.getParserConfig().putDeserializer(SourceTypeEnum.class, new SourceTypeSerializer());

        fastJsonConfig.getSerializeConfig().put(TargetTypeEnum.class, new TargetTypeSerializer());
        fastJsonConfig.getParserConfig().putDeserializer(TargetTypeEnum.class, new TargetTypeSerializer());

        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }
}
