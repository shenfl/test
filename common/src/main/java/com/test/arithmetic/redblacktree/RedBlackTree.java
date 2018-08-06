package com.test.arithmetic.redblacktree;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/11/1.
 */
public class RedBlackTree<E> {
    private final Node nil;
    private final Comparator<E> comparator;

    public RedBlackTree(Comparator<E> comparator) {
        nil = new Node();
        setE(nil, null);
        setBlack(nil);
        setLeft(nil, nil);
        setRight(nil, nil);
        this.comparator = comparator;
    }

    public void insert(E e) {
        Node node = create(e);
        if (!hasRoot()) {
            setBlack(node);
            asRoot(node);
        } else {
            setRed(node);
            Node itr = root();
            while (true) {
                int cr = compare(node, itr);
                if (cr > 0) {
                    if (hasRight(itr)) {
                        itr = rightOf(itr);
                    } else {
                        setRight(itr, node);
                        break;
                    }
                } else if (cr < 0) {
                    if (hasLeft(itr)) {
                        itr = leftOf(itr);
                    } else {
                        setLeft(itr, node);
                        break;
                    }
                } else {
                    copyE(itr, node);
                    return;
                }
            }
            insertionFixUp(node);
        }
    }

    public boolean delete(E e) {
        Node node = create(e);
        node = searchFor(node);
        if (node != null) {
            Node itr = node;
            while (hasLeft(itr)) {
                Node pre = preSuccessor(itr);
                copyE(itr, pre);
                itr = pre;
            }
            Node endUp = itr;
            if (endUp == node) {
                Node right = rightOf(endUp);
                transplant(endUp, right);
                if (isBlack(endUp) ^ isBlack(right)) {
                    if (isRed(endUp)) {
                        setBlack(endUp);
                    }
                } else if (isBlack(endUp) && isBlack(right)) {
                    deletionFixUp(endUp);
                }
            } else {
                transplant(endUp, nil);
                if (isBlack(endUp)) {
                    deletionFixUp(nil);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void deletionFixUp(Node node) {
        if (isBlack(node)) {
            if (hasParent(node)) {
                Node parent = parentOf(node);
                boolean isLeftChild = leftOf(parent) == node;
                boolean hasSibling = isLeftChild ? hasRight(parent) : hasLeft(parent);
                if (!hasSibling) {
                    deletionFixUp(parent);
                } else {
                    Node sibling = isLeftChild ? rightOf(parent) : leftOf(parent);
                    if (isBlack(sibling)) {
                        if (hasRedChild(sibling)) {
                            boolean hasLeftRedNephew = hasLeft(sibling) && (isRed(leftOf(sibling)));
                            boolean hasRightRedNephew = hasRight(sibling) && (isRed(rightOf(sibling)));
                            if (isLeftChild && hasRightRedNephew) {
                                Node rightRedNephew = rightOf(sibling);
                                setBlack(rightRedNephew);
                                if (isRed(parent)) {
                                    setBlack(parent);
                                    setRed(sibling);
                                }
                                leftRotate(parent);
                            } else if (!isLeftChild && hasLeftRedNephew) {
                                Node leftRedNephew = leftOf(sibling);
                                setBlack(leftRedNephew);
                                if (isRed(parent)) {
                                    setBlack(parent);
                                    setRed(sibling);
                                }
                                rightRotate(parent);
                            } else if (isLeftChild && hasLeftRedNephew) {
                                Node leftRedNephew = leftOf(sibling);
                                setBlack(leftRedNephew);
                                setRed(sibling);
                                rightRotate(sibling);
                                deletionFixUp(node);
                            } else if (!isLeftChild && hasRightRedNephew) {
                                Node rightRedNephew = rightOf(sibling);
                                setBlack(rightRedNephew);
                                setRed(sibling);
                                leftRotate(sibling);
                                deletionFixUp(node);
                            } else throw new UnsupportedOperationException();
                        } else {
                            setRed(sibling);
                            deletionFixUp(parent);
                        }
                    } else {
                        setRed(parent);
                        setBlack(sibling);
                        if (isLeftChild) {
                            leftRotate(parent);
                        } else {
                            rightRotate(parent);
                        }
                        deletionFixUp(node);
                    }
                }
            }
        }
    }

    private boolean hasRedChild(Node sibling) {
        if (isLeaf(sibling)) {
            return false;
        } else if (hasLeft(sibling)) {
            if (isRed(leftOf(sibling))) {
                return true;
            }
        } else if (hasRight(sibling)) {
            if (isRed(rightOf(sibling))) {
                return true;
            }
        }
        return false;
    }

    private void transplant(Node before, Node after) {
        Node p = parentOf(before);
        if (leftOf(p) == before) {
            setLeft(p, after);
        } else {
            setRight(p, after);
        }
    }

    private Node preSuccessor(Node node) {
        if (hasLeft(node)) {
            Node itr = leftOf(node);
            while (hasRight(itr)) {
                itr = rightOf(itr);
            }
            return itr;
        } else throw new IllegalArgumentException();
    }

    private Node searchFor(Node node) {
        if (!hasRoot()) {
            return null;
        } else {
            Node itr = root();
            while (true) {
                int cr = compare(node, itr);
                if (cr > 0) {
                    if (hasRight(itr)) {
                        itr = rightOf(itr);
                    } else {
                        return null;
                    }
                } else if (cr < 0) {
                    if (hasLeft(itr)) {
                        itr = leftOf(itr);
                    } else {
                        return null;
                    }
                } else {
                    return itr;
                }
            }
        }
    }

    private void insertionFixUp(Node node) {
        if (!isRed(node)) throw new UnsupportedOperationException();
        if (hasParent(node)) {
            Node p = parentOf(node);
            boolean isLeftChild = leftOf(p) == node;
            if (isRed(p)) {
                if (hasParent(p)) {
                    Node pp = parentOf(p);
                    boolean isLeftExChild = leftOf(pp) == p;
                    boolean hasUncle = isLeftExChild ? hasRight(pp) : hasLeft(pp);
                    if (hasUncle) {
                        Node uncle = isLeftExChild ? rightOf(pp) : leftOf(pp);
                        if (isRed(uncle)) {
                            setBlack(p);
                            setBlack(uncle);
                            setRed(pp);
                            insertionFixUp(pp);
                            return;
                        }
                    }
                    if (isLeftChild && isLeftExChild) {
                        setRed(pp);
                        setBlack(p);
                        rightRotate(pp);
                    } else if (!isLeftChild && !isLeftExChild) {
                        setRed(pp);
                        setBlack(p);
                        leftRotate(pp);
                    } else if (!isLeftChild && isLeftExChild) {
                        leftRotate(p);
                        insertionFixUp(p);
                    } else if (isLeftChild && !isLeftExChild) {
                        rightRotate(p);
                        insertionFixUp(p);
                    } else throw new UnsupportedOperationException();
                } else throw new UnsupportedOperationException();
            }
        } else {
            setBlack(node);
        }
    }

    private boolean isRed(Node node) {
        return node.color == Color.RED;
    }

    private boolean isBlack(Node node) {
        return node.color == Color.BLACK;
    }

    private void copyE(Node before, Node after) {
        before.e = after.e;
    }

    private int compare(Node n1, Node n2) {
        return comparator.compare(n1.e, n2.e);
    }

    private boolean leftRotate(Node node) {
        if (hasRight(node)) {
            Node r = rightOf(node);
            Node p = parentOf(node);
            if (leftOf(p) == node) {
                setLeft(p, r);
            } else {
                setRight(p, r);
            }
            if (hasLeft(r)) {
                setRight(node, leftOf(r));
            } else {
                setRight(node, nil);
            }
            setLeft(r, node);
            return true;
        } else throw new UnsupportedOperationException();
    }

    private boolean rightRotate(Node node) {
        if (hasLeft(node)) {
            Node l = leftOf(node);
            Node p = parentOf(node);
            if (rightOf(p) == node) {
                setRight(p, l);
            } else {
                setLeft(p, l);
            }
            if (hasRight(l)) {
                setLeft(node, rightOf(l));
            } else {
                setLeft(node, nil);
            }
            setRight(l, node);
            return true;
        } else throw new UnsupportedOperationException();
    }

    private boolean hasRoot() {
        return leftOf(nil) != nil;
    }

    private void asRoot(Node node) {
        setLeft(nil, node);
    }

    private Node root() {
        Node node = leftOf(nil);
        if (node != nil) {
            return node;
        } else throw new UnsupportedOperationException();
    }


    private void setRed(Node node) {
        node.color = Color.RED;
    }

    private void setBlack(Node node) {
        node.color = Color.BLACK;
    }

    private void setLeft(Node parent, Node left) {
        parent.left = left;
        left.parent = parent;
    }

    private void setRight(Node parent, Node right) {
        parent.right = right;
        right.parent = parent;
    }

    private void setE(Node node, E e) {
        node.e = e;
    }

    private Node leftOf(Node node) {
        return node.left;
    }

    private Node rightOf(Node node) {
        return node.right;
    }

    private Node parentOf(Node node) {
        return node.parent;
    }

    private boolean hasLeft(Node node) {
        return node.left != nil;
    }

    private boolean hasRight(Node node) {
        return node.right != nil;
    }

    private boolean hasParent(Node node) {
        return node.parent != nil;
    }

    private boolean isLeaf(Node node) {
        return !hasLeft(node) && !hasRight(node);
    }

    private Node create(E e) {
        Node node = new Node();
        setE(node, e);
        setLeft(node, nil);
        setRight(node, nil);
        return node;
    }

    private class Node {
        Color color;
        Node parent;
        Node left;
        Node right;
        E e;
    }

    enum Color {
        RED, BLACK
    }

    public int height() {
        if (hasRoot()) {
            return height(root());
        } else {
            return 0;
        }
    }

    private int height(Node node) {
        if (hasLeft(node) && hasRight(node)) {
            int lHeight = height(leftOf(node));
            int rHeight = height(rightOf(node));
            return (lHeight > rHeight ? lHeight : rHeight) + 1;
        } else if (hasLeft(node)) {
            int lHeight = height(leftOf(node));
            return lHeight + 1;
        } else if (hasRight(node)) {
            int rHeight = height(rightOf(node));
            return rHeight + 1;
        } else return 1;
    }
}
