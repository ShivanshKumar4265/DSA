class Solution {
  public static void main(String[] args){
    System.out.println(singleNumber(new int[]{4,1,2,1,2}))  ;  
  }
  
    public int singleNumber(int[] nums) {
        /**
            Input =  [4,1,2,1,2]
            we have to fing the one data that is not repeated, we will use  the xor  for it
            4 ^ 1 ^ 2 ^ 1 ^ 2

            = 4 ^ (1 ^ 1) ^ (2 ^ 2)

            = 4 ^ 0 ^ 0

            = 4
         */
        int ans = 0;
        for(int item : nums){
            ans = ans ^ item;
        }

        return ans;

    }
}
