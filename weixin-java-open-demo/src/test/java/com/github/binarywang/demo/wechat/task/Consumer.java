package com.github.binarywang.demo.wechat.task;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

/**
 * 对应的消费者线程的实现如下。它从BlockingQueue队列中取出PCData对象，并进行相应的计算。
 *
 */
public class Consumer implements Runnable {  
    private BlockingDeque<PCData> queue;  
    private static final int SLEEPTIME = 1000;  
    //同理,和Producter共用同一个BlockingQueue,保证存/取都在一个缓冲区  
    public Consumer(BlockingDeque<PCData> queue) {  
        this.queue = queue;  
    }  
    
    public void run() {  
        System.out.println("start Consumer id : "+Thread.currentThread().getId());  
        Random r = new Random();  
        try {  
            while (true){  
                PCData data = queue.take();  
                if (null != data){  
                    int re = data.getIntData() * data.getIntData();  
                    System.out.println(MessageFormat.format("{0} * {0} = {1}",data.getIntData(),re));  
                    Thread.sleep(r.nextInt(SLEEPTIME));  
                }  
            }  
        }catch (InterruptedException e){  
            e.printStackTrace();  
            Thread.currentThread().interrupt();  
        }  
    }  
}  