package com.guseyn;

import java.util.function.ToIntFunction;

public class IPToIntConvertor implements ToIntFunction<CharSequence> {
    private static final int DECIMAL_BASE = 10;

    @Override
    public int applyAsInt(CharSequence ipAddress) {
        int base = 0;
        int part = 0;

        for (int i = 0, n = ipAddress.length(); i < n; ++i) {
            char symbol = ipAddress.charAt(i);
            if (symbol == '.') {
                base = (base << Byte.SIZE) | part;
                part = 0;
            } else {
                part = part * DECIMAL_BASE + symbol - '0';
            }
        }
        return  (base << Byte.SIZE) | part;
    }
}
