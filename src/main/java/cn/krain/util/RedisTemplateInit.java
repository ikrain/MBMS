package cn.krain.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author CC
 * @data 2020/12/15 - 20:31
 */
public class RedisTemplateInit {

    /**
     * 修改Redis序列化方式，解决Redis可视化工具乱码问题
     * @param redisTemplate
     * @return
     */
    public static RedisTemplate redisTemplateChange(RedisTemplate<String, Object> redisTemplate) {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
