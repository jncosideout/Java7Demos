package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ch4_6_SymbolicManage {
	private static String pathname = "C:\\log\\test";

	public static void main(String[] args) throws IOException {
		createSymbolicLink();
	}

	/**
	 * create a symbolic link
	 * 
	 * @throws IOException
	 */
	public static void createSymbolicLink() throws IOException {
		Path targetFile = Paths.get("C:/home/docs/users.txt");
		Path linkFile = Paths.get("C:/home/music/users.txt");
		Files.createSymbolicLink(linkFile, targetFile);
	}
	
	/**
	 * Creating a hard link
	 * 
	 * @throws IOException
	 */
	public static void createHardLink() throws IOException {
		Path targetFile = Paths.get("C:/home/docs/users.txt");
		Path linkFile = Paths.get("C:/home/music/users.txt");
		Files.createLink(linkFile, targetFile);
	}
	
	/**
	 * Determining the target of a link file
	 * 
	 * @throws IOException
	 */
	public static void readSymbolicLink() throws IOException {
		Path linkFile = Paths.get("C:/home/music/users.txt");
		System.out.println("Target file is: " + Files.readSymbolicLink(linkFile));
	}

}
