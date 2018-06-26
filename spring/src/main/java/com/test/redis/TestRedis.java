package com.test.redis;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-redis.xml"})
public class TestRedis {

    @Resource(name="redisTemplate")
    private RedisTemplate<String, String> template;
    // inject the template as ListOperations -- 自动转换
//    @Resource(name="redisTemplate")
//    private ListOperations<String, String> listOps;
//    public void addLink(String userId, URL url) {
//        listOps.leftPush(userId, url.toExternalForm());
//    }

    @Test
    public void test() {
        RedisSerializer<?> hashKeySerializer = template.getHashKeySerializer();
        System.out.println(hashKeySerializer);
        Set<String> keys = template.keys("*");
        System.out.println(keys);
    }

    @Test
    public void testAdd() {
//        template.
        ValueOperations<String, String> operations = template.opsForValue();
//        operations.set("souche-search:flag", "false");
        String jjssll = operations.get("souche-search:flag");
        System.out.println(jjssll);
    }

    @Test
    public void testQuery() {
        RedisSerializer<?> hashKeySerializer = template.getHashKeySerializer();
        System.out.println(hashKeySerializer);
    }

    @After
    public void after() {

    }

    @Test
    public void testAtomic() {
        AtomicBoolean b = new AtomicBoolean(true);
//        boolean b1 = b.compareAndSet(false, true);
        boolean b1 = b.getAndSet(true);
        System.out.println(b1);
        System.out.println(b.get());

        AtomicInteger integer = new AtomicInteger(10);
        boolean b2 = integer.compareAndSet(2, 12);
        System.out.println(b2);
        System.out.println(integer.get());
    }

}
