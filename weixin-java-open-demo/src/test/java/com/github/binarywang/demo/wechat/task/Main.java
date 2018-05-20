package com.github.binarywang.demo.wechat.task;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {  
    public static void main(String[] a) throws InterruptedException {  
        //建立共享缓冲区  
        BlockingDeque<PCData> queue = new LinkedBlockingDeque<PCData>(10);  
        //建立生产者  
        Producer producter1 = new Producer(queue);  
        Producer producter2 = new Producer(queue);  
        Producer producter3 = new Producer(queue);  
        Producer producter4 = new Producer(queue);  
        Producer producter5 = new Producer(queue);  
        //建立消费者  
        Consumer consumer1 = new Consumer(queue);  
        Consumer consumer2 = new Consumer(queue);  
        Consumer consumer3 = new Consumer(queue);  
        //建立线程池  
        ExecutorService es = Executors.newCachedThreadPool();  
        //运行生产者  
        es.execute(producter1);  
        es.execute(producter2);  
        es.execute(producter3);  
        es.execute(producter4);  
        es.execute(producter5);  
        //运行消费者  
        es.execute(consumer1);  
        es.execute(consumer2);  
        es.execute(consumer3);  
        //运行时间  
        Thread.sleep(1000 * 10);  
        //停止生产者  
        producter1.stop();  
        producter2.stop();  
        producter3.stop();  
        producter4.stop();  
        producter5.stop();  
        //停止生产者后,预留时间给消费者执行  
        Thread.sleep(1000 * 5);  
        System.out.println("关闭线程池...");  
        //关闭线程池  
        es.shutdown();  
    }  
}  
