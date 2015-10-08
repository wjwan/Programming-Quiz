Construct Special Binary Tree from given Inorder traversal
Given Inorder Traversal of a Special Binary Tree in which key of every node is greater than keys in left and right children, construct the Binary Tree and return root.

```
Examples:

Input: inorder[] = {5, 10, 40, 30, 28}
Output: root of following tree
         40
       /   \
      10     30
     /         \
    5          28 
```
    
(This puzzle is got from http://www.geeksforgeeks.org/construct-binary-tree-from-inorder-traversal/)

```c++
TreeNode* getTreeFromInorderTraversal(int arr[], int begin, int end){
	if(begin>end){
		return NULL;
	}
	if(begin==end){
		return new TreeNode(arr[begin]);
	}
	
	int max = begin;
	
	for(int i=begin;i<=end;i++){
		if(arr[i]>arr[max])
			max = i;
	}
	
	TreeNode *root = new TreeNode(arr[max]);
	root->left = getTreeFromInorderTraversal(arr,begin,max-1);
	root->right = getTreeFromInorderTraversal(arr,max+1,end);
	return root;
}
```
