package com.kangwon;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main2 {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // hash
                jedis.hset("users:2:info", "name", "greg2");

                var map = new HashMap<String, String>();
                map.put("email", "greg2@gmail.com");
                map.put("phone", "010-1234-5678");

                jedis.hset("users:2:info", map);

                // hdel
                jedis.hdel("users:2:info", "phone");

                // get, mget
                System.out.println(jedis.hget("users:2:info", "email"));
                Map<String, String> user2Info =  jedis.hgetAll("users:2:info");
                user2Info.forEach((k, v) -> System.out.printf("%s %s%n", k, v));

                // hinter
                jedis.hincrBy("users:2:info", "vitis", 1);
            }
        }
    }
}
