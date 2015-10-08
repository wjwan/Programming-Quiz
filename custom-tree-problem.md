The puzzle is got from (http://www.geeksforgeeks.org/custom-tree-problem/)

void customPrint(vector<pair<char,char> > pairs){
    int position[26];
    memset(position,-1,sizeof(int)*26);
    stack<char> last;
    
    while(last.empty()!=true||pairs.empty()!=true){
        
        for(int i=0; i<pairs.size(); i++){
            pair<char,char> *tmp = &pairs[i];
            if(last.empty()!=true&&tmp->first!=last.top())
                continue;
            if(position[tmp->first-'a']==-1){
                //first apear and no prehead
                position[tmp->first-'a'] = 0;
                cout << "-->" << tmp->first << endl;
                position[tmp->second-'a'] = 1;
                cout << "   |-->" << tmp->second << endl;
                last.push(tmp->first);
                last.push(tmp->second);
                
            }
            else{
                position[tmp->second-'a'] = position[tmp->first-'a']+1;
                for(int k=0; k<position[tmp->second-'a']-1; k++)
                    cout << "   |";
                cout <<"   |";
                
                cout << "-->" << tmp->second << endl;
                last.push(tmp->second);
                
            }
            pairs.erase(pairs.begin()+i);
            //very important
            i--;
        }
        last.pop();
    }
}