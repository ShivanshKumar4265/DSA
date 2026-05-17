import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
public class MyQueue{
	public static void main(String[] args){
		Queue<Integer>  pq = new PriorityQueue<>(new MyComparator());
		for(Integer i = 0;i<10;i++){
			pq.offer(i);
		}
		
		pq.offer(5);
		
		System.out.println("Priority Queue "+ pq);
		System.out.println("Priority Queue head "+ pq.peek());
		System.out.println("Priority Queue head element "+ pq.element()); //
		
		pq.remove(9);
		System.out.println("Priority Queue after remove "+ pq);
		
	}
	
	
	
	// here we are implemnt our own custom order
	 static class MyComparator implements Comparator{
	
		public int compare(Object obj1, Object obj2){
			Integer i1 = (Integer) obj1;
			Integer i2 = (Integer) obj2;
			
			if(i1 > i2){
				return 1;
			}else if(i1 < i2){
				return -1;
			}else{
				return 0;
			}
			/*
				-ve when i1 come before i2
				+ve when i1 come after 12
				0   when i1 == i2
			*/
			// return i2.compareTo(i1);
		}
	}
}


