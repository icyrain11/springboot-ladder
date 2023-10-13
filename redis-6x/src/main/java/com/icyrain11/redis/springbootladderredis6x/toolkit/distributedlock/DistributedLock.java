package com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock;

/**
 * @author icyrain11
 * @version 1.8
 */
public interface DistributedLock {

    Boolean lock(String serviceName, Long expireTime);


    Boolean unlock(String serviceName);

}
