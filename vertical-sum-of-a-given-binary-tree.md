Given a Binary Tree, find vertical sum of the nodes that are in same vertical line. Print all sums through different vertical lines.
```
Examples:

      1
    /   \
  2      3
 / \    / \
4   5  6   7
```
The tree has 5 vertical lines

Vertical-Line-1 has only one node 4 => vertical sum is 4
Vertical-Line-2: has only one node 2=> vertical sum is 2
Vertical-Line-3: has three nodes: 1,5,6 => vertical sum is 1+5+6 = 12
Vertical-Line-4: has only one node 3 => vertical sum is 3
Vertical-Line-5: has only one node 7 => vertical sum is 7

So expected output is 4, 2, 12, 3 and 7

(Puzzle get from http://www.geeksforgeeks.org/vertical-sum-in-a-given-binary-tree/)


I want to make a double linked list and store the sums into that linked list

```c++
struct LinkNode{
    int val;
    LinkNode* left;
    LinkNode* right;
    LinkNode(int _val): val(_val),left(NULL),right(NULL){}
};

void getSumTool(TreeNode* root, LinkNode *p, LinkNode *&head, LinkNode *&tail){
    /*
     p is the pointer of the current node in the double linked list
     p should be initialized outside this fun, so as the head and tail
     head is the head pointer, similar situation for tail
     */
    if(root==NULL) return; //terminal case
    
    
    p->val += root->val; // add the current value to the current node
    
    //deal with the left subtree recursivly
    if(root->left){
        if(p->left==NULL){
            p->left = new LinkNode(0);
            p->left->right = p;
            head = p->left;
        }
        
        getSumTool(root->left,p->left,head,tail);
    }
    
    
    //right subtree
    if(root->right){
        if(p->right==NULL){
            p->right = new LinkNode(0);
            p->right->left = p;
            tail = p->right;
        }
        
        getSumTool(root->right,p->right,head,tail);
    }
    
}

vector<int> getVerSum(TreeNode *root){
    vector<int> result;
    if(root==NULL)
        return result;
    LinkNode *tmp = new LinkNode(0);
    LinkNode *head = tmp, *tail = tmp;
    
    getSumTool(root,tmp,head,tail);
    tmp = head;
    while(tmp){
        result.push_back(tmp->val);
        tmp = tmp->right;
    }
    return result;
}

int main(){
    TreeNode *root = new TreeNode(1);
    root->left = new TreeNode(2);
    root->right = new TreeNode(3);
    root->left->left = new TreeNode(4);
    root->left->right = new TreeNode(5);
    root->right->right = new TreeNode(8);
    root->right->right->left = new TreeNode(6);
    root->right->right->left->right = new TreeNode(7);
    root->right->right->left->right->right = new TreeNode(9);
    //root->right->right->left->right->right->right = new TreeNode(10);
    vector<int> result = getVerSum(root);
    for(auto n:result)
        cout << n << " ";
    cout << endl;
    return 0;
}
```
