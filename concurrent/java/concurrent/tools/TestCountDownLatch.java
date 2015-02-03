package concurrent.tools;

import java.util.concurrent.CountDownLatch;

/**
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。用给定的计数初始化CountDownLatch。<br>
 * 由于调用了countDown()方法， 所以在当前计数到达零之前，await方法会一直受阻塞。 之后，会释放所有等待的线程，<br>
 * await的所有后续调用都将立即返回 。 这种现象只出现一次——计数无法被重置。<br>
 * 这就是说，CountDownLatch可以用来管理一组相关的线程执行，只需在主线程中调用CountDownLatch的await方法（一直阻塞），<br>
 * 让各个线程调用countDown方法 。当所有的线程都只需完countDown了，await也顺利返回，不再阻塞了。在这样情况下尤其适用<br>
 * ：将一个任务分成若干线程执行，等到所有线程执行完，再进行汇总处理。
 * 
 * 
 */
public class TestCountDownLatch {

	private static final int N = 10;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(N);
		CountDownLatch startSignal = new CountDownLatch(1);// 开始执行信号

		for (int i = 1; i <= N; i++) {
			new Thread(new Worker(i, doneSignal, startSignal)).start();// 线程启动了
		}
		System.out.println("begin------------");
		startSignal.countDown();// 开始执行啦
		doneSignal.await();// 等待所有的线程执行完毕
		System.out.println("Ok");

	}

	static class Worker implements Runnable {
		private final CountDownLatch doneSignal;
		private final CountDownLatch startSignal;
		private int beginIndex;

		Worker(int beginIndex, CountDownLatch doneSignal,
				CountDownLatch startSignal) {
			this.startSignal = startSignal;
			this.beginIndex = beginIndex;
			this.doneSignal = doneSignal;
		}

		public void run() {
			try {
				startSignal.await(); // 等待开始执行信号的发布
				beginIndex = (beginIndex - 1) * 10 + 1;
				for (int i = beginIndex; i <= beginIndex + 10; i++) {
					System.out.println(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				doneSignal.countDown();
			}
		}
	}

}
