package concurrent.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 从java7开始在concurrent包中加入了Phaser类，它几乎可以取代CountDownLatch和CyclicBarrier，<br>
 * 其功能更灵活，更强大，支持动态调整需要控制的线程数。
 * 
 */
public class TestPhaser {

	public static void main(String[] args) {
		final String[] names = { "Jim", "Hary", "Mary", "Jhon", "Ali", "Lucy",
				"Wang", "Zhang", "Li", "Uhm", "Hey", "Gu", "Asu", "Who" };
		Phaser phaser = new Phaser(names.length);
		List<Thread> threads = new ArrayList<>(names.length);
		for (String name : names) {
			threads.add(new Thread(new Examinee(name, phaser), name)); /* 创建各个考生线程 */
		}
		for (Thread t : threads) {
			t.start(); /* 开始考试 */
		}
		for (Thread t : threads) {
			try {
				t.join(); /* 等待考试结束 */
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		}
		System.out.printf("Terminal ? %s\n", phaser.isTerminated());
	}

	static class Examinee implements Runnable {
		public static final Random random = new Random(new Date().getTime());
		private String name;
		private Phaser phaser;
		private int english;
		private int chinese;
		private int math;

		public Examinee(String name, Phaser phaser) {
			this.name = name;
			this.phaser = phaser;
		}

		public boolean english() {
			int time = random.nextInt(10);
			try {
				TimeUnit.SECONDS.sleep(time); /* 考试时间是一个随机数 */
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			english = random.nextInt(101); /* 假定考试成绩是随机的 */
			if (english < 60) {
				System.out.printf("%s Failed to pass English!\n", name);
				phaser.arriveAndDeregister(); /* 考试不合格，取消考试资格 */
				return false;
			} else {
				phaser.arriveAndAwaitAdvance(); /* 通过考试，可以进行下一门考试 */
				return true;
			}
		}

		public boolean chinese() {
			int time = random.nextInt(10);
			try {
				TimeUnit.SECONDS.sleep(time);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			chinese = random.nextInt(101);
			if (chinese < 60) {
				System.out.printf("%s Failed to pass Chinese!\n", name);
				phaser.arriveAndDeregister();
				return false;
			} else {
				phaser.arriveAndAwaitAdvance();
				return true;
			}
		}

		public boolean math() {
			int time = random.nextInt(10);
			try {
				TimeUnit.SECONDS.sleep(time);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			math = random.nextInt(101);
			if (math < 60) {
				System.out.printf("%s Failed to pass Math!\n", name);
				phaser.arriveAndDeregister();
				return false;
			} else {
				phaser.arriveAndAwaitAdvance();
				return true;
			}
		}

		public void showInfo() {
			System.out.printf("%s\tChinese:%d\tEnglish:%d\tMath:%d\n", name,
					chinese, english, math);
			phaser.arriveAndDeregister();
		}

		@Override
		public void run() {
			phaser.arriveAndAwaitAdvance(); /* 等待考生做好准备，即在同一个起跑线上 */
			System.out.printf("%s is ready!\n", name);
			if (!chinese() || !english() || !math())
				return;
			showInfo(); /* 只显示所有科目均合格的考生分数 */
		}
	}

}
