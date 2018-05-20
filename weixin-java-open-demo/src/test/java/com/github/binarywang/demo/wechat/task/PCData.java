package com.github.binarywang.demo.wechat.task;

/**
 * PCData作为生产者和消费者之间的共享数据模型，定义入下
 *
 */
public class PCData {  
    private final int intData;  
    public PCData(int d) {  
        intData = d;  
    }  
    public PCData(String d){  
        intData = Integer.parseInt(d);  
    }  
    public int getIntData() {  
        return intData;  
    }  
    @Override  
    public String toString() {  
        return "PCData{" +  
                "intData=" + intData +  
                '}';  
    }  
}  