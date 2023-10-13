package com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.impl;

import com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.icyrain11.redis.springbootladderredis6x.common.constant.DistributedLockConstant.LOCK_KEY_PREFIX;

/**
 * 简易分布式锁
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
        //获取当前线程id 不同jvm的threadId并不会相同
        String threadId = String.valueOf(Thread.currentThread().getId());
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(LOCK_KEY_PREFIX + serviceName, threadId, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public Boolean unlock(String serviceName) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        String lockId = stringRedisTemplate.opsForValue().get(LOCK_KEY_PREFIX + serviceName);
        if (Objects.equals(threadId, lockId)) {
            Boolean success = stringRedisTemplate.delete(LOCK_KEY_PREFIX + serviceName);
            return Boolean.TRUE.equals(success);
        }
        return false;
    }

}
