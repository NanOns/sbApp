package com.netty;

import org.msgpack.annotation.Message;
 
/**
 * Created on 2018-08-08 13:43
 *
 * @author zhshuo
 */
@Message
public class UserInfo {
 
    private String name;
 
    private int age;
 
    public String getName() {
        return name;
    }
 
    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }
 
    public int getAge() {
        return age;
    }
 
    public UserInfo setAge(int age) {
        this.age = age;
        return this;
    }
}