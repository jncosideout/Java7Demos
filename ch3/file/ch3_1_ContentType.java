package file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Determining the file content type
 */
public class ch3_1_ContentType {

	public static void main(String[] args) throws Exception {
		displayContentType("/home/docs/users.txt");
		displayContentType("/home/docs/Chapter 2.doc");
		displayContentType("/home/docs/java.exe");

	}

	static void displayContentType(String pathText) throws Exception {
		Path path = Paths.get(pathText);
		String type = Files.probeContentType(path);
		System.out.println(type);
	}

}
