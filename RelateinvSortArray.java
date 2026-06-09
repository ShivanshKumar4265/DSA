class Solution {

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int max = findMaxElement(arr1);
        System.out.println("max " + max);

        int[] freqArray = createFreqArray(max, arr1);
        System.out.println("freqArray " + Arrays.toString(freqArray));

        int[] ans = sort(arr1, arr2, max, freqArray);
        System.out.println("ans " + Arrays.toString(ans));

        return ans;
    }

    public int[] sort(int[] arr1, int[] arr2, int max, int[] freqArray) {
        // arr1 is original
        // arr2 for relative
        // freqArr for freq array

        /**
                [0, 1, 3, 2, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1]
        */

        int currentIndex = 0;

        for (int i = 0; i < arr2.length; i++) {
            int number = arr2[i];
            int frq = freqArray[number];
            while (frq > 0) {
                arr1[currentIndex] = number;
                currentIndex++;
                frq--;
                // below code is reducung the frequwncy  also and after putting all the  it will becoem zero 
                freqArray[number]--;
            }
        }

        System.out.println("currentIndex" + currentIndex);

        for (int num = 0; num <= max; num++) {
            while (freqArray[num] > 0) {
                arr1[currentIndex++] = num;
                freqArray[num]--;
            }
        }

        return arr1;
    }

    public int[] createFreqArray(int max, int[] input) {
        int[] freqArr = new int[max + 1];
        // _ _ _ _ _ _ _ _ _ _
        //0 1 2 3 4 5 6 7 8 9

        for (int i = 0; i < input.length; i++) {
            freqArr[input[i]] = freqArr[input[i]] + 1;
        }

        return freqArr;
    }

    public int findMaxElement(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}
