package com.authentication.Authentication.event;

import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

    private final Long userId;

    public UserCreatedEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

}
