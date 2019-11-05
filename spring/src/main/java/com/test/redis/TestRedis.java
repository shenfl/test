package com.test.redis;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.*;
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
        operations.set("souche-search:flag", "false");
    }

    @Test
    public void testGet() {
//        template.
        ValueOperations<String, String> operations = template.opsForValue();
        String flag = operations.get("shenfl:atomic");
        System.out.println(flag);
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ValueOperations<String, String> operations = template.opsForValue();
                for (int i = 0; i < 10000; i++) {
                    operations.increment("shenfl:atomic", 1);
                }
                System.out.println("over");
            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ValueOperations<String, String> operations = template.opsForValue();
                for (int i = 0; i < 10000; i++) {
                    operations.increment("shenfl:atomic", 1);
                }
                System.out.println("over");
            }
        });
        thread1.start();
        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueueSecurity() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ListOperations<String, String> operations = template.opsForList();
                for (int i = 0; i < 5000; i++) {
                    operations.leftPush("shenfl:list", "aa" + i);
                }
                System.out.println("over");
            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ListOperations<String, String> operations = template.opsForList();
                for (int i = 0; i < 5000; i++) {
                    operations.leftPush("shenfl:list", "bb" + i);
                }
                System.out.println("over");
                System.out.println("over");
            }
        });
        thread1.start();
        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPublish() {
        template.convertAndSend("shenfl", "java");
    }

    @Test
    public void testSubscribe() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "redis subscribe");
                thread.setDaemon(true);
                return thread;
            }
        };
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(template.getConnectionFactory());
        Executor executor = new ThreadPoolExecutor(2, 2, // 线程池大小不能设置成1，否则接收不到消息
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory);
        container.setTaskExecutor(executor);
        container.setSubscriptionExecutor(executor);

        MessageListener listener = new MessageListenerAdapter() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                System.out.println("channel: " + new String(message.getChannel()));
                System.out.println("message: " + new String(message.getBody()));
            }
        };
        container.addMessageListener(listener, new ChannelTopic("shenfl"));
        container.start();
        System.out.println("waiting...");
        try {
            Thread.sleep(10000000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
