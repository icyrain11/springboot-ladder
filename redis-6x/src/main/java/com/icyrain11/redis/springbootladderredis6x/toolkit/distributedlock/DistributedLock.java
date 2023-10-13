package com.icyrain11.redis.springbootladderredis6x.toolkit.distributedlock;

/**
 * @author icyrain11
 * @version 1.8
 */
public interface DistributedLock {

    void lock();


    void unLock();


}
