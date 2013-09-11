package file;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Set;

public class ch3_4_AttributeView {
	private static String pathname = "C:\\go\\api\\README";

	public static void main(String[] args) {
		// osAttributeView();
//		fileAttributeView_classArg();
		fileAttributeView_stringArg();
	}

	/**
	 * Determining operating system support for attribute views
	 */
	public static void osAttributeView() {
		Path path = Paths.get(pathname);
		FileSystem fileSystem = path.getFileSystem();
		Set<String> supportedViews = fileSystem.supportedFileAttributeViews();
		for (String view : supportedViews) {
			System.out.println(view);
		}
	}

	/**
	 * Using the supportsFileAttributeView method with a class argument
	 */
	public static void fileAttributeView_classArg() {
		try {
			Path path = Paths.get(pathname);
			FileStore fileStore = Files.getFileStore(path);
			System.out
					.println("FileAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(FileAttributeView.class));
			System.out
					.println("BasicFileAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(BasicFileAttributeView.class));
			System.out
					.println("FileOwnerAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(FileOwnerAttributeView.class));
			System.out
					.println("AclFileAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(AclFileAttributeView.class));
			System.out
					.println("PosixFileAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(PosixFileAttributeView.class));
			System.out
					.println("UserDefinedFileAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(UserDefinedFileAttributeView.class));
			System.out
					.println("DosFileAttributeView supported: "
							+ fileStore
									.supportsFileAttributeView(DosFileAttributeView.class));
		} catch (IOException ex) {
			System.out.println("Attribute view not supported");
		}
	}

	/**
	 * Using the supportsFileAttributeView method with a String argument
	 */
	public static void fileAttributeView_stringArg() {
		try {
			Path path = Paths.get(pathname);
			FileStore fileStore = Files.getFileStore(path);
			System.out.println("FileAttributeView supported: "
					+ fileStore.supportsFileAttributeView("file"));
			System.out.println("BasicFileAttributeView supported: "
					+ fileStore.supportsFileAttributeView("basic"));
			System.out.println("FileOwnerAttributeView supported: "
					+ fileStore.supportsFileAttributeView("owner"));
			System.out.println("AclFileAttributeView supported: "
					+ fileStore.supportsFileAttributeView("acl"));
			System.out.println("PosixFileAttributeView supported: "
					+ fileStore.supportsFileAttributeView("posix"));
			System.out.println("UserDefinedFileAttributeView supported: "
					+ fileStore.supportsFileAttributeView("user"));
			System.out.println("DosFileAttributeView supported: "
					+ fileStore.supportsFileAttributeView("dos"));
		} catch (IOException ex) {
			System.out.println("Attribute view not supported");
		}
	}
}
