/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.dromara.soul.remoting.redis.jedis;

import org.dromara.soul.remoting.redis.RedisModule;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.commands.SentinelCommands;
import redis.clients.jedis.util.Pool;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * JedisSentinelConnection .
 * <p>
 * 2019/11/23
 *
 * @author sixh
 */
public class JedisSentinelConnection extends JedisSingleConnection {

    public JedisSentinelConnection(RedisModule module, JedisPoolConfig poolConfig, Set<HostAndPort> hostAndPorts) {
        super(module, poolConfig, hostAndPorts);
    }

    public static JedisConnection build(RedisModule module, JedisPoolConfig config, Set<HostAndPort> hostAndPorts) {
        return new JedisSentinelConnection(module, config, hostAndPorts);
    }

    @Override
    public Pool<Jedis> getPool(RedisModule module) {
        Set<String> hosts = getHosts().stream().map(HostAndPort::toString).collect(toSet());
        return new JedisSentinelPool(module.getMasterName(),
                hosts,
                getPoolConfig(),
                module.getTimeOut(),
                module.getPassword());
    }
}