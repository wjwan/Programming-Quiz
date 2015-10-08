Largest Independent set in a given binary tree
(See original page in http://www.geeksforgeeks.org/largest-independent-set-problem/)

This problem can be solved with DP.
Two cases:
1. the current node is included and all its children will not be included. All its grandchildren will be included.
2. the current node is not included. Its children is included.

`LISS(X) = MAX{(1 + sum of LISS for all grandchildren of x)
			  (sum of LISS for all children of x)}`

```c++			  
int getLISS(TreeNode *root){
	if(root==NULL)
		return 0;
	if(root->left==NULL&&root->right==NULL){
		return 1;
	}
	if(root->left==NULL){
		return 1+getLISS(root->right);
	}
	if(root->right==NULL){
		return 1+getLISS(root->left);
	}
	
	int children = getLISS(root->left)+getLISS(root->right);
	int grandChildren = getLISS(root->left->left)+getLISS(root->left->right)+getLISS(root->right->left)+getLISS(root->right->right);
	return max(children,1+grandChildren);
}
```
