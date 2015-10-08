周四Google电面完，周五收到onsite，周六来发下面经。

可能是因为我做得慢，总共就做了一道题，就是一个游戏。 
input string：“+++--++-+”-google 1point3acres
游戏规则：每人每次可以 flip "++" to "--"（必须是相邻的）

. from: 1point3acres.com/bbs 
第一问：generate all possible moves
第二问：determine if this player can will
Extra：这两个问题的implementation的Big-O


补充内容 (2015-7-12 13:13):
下个人没法flip了这个人就赢；all possible moves for next flip

http://www.1point3acres.com/bbs/thread-137928-1-1.html
```c++
vector<string> allPossibleFlips(string currState){
    int size = currState.length();
    vector<string> result;
    if(size<2)
        return result;
    for(int i=0; i<size-1; i++){
        if(currState[i]=='+'&&currState[i+1]=='+'){
            string tmp = currState;
            tmp[i] = '-';
            tmp[i+1] = '-';
            result.push_back(tmp);
        }
    }
    
    return result;
}


bool haveNextFlip(string currState){
    int size = currState.length();
    vector<string> result;
    if(size<2)
        return false;
    for(int i=0; i<size-1; i++){
        if(currState[i]=='+'&&currState[i+1]=='+'){
            string tmp = currState;
            tmp[i] = '-';
            tmp[i+1] = '-';
            result.push_back(tmp);
            return true;
        }
    }
    
    return false;
}


bool ifCanWinOneStep(string currState){
    vector<string> nextFlips = allPossibleFlips(currState); //O(N)
    if(nextFlips.size()>0){
        for(int i=0; i<nextFlips.size(); i++){ //N
            if(haveNextFlip(nextFlips[i])==false) //O(N)
                return true;
        }
    }
    return false;
}



bool ifCanWinAllSteps(string currState, int count){
    vector<string> nextFlips = allPossibleFlips(currState);
    if(count&1){
        //competitor round
        for(int i=0; i<nextFlips.size(); i++){
            if(haveNextFlip(nextFlips[i])==false){
                return false;
            }
        }
        for(int i=0; i<nextFlips.size(); i++){
            if(!ifCanWinAllSteps(nextFlips[i],count+1)){
                return false;
            }
        }
        return true;
    }
    else{
        //self round
        for(int i=0; i<nextFlips.size(); i++){
            if(!haveNextFlip(nextFlips[i])){
                return true;
            }
        }
        for(int i=0; i<nextFlips.size(); i++){
            if(ifCanWinAllSteps(nextFlips[i],count+1)){
                return true;
            }
        }
        return false;
    }
}
```
