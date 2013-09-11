package file;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ch2_1_PathProcess {

	public static void main(String[] args) {
		// testPath();
		// testFilePath();
		// relative2Absolute();
		// normalize();
		// combinPath();
	}

	public static void testFilePath() {
		try {
			Path path = Paths.get(new URI("file:///C:/go/api/README"));
			File file = new File("C:\\go\\api\\README");
			Path toPath = file.toPath();
			System.out.println(toPath.equals(path));
		} catch (URISyntaxException e) {
			System.out.println("Bad URI");
		}
	}

	public static void testPath() {
		Path path = FileSystems.getDefault().getPath("C:\\go\\api\\README");
		Iterator iterator = path.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.printf("toString: %s\n", path.toString());
		System.out.printf("getFileName: %s\n", path.getFileName());
		System.out.printf("getRoot: %s\n", path.getRoot());
		System.out.printf("getNameCount: %d\n", path.getNameCount());
		for (int index = 0; index < path.getNameCount(); index++) {
			System.out.printf("getName(%d): %s\n", index, path.getName(index));
		}
		System.out.printf("subpath(0,2): %s\n", path.subpath(0, 2));
		System.out.printf("getParent: %s\n", path.getParent());
		System.out.println(path.isAbsolute());

		try {
			path = Paths.get("C:", "go", "api", "README");
			System.out.printf("Absolute path: %s", path.toAbsolutePath());
		} catch (InvalidPathException ex) {
			System.out.printf("Bad path: [%s] at position %s", ex.getInput(),
					ex.getIndex());
		}
	}

	public static void relative2Absolute() {
		String separator = FileSystems.getDefault().getSeparator();
		System.out.println("The separator is " + separator);
		try {
			Path path = Paths.get(new URI("file:///C:/go/api/README"));
			System.out.println("subpath: " + path.subpath(0, 3));
			path = Paths.get("C:", "go", "api", "README");
			System.out.println("Absolute path: " + path.toAbsolutePath());
			System.out.println("URI: " + path.toUri());
		} catch (URISyntaxException ex) {
			System.out.println("Bad URI");
		} catch (InvalidPathException ex) {
			System.out.println("Bad path: [" + ex.getInput() + "] at position"
					+ ex.getIndex());
		}
	}

	public static void normalize() {
		Path path = Paths.get("/home/docs/../music/SpaceMachine A.mp3");
		System.out.println("Absolute path: " + path.toAbsolutePath());
		System.out.println("URI: " + path.toUri());
		System.out.println("Normalized Path: " + path.normalize());
		System.out.println("Normalized URI: " + path.normalize().toUri());
		System.out.println();
		path = Paths.get("/home/./music/ Robot Brain A.mp3");
		System.out.println("Absolute path: " + path.toAbsolutePath());
		System.out.println("URI: " + path.toUri());
		System.out.println("Normalized Path: " + path.normalize());
		System.out.println("Normalized URI: " + path.normalize().toUri());
		try {
			System.out.println("Real path: " + path.toRealPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void combinPath() {
		Path rootPath = Paths.get("C:\\go\\api\\");
		Path partialPath = Paths.get("README");
		Path resolvedPath = rootPath.resolve(partialPath);
		System.out.println("rootPath: " + rootPath);
		System.out.println("partialPath: " + partialPath);
		System.out.println("resolvedPath: " + resolvedPath);
		System.out.println("Resolved absolute path: "
				+ resolvedPath.toAbsolutePath());
	}

}
