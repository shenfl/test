package com.test.arithmetic.tree;

import java.util.ArrayDeque;
import java.util.Queue;
/**
 * Created by shenfl on 2018/8/6
 * 树的序列化和反序列化 牛客网
 */
public class TreeNode<T> {
    private static int n = 0;
    private TreeNode left;
    private TreeNode right;
    private T value;
    public TreeNode(T value) {
        this.value = value;
    }

    /**
     * Breadth first travel and print with level
     */
    public void breadthTravel() {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(this);
        TreeNode current = null;
        // For print
        TreeNode last = this;
        TreeNode nextLast = null;
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            System.out.print(poll.value);
            if (poll.left != null) {
                queue.add(poll.left);
                nextLast = poll.left;
            }
            if (poll.right != null) {
                queue.add(poll.right);
                nextLast = poll.right;
            }
            if (last == poll) {
                System.out.println();
                last = nextLast;
            }
        }
    }

    /**
     * 先序方式序列化
     * @param node
     * @param sb
     */
    public static void serialize(TreeNode node, StringBuilder sb) {
        sb.append(node.value).append("!");
        if (node.left != null) {
            serialize(node.left, sb);
        } else {
            sb.append("#!");
        }
        if (node.right != null) {
            serialize(node.right, sb);
        } else {
            sb.append("#!");
        }
    }

    /**
     * 先序方式反序列化
     * @param arr
     * @return
     */
    public static TreeNode deSerialize(String[] arr) {
        if (n >= arr.length) return null;
        if ("#".equals(arr[n])) {
            n++;
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.valueOf(arr[n]));
            n++;
            node.left = deSerialize(arr);
            node.right = deSerialize(arr);
            return node;
        }
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode("1");
        TreeNode t2 = new TreeNode("2");
        TreeNode t3 = new TreeNode("3");
        root.left = t2;
        root.right = t3;
        TreeNode t4 = new TreeNode("4");
        t2.left = t4;
        TreeNode t5 = new TreeNode("5");
        TreeNode t6 = new TreeNode("6");
        t3.left = t5;
        t3.right = t6;
        TreeNode t7 = new TreeNode("7");
        TreeNode t8 = new TreeNode("8");
        t5.left = t7;
        t5.right = t8;
        root.breadthTravel();

        StringBuilder sb = new StringBuilder();
        TreeNode.serialize(root, sb);
        System.out.println(sb.toString());

        TreeNode node = TreeNode.deSerialize(sb.toString().split("!"));
        node.breadthTravel();


//        Queue<String> queue = new ArrayDeque<>();
//        queue.add("1");
//        queue.add("2");
//        queue.add("3");
//
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
//        System.out.println(queue.poll());
    }
}