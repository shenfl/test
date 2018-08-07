package com.test.netty;

import io.netty.util.Recycler;

/**
 * Created by shenfl on 2018/8/6
 */
public class TestRecycler {
    private static final Recycler<User> RECYCLER = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };
    private static class User {
        private final Recycler.Handle<User> handler;
        private User(Recycler.Handle<User> handler) {
            this.handler = handler;
        }
        private void recycle() {
            handler.recycle(this);
        }
    }
    public static void main(String[] args) {
        User user = RECYCLER.get();
        user.recycle();

        User user1 = RECYCLER.get();

        System.out.println(user == user1);
    }
}
