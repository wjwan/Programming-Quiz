一亩三分地里找到的一个google面试题：
有一个电子二进制手表，有两排灯，上面一排四个灯，表示小时，下面一排6灯，表示分钟。
如下所示：（http://www.slipperybrick.com/wp-content/uploads/2007/12/led_binary_watch.jpg）
 0 0 0 0
 000000
最右边表示1，最左边表示significant位。
输入：一个整数n表示有几盏灯亮着
输出：所有可能的时间，用string表示
题目链接见（http://www.1point3acres.com/bbs/thread-141438-1-1.html）

思路：BFS
两层循环，外循环iterate from 0～x，表示上面x盏灯，下面n-x盏灯
内层循环则将所有亮灯的可能存在数组里，然后cross join两个数组

```c++
vector<string> tool(int n, int m);

vector<string> getAllPossibleTime(int n){
    int top_Max = min(4,n);
    vector<string> result;
    
    for(int i=0; i<=top_Max; i++){
        if(n-i>6)
            continue;
        
        //upper i, lower n-i
        //0<=i<=4, 0<=n-i<=6
        
        vector<string> front = tool(i,4);
        vector<string> last = tool(n-i,6);
        
        for(int k=0; k<front.size(); k++){
            for(int l=0; l<last.size(); l++){
                string tmp = front[k];
                tmp.append(":");
                tmp.append(last[l]);
                result.push_back(tmp);
            }
        }
        
    }
    
    return result;
}

vector<string> tool(int n, int m){
    int upper = 0;
    if(m==4)
        upper = 24;
    if(m==6)
        upper = 60;
    vector<string> result;
    for(int i=0; i<=upper; i++){
        int tmp = i;
        int count = 0;
        while(tmp){
            if(tmp&1)
                count++;
            tmp >>= 1;
        }
        if(count==n){
            if(i<10){
                string str = "0";
                str.append(to_string(i));
                result.push_back(str);
            }
            else{
                result.push_back(to_string(i));
            }
        }
    }
    return result;
}
```
