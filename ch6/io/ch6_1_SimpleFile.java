package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Managing simple files
 */
public class ch6_1_SimpleFile {
	private static String pathname = "C:\\log\\test\\newFile1.txt";

	public static void main(String[] args) {
		// readByte();
		// write();
		// randomAccess();
		queryPosition();
	}

	public static void readByte() {
		Path path = Paths.get(pathname);
		byte[] contents;
		try {
			contents = Files.readAllBytes(path);
			for (byte b : contents) {
				System.out.print((char) b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readline() {
		Path path = Paths.get(pathname);
		try {
			List<String> contents = Files.readAllLines(path,
					Charset.defaultCharset());
			for (String b : contents) {
				System.out.println(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readWithBuffer() {
		Path path = Paths.get(pathname);
		Charset charset = Charset.forName("ISO-8859-1");
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writing to a file using the BufferedWriter class
	 */
	public static void write() {
		String newName = "Vivian";
		Path path = Paths.get(pathname);
		try (BufferedWriter writer = Files.newBufferedWriter(path,
				Charset.defaultCharset(), StandardOpenOption.APPEND)) {
			writer.newLine();
			writer.write(newName, 0, newName.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Un-buffered IO support in the Files class
	 */
	public static void read2File() {
		Path file = Paths.get(pathname);
		Path newFile = Paths.get("/home/docs/newUsers.txt");
		try (InputStream in = Files.newInputStream(file);
				OutputStream out = Files.newOutputStream(newFile,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
			int data = in.read();
			while (data != -1) {
				out.write(data);
				data = in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Random access IO using the SeekableByteChannel
	 */
	public static void randomAccess() {
		int bufferSize = 8;
		Path path = Paths.get(pathname);
		try (SeekableByteChannel sbc = Files.newByteChannel(path)) {
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			sbc.position(4);
			sbc.read(buffer);
			for (int i = 0; i < 5; i++) {
				System.out.print((char) buffer.get(i));
			}
			System.out.println();
			buffer.clear();
			sbc.position(0);
			sbc.read(buffer);
			for (int i = 0; i < 4; i++) {
				System.out.print((char) buffer.get(i));
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writing to a file using the SeekableByteChannel interface
	 */
	public static void writeWithSeekableByteChannel() {
		Path path = Paths.get(pathname);
		final String newLine = System.getProperty("line.separator");
		try (SeekableByteChannel sbc = Files.newByteChannel(path,
				StandardOpenOption.APPEND)) {
			String output = newLine + "Paul" + newLine + "Carol" + newLine
					+ "Fred";
			ByteBuffer buffer = ByteBuffer.wrap(output.getBytes());
			sbc.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Query the position
	 */
	public static void queryPosition() {
		Path path = Paths.get(pathname);
		final String newLine = System.getProperty("line.separator");
		try (SeekableByteChannel sbc = Files.newByteChannel(path,
				StandardOpenOption.WRITE)) {
			ByteBuffer buffer;
			long position = sbc.size();
			sbc.position(position);
			System.out.println("Position: " + sbc.position());
			buffer = ByteBuffer.wrap((newLine + "Paul").getBytes());
			sbc.write(buffer);
			System.out.println("Position: " + sbc.position());
			buffer = ByteBuffer.wrap((newLine + "Carol").getBytes());
			sbc.write(buffer);
			System.out.println("Position: " + sbc.position());
			buffer = ByteBuffer.wrap((newLine + "Fred").getBytes());
			sbc.write(buffer);
			System.out.println("Position: " + sbc.position());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
