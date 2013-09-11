package filesystems;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * Processing the contents of a directory by using the DirectoryStream interface
 */
public class ch5_4_DirectoryStream {

	private static String pathname = "C:\\log\\test";

	public static void main(String[] args) {
		// show();
		// filte();
		writeOwnFilter();
	}

	/**
	 * 
	 */
	public static void show() {
		Path directory = Paths.get(pathname);
		try (DirectoryStream<Path> directoryStream = Files
				.newDirectoryStream(directory)) {
			for (Path file : directoryStream) {
				System.out.println(file.getFileName());
			}
		} catch (IOException | DirectoryIteratorException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Filtering a directory using globbing
	 */
	public static void filte() {
		Path directory = Paths.get(pathname);
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				directory, "*.txt")) {
			for (Path file : directoryStream) {
				System.out.println(file.getFileName());
			}
		} catch (IOException | DirectoryIteratorException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Using the PathMatcher interface to filter a directory
	 */
	public static void filteWithPathMatcher() {
		Path directory = Paths.get(pathname);
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(
				"glob:*.txt");
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				directory, "*.txt")) {
			for (Path file : directoryStream) {
				if (pathMatcher.matches(file.getFileName())) {
					System.out.println(file.getFileName());
				}
			}
		} catch (IOException | DirectoryIteratorException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Writing your own directory filter
	 */
	public static void writeOwnFilter() {
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) throws IOException {
				return Files.isHidden(file);
			}
		};

		Path directory = Paths.get("C:");
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				directory, filter)) {
			for (Path file : directoryStream) {
				System.out.println(file.getFileName());
			}
		} catch (IOException | DirectoryIteratorException ex) {
			ex.printStackTrace();
		}
	}

}
