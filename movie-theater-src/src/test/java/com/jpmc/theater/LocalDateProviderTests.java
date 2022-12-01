package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
        // System.out.println("current time is - " + LocalDateProvider.singleton().currentDate());
        // We should assert that this object is non-null
        assertNotNull(LocalDateProvider.singleton().currentDate());
    }
}
