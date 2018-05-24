package com.lj.cloud.weixin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;
@Component
public class RedisBusiness {
	private static final Logger logger = LoggerFactory.getLogger(RedisBusiness.class);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public void setEx(String key, String value , int expire) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            connection.setEx(key.getBytes(), expire, value.getBytes());
        }catch (Exception e){
            logger.warn("redis 保存数据出现情况，现发起一次重复连接", e);
            if (connection != null)
                connection.close();
            connection = redisConnectionFactory.getConnection();
            connection.setEx(key.getBytes(), expire, value.getBytes());
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    public void del(String key) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            connection.del(key.getBytes());
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    public String get(String key) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        byte[] bytes = null;
        try {
            bytes = connection.get(key.getBytes());
        }finally {
            if (connection != null)
                connection.close();
        }
        if (bytes == null) {
            logger.warn("redis缓存无此key，可能是超时失效了");
            throw new Exception("redis 失效");
        }
        return  new String(bytes);
    }

    public void set(String key,String value) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            connection.set(key.getBytes(), value.getBytes());
        }finally {
            if (connection != null)
                connection.close();
        }
    }

}
