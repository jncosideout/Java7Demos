package concurrent.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 一个计数信号量。从概念上讲，信号量维护了一个许可集。如有必要，在许可可用前会阻塞每一个 acquire()，然后再获取该许可。<br>
 * 每个 release()添加一个许可，从而可能释放一个正在阻塞的获取者。但是，不使用实际的许可对象，Semaphore
 * 只对可用许可的号码进行计数，并采取相应的行动。
 * 
 */
public class TestSemaphore {
	
	private static final Semaphore semaphore = new Semaphore(2);
	private static ExecutorService exec = Executors.newCachedThreadPool();
	private static List<String> queue = Collections.synchronizedList(new ArrayList<String>());

	public static void main(String[] args) {
		final Random random = new Random();
		final int count = 10;
		final CountDownLatch doneSignal = new CountDownLatch(6);
		for (int i = 0; i < 3; i++) {// 三个线程同时操作add
			exec.execute(new Runnable() {
				public void run() {
					for(int i=0;i < count; i++){
						try {
							semaphore.acquire();
							String name = Thread.currentThread().getName();
							String value = name + "-" + System.currentTimeMillis();
							queue.add(value);
							long sleeptime = random.nextInt(1500);
							System.out.println("Thread:" + name + " add value[" + value + "] then sleep[" + sleeptime + "]ms, available:" + semaphore.availablePermits());
							Thread.sleep(sleeptime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					doneSignal.countDown();
				}
			});
		}

		for (int j = 0; j < 3; j++) {// 三个线程同时操作remove
			exec.execute(new Runnable() {
				public void run() {
					for(int i=0;i < count; i++){
						String name = Thread.currentThread().getName();
						long sleeptime = random.nextInt(6500);
						try {
							Thread.sleep(sleeptime);
							String value = queue.remove(0);
							if(value != null){
								System.out.println("Thread:" + name + " remove value[" + value + "] after sleep[" + sleeptime + "]ms, available:" + semaphore.availablePermits());
								semaphore.release();								
							}else{
								System.out.println("Thread:" + name + " remove value[" + value + "] after sleep[" + sleeptime + "]ms, available:" + semaphore.availablePermits());
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					doneSignal.countDown();
				}
			});
		}
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdown();
		System.out.println("availablePermits:" + semaphore.availablePermits() + ", value:" + queue.size());
	}

}
