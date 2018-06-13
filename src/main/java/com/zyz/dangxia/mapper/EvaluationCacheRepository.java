package com.zyz.dangxia.mapper;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.zyz.dangxia.dto.PriceSection;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.PostConstruct;
import java.util.Date;

@Repository
public class EvaluationCacheRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    @Value("${spring.redis.host:localhost}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    private RuntimeSchema<PriceSection> schema =
            RuntimeSchema.createFrom(PriceSection.class);

    @PostConstruct
    public void init() {
        jedisPool = new JedisPool(host, port);
    }

    public PriceSection getPriceSection(String key) throws JedisException {
        logger.info("key={}", key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            byte[] result = jedis.get(key.getBytes());
            PriceSection priceSection = new PriceSection();
            //采用自定义的序列化方式,可以有效提升效率，减少空间
            if (result != null) {
                logger.info("从redis中取到了值");
                ProtostuffIOUtil.mergeFrom(result, priceSection, schema);
                return priceSection;
            } else {
                logger.info("结果是空的");
            }
        } catch (JedisException e) {
            logger.error("淦，redis又挂了");
//            throw e;
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }


    public String putPriceSection(String key, PriceSection priceSection) throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            byte[] bytes = ProtostuffIOUtil.toByteArray(priceSection, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //动态的决定键值对的生命周期、让其在午夜三点钟消失
//            DateTime dateTime = new DateTime();
//            DateTime endTime;
//            if (dateTime.getHourOfDay() < 3) { //发生在一天中的0点-3点，将会保留至3点
//                endTime = dateTime.withHourOfDay(3).withMinuteOfHour(0);
//            } else { //否则保留至第二天的凌晨3点
//                endTime = dateTime.plusDays(1).withHourOfDay(3).withMinuteOfHour(0);
//            }
//            DateTime currentTime = new DateTime();
//            logger.info("结束时间为{}号{}时{}分，当前时间为{}号{}时{}分",endTime.getDayOfMonth(),
//                    endTime.getHourOfDay(),endTime.getMinuteOfHour(),currentTime.getDayOfMonth(),currentTime.getHourOfDay(),
//                    currentTime.getMinuteOfHour());
//            logger.info("两者的差别为{}-{}={}毫秒",endTime.getMillis(),currentTime.getMillis(),
//                    endTime.getMillis()-currentTime.getMillis());
//            logger.info("当前真正的时间戳为{}",new Date().getTime());
//            int life = (int) ((endTime.getMillis() - currentTime.getMillis()) / 1000.0);
//            logger.info("该结果会存活{}秒", life);
//            return jedis.setex(key.getBytes(), life, bytes);
            return jedis.set(key.getBytes(), bytes);
        } catch (JedisException e) {
            logger.error("淦，redis又挂了");
//            throw e;
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }

    public String flashDB() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            return jedis.flushDB();
        } catch (JedisException e) {
            logger.error("淦，redis又挂了");
//            throw e;
        } finally {
            if (jedis != null)

                jedis.close();
        }
        return null;
    }

    public String flashAll() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            return jedis.flushAll();
        } catch (JedisException e) {
            logger.error("淦，redis又挂了");
//            throw e;
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }
}
