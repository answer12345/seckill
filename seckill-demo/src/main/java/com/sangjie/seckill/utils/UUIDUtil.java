package com.sangjie.seckill.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String uudi() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
