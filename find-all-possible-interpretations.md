Find all possible interpretations of an array of digits
Consider a coding system for alphabets to integers where ‘a’ is represented as 1, ‘b’ as 
2, .. ‘z’ as 26. Given an array of digits (1 to 9) as input, write a function that prints 
all valid interpretations of input array.
Puzzle got from(http://www.geeksforgeeks.org/find-all-possible-interpretations/)

Examples
```
Input: {1, 1}
Output: ("aa", 'k") 
[2 interpretations: aa(1, 1), k(11)]

Input: {1, 2, 1}
Output: ("aba", "au", "la") 
[3 interpretations: aba(1,2,1), au(1,21), la(12,1)]

Input: {9, 1, 8}
Output: {"iah", "ir"} 
[2 interpretations: iah(9,1,8), ir(9,18)]
```
```c++
vector<string> getAllPossibleInterpretations(vector<int> nums){
    int size = nums.size();
    vector<string> result;
    if(size==0)
        return result;
    vector<vector<string> > DP(3,vector<string>(1,""));
    for(int i=0; i<size; i++){
        
        DP[i%3].clear();
        
        //one digit
        if(nums[i]>0){
            for(int k=0; k<DP[(i+2)%3].size(); k++){
                string tmp = DP[(i+2)%3][k];
                tmp += ('a'+(nums[i]-1));
                DP[i%3].push_back(tmp);
            }
        }
        
        //two digits
        if(i>0&&(nums[i-1]==1||(nums[i-1]==2&&nums[i]<7))){
            int add = nums[i-1]*10+nums[i];
            char c = 'a'+add-1;
            for(int k=0; k<DP[(i+1)%3].size(); k++){
                string tmp = DP[(i+1)%3][k];
                tmp += c;
                DP[i%3].push_back(tmp);
            }
        }
    }
    
    return DP[(size-1)%3];
}
```
Time O(N)
Space O(N)
