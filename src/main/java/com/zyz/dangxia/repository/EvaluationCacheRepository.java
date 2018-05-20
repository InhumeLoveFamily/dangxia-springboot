package com.zyz.dangxia.repository;

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

import javax.annotation.PostConstruct;

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

    public PriceSection getPriceSection(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] result = jedis.get(key.getBytes());
            PriceSection priceSection = new PriceSection();
            //采用自定义的序列化方式,可以有效提升效率，减少空间
            if (result != null) {
                ProtostuffIOUtil.mergeFrom(result, priceSection, schema);
                return priceSection;
            }
        } finally {
            jedis.close();
        }
        return null;
    }

    public String putPriceSection(String key, PriceSection priceSection) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] bytes = ProtostuffIOUtil.toByteArray(priceSection, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //动态的决定键值对的生命周期、让其在午夜三点钟消失
            DateTime dateTime = new DateTime();
            DateTime endTime;
            if (dateTime.getHourOfDay() < 3) { //发生在一天中的0点-3点，将会保留至3点
                endTime = dateTime.withHourOfDay(3);
            } else { //否则保留至第二天的凌晨3点
                endTime = dateTime.plusDays(1).withHourOfDay(3);
            }
            int life = (int) ((endTime.getMillis() - dateTime.getMillis()) / 1000);
            logger.info("该结果会存活{}秒", life);
            return jedis.setex(key.getBytes(), life, bytes);
        } finally {
            jedis.close();
        }
    }
}
