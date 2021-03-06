Given two string S and T with same length, the distance is defined as the number of positions in which S and T have different characters. Your task is to minimize this distance, by swap at most 2 characters (which means at most 1 swap) in S. Return the two index. If it is not necessary to swap, return -1, -1. Both S and T contain only lowercase alphabets. If there is multiple solutions, return anyone.

First we go through the two strings from the beginning to the end. Store the different position into two hash tables separately. Then the problem can be discussed in two ways.
1. a swap with will -2 the distance
2. a swap will -1 the distance
3. no need to swap
we could check those three kind of swaps

```c++
vector<int> getSwap(string s1, string s2){
    int len1 = s1.length();
    int len2 = s2.length();
    map<char,vector<int>> mapS1;
    map<char,vector<int>> mapS2;
    for(int i=0; i<min(len1,len2);i++){
        if(s1[i]!=s2[i]){
            
            //mapS1 stores what s1 need
            if(mapS1.count(s2[i])==0){
                vector<int> tmpVec;
                mapS1[s2[i]] = tmpVec;
            }
            mapS1[s2[i]].push_back(i);
            
            //mapS2 stores what s2 can offer
            if(mapS2.count(s2[i])==0){
                vector<int> tmpVec;
                mapS2[s2[i]] = tmpVec;
            }
            mapS2[s2[i]].push_back(i);
        }
    }
    vector<int> result;
    vector<int> tmpResult;
    for(std::map<char,vector<int>>::iterator it=mapS1.begin(); it!=mapS1.end(); ++it){
        char cS1Need = it->first;
        if(mapS2.count(cS1Need)>0){
            vector<int> tmpVec = it->second;
            vector<int> tmpVecS2 = mapS2[cS1Need];
            for(int i=0; i<tmpVec.size(); i++){
                char S1Original = s1[tmpVec[i]];
                for(int j=0; j<tmpVecS2.size(); j++){
                    char S2Need = s1[tmpVecS2[j]];
                    if(tmpVec[i]==tmpVecS2[j])
                        continue;
                    if(S1Original==S2Need){
                        result.push_back(tmpVec[i]);
                        result.push_back(tmpVecS2[j]);
                        break;
                    }
                    else{
                        if(tmpResult.empty()){
                            tmpResult.push_back(tmpVec[i]);
                            tmpResult.push_back(tmpVecS2[j]);
                        }
                    }
                }
            }
        }
    }
    
    if(result.empty()&&tmpResult.empty()){
        return vector<int>({-1,-1});
    }
    else if(result.empty()!=true)
        return result;
    else
        return tmpResult;
    
}
```
