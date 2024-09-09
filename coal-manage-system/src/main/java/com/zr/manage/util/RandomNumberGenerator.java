package com.zr.manage.util;

import java.util.Random;

/**
 * @Author: Odin
 * @Date: 2024/9/9 22:43
 * @Description:
 */
public class RandomNumberGenerator {
    public static String gen() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
