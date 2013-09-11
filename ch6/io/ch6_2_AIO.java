package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Managing simple files
 */
public class ch6_2_AIO {

	public static void main(String[] args) {
//		 server2();
		client1();
	}

	public static void server1() {
		try {
			final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel
					.open();
			InetSocketAddress address = new InetSocketAddress("localhost", 5000);
			listener.bind(address);
			listener.accept(null,
					new CompletionHandler<AsynchronousSocketChannel, Void>() {
						@Override
						public void completed(
								AsynchronousSocketChannel channel,
								Void attribute) {
							try {
								System.out
										.println("Server: completed method executing");
								while (true) {
									ByteBuffer buffer = ByteBuffer.allocate(32);
									Future<Integer> readFuture = channel
											.read(buffer);
									Integer number = readFuture.get();
									System.out
											.println("Server: Message received: "
													+ new String(buffer.array()));
								}
							} catch (InterruptedException | ExecutionException ex) {
								ex.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, Void attachment) {
							System.out
									.println("Server: CompletionHandler exception");
							exc.printStackTrace();
						}
					});
			while (true) {
				// wait – Prevents the program from// terminating before the
				// client can connect
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void client1() {
		try {
			AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
			InetSocketAddress address = new InetSocketAddress("localhost", 5000);
			Future<Void> future = client.connect(address);
			System.out
					.println("Client: Waiting for the connection to complete");
			future.get();
			String message;
			do {
				System.out.print("Enter a message: ");
				Scanner scanner = new Scanner(System.in);
				message = scanner.nextLine();
				System.out.println("Client: Sending ...");
				ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
				System.out.println("Client: Message sent: "
						+ new String(buffer.array()));
				client.write(buffer);
			} while (!"quit".equals(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Using the Future object in a server
	 */
	public static void server2() {
		try {
			final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel
					.open();
			InetSocketAddress address = new InetSocketAddress("localhost", 5000);
			listener.bind(address);
			Future<AsynchronousSocketChannel> future = listener.accept();
			AsynchronousSocketChannel worker = future.get();
			while (true) {
				// Wait
				System.out.println("Server: Receiving ...");
				ByteBuffer buffer = ByteBuffer.allocate(32);
				Future<Integer> readFuture = worker.read(buffer);
				Integer number = readFuture.get();
				System.out.println("Server: Message received: "
						+ new String(buffer.array()));
			}
		} catch (IOException | InterruptedException | ExecutionException ex) {
			ex.printStackTrace();
		}
	}

}
