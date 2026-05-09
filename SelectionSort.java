
/*
 * during implemeting the selection sor i used two loop unnecessayr, and also for start and last index, there was a logical issue, and for finding biggest and smallest elemnt  there was logical issue
 */
 
 
 
 
/*
========================================================
                SELECTION SORT — NOTES
========================================================

My Original Idea
----------------
1. Find the biggest element in the unsorted part
2. Swap it with the last unsorted position
3. Reduce the search space
4. Repeat

The overall idea was correct.

========================================================
Mistake 1 — Wrong Initial Index
========================================================

What I wrote:

    int biggetElementIndex = 0;

Why it was wrong:
-----------------
The searching was starting from:

    start_index

So if the biggest element was already at
start_index, then the index should also
start from start_index.

By initializing with 0, sometimes the
wrong index was returned and incorrect
swapping happened.

Correct version:

    int biggetElementIndex = start_index;

========================================================
Mistake 2 — Unnecessary Inner Loop
========================================================

What I wrote:

    for(int j = 0; j < arr.length - i; j++)

Why it was wrong:
-----------------
Selection sort only needs:

1. One scan to find biggest element
2. One swap per pass

But my code was:
- finding biggest multiple times
- swapping multiple times in same pass

This caused unnecessary operations and
incorrect behavior.

Correct understanding:
----------------------

    1 pass = 1 selection + 1 swap

========================================================
Mistake 3 — Wrong Search Range
========================================================

What I wrote:

    int start_index = i;

Why it was wrong:
-----------------
My version of selection sort works like:

- place biggest element at end
- shrink array from right side

So every pass should search from:

    0 → last unsorted index

But by using:

    start_index = i;

I skipped elements at beginning that were
still unsorted.

Example:
--------

After first pass:

    [1, 4, 3, 2, 5]

Next pass used:

    start_index = 1

Search happened only in:

    [4, 3, 2]

Element 1 at index 0 was ignored even
though array was not fully sorted.

Correct version:

    int start_index = 0;

========================================================
Final Understanding
========================================================

My logic actually works like this:

Pass 1:
- Find biggest from whole array
- Move it to last position

Pass 2:
- Find biggest from remaining unsorted part
- Move it to second last position

Pass 3:
- Repeat

Meaning:
--------
- Right side becomes sorted first
- Left side remains unsorted initially

So these are correct:

    start_index = 0;
    last_index = arr.length - i;

========================================================
Final Mental Model
========================================================

Search Range:

    0 → last unsorted index

After every pass:
- largest element gets fixed at end

========================================================
Important Learning
========================================================

Before coding sorting algorithms:

1. Decide which side becomes sorted
   - left side?
   - right side?

2. Then design loop boundaries accordingly

Because loop boundaries are one of the
most common sources of bugs in sorting
algorithms.

========================================================
*/

import java.util.Scanner;
import java.util.Arrays;
public class SelectionSort{
	public static void main(String[] args){
		int[] arr = takeUserInput();
		System.out.println("User input array: "+ Arrays.toString(arr));
		
		sortdecreaseing(arr);
		
		System.out.println("User input array after sort: "+ Arrays.toString(arr));
							
	}
	
	
	public static void sort(int[] arr){
	
		for(int i = 0;i<arr.length;i++){
			//for(int j = 0;j<arr.length-i;j++){
			
				int start_index = 0;
				int last_index = arr.length-i;
				int biggestElement = biggestElement(arr, start_index, last_index);	
				// swap 
				int temp = arr[biggestElement];
				arr[biggestElement] = arr[last_index-1];
				arr[last_index-1] = temp;
				
			//}
		}


	}
	
	public static void sortdecreaseing(int[] arr){
	
		for(int i = 0;i<arr.length;i++){
			//for(int j = 0;j<arr.length-i;j++){
			
				int start_index = 0;
				int last_index = arr.length-i;
				int smallestElement = smallestElement(arr, start_index, last_index);	
				// swap 
				int temp = arr[smallestElement];
				arr[smallestElement] = arr[last_index-1];
				arr[last_index-1] = temp;
				
			//}
		}


	}
	
	
	public static int biggestElement(int[] arr, int start_index, int last_index){
		int biggestElement = arr[start_index];
		int biggetElementIndex = start_index;
		for(int i = start_index + 1;i<last_index;i++){
			if(arr[i]>biggestElement){
				biggestElement = arr[i];
				biggetElementIndex= i;
			}
		}
		
		System.out.println("Biggest Element "+ biggestElement);
		System.out.println("Biggest Element index"+ biggetElementIndex);
		
		return biggetElementIndex;
	}
	
	
	public static int smallestElement(int[] arr, int start_index, int last_index){
		int smallestElement = arr[start_index];
		int smallestElementIndex = start_index;
		for(int i = start_index + 1;i<last_index;i++){
			if(arr[i]<smallestElement){
				smallestElement = arr[i];
				smallestElementIndex= i;
			}
		}
		
		System.out.println("Biggest Element "+ smallestElement);
		System.out.println("Biggest Element index"+ smallestElementIndex);
		
		return smallestElementIndex;
	}
	
	
	public static int[] takeUserInput(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of array");
		
		int size = sc.nextInt();
		int[] arr = new int[size];
		
		System.out.println("Enter the values for array");
		
		for(int i = 0;i<size;i++){
			arr[i] = sc.nextInt();
		}
		return arr;
	}
}
