/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.redis;

import com.andrei1058.bedwars.BedWars;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisConnection {

    private final String channel;
    private final JedisPool dataSource;
    private final RedisListener listener;

    private final ExecutorService listenerPool = Executors.newCachedThreadPool();

    public RedisConnection() {
        final String host = BedWars.config.getYml().getString("redis.host");
        int port = BedWars.config.getYml().getInt("redis.port");
        this.channel = BedWars.config.getYml().getString("redis.channel");
        final String username = BedWars.config.getYml().getString("redis.auth.username");
        final String password = BedWars.config.getYml().getString("redis.auth.password");
        this.dataSource = new JedisPool(host, port, username, password);
        this.listener = new RedisListener(channel);
    }

    public boolean connect() {
        try (final Jedis listenerConnection = dataSource.getResource()) {
            listenerPool.execute(() -> {
                listenerConnection.subscribe(listener, channel);
                /*
                 * Since Jedis PubSub channel listener is thread-blocking,
                 * we can shut down thread when the pub-sub listener stops
                 * or fails.
                 */
                listenerPool.shutdown();
            });
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public void disable() {
        dataSource.close();
    }

    public void sendMessage(String message) {
        try (final Jedis listenerConnection = dataSource.getResource()) {
            listenerConnection.publish(channel, message);
        }
    }

}