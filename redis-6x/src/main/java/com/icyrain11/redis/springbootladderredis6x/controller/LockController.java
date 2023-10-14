package com.icyrain11.redis.springbootladderredis6x.controller;

import com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.DistributedLock;
import com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock.impl.RedisDistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icyrain11
 * @version 1.8
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LockController {

    private final DistributedLock distributedLock;

    @GetMapping("/api/lock-service/{serviceName}")
    public String lock(@PathVariable String serviceName) {
        try {
            Boolean lock = distributedLock.lock(serviceName, 500L);
            log.info("has been lock {}", serviceName);
        } finally {
            distributedLock.unlock(serviceName);
        }
        return "has lock";
    }

    @GetMapping("/api/unlock-service/{serviceName}")
    public String unLock(@PathVariable String serviceName) {
        distributedLock.unlock(serviceName);
        return "unLock" + serviceName;
    }

}
