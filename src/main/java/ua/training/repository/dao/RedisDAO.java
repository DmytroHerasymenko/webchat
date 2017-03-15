package ua.training.repository.dao;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dima on 14.03.17.
 */
@Component
@PropertySource("classpath:messages_en.properties")
public class RedisDAO {
    Jedis connection;

    @Resource
    Environment environment;

    public RedisDAO(){
        connection = new Jedis(environment.getProperty("jedis.url"));
    }

    public List<String> getAllBroadcastMessages(){
        return connection.lrange("broadcast", 0, -1);
    }

    public void saveMessage(String login, String message){
        connection.lpush("broadcast", login + ":" + message);
    }
}
