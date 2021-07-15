package com.apiautomation.utils.apiutils;

public class TestAPIBase {

    public static APIExecutor executor;

    public static void setUp() {
        executor = new APIExecutor();

    }
}
