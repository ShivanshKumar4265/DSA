import java.util.HashMap;
import java.util.Map;
public class Frequency{
	public static void main(String[] args){
		String input = "shivansh pandey qwertyuiop[qwertyuiop[qwertyuiop[asdfghjkl";
		Map<Character, Integer> map = new HashMap<>();
		for(int i = 0;i<input.length();i++){
			char c = input.charAt(i);
			if(c == ' '){
				continue;
			}

			//System.out.println("char at "+i + " "+ c);
			// when keys insert firt time, then map.get(c) will return null so we can use getOrDefault() or some logic for it;
			map.put(c, map.get(c) == null ? 1: map.get(c) + 1);
		}

		System.out.println(map);
		
	}
}
