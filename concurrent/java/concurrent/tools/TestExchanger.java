package concurrent.tools;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

/**
 * 可以在pair中对元素进行配对和交换的线程的同步点。每个线程将条目上的某个方法呈现给exchange方法，<br>
 * 与伙伴线程进行匹配，并且在返回时接收其伙伴的对象。Exchanger可能被视为 SynchronousQueue的双向形式。<br>
 * Exchanger可能在应用程序（比如遗传算法和管道设计）中很有用。
 * 
 */
public class TestExchanger {

	 public static void main(String[] args) {  
	        final Exchanger<ArrayList<Integer>> exchanger = new Exchanger<ArrayList<Integer>>();  
	        final ArrayList<Integer> buff1 = new ArrayList<Integer>(10);  
	        final ArrayList<Integer> buff2 = new ArrayList<Integer>(10);  
	  
	        new Thread(new Runnable() {  
	            @Override  
	            public void run() {  
	                ArrayList<Integer> buff = buff1;  
	                try {  
	                    while (true) {  
	                        if (buff.size() >= 10) {  
	                        	System.out.println("exchange buff1." + buff);  
	                            buff = exchanger.exchange(buff);//开始跟另外一个线程交互数据  
	                            buff.clear();  
	                        }  
	                        buff.add((int)(Math.random()*100));  
	                        Thread.sleep((long)(Math.random()*1000));  
	                    }  
	                } catch (InterruptedException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }).start();  
	          
	        new Thread(new Runnable(){  
	            @Override  
	            public void run() {  
	                ArrayList<Integer> buff=buff2;  
	                while(true){  
	                    try {  
	                        Thread.sleep(1000);  
	                        buff=exchanger.exchange(buff);//开始跟另外一个线程交换数据  
	                        System.out.println("exchange buff2." + buff); 
	                        buff.clear();
	                    } catch (InterruptedException e) {  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }}).start();  
	    }  
	
}
