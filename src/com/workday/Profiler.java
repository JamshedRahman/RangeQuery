package com.workday;

/*
Memory and Time profiler helper class with static methods
 */
public class Profiler{
    static long startTime, endTime;
    static long startMemory, endMemory;
    static Runtime runtime = Runtime.getRuntime();

    /*
    Starts a Memory and Time profiler
     */
    public static void Start(){
        startTime = System.currentTimeMillis();
        startMemory = runtime.totalMemory() - runtime.freeMemory();
    }

    /*
    Ends Memory and Time profiler and prints out results
     */
    public static void End(){
        endMemory = runtime.totalMemory() - runtime.freeMemory();
        long diffMemory = endMemory - startMemory;
        System.out.println("\tMemory (kB)= " + diffMemory/1024);
        endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        System.out.println("\tTest time (ms)= " + diff);
    }
}