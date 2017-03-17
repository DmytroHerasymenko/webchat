package ua.training.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import ua.training.config.DataBaseConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dima on 14.03.17.
 */
@Component
public class RedisDAO {
    Jedis connection;

    public RedisDAO(){
        connection = new Jedis(DataBaseConfig.REDIS_CONNECTION_DATABASE);
    }

    public List<String> getAllBroadcastMessages(){
        return connection.lrange("broadcast", 0, -1);
    }

    public void saveMessage(String login, String message){
        connection.lpush("broadcast", login + ":" + message);
    }
}
