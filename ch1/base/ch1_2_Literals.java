package base;

public class ch1_2_Literals {

	/**
	 * 1.二进制字面量 (只要在二进制数值前面加0b或者0B)<br>
	 * 2.数字字面量可以出现下划线.<br>
	 * 注意：你只能将下划线置于数字之间，以下使用方法是错误的，<br>
	 * (1).数字的开头或者结尾<br>
	 * (2).小数点的前后<br>
	 * (3).‘F’或者‘f’的后缀<br>
	 * (4).只能用数字的位置
	 */
	public static void main(String[] args) {

		byte nByte = (byte) 0b0001;
		short nShort = (short) 0B0010;
		int nInt = 0b0011;
		long nLong = 0b0100L;
		System.out.println(nByte + ", " + nShort + ", " + nInt + ", " + nLong);

		int a = 10_0000_0000;
		long b = 0x0fff_ffff_ffff_ffffl;
		byte c = 0b0001_1000;
		System.out.println(a + ", " + b + ", " + c);
		
		int commandInHex = 0xE_23D5_8C_7;
		int commandInBinary = 0b1110_0010001111010101_10001100_0111;
		byte initializationSequence = (byte) 0b10_110_010;
		
		// int err1 = _11, err2=11_;
		// float err3=3._4f,err4=3_.4f;
		// long err5 = 0x888_l;

		long debitCard = 1234_5678_9876_5432L;
		System.out.println("The card number is: " + debitCard);
		System.out.print("The formatted card number is:");
		printFormatted(debitCard);
		float minAmount = 5_000F;
		float currentAmount = 5_250F;
		float withdrawalAmount = 500F;
		if ((currentAmount - withdrawalAmount) < minAmount) {
			System.out.println("Minimum amount limit exceeded " + minAmount);
		}
	}

	private static void printFormatted(long cardNumber) {
		String formattedNumber = Long.toString(cardNumber);
		for (int i = 0; i < formattedNumber.length(); i++) {
			if (i % 4 == 0) {
				System.out.print(" ");
			}
			System.out.print(formattedNumber.charAt(i));
		}
		System.out.println();
	}

}
