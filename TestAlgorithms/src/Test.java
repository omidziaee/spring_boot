import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		TestReferencing testReferencing = new TestReferencing();
		List<String> refName = testReferencing.getNames();
		refName.add("Omid");
		refName.add("Maryam");
		for(String name: testReferencing.getNames()) {
			System.out.println(name);
		}
		List<Integer> temp = new ArrayList<>();
		temp.add(10);
		temp.add(20);
		temp.add(30);
		changeList(temp);
		for(Integer i: temp) {
			System.out.println(i);
		}
		
		final boolean useOmid = testReferencing.isUseOmid();
		if(testReferencing.isUseOmid()) {
			System.out.println("this is omid");
		} else {
			System.out.println("this is not omid");
		}
		

	}
	
	public static void changeList(List<Integer> in) {
//		for(int i = 0; i < in.size(); i++) {
//			in.set(i, in.get(i) + 10);
//		}
		List<Integer> in2 = new ArrayList<>();
		in2.add(1);
		in2.add(2);
		in2.add(3);
		in = in2;
		in.add(40);
		System.out.println(in);
		
	}

}
