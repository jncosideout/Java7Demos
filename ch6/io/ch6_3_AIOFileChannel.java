package io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Writing to a file using the AsynchronousFileChannel class
 */
public class ch6_3_AIOFileChannel {

	private static String pathname = "C:\\log\\test\\newFile1.txt";

	public static void main(String[] args) {
		// writeFileChannel();
		readFileChannel();
	}

	/**
	 * Writing to a file using the AsynchronousFileChannel class
	 */
	public static void writeFileChannel() {
		try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel
				.open(Paths.get(pathname), StandardOpenOption.READ,
						StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
			CompletionHandler<Integer, Object> handler = new CompletionHandler<Integer, Object>() {
				@Override
				public void completed(Integer result, Object attachment) {
					System.out.println("Attachment: " + attachment + " "
							+ result + " bytes written");
					System.out.println("CompletionHandler Thread ID: "
							+ Thread.currentThread().getId());
				}

				@Override
				public void failed(Throwable e, Object attachment) {
					System.err.println("Attachment: " + attachment
							+ " failed with:");
					e.printStackTrace();
				}
			};
			System.out.println("Main Thread ID: "
					+ Thread.currentThread().getId());
			fileChannel.write(ByteBuffer.wrap("Sample".getBytes()), 0,
					"First Write", handler);
			fileChannel.write(ByteBuffer.wrap("Box".getBytes()), 0,
					"Second Write", handler);

			Future<Integer> writeFuture1 = fileChannel.write(
					ByteBuffer.wrap("Sample".getBytes()), 0);
			Future<Integer> writeFuture2 = fileChannel.write(
					ByteBuffer.wrap("Box".getBytes()), 0);
			int result = writeFuture1.get();
			System.out.println("Sample write completed with " + result
					+ " bytes written");
			result = writeFuture2.get();
			System.out.println("Box write completed with " + result
					+ " bytes written");
		} catch (IOException | InterruptedException | ExecutionException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Reading from a file using the AsynchronousFileChannel class
	 */
	public static void readFileChannel() {
		ExecutorService pool = new ScheduledThreadPoolExecutor(3);
		try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel
				.open(Paths.get(pathname), EnumSet.of(StandardOpenOption.READ),
						pool)) {
			System.out.println("Main Thread ID: "
					+ Thread.currentThread().getId());
			CompletionHandler<Integer, ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public synchronized void completed(Integer result,
						ByteBuffer attachment) {
					for (int i = 0; i < attachment.limit(); i++) {
						System.out.print((char) attachment.get(i));
					}
					System.out.println("");
					System.out.println("CompletionHandler Thread ID: "
							+ Thread.currentThread().getId());
					System.out.println("");
				}

				@Override
				public void failed(Throwable e, ByteBuffer attachment) {
					System.out.println("Failed");
				}
			};

			final int bufferCount = 5;
			ByteBuffer buffers[] = new ByteBuffer[bufferCount];
			for (int i = 0; i < bufferCount; i++) {
				buffers[i] = ByteBuffer.allocate(10);
				fileChannel.read(buffers[i], i * 10, buffers[i], handler);
			}

			pool.awaitTermination(1, TimeUnit.SECONDS);
			System.out.println("Byte Buffers");
			for (ByteBuffer byteBuffer : buffers) {
				for (int i = 0; i < byteBuffer.limit(); i++) {
					System.out.print((char) byteBuffer.get(i));
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
