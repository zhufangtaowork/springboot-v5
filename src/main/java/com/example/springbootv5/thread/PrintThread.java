package com.example.springbootv5.thread;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName： PrintThread
 * @Description： TODO
 * @Date： 2020/11/12 3:12 下午
 * @author： ZhuFangTao
 */
@Data
@AllArgsConstructor
public class PrintThread implements Runnable {
    private String name;
    private Object pre;
    private Object self;


    @Override
    public void run() {
        int count = 10;
        while (count>0){
            synchronized (pre){
                synchronized (self){
                    count--;
                    System.out.println(name);
                    self.notify();
                }
                try {
                    pre.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
