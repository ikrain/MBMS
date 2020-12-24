package cn.krain.security;

import cn.krain.exception.TokenException;
import cn.krain.result.ResultUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author CC
 * @data 2020/12/15 - 18:32
 *
 * 1、用户登录请求登录接口时，验证用户名密码等，验证成功会返回给前端一个token，
 *      这个token就是之后鉴权的唯一凭证。
 *
 * 2、后台可能将token存储在redis或者数据库中。
 *
 * 3、之后前端的请求，需要在header中携带token，后端取出token去redis或者数据库中进行验证，
 *      如果验证通过则放行，如果不通过则拒绝操作。
 *
 */
@Component
public class TokenUtil {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 生成token
     * @return
     */
    public String getToKen(){
        return UUID.randomUUID()+"";
    }

    /**
     * 删除redis中的token
     * @param token
     */
    public void removeToken(String token){
        redisUtil.deleteKey(token);
    }

    /**
     * 通过token验证当前请求的用户是否处于登录状态
     * @param token
     * @return
     */
    @SneakyThrows
    public void authToken(String token){
        if (!redisUtil.hasKey(token)){
            System.out.println(redisUtil.hasKey(token));
            throw new TokenException("用户未登录，请登录");
        }
    }
}
