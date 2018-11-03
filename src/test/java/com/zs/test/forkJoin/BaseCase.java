package com.zs.test.forkJoin;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.ToLongFunction;
import java.util.stream.LongStream;

/**
 * Author: zhshuo
 * Time : 2018/10/20 15:38 星期六
 **/
public class BaseCase {

    public long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    @Test
    public void bbTest(){
        int d = 10_100_1002;
        System.out.println(d);
    }

    @Test
    public void forkTest(){

    }

}
