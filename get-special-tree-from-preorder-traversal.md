Construct a special tree from given preorder traversal
Given an array ‘pre[]’ that represents Preorder traversal of a spacial binary tree where every node has either 0 or 2 children. One more array ‘preLN[]’ is given which has only two possible values ‘L’ and ‘N’. The value ‘L’ in ‘preLN[]’ indicates that the corresponding node in Binary Tree is a leaf node and value ‘N’ indicates that the corresponding node is non-leaf node. Write a function to construct the tree from the given two arrays.

Source: Amazon Interview Question

```
Example:

Input:  pre[] = {10, 30, 20, 5, 15},  preLN[] = {'N', 'N', 'L', 'L', 'L'}
Output: Root of following tree
          10
         /  \
        30   15
       /  \
      20   5
```
      
(This puzzle is got from http://www.geeksforgeeks.org/construct-a-special-tree-from-given-preorder-traversal/)

```c++
TreeNode* getSpecialTreeFromPreorderTraversal(int pre[], char preLN[], int size){
	TreeNode* root = NULL;
	if(size==0)
		return root;
	stack<TreeNode*> nodeStack;
	for(int i=0;i<size;i++){
		TreeNode* tmp = new TreeNode(pre[i]);
		TreeNode* top = NULL;
		if(preLN[i]=='N'){
			if(root==NULL)
				root = tmp;
			else{
				top = nodeStack.top();
				top->left = tmp;
			}
			nodeStack.push(tmp);
		}
		else{
			top = nodeStack.top();
			if(top->left==NULL)
				top->left = tmp;
			else{
				top->right = tmp;
				nodeStack.pop();
			}
		}
	}
	return root;
}
```
