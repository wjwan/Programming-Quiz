Extract Leaves of a Binary Tree in a Doubly Linked List

Given a Binary Tree, extract all leaves of it in a Doubly Linked List (DLL). Note that the 
DLL need to be created in-place. Assume that the node structure of DLL and Binary Tree is 
same, only the meaning of left and right pointers are different. In DLL, left means 
previous pointer and right means next pointer.


Let the following be input binary tree
        1
     /     \
    2       3
   / \       \
  4   5       6
 / \         / \
7   8       9   10


Output:
Doubly Linked List
7<->8<->5<->9<->10

Modified Tree:
        1
     /     \
    2       3
   /         \
  4           6
  
  
Got from(http://www.geeksforgeeks.org/connect-leaves-doubly-linked-list/)

void leavesToList(TreeNode* root, TreeNode* &left, TreeNode* &right){
    
    if(root->left==NULL && root->right==NULL){
        left=root;
        right=root;
        return;
    }
    TreeNode *lLeft=NULL, *lRight=NULL, *rLeft=NULL, *rRight=NULL;
    if(root->left)
        leavesToList(root->left,lLeft,lRight);
    if(root->right)
        leavesToList(root->right,rLeft,rRight);
    if(lLeft==lRight&&lLeft==root->left){
        root->left = NULL;
    }
    if(rLeft==rRight&&rLeft==root->right){
        root->right = NULL;
    }
    if(lRight)
        lRight->right = rLeft;
    if(rLeft)
        rLeft->left = lRight;
    if(lLeft)
        left = lLeft;
    else
        left = rLeft;
    if(rRight)
        right = rRight;
    else
        right = lRight;
}