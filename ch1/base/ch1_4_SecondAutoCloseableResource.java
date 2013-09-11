package base;
public class ch1_4_SecondAutoCloseableResource implements AutoCloseable {

	@Override
	public void close() throws Exception {
		// Close the resource as appropriate
		System.out.println("SecondAutoCloseableResource close	method executed");
		throw new UnsupportedOperationException(
				"A problem has occurred in	SecondAutoCloseableResource");
	}

	public void manipulateResource() {
		// Perform some resource specific operation
		System.out
				.println("SecondAutoCloseableResource	manipulateResource method executed");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
