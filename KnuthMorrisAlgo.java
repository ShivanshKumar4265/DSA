class KnuthMorrisAlgo {
// KnuthMorrisAlgo learn this very important
    public int strStr(String haystack, String needle) {
        if (haystack.length() < needle.length()) {
            return -1;
        }

        int hLen = haystack.length();
        int nLen = needle.length();
        int j = 0;
        int ans = 0;
        for (int i = 0; i < hLen; i++) {
            char ch = needle.charAt(j);
            if(haystack.charAt(i) != ch){
                i = i-j; //for this I took help of gpt
                j = 0;
            }else{
                j++;
                if(j == needle.length()){
                    return ( i - nLen ) + 1;
                }
            }
        }

        return -1;
    }
}
