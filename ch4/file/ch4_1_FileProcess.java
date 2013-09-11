package file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ch4_1_FileProcess {
	private static String pathname = "C:\\log\\test";

	public static void main(String[] args) {
		// create();
		// copy();
		// move();
		delete();
		// copySymLink();
		// copyDir();
		// copyInputStream();
		// copyOutputStream();
	}

	/**
	 * Creating files and directories
	 */
	public static void create() {
		try {
			Path testDirectoryPath = Paths.get(pathname);
			Path testDirectory = Files.createDirectory(testDirectoryPath);
			System.out.println("Directory created successfully!");
			Path newFilePath = FileSystems.getDefault().getPath(
					"C:\\log\\test\\newFile.txt");
			Path testFile = Files.createFile(newFilePath);
			System.out.println("File created successfully!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Moving a file and a directory
	 */
	public static void move() {
		try {
			Path sourceFile = Paths.get(pathname);
			Path destinationFile = Paths.get(pathname + "2");
			Files.move(sourceFile, destinationFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Deleting files or directories
	 */
	public static void delete() {
		try {
			Path sourceFile = Paths.get(pathname + "\\newFile.txt");
			Files.delete(sourceFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Controlling how a file is copied
	 */
	public static void copy() {
		Path newFile = FileSystems.getDefault().getPath(
				"C:\\log\\test\\newFile.txt");
		Path copiedFile = FileSystems.getDefault().getPath(
				"C:\\log\\test\\copiedFile.txt");
		try {
			Files.createFile(newFile);
			System.out.println("File created successfully!");
			Files.copy(newFile, copiedFile);
			System.out.println("File copied successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copying a symbolic link file
	 */
	public static void copySymLink() {
		Path originalLinkedFile = FileSystems.getDefault().getPath(
				"C:/home/music/users.txt");
		Path newLinkedFile = FileSystems.getDefault().getPath(
				"C:/home/music/users2.txt");
		try {
			Files.copy(originalLinkedFile, newLinkedFile);
			System.out.println("Symbolic link file copied successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copying a directory
	 */
	public static void copyDir() {
		Path originalDirectory = FileSystems.getDefault().getPath(pathname);
		Path newDirectory = FileSystems.getDefault()
				.getPath("C:\\log\\testtmp");
		try {
			Files.copy(originalDirectory, newDirectory);
			System.out.println("Directory copied successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copying an input stream to a file
	 */
	public static void copyInputStream() {
		Path newFile = FileSystems.getDefault().getPath(
				"C:\\log\\test\\newFile1.txt");
		URI url = URI.create("http://jdk7.java.net/");
		try (InputStream inputStream = url.toURL().openStream()) {
			Files.copy(inputStream, newFile);
			System.out.println("Site copied successfully!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Copying a file to an output stream
	 */
	public static void copyOutputStream() {
		Path sourceFile = FileSystems.getDefault().getPath(
				"C:\\log\\test\\newFile1.txt");
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			Files.copy(sourceFile, outputStream);
			byte arr[] = outputStream.toByteArray();
			System.out.println("The contents of " + sourceFile.getFileName());
			for (byte data : arr) {
				System.out.print((char) data);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
