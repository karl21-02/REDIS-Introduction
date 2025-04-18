package com.kangwon;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set("users:300:email", "dskljf");
                jedis.set("users:300:name", "kim");
                jedis.set("users:300:age", "100");

                var userEmail = jedis.get("users:300:email");
                System.out.println(userEmail);

                List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                userInfo.forEach(System.out::println);

                long counter = jedis.incr("counter");
                System.out.println(counter);

                counter = jedis.incrBy("counter", 10);
                System.out.println(counter);

                counter = jedis.decr("counter");
                System.out.println(counter);

                counter = jedis.decrBy("counter", 20L);
                System.out.println(counter);

                // optimized using pipline

                Pipeline pipeline = jedis.pipelined();
                pipeline.set("users:400:email", "manuna530@gmail.com");
                pipeline.set("users:400:name", "kimjunhee");
                pipeline.set("users:400:age", "24");
                List<Object> objects = pipeline.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i));
            }
        }
    }
}