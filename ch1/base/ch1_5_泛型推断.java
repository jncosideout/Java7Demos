package base;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ch1_5_泛型推断 {
	//before
	public static Map<String, List<String>> myMap1 = new HashMap<String, List<String>>();
	
	//now
	public static Map<String, List<String>> myMap2 = new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
