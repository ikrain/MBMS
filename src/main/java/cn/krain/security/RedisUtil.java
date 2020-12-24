package cn.krain.security;

import cn.krain.util.RedisTemplateInit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author CC
 * @data 2020/12/16 - 9:39
 */
@Component
public class RedisUtil {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加redis值
     * @param key
     * @param object
     */
    public void addKey(String key, Object object){
        RedisTemplateInit.redisTemplateChange(redisTemplate).
                opsForValue().set(key,object, Duration.ofMinutes(30L));
    }

    /**
     * 删除redis值
     * @param key
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
}
