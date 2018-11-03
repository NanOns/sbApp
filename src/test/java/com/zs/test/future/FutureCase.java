package com.zs.test.future;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Author: zhshuo
 * Time : 2018/10/21 22:58 星期日
 **/
@Log4j2
public class FutureCase {

    public void ddd(){
        new Thread(new ThreadExp()).start();
    }

    private class ThreadExp implements Runnable{
        @Override
        public void run() {
            try {
                int i = 1000 + new Random().nextInt(5000);
                Thread.sleep(i);
                log.info("{}休眠时间:{}",Thread.currentThread().getName(),i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
