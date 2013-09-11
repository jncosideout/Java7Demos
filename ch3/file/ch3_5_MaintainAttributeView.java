package file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Set;

/**
 * Maintaining basic file attributes using the BasicFileAttributeView
 */
public class ch3_5_MaintainAttributeView {
	private static String pathname = "C:\\go\\api\\README";

	public static void main(String[] args) {
		// baseAttributeView();
		// posixAttributeView();
		// dosAttributeView();
		// fileOwnerAttributeView();
		// aclAttributeView();
		userDefAttributeView();
	}

	public static void baseAttributeView() {
		Path path = FileSystems.getDefault().getPath(pathname);
		try {
			BasicFileAttributes attributes = Files.readAttributes(path,
					BasicFileAttributes.class);
			System.out.println("Creation Time: " + attributes.creationTime());
			System.out.println("Last Accessed Time: "
					+ attributes.lastAccessTime());
			System.out.println("Last Modified Time: "
					+ attributes.lastModifiedTime());
			System.out.println("File Key: " + attributes.fileKey());
			System.out.println("Directory: " + attributes.isDirectory());
			System.out.println("Other Type of File: " + attributes.isOther());
			System.out.println("Regular File: " + attributes.isRegularFile());
			System.out.println("Symbolic File: " + attributes.isSymbolicLink());
			System.out.println("Size: " + attributes.size());
		} catch (IOException ex) {
			System.out.println("Attribute error");
		}
	}

	/**
	 * Maintaining POSIX file attributes using the PosixFileAttributeView
	 */
	public static void posixAttributeView() {
		Path path = Paths.get(pathname);
		try {
			PosixFileAttributeView view = Files.getFileAttributeView(path,
					PosixFileAttributeView.class);
			PosixFileAttributes attributes = view.readAttributes();
			System.out.println("Group: " + attributes.group());
			System.out.println("Owner: " + attributes.owner().getName());
			Set<PosixFilePermission> permissions = attributes.permissions();
			for (PosixFilePermission permission : permissions) {
				System.out.print(permission.name() + " ");
			}
		} catch (IOException ex) {
			System.out.println("Attribute error");
		}
	}

	/**
	 * Maintaining FAT table attributes using the DosFileAttributeView
	 */
	public static void dosAttributeView() {
		Path path = FileSystems.getDefault().getPath(pathname);
		try {
			DosFileAttributeView view = Files.getFileAttributeView(path,
					DosFileAttributeView.class);
			DosFileAttributes attributes = view.readAttributes();
			System.out.println("isArchive: " + attributes.isArchive());
			System.out.println("isHidden: " + attributes.isHidden());
			System.out.println("isReadOnly: " + attributes.isReadOnly());
			System.out.println("isSystem: " + attributes.isSystem());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Maintaining file ownership attributes using the FileOwnerAttributeView
	 */
	public static void fileOwnerAttributeView() {
		Path path = Paths.get(pathname);
		try {
			FileOwnerAttributeView view = Files.getFileAttributeView(path,
					FileOwnerAttributeView.class);
			UserPrincipal userPrincipal = view.getOwner();
			System.out.println(userPrincipal.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maintaining a file's ACL using the AclFileAttributeView
	 */
	public static void aclAttributeView() {
		Path path = Paths.get(pathname);
		try {
			AclFileAttributeView view = Files.getFileAttributeView(path,
					AclFileAttributeView.class);
			List<AclEntry> aclEntryList = view.getAcl();
			for (AclEntry entry : aclEntryList) {
				System.out.println("User Principal Name: "
						+ entry.principal().getName());
				System.out.println("ACL Entry Type: " + entry.type());
				displayEntryFlags(entry.flags());
				displayPermissions(entry.permissions());
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void displayPermissions(Set<AclEntryPermission> permissionSet) {
		if (permissionSet.isEmpty()) {
			System.out.println("No Permissions present");
		} else {
			System.out.println("Permissions");
			for (AclEntryPermission permission : permissionSet) {
				System.out.print(permission.name() + " ");
			}
			System.out.println();
		}
	}

	private static void displayEntryFlags(Set<AclEntryFlag> flagSet) {
		if (flagSet.isEmpty()) {
			System.out.println("No ACL Entry Flags present");
		} else {
			System.out.println("ACL Entry Flags");
			for (AclEntryFlag flag : flagSet) {
				System.out.print(flag.name() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Maintaining user-defined file attributes using the
	 * UserDefinedFileAttributeView
	 */
	public static void userDefAttributeView() {
		Path path = Paths.get(pathname);
		try {
			UserDefinedFileAttributeView view = Files.getFileAttributeView(
					path, UserDefinedFileAttributeView.class);
			view.write("publishable", Charset.defaultCharset().encode("true"));
			System.out.println("Publishable set");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
