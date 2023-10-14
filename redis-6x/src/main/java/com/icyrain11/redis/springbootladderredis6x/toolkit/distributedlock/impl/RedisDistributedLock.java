package com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.impl;

import com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.icyrain11.redis.springbootladderredis6x.common.constant.DistributedLockConstant.JVM_ID_PREFIX;
import static com.icyrain11.redis.springbootladderredis6x.common.constant.DistributedLockConstant.LOCK_KEY_PREFIX;

/**
 * 简易分布式锁
 * TODO 原子性问题
 *
 * @author icyrain11
 * @version 17
 */
@Component
@RequiredArgsConstructor
public class RedisDistributedLock implements DistributedLock {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean lock(String serviceName, Long expireTime) {
        //threadId为递增 不同jvm可能重复
        String threadId = String.valueOf(Thread.currentThread().getId());
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(LOCK_KEY_PREFIX + serviceName, JVM_ID_PREFIX + threadId, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public Boolean unlock(String serviceName) {
        //拼接字符串id
        String threadId = JVM_ID_PREFIX + Thread.currentThread().getId();
        String lockId = stringRedisTemplate.opsForValue().get(LOCK_KEY_PREFIX + serviceName);
        if (Objects.equals(threadId, lockId)) {
            Boolean success = stringRedisTemplate.delete(LOCK_KEY_PREFIX + serviceName);
            return Boolean.TRUE.equals(success);
        }
        //unlock失败
        return false;
    }

}
