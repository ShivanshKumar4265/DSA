import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
public class MyTreeSet{

	
	public static void main(String[] args){
		Set<String> treeSet = new TreeSet<String>(new MyComparator());
		treeSet.add("aa");
		treeSet.add("bbb");
		treeSet.add("ghth");
		treeSet.add("aaqwerty");
		treeSet.add("ghth");
		treeSet.add("qazwsxedv");
		treeSet.add("zxcvb");
		treeSet.add("aaokmljyhg");
		treeSet.add("zxcvb");	
		treeSet.add("oklpij");
		System.out.println("Tree Set usign custome comparator for decreain ordein length"+treeSet);
		
		
		
		
	}

	static class MyComparator implements Comparator<String>{
		public int compare(String obj1, String obj2){
			String s1 = (String) obj1;
			String s2 = (String) obj2;
			if(s1.length() > s2.length()){
				return -1;
			}else if(s1.length() < s2.length()){
				return 1;
			}else{
				return 0;
			}
		}
	}
}
