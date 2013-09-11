package base;
public class ch1_4_FirstAutoCloseableResource implements AutoCloseable {

	@Override
	public void close() throws Exception {
		// Close the resource as appropriate
		System.out.println("FirstAutoCloseableResource close method executed");
		throw new UnsupportedOperationException(
				"A problem has occurred in	FirstAutoCloseableResource");
	}

	public void manipulateResource() {
		// Perform some resource specific operation
		System.out
				.println("FirstAutoCloseableResource	manipulateResource method executed");
	}

	public static void main(String[] args) {
		try (ch1_4_FirstAutoCloseableResource resource1 = new ch1_4_FirstAutoCloseableResource();
				ch1_4_SecondAutoCloseableResource resource2 = new ch1_4_SecondAutoCloseableResource()) {
			resource1.manipulateResource();
			resource2.manipulateResource();
		} catch (Exception e) {
			e.printStackTrace();
			for (Throwable throwable : e.getSuppressed()) {
				System.out.println(throwable);
			}
		}
	}

}
