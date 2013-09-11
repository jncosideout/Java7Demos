package file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;

public class ch4_2_TmpFile {
	private static String pathname = "C:\\log\\test";

	public static void main(String[] args) {
		// createTemp();
		// fileRelated();
	}

	/**
	 * Managing temporary files and directories
	 */
	public static void createTemp() {
		try {
			Path rootDirectory = FileSystems.getDefault().getPath(pathname);
			Path tempDirectory = Files.createTempDirectory(rootDirectory, "");
			System.out.println("Temporary directory created	successfully!");
			String dirPath = tempDirectory.toString();
			System.out.println(dirPath);
			Path tempFile = Files.createTempFile(tempDirectory, "", "");
			System.out.println("Temporary file created					successfully!");
			String filePath = tempFile.toString();
			System.out.println(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setting time-related attributes of a file or directory
	 */
	public static void fileRelated() {
		Path path = Paths.get(pathname);
		BasicFileAttributeView view = Files.getFileAttributeView(path,
				BasicFileAttributeView.class);
		FileTime lastModifedTime;
		FileTime lastAccessTime;
		FileTime createTime;
		BasicFileAttributes attributes;
		try {
			attributes = view.readAttributes();
			lastModifedTime = attributes.lastModifiedTime();
			createTime = attributes.creationTime();
			long currentTime = Calendar.getInstance().getTimeInMillis();
			lastAccessTime = FileTime.fromMillis(currentTime);
			view.setTimes(lastModifedTime, lastAccessTime, createTime);
			System.out.println(attributes.lastAccessTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
