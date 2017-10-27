package com.workday;

public class Main {
    public static void main(String[] args) {

        System.out.println("\nRunning WorkDay Range Query Tests...\n");
        RangeQueryBasicTest rqbt = new RangeQueryBasicTest();

        rqbt.setRangeContainerFactoryType("Array");
        rqbt.setUp();
        RunTestLoop(rqbt);

        rqbt.setRangeContainerFactoryType("TreeMap");
        rqbt.setUp();
        RunTestLoop(rqbt);

        rqbt.runManyRangeQueries();
    }

    private static void RunTestLoop(RangeQueryBasicTest rqbt) {

        int testLoop = 1000;

        Profiler.Start();
        for(int i = 0; i < testLoop; i++) {
            rqbt.runARangeQuery();
        }
        Profiler.End();
    }
}