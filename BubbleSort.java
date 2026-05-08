import java.util.Scanner;
import java.util.Arrays;
public class BubbleSort{
	public static void main(String[] args){
	
		int[] userInput = takeUserInput();
		System.out.println(Arrays.toString(sort(userInput)));
		
		
		
	}
	
	
	public static int[] sort(int[] arr){
		for(int i = 0;i<arr.length;i++){
		
			for(int j = 1;j<arr.length - i;j++){
				if(arr[j]<arr[j-1]){
					int temp = arr[j-1];
					arr[j-1] = arr[j];
					arr[j] = temp;
					
				}
				
			}
		}
		
		return arr;
	}
	
	
	public static int[] takeUserInput(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of the array\n");
		
		
		int size = sc.nextInt();
		int[] arr = new int[size];
		
		System.out.println("Enter the values for the array\n");	
		
		for(int i = 0;i<size;i++){
			arr[i] = sc.nextInt();
		}
		
		
		System.out.println("User array: "+ Arrays.toString(arr));
		return arr;
	}
}
