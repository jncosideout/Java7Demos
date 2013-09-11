package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

/**
 * Determining the file content type
 */
public class ch3_2_Attribute {

	public static void main(String[] args) throws Exception {
		// getAttribute();
		// obtainAttribute();
		obtainMapAttribute();
	}

	/**
	 * AttributeName---------------DataType<br>
	 ******************************* <br>
	 * lastModifiedTime------------FileTime<br>
	 * lastAccessTime--------------FileTime<br>
	 * creationTime----------------FileTime<br>
	 * size------------------------long<br>
	 * isRegularFile---------------Boolean<br>
	 * isDirectory-----------------Boolean<br>
	 * isSymbolicLink--------------Boolean<br>
	 * isOther---------------------Boolean<br>
	 * fileKey---------------------Object<br>
	 */
	public static void getAttribute() {
		try {
			Path path = Paths.get("C:\\go\\api\\README");
			System.out.println(Files.getAttribute(path, "size"));
			System.out.println(Files.getAttribute(path, "lastModifiedTime"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void obtainAttribute() {
		Path path = Paths.get("C:\\go\\api\\README");
		try {
			Map<String, Object> attrsMap = Files.readAttributes(path, "*");
			Set<String> keys = attrsMap.keySet();
			for (String attribute : keys) {
				System.out.println(attribute + ": "
						+ Files.getAttribute(path, attribute));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtaining a map of file attributes <br>
	 * String Attributes----returned <br>
	 * "*"------------------All ofthe basic file attributes <br>
	 * "basic:*"----------- All of the basic file attributes<br>
	 * "basic:isDirectory,lastAccessTime"---------Only the isDirectory and lastAccessTime attributes<br>
	 * "isDirectory,lastAccessTime"---------------Only the isDirectory and lastAccessTime attributes<br>
	 * ""---------------None - a java.lang.IllegalArgumentException is generated<br>
	 */
	public static void obtainMapAttribute() throws Exception {
		Path path = Paths.get("C:\\go\\api\\README");
		Map<String, Object> attrsMap = Files.readAttributes(path, "*");
		Set<String> keys = attrsMap.keySet();
		for (String attribute : keys) {
			System.out.println(attribute + ": "
					+ Files.getAttribute(path, attribute));
		}
	}
}
