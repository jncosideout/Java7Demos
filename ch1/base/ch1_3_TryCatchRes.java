package base;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Creating a resource that can be used with the try-with-resources technique
 */
public class ch1_3_TryCatchRes {

	public static void main(String[] args) throws Exception {
		try (BufferedReader inputReader = Files.newBufferedReader(
				Paths.get(new URI("file:///C:/home/docs/users.txt")),
				Charset.defaultCharset());
				BufferedWriter outputWriter = Files.newBufferedWriter(
						Paths.get(new URI("file:///C:/home/docs/users.bak")),
						Charset.defaultCharset())) {
			String inputLine;
			while ((inputLine = inputReader.readLine()) != null) {
				outputWriter.write(inputLine);
				outputWriter.newLine();
			}
			System.out.println("Copy complete!");
		} catch (URISyntaxException | IOException ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}finally{
			//...
		}

	}
}
