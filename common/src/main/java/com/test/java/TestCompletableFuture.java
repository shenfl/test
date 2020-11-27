package com.test.java;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * https://zhuanlan.zhihu.com/p/34921166
 * @author shenfl
 */
public class TestCompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    //使用ForkJoinPool线程
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("F1  "+Thread.currentThread().getName());
                    return "F1";
                }
        );

        //主线程
        System.out.println(Thread.currentThread().getName());
        CompletableFuture<String> future2 = future.whenComplete((s, throwable) -> {
            System.out.println(s);
            System.out.println(throwable);
            //使用主线程
            System.out.println("F2  "+Thread.currentThread().getName());
        });


        future2.join();
        System.out.println(future2.get());
    }

    @Test
    public void test() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
    }

    @Test
    public void test1() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("F2  "+Thread.currentThread().getName()); // F2  ForkJoinPool.commonPool-worker-1
            sleep(100);
        });
        System.out.println(cf.isDone()); // false
        sleep(1000);
        System.out.println(cf.isDone()); // true
    }

    @Test
    public void test2() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            System.out.println("F2  "+Thread.currentThread().getName()); // F2  main
            sleep(100);
            return s.toUpperCase();
        });
        System.out.println(cf.getNow(null)); // MESSAGE
    }

    @Test
    public void test3() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            sleep(100);
            return s.toUpperCase();
        });
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void test4() {
        ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int count = 1;
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "custom-executor-" + count++);
            }
        });
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            assertFalse(Thread.currentThread().isDaemon());
            sleep(100);
            return s.toUpperCase();

        }, executor);
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    @Test
    public void test5() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> {
                    System.out.println("F2  "+Thread.currentThread().getName()); // F2  main
                    result.append(s);
                }); // 与thenApply区别是没有返回值：CompletableFuture<Void>
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void test6() {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> {
                    result.append(s);
                    System.out.println("F2  "+Thread.currentThread().getName()); // F2  ForkJoinPool.commonPool-worker-1
                }); // 与thenAcceptAsync区别是没有返回值
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void test7() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            sleep(1000);
            System.out.println("run run " + System.currentTimeMillis()); // 虽然报错了，但是最终还是会执行
            return s.toUpperCase();
        });
        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> { return (th != null) ? "message upon cancel" : ""; });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch(CompletionException ex) { // just for testing
            System.out.println(ex.getCause().getMessage() + " " + System.currentTimeMillis());
        }
        assertEquals("message upon cancel", exceptionHandler.join());
        sleep(2000);
    }

    @Test
    public void test8() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            sleep(1000);
            System.out.println("run run " + System.currentTimeMillis());
            return s.toUpperCase();
        });
        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
        sleep(2000); // 不sleep不会执行toUpperCase，sleep后会执行，只是结果已经不会接收了
    }

    @Test
    public void test9() {
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> {
                    sleep(1000);
                    System.out.println("run first " + System.currentTimeMillis());
                    return s.toUpperCase();
                });
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
                    sleep(1000);
                    System.out.println("run second " + System.currentTimeMillis());
                    return s.toLowerCase();
                }),
                s -> { // 也是异步执行
                    System.out.println(Thread.currentThread().getName());
                    return s + " from applyToEither";
                });
        System.out.println(cf2.join());
    }

    private static void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("interrupted.");
            e.printStackTrace();
        }
    }
}
