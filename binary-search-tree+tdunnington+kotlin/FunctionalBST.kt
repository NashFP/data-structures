import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

sealed class Node {
    class Value(val value: Int, val left: Node = Node.Nothing, val right: Node = Node.Nothing) : Node()
    object Nothing : Node()
}

private fun treeAttach(subTree: Node, orphan: Node.Value) : Node = when(subTree) {
    Node.Nothing -> orphan
    is Node.Value -> when {
        orphan.value < subTree.value -> treeAttach(subTree.left, orphan)
        orphan.value > subTree.value -> treeAttach(subTree.right, orphan)
        else -> Node.Nothing
    }
}

fun treeRemove(subTree: Node, value: Int) : Node = when(subTree) {
    Node.Nothing -> Node.Nothing
    is Node.Value -> when {
        value < subTree.value -> Node.Value(subTree.value, treeRemove(subTree.left, value), subTree.right)
        value > subTree.value -> Node.Value(subTree.value, subTree.left, treeRemove(subTree.right, value))
        else -> when {
            subTree.left is Node.Value && subTree.right == Node.Nothing -> subTree.left
            subTree.left == Node.Nothing && subTree.right is Node.Value -> subTree.right
            subTree.left is Node.Value && subTree.right is Node.Value -> treeAttach(subTree.right, subTree.left)
            else -> Node.Nothing
        }
    }
}

fun treeAdd(subTree: Node, newValue: Int) : Node = when(subTree) {
    Node.Nothing -> Node.Value(newValue)
    is Node.Value -> when {
        newValue < subTree.value -> Node.Value(subTree.value, treeAdd(subTree.left, newValue), subTree.right)
        newValue > subTree.value -> Node.Value(subTree.value, subTree.left, treeAdd(subTree.right, newValue))
        else -> subTree
    }
}

fun treeNew(newValue: Int) : Node = Node.Value(newValue)

fun treeNew() : Node = Node.Nothing

fun treeSort(subTree: Node) : Array<Int> = when(subTree) {
    Node.Nothing -> emptyArray()
    is Node.Value -> treeSort(subTree.left) + subTree.value + treeSort(subTree.right)
}

tailrec fun isInTree(subTree: Node, target: Int) : Boolean = when(subTree) {
    Node.Nothing -> false
    is Node.Value -> when {
        target < subTree.value -> isInTree(subTree.left, target)
        target > subTree.value -> isInTree(subTree.right, target)
        target == subTree.value -> true
        else -> false
    }
}

fun treeSize(subTree: Node) : Int = when(subTree) {
    Node.Nothing -> 0
    is Node.Value -> 1 + treeSize(subTree.left) + treeSize(subTree.right)
}

fun main(args:Array<String>) {
    var t = treeNew()

    File("data/thousand.txt").bufferedReader().forEachLine {
        t = treeAdd(t, it.toInt())
    }

    assertTrue(isInTree(t, 325652))
    assertTrue(isInTree(t, 22288))
    assertEquals(998, treeSize(t))

    t = treeRemove(t, 22288)
    assertFalse(isInTree(t, 22288))
    assertTrue(isInTree(t, 325652))
    assertEquals(997, treeSize(t))

    val sorted = treeSort(t)
    assertEquals(545, sorted.first())
    assertEquals(999967, sorted.last())
}