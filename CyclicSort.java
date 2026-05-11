/*
   this wil work when the input range is aleay [1 to N]
   and no duplicates
*/

import java.util.Scanner;  
import java.util.Arrays;
public class CyclicSort{
	public static void main(String[] args){
		int[] userInputArray = takeUserInput();
		System.out.println("Arrays befoe sort "+ Arrays.toString(userInputArray));
		sort(userInputArray);
		System.out.println("Arrays after sort "+ Arrays.toString(userInputArray));
		
	}
	
	public static void sort(int[] arr){  // dummy array [1,5,6,3,4,2]
		
		int i = 0;
		while(i<arr.length){
			int correctIndex = arr[i] - 1;
			
			if(i != correctIndex){
				int temp = arr[correctIndex];
				arr[correctIndex] = arr[i];
				arr[i] = temp;
				
			}else{
				i++;
			}
			
		}
		
	}
	
	
	
	public static int[] takeUserInput(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of the Array");
		
		int size = sc.nextInt();
		
		int[] arr = new int[size];
		System.out.println("Enter the values for the Array");
		
		for(int i = 0;i<size;i++){
			arr[i] = sc.nextInt();
		}
		
		
		return arr;
		
	}
}
