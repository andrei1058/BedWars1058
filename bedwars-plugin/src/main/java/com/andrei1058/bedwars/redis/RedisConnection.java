package com.andrei1058.bedwars.redis;

import com.andrei1058.bedwars.BedWars;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnection {

    private final String host, username, password, channel;
    private final int port;
    private final JedisPool dataSource;
    private final RedisListener listener;
    private Jedis listenerConnection;

    public RedisConnection() {
        this.host = BedWars.config.getYml().getString("redis.host");
        this.port = BedWars.config.getYml().getInt("redis.port");
        this.channel = BedWars.config.getYml().getString("redis.channel");
        this.username = BedWars.config.getYml().getString("redis.auth.username");
        this.password = BedWars.config.getYml().getString("redis.auth.password");
        this.dataSource = new JedisPool(host, port, username, password);
        this.listener = new RedisListener(channel);
    }

    public boolean connect() {
        this.listenerConnection = dataSource.getResource();
        if(this.listenerConnection == null) {
            return false;
        }
        this.listenerConnection.subscribe(listener, channel);
        return true;
    }

    public void sendMessage(String message) {
        this.listenerConnection.publish(channel, message);
    }


}
