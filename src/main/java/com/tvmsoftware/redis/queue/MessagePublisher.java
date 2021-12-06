package com.tvmsoftware.redis.queue;

public interface MessagePublisher {
    void publish(final String message);
}