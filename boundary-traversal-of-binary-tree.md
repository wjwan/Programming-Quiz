Boundary Traversal of binary tree
Given a binary tree, print boundary nodes of the binary tree Anti-Clockwise starting from the root. For example, boundary traversal of the following tree is "20 8 4 10 14 25 22"

```
		20
	   /  \
	 8     22
    /  \     \
   4    12    25
       /  \  
      10  14
```

We break the problem in 3 parts:
1. Print the left boundary in top-down manner.
2. Print all leaf nodes from left to right, which can again be sub-divided into two sub-parts:
…..2.1 Print all leaf nodes of left sub-tree from left to right.
…..2.2 Print all leaf nodes of right subtree from left to right.
3. Print the right boundary in bottom-up manner.

(This problem is got from http://www.geeksforgeeks.org/boundary-traversal-of-binary-tree/)

```c++
void leafTraversal(TreeNode *root){
    if(root->left==NULL&&root->right==NULL){
        cout << root->val << " ";
    }
    else{
        if(root->left)
            leafTraversal(root->left);
        if(root->right)
            leafTraversal(root->right);
    }
}


void boundaryTraversal(TreeNode *root){
    //left boundary
    TreeNode *p = root;
    while(p->left){
        cout << p->val << " ";
        p = p->left;
    }
    
    //all the leaves
    leafTraversal(root);
    
    //right boudary
    p = root->right;
    stack<int> ss;
    while(p){
        ss.push(p->val);
        p = p->right;
    }
    while(ss.empty()!=true){
        cout << ss.top() << " ";
        ss.pop();
    }
    
}
```
