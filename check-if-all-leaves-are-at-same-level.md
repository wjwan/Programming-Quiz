Check if all leaves are at same level
Given a Binary Tree, check if all leaves are at same level or not.

          12
        /    \
      5       7       
    /          \ 
   3            1
  Leaves are at same level

          12
        /    \
      5       7       
    /          
   3          
   Leaves are Not at same level


          12
        /    
      5             
    /   \        
   3     9
  /      /
 1      2
 Leaves are at same level
 
 Puzzle got from(http://www.geeksforgeeks.org/check-leaves-level/)
 
 bool checkAllLeaves(TreeNode *root, int level, int &allLevel){
 	if(root==NULL){
 		return true;
 	}
 	else{
 		if(root->left==NULL&&root->right==NULL){
 			if(allLevel==-1||level==allLevel){
 				allLevel = level;
 				return true;
 			}
 			else
 				return false;
 		}
 		else{
 			return checkAllLeaves(root->left,level+1,allLevel)&&checkAllLeaves(root->right,level+1,allLevel);
 		}
 	}
 }