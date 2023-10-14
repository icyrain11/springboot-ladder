package com.icyrain11.redis.springbootladderredis6x.controller;

import com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.DistributedLock;
import com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.impl.RedisDistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonDelayedQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icyrain11
 * @version 17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LockController {

    private final DistributedLock distributedLock;

    private final RedissonClient redissonClient;

    @GetMapping("/api/lock-service/lock/{serviceName}")
    public String lock(@PathVariable String serviceName) {
        try {
            Boolean lock = distributedLock.lock(serviceName, 500L);
            log.info("has been lock {}", serviceName);
        } finally {
            distributedLock.unlock(serviceName);
        }
        return "has lock";
    }

    @GetMapping("/api/lock-service/redisson-lock/{serviceName}")
    public String redissonLock(@PathVariable String serviceName) {
        redissonClient.getLock(serviceName);
        return "redissonLock";
    }

    @GetMapping("/api/lock-service/unlock/{serviceName}")
    public String unLock(@PathVariable String serviceName) {
        distributedLock.unlock(serviceName);
        return "unLock" + serviceName;
    }

    @GetMapping("/api/lock-service/delay-queue/{serviceName}")
    public String delayQueue(@PathVariable String serviceName) {
        //use redisson RDelayQueu
        RQueue<Object> queue = redissonClient.getQueue(serviceName);
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(queue);
//       delayedQueue.addListener( );
        return "delayQueue";
    }

}
