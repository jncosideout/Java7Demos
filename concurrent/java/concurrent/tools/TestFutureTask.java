package concurrent.tools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 取消的异步计算。利用开始和取消计算的方法、查询计算是否完成的方法和获取计算结果的方法，此类提供了对 Future的基本实现。仅在计算完成时才能获取结果；<br>
 * 如果计算尚未完成，则阻塞 get方法。一旦计算完成，就不能再重新开始或取消计算。 可使用 FutureTask包装Callable或Runnable对象。<br>
 * 因为FutureTask实现了Runnable，所以可将FutureTask提交给 Executor执行。<br>
 * 除了作为一个独立的类外，此类还提供了protected功能， 这在创建自定义任务类时可能很有用。<br>
 * 
 */
public class TestFutureTask {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newSingleThreadExecutor();

		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {// FutrueTask的构造参数是一个Callable接口
					@Override
					public String call() throws Exception {
						return Thread.currentThread().getName();// 这里可以是一个异步操作
					}
				});

		try {
			exec.execute(task);// FutureTask实际上也是一个线程
			String result = task.get();// 取得异步计算的结果，如果没有返回，就会一直阻塞等待
			System.out.printf("get:%s%n", result);
			exec.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
