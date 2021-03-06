Given a Binary Tree, find the maximum sum path from a leaf to root. For example, in the following tree, there are three leaf to root paths 8->-2->10, -4->-2->10 and 7->10. The sums of these three paths are 16, 4 and 17 respectively. The maximum of them is 17 and the path for maximum is 7->10.
```
                  10
               /      \
	     -2        7
           /   \     
	 8     -4    
```
(This puzzle is got from http://www.geeksforgeeks.org/find-the-maximum-sum-path-in-a-binary-tree/)

algorithm:
1.if the node is leaf node, return itself
2.get the two sum of two children and choose the larger one, put curr node inside and return
```c++
vector<TreeNode*> getMaxSumPath(TreeNode* root, int &sum){
    vector<TreeNode*> result;
    if(root==NULL){
        sum = 0;
        return result;
    }
        int left = 0;
    int right = 0;
    vector<TreeNode*> leftVec = getMaxSumPath(root->left,left);
    vector<TreeNode*> rightVec = getMaxSumPath(root->right,right);
    if(left>right){
        result = leftVec;
        result.push_back(root);
        sum = left+root->val;
    }
    else{
        result = rightVec;
        result.push_back(root);
        sum = right+root->val;
    }
    return result;
}
```
