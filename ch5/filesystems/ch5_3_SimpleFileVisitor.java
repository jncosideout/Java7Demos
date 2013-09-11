package filesystems;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class ch5_3_SimpleFileVisitor {

	public static void main(String[] args) {
		walkFileTree();

	}

	/**
	 * Using the SimpleFileVisitor class to traverse filesystems
	 */
	public static void walkFileTree() {
		try {
			Path path = Paths.get("c:\\");
			ListFiles listFiles = new ListFiles();
			Files.walkFileTree(path, listFiles);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Deleting a directory using the SimpleFileVisitor class
	 */
	public static void delete() {
		try {
			Files.walkFileTree(Paths.get("/home"), new DeleteDirectory());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Copying a directory using the SimpleFileVisitor class
	 */
	public static void copy() {
		try {
			Path source = Paths.get("/home");
			Path target = Paths.get("/backup");
			Files.walkFileTree(source,
					EnumSet.of(FileVisitOption.FOLLOW_LINKS),
					Integer.MAX_VALUE, new CopyDirectory(source, target));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}

class ListFiles extends SimpleFileVisitor<Path> {
	private final int indentionAmount = 3;
	private int indentionLevel;

	public ListFiles() {
		indentionLevel = 0;
	}

	private void indent() {
		for (int i = 0; i < indentionLevel; i++) {
			System.out.print(' ');
		}
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
		indent();
		System.out.println("Visiting file:" + file.getFileName());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path directory, IOException e)
			throws IOException {
		indentionLevel -= indentionAmount;
		indent();
		System.out.println("Finished with the directory: "
				+ directory.getFileName());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path directory,
			BasicFileAttributes attributes) throws IOException {
		indent();
		System.out.println("About to traverse the directory: "
				+ directory.getFileName());
		indentionLevel += indentionAmount;
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		System.out.println("A file traversal error ocurred");
		return super.visitFileFailed(file, exc);
	}
}

class DeleteDirectory extends SimpleFileVisitor<Path> {
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes)
			throws IOException {
		System.out.println("Deleting " + file.getFileName());
		Files.delete(file);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path directory,
			IOException exception) throws IOException {
		if (exception == null) {
			System.out.println("Deleting " + directory.getFileName());
			Files.delete(directory);
			return FileVisitResult.CONTINUE;
		} else {
			throw exception;
		}
	}
}

class CopyDirectory extends SimpleFileVisitor<Path> {
	private Path source;
	private Path target;

	public CopyDirectory(Path source, Path target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes)
			throws IOException {
		System.out.println("Copying " + source.relativize(file));
		Files.copy(file, target.resolve(source.relativize(file)));
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path directory,
			BasicFileAttributes attributes) throws IOException {
		Path targetDirectory = target.resolve(source.relativize(directory));
		try {
			System.out.println("Copying " + source.relativize(directory));
			Files.copy(directory, targetDirectory);
		} catch (FileAlreadyExistsException e) {
			if (!Files.isDirectory(targetDirectory)) {
				throw e;
			}
		}
		return FileVisitResult.CONTINUE;
	}
}
