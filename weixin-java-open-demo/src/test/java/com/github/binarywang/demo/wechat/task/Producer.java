package com.github.binarywang.demo.wechat.task;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者线程的实现如下，它构建PCData对象，并放入BlockingQueue队列中。
 *
 */
public class Producer implements Runnable{  
    private volatile boolean isRunning = true;  
    private BlockingDeque<PCData> queue; //内存缓冲区,通过构造时外部引入,保证和消费者用的是同样的内存缓冲区.  
    private static AtomicInteger count = new AtomicInteger(); //总数,原子操作.  
    private static final int SLEEPTIME = 1000;  
  
    public Producer(BlockingDeque<PCData> queue) {  
        this.queue = queue;  
    }  
  
    public void run() {  
        PCData data = null;  
        Random random = new Random();  
        System.out.println("start producter .."+Thread.currentThread().getId());  
        try {  
            while (isRunning){  
                Thread.sleep(random.nextInt(SLEEPTIME)); //模拟执行过程  
                data = new PCData(count.incrementAndGet()); //现获取当前值再+1  
                System.out.println(data + " is put into Queue");  
                //提交数据到缓冲队列中.设定等待的时间，如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败  
                if (!queue.offer(data,2, TimeUnit.SECONDS)){  
                    System.out.println("failed to put data "+data);  
                }  
            }  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
            //因为BlockingQueue的offer操作上的锁是重入锁中的可以中断的锁,所以如果有异常,就中断,防止死锁.  
            Thread.currentThread().interrupt();  
        }  
    }  
    public void stop(){  
        isRunning = false;  
    }  
}  