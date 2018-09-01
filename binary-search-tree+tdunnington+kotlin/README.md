Functional Binary Search Tree in Kotlin
===========================================

FunctionalBST.kt shows an FP implementation of a Binary Search Tree in Kotlin. This BST is a very simple implementation and does not include re-balancing (such as Red-Black trees).

It employs a Sealed Class (discriminated union) to represent the tree and the tree nodes. Each node can be either a value node or nothing. The value nodes are tuples containing an integer value, a left node and a right node.

Functions
---------------
* treeNew() - creates an empty tree
* treeNew(Int) - creates a tree with a single node with the supplied value
* treeAdd(Node, Int):Node - adds Int to the tree and returns a new tree
* treeRemove(Node, Int):Node - removes Int from the tree and returns a new tree
* treeSort(Node):Array - returns a sorted array of the nodes in the tree
* treeSize(Node):Int - returns the number of items in the tree
* isInTree(Node, Int):Boolean - returns true if Int is found in the tree, false otherwise

Notes
-----------------
* isInTree demonstrates a tail recursive Kotlin function
* All functions are single-expression functions (they consist of a single statement)
* Node is a sealed class (discriminated union), and all tree functions use the "when" clause to handle the union's different types. This is a good way to deal with nulls (or I should say, avoid having to deal with null pointer issues). Kotlin variables and values are non-nullable by default, so all functions and code in this project are guaranteed non-null by the compiler.

Main Function
----------------
The main function is a test function for the project, including assertions for testing the tree functions using the 1,000 integer test data file provided one directory above this one in Git.