package base;

public class ch1_1_StringSwitchCase {

	private static String[] daysInWeek = new String[] { "SUNDAY", "MONDAY",
			"TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };

	public static void main(String[] args) {
		for (String day : daysInWeek) {
			switch (day) {
			case "MONDAY":
				System.out.println("Oh, My god! Starting work again.");
				break;
			case "TUESDAY":
				System.out.println("The second day...");
				break;
			case "WEDNESDAY":
				System.out.println("The half of a week is passed.");
				break;
			case "THURSDAY":
				System.out.println("Just need another day. hoho!");
				break;
			case "FRIDAY":
				System.out.println("haha...");
				break;

			default:
				System.out.println("weekend...");
				break;
			}
		}
	}

}
