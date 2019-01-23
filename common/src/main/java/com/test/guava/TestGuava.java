package com.test.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;

/**
 * 类似之前的mapmaker，详见canal的CanalController
 */
public class TestGuava {
    public static void main(String[] args) throws Exception {
        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)    // 最多可以缓存1000个key
                .expireAfterWrite(10, TimeUnit.SECONDS)  // 过期时间
                .build(
                        new CacheLoader<String, Integer>() {
                            public Integer load(String key) {
                                return compute(key);   // DOTO
                            }
                        });
        for (int i = 0; i < 100; i++) {
            Integer integer = cache.get("");
            Thread.sleep(1000);
        }
    }

    private static Integer compute(String key) {
        System.out.println("compute");
        return 2;
    }

}
