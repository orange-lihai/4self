package biz.churen.self.ds0;

import org.junit.jupiter.api.Test;

/**
 * Created by lihai5 on 2018/7/9.
 */
class XBinaryTreeTest {

  @Test XBinaryTree<String> testCreateBinaryTree() {
    // 创建二叉树
    XBinaryTree<String> tree = new XBinaryTree<>();
    tree.insertAsRoot("R");
    XBinaryNode<String> root = tree.root();
    XBinaryNode<String> left1 = tree.insertAsLeftChild(root, "A");
    tree.insertAsLeftChild(left1, "C");
    tree.insertAsRightChild(left1, "D");

    XBinaryNode<String> right1 = tree.insertAsRightChild(root, "B");
    tree.insertAsLeftChild(right1, "E");
    tree.insertAsRightChild(right1, "F");

    // System.out.println(tree);
    return tree;
  }

  @Test XBinaryTree<String> testCreateBinaryTree2() {
    // 创建二叉树
    XBinaryTree<String> tree = new XBinaryTree<>();
    tree.insertAsRoot("K");
    XBinaryNode<String> root = tree.root();
    XBinaryNode<String> i = tree.insertAsLeftChild(root, "I");
    XBinaryNode<String> h = tree.insertAsRightChild(i, "H");
    XBinaryNode<String> b = tree.insertAsLeftChild(h, "B");
    XBinaryNode<String> g = tree.insertAsRightChild(h, "G");
    XBinaryNode<String> a = tree.insertAsRightChild(b, "A");
    XBinaryNode<String> e = tree.insertAsLeftChild(g, "E");
    XBinaryNode<String> f = tree.insertAsRightChild(g, "F");

    XBinaryNode<String> c = tree.insertAsLeftChild(e, "C");
    XBinaryNode<String> d = tree.insertAsRightChild(e, "D");

    XBinaryNode<String> j = tree.insertAsRightChild(root, "J");
    
    // System.out.println(tree);
    return tree;
  }

  @Test void testTravelByLevel() {
    XBinaryTree<String> tree = testCreateBinaryTree();
    tree.travelByLevel(s -> { System.out.print(s); System.out.print(" "); });
    System.out.println();
  }

  @Test void testTravelByPreorder() {
    XBinaryTree<String> tree = testCreateBinaryTree();
    tree.travelByPreorder2(s -> {
      System.out.print(s);
      System.out.print(" ");
    });
    System.out.println();
  }

  @Test void testTravelByInorder() {
    XBinaryTree<String> tree = testCreateBinaryTree();
    tree.travelByInorder(s -> {
      System.out.print(s);
      System.out.print(" ");
    });
    System.out.println();
  }

  @Test void testTravelByPostorder() {
    // XBinaryTree<String> tree = testCreateBinaryTree();
    XBinaryTree<String> tree = testCreateBinaryTree2();
    tree.travelByPostorder(s -> {
      System.out.print(s);
      System.out.print(" ");
    });
    System.out.println();
  }

}
