package com.cmgun.serialNumber.bean;

/**
 * 序列号生成模式
 * Created by cmgun on 2017/5/18.
 */
public enum SerialNumberMode {

    DEFAULT("default"),
    DB("db"),
    REDIS("redis");

    private String mode;

    SerialNumberMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.mode;
    }
}