package filesystems;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

/**
 * Getting FileSystem information
 * 
 */
public class ch5_2_FileSystem {

	public static void main(String[] args) {
		getFileSystemInfo();
	}

	public static void getFileSystemInfo() {
		FileSystem fileSystem = FileSystems.getDefault();
		FileSystemProvider provider = fileSystem.provider();
		System.out.println("Provider: " + provider.toString());
		System.out.println("Open: " + fileSystem.isOpen());
		System.out.println("Read Only: " + fileSystem.isReadOnly());
		Iterable<Path> rootDirectories = fileSystem.getRootDirectories();
		System.out.println();
		System.out.println("Root Directories");
		for (Path path : rootDirectories) {
			System.out.println(path);
		}
		Iterable<FileStore> fileStores = fileSystem.getFileStores();
		System.out.println();
		System.out.println("File Stores");
		for (FileStore fileStore : fileStores) {
			System.out.println(fileStore.name());
		}
	}

}


