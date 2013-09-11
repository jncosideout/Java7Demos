package file;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Set;

public class ch4_5_POSIXManage {
	private static String pathname = "C:\\log\\test";

	public static void main(String[] args) throws IOException {
		// mngPosix();
		// modify();
		// pmsChg();
		create();
	}

	/**
	 * Managing POSIX attributes
	 * 
	 * @throws IOException
	 */
	public static void mngPosix() throws IOException {
		Path path = Paths.get(pathname);
		FileSystem fileSystem = path.getFileSystem();
		PosixFileAttributeView view = Files.getFileAttributeView(path,
				PosixFileAttributeView.class);
		PosixFileAttributes attributes = view.readAttributes();
		Set<PosixFilePermission> permissions = attributes.permissions();
		listPermissions(permissions);
		permissions.add(PosixFilePermission.OTHERS_WRITE);
		view.setPermissions(permissions);
		System.out.println();
		listPermissions(permissions);
	}

	private static void listPermissions(Set<PosixFilePermission> permissions) {
		System.out.print("Permissions: ");
		for (PosixFilePermission permission : permissions) {
			System.out.print(permission.name() + " ");
		}
		System.out.println();
	}

	/**
	 * Modifying the POSIX ownership of a file
	 * 
	 * @throws IOException
	 */
	public static void modify() throws IOException {
		Path path = Paths.get(pathname);
		FileSystem fileSystem = path.getFileSystem();
		PosixFileAttributeView view = Files.getFileAttributeView(path,
				PosixFileAttributeView.class);
		PosixFileAttributes attributes = view.readAttributes();
		Set<PosixFilePermission> permissions = attributes.permissions();
		System.out.println("Old Group: " + attributes.group().getName());
		System.out.println("Old Owner: " + attributes.owner().getName());
		System.out.println();
		UserPrincipalLookupService lookupService = FileSystems.getDefault()
				.getUserPrincipalLookupService();
		UserPrincipal userPrincipal = lookupService
				.lookupPrincipalByName("jennifer");
		GroupPrincipal groupPrincipal = lookupService
				.lookupPrincipalByGroupName("jennifer");
		view.setGroup(groupPrincipal);
		view.setOwner(userPrincipal);
		attributes = view.readAttributes();
		System.out.println("New Group: " + attributes.group().getName());
		System.out.println("New Owner: " + attributes.owner().getName());
		System.out.println();
	}

	/**
	 * Using the Files class' set/get POSIX file permission methods
	 * 
	 * @throws IOException
	 */
	public static void pmsChg() {
		Path path = Paths.get(pathname);
		try {
			Set<PosixFilePermission> permissions = Files
					.getPosixFilePermissions(path);
			System.out.print("Permissions: ");
			for (PosixFilePermission permission : permissions) {
				System.out.print(permission.name() + " ");
			}
			System.out.println();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Using the PosixFilePermissions class to create PosixFilePermissions
	 * 
	 * @throws IOException
	 */
	public static void create() {
		Path path = Paths.get(pathname);
		try {
			FileSystem fileSystem = path.getFileSystem();
			PosixFileAttributeView view = Files.getFileAttributeView(path,
					PosixFileAttributeView.class);
			PosixFileAttributes attributes = view.readAttributes();
			Set<PosixFilePermission> permissions = attributes.permissions();
			for (PosixFilePermission permission : permissions) {
				System.out.print(permission.toString() + ' ');
			}
			System.out.println();
			FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions
					.asFileAttribute(permissions);
			Set<PosixFilePermission> fileAttributeSet = fileAttributes.value();
			for (PosixFilePermission posixFilePermission : fileAttributeSet) {
				System.out.print(posixFilePermission.toString() + ' ');
			}
			System.out.println();
			System.out.println(PosixFilePermissions.toString(permissions));
			permissions = PosixFilePermissions.fromString("rw-rw-r--");
			for (PosixFilePermission permission : permissions) {
				System.out.print(permission.toString() + ' ');
			}
			System.out.println();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
