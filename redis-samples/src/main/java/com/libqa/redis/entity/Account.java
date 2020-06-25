package com.libqa.redis.entity;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author edell.lee
 * @version 2020-06-24 12:22
 * @implNote
 */
@RedisHash("accounts")
@ToString
public class Account {

    @Id
    private String id;

    private String userName;

    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
