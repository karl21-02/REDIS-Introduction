package com.kangwon;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;

public class Main4 {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // geo add
                jedis.geoadd("stores2:geo", 127.12321, 37.156814, "some1");
                jedis.geoadd("stores2:geo", 127.12321, 37.555414, "some1");

                // geo dist
                Double geodist = jedis.geodist("stores2:geo", "some1", "some2");
                System.out.println(geodist);

                // geo search
                List<GeoRadiusResponse> radiusResponseList = jedis.geosearch("stores2:geo", new GeoCoordinate(127.031, 37.495),
                        100,
                        GeoUnit.M
                );

                radiusResponseList.forEach(response ->
                        System.out.printf("%s %f %f".formatted(
                                response.getMemberByString(),
                                response.getCoordinate().getLatitude(),
                                response.getCoordinate().getLongitude()
                                )
                        )
                );

                jedis.geosearch("stores2:geo", new GeoSearchParam().fromLonLat(new GeoCoordinate(127.033, 37.495)).byRadius(500, GeoUnit.M).withCoord());
                jedis.unlink("stores2:geo");
            }
        }
    }
}
