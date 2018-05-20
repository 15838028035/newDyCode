package com.github.binarywang.demo.wechat.task;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.weixindev.micro.serv.common.util.DateUtil;

public class ImgArticleProducer implements Runnable{  
    private volatile boolean isRunning = true;  
    private BlockingDeque<WxDataCubeArticleTotalVO> queue; //内存缓冲区,通过构造时外部引入,保证和消费者用的是同样的内存缓冲区.  
    private static AtomicInteger count = new AtomicInteger(); //总数,原子操作.  
    private static final int SLEEPTIME = 1000;  
    
    private WxDataCubeArticleTotalVO WxDataCubeArticleTotalVO;
  
    public ImgArticleProducer(BlockingDeque<WxDataCubeArticleTotalVO> queue) {  
        this.queue = queue;  
    }  
  
    public void run() {  
    	WxDataCubeArticleTotalVO data = null;  
        Random random = new Random();  
        System.out.println("start producter .."+Thread.currentThread().getId());  
        try {  
            while (isRunning){  
                Thread.sleep(random.nextInt(SLEEPTIME)); //模拟执行过程  
                data = WxDataCubeArticleTotalVO;
                System.out.println(data + " is put into Queue");  
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
    
    public void putData(WxDataCubeArticleTotalVO WxDataCubeArticleTotalVO) throws Exception {
    	System.out.println("生产者开始生产对象"+ DateUtil.getNowDateYYYYMMddHHMMSS());
    	this.WxDataCubeArticleTotalVO = WxDataCubeArticleTotalVO;
    } 
}
