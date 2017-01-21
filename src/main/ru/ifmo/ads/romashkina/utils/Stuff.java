package ru.ifmo.ads.romashkina.utils;

public class Stuff {

    // TODO: bitwise operations
    public static int log(int num) {
        int i, n = 1;
        for (i = 0; i < num / 2; i++) {
            if (n > num) break;
            n *= 2;
        }
        return i;
    }
}
