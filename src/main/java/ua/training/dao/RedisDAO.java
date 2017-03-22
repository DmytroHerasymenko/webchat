package ua.training.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import ua.training.config.DataBaseConfig;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * Created by dima on 14.03.17.
 */
@Component
public class RedisDAO {
    Jedis connection;

    public RedisDAO(){
        ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
        bean.setBasename("db");
        connection = new Jedis(bean.getMessage("db.jedisurl", null, Locale.ENGLISH));
    }

    public List<String> getAllBroadcastMessages(){
        return connection.lrange("broadcast", 0, -1);
    }

    public void saveMessage(String login, String message){
        connection.lpush("broadcast", login + ":" + message);
    }
}
