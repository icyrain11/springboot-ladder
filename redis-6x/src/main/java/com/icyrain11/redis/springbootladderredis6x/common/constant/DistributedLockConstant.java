package com.icyrain11.redis.springbootladderredis6x.common.constant;

import java.util.UUID;

/**
 * @author icyrain11
 * @version 1.8
 */
public interface DistributedLockConstant {

    String LOCK_KEY_PREFIX = "distributed_lock:";

    String JVM_ID_PREFIX = UUID.randomUUID().toString();

}
