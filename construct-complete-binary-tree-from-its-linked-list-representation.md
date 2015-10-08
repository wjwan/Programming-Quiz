This puzzle is got from 
(http://www.geeksforgeeks.org/given-linked-list-representation-of-complete-tree-convert-it-to-linked-representation/)

Given Linked List Representation of Complete Binary Tree, construct the Binary tree. A 
complete binary tree can be represented in an array in the following approach.

If root node is stored at index i, its left, and right children are stored at indices 
2*i+1, 2*i+2 respectively.

Suppose tree is represented by a linked list in same way, how do we convert this into 
normal linked representation of binary tree where every node has data, left and right 
pointers? In the linked list representation, we cannot directly access the children of 
the current node unless we traverse the list.

The complete tree is stored in the linked list by level traversal order
10->12->15->25->30->36

		 10
		/    \
	  12	   15
    /   \      /
  25	 30   36
  
TreeNode* getTree(ListNode *head){
    ListNode *p = head;
    queue<TreeNode*> nodeQueue;
    TreeNode *root = new TreeNode(head->val);
    nodeQueue.push(root);
    p = p->next;
    while(p){
        TreeNode *tmp = nodeQueue.front();
        nodeQueue.pop();
        if(p){
            tmp->left = new TreeNode(p->val);
            nodeQueue.push(tmp->left);
            p = p->next;
        }
        if(p){
            tmp->right = new TreeNode(p->val);
            nodeQueue.push(tmp->right);
            p = p->next;
        }
    }
    return root;
}