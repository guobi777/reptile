package com.gpp.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gpp.api.entity.TbHuodongxiangqing;
import com.gpp.api.service.ShareArticleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ControllerAdvice(basePackages = "com.gpp.api.controller")
@SpringBootApplication
@EnableScheduling
@MapperScan("com.gpp.api.mapper")
public class ApiApplication {

    @Autowired
    private ShareArticleService shareArticleService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class, args);
    }

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "ok";
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //设置value的序列化方式为JSOn
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //设置key的序列化方式为String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    @ResponseBody
    @GetMapping("/redis/init")
    public Long init() {
        EntityWrapper<TbHuodongxiangqing> entityWrapper = new EntityWrapper<>();
        entityWrapper.setSqlSelect("title").groupBy("title");
        List<Object> titles = shareArticleService.selectObjs(entityWrapper);
        return redisTemplate.opsForSet().add("titles", titles.toArray());
    }

    @ResponseBody
    @GetMapping("/redis/delete")
    public Boolean delete() {
        return redisTemplate.delete("titles");
    }

    @ResponseBody
    @GetMapping("/redis/get")
    public List<Object> get() {
        EntityWrapper<TbHuodongxiangqing> entityWrapper = new EntityWrapper<>();
        entityWrapper.setSqlSelect("title").groupBy("title");
        return shareArticleService.selectObjs(entityWrapper);
    }

}
