import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
public class MyMap{
	public static void main(String[] args){
		Map<Character, Integer> map = new HashMap<>();
		System.out.println("Before adding " + map);
		System.out.println("before Adding Map Size " + map.size());

		map.put('D', 101);   // Delhi
		map.put('M', 102);   // Mumbai
		map.put('C', 103);   // Chennai
		map.put('K', 104);   // Kolkata
		map.put('B', 105);   // Bangalore
		map.put('H', 106);   // Hyderabad
		map.put('P', 107);   // Pune
		map.put('A', 108);   // Ahmedabad
		map.put('J', 109);   // Jaipur

		System.out.println(
			"Old value replaced by new val 500, old val = "
			+ map.put('B', 500)
		);

		System.out.println("after adding " + map);
		System.out.println("after Adding Map Size " + map.size());

		Map<Character, Integer> map1 = new HashMap<>();

		map1.put('L', 201);   // Lucknow
		map1.put('S', 202);   // Surat
		map1.put('N', 203);   // Noida
	
		
		map.putAll(map1);
		
		
		System.out.println("after adding put all "+ map1);
		System.out.println("after Adding put all Map Size " + map1.size());
		
		System.out.println("original after adding put all "+ map);
		System.out.println("original after Adding put all Map Size " + map.size());
		
		/*
			so both the key and val dont exist, then the entry  wont be deleted
		*/
		map.remove('k', (Integer) 12); 
		
		System.out.println("\n\n\n---------------------------------------------------------------");
		
		System.out.println("after remove with both key and value "+ map);
		System.out.println("after remove with both key and value size " + map.size());
		
		
		/*
			so both the key and val dont exist, then the entry  wont be deleted, if exist then deleted
		*/
		
		map.remove('k', (Integer) 1); 
		System.out.println("\n\n\n---------------------------------------------------------------");
		
		System.out.println("after remove with both key and value "+ map);
		System.out.println("after remove with both key and value size " + map.size());
		
		
		map.remove('b'); 
		System.out.println("\n\n\n---------------------------------------------------------------");
		
		System.out.println("after remove with only key  "+ map);
		System.out.println("after remove with only key  " + map.size());
		
		
		// -----------------------------------------------------------------
		
		System.out.println("get value of key g  " + map.get('g')); // the the key exist , the we got the value, but what if
																   // the key doent exit try to get the val. here we have have key b
																   // coz we remove it above;
																   
		System.out.println("get value of key b (doesnt exist)  " + map.get('b')); // if key doent exit then we will get null
		
		
		System.out.println("\n\n\n---------------------------------------------------------------");
		map1.clear();
		System.out.println("after clear the map1   "+ map1);
		System.out.println("after clear the map1 size  " + map1.size());
		
		
		System.out.println("\n\n\n---------------------------------------------------------------");
		System.out.println("containsKey w "+ map.containsKey('w'));
		System.out.println("containsKey a "+ map.containsKey('a'));
		System.out.println("containsKey a "+ map.containsKey(1));
		
		
		System.out.println("\n\n\n---------------------------------------------------------------");
		
		
		
		Set<Set<Character>> keySetOfMap = new HashSet<>(); // here we are creating the set of set of character
														   // but, we can direclty assign the map.keySet as it return set only
		keySetOfMap.add(map.keySet());
		System.out.println(keySetOfMap);
		
		// this can be also done
		Set<Character> set = map.keySet();
		System.out.println("Key set of the the map "+ set);
		
		System.out.println("\n\n\n-----------------Iteration using list in set-----------------------------");
		Iterator iterator = set.iterator();
		
		while(iterator.hasNext()){
			System.out.println("set Data -> " + iterator.next());
		}
		
		
		System.out.println("\n\n\n-----------------Getting the values of the map apart from the keys ---------");
		
		List<Integer> listOfVal = new ArrayList<>(map.values()); // this is right, 

		// List<Integer>  listOfVal  = (ArrayList) map.values(); // this is wrong 
		
		
		Collection collection = map.values(); // this is also right 
		
		System.out.println("listOfVal : "+listOfVal);
		System.out.println("collection of values of map : "+collection);
		
		System.out.println("\n\n\n-----------------will fetch the entry of map--------");
		
		// first enetry is the inteface inseid the map interface, it has 3 method
		// 1. object getKey();
		// 2. object getValue();
		// 3. object setValue();
		
		Set entrySet = map.entrySet();
		
		Iterator it = entrySet.iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry)it.next();
			System.out.println("Entry key :" + entry.getKey() + " Entry Value : " + entry.getValue());

			if(entry.getKey().equals('A')){
				entry.setValue((Integer)7890);
			}
		}
		
		
	}
}
