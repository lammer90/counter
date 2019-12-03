package com.example.counter;

import com.example.counter.util.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.valueOf("text/plain;charset=UTF-8"));
        supportedMediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof org.springframework.http.converter.json.MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(JacksonObjectMapper.getMapper());
            }
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setSupportedMediaTypes(supportedMediaTypes);
            }
        }
    }
}
