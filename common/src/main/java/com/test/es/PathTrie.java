package com.test.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

/**
 * es源码
 * @param <T>
 */
public class PathTrie<T> {

    enum TrieMatchingMode {
        /*
         * Retrieve only explicitly mapped nodes, no wildcards are
         * matched.
         */
        EXPLICIT_NODES_ONLY,
        /*
         * Retrieve only explicitly mapped nodes, with wildcards
         * allowed as root nodes.
         */
        WILDCARD_ROOT_NODES_ALLOWED,
        /*
         * Retrieve only explicitly mapped nodes, with wildcards
         * allowed as leaf nodes.
         */
        WILDCARD_LEAF_NODES_ALLOWED,
        /*
         * Retrieve both explicitly mapped and wildcard nodes.
         */
        WILDCARD_NODES_ALLOWED
    }

    static EnumSet<TrieMatchingMode> EXPLICIT_OR_ROOT_WILDCARD =
            EnumSet.of(TrieMatchingMode.EXPLICIT_NODES_ONLY, TrieMatchingMode.WILDCARD_ROOT_NODES_ALLOWED);

    public interface Decoder {
        String decode(String value);
    }

    private final Decoder decoder;
    private final TrieNode root;
    private T rootValue;

    private static final String SEPARATOR = "/";
    private static final String WILDCARD = "*";

    public PathTrie(Decoder decoder) {
        this.decoder = decoder;
        root = new TrieNode(SEPARATOR, null, WILDCARD);
    }

    public class TrieNode {
        private transient String key;
        private transient T value;
        private boolean isWildcard;
        private final String wildcard;

        private transient String namedWildcard;

        private Map<String, TrieNode> children;

        public TrieNode(String key, T value, String wildcard) {
            this.key = key;
            this.wildcard = wildcard;
            this.isWildcard = (key.equals(wildcard));
            this.value = value;
            this.children = emptyMap();
            if (isNamedWildcard(key)) {
                namedWildcard = key.substring(key.indexOf('{') + 1, key.indexOf('}'));
            } else {
                namedWildcard = null;
            }
        }

        public void updateKeyWithNamedWildcard(String key) {
            this.key = key;
            namedWildcard = key.substring(key.indexOf('{') + 1, key.indexOf('}'));
        }

        private void addInnerChild(String key, TrieNode child) {
            Map<String, TrieNode> newChildren = new HashMap<>(children);
            newChildren.put(key, child);
            children = unmodifiableMap(newChildren);
        }

        public synchronized void insert(String[] path, int index, T value) {
            if (index >= path.length)
                return;

            String token = path[index];
            String key = token;
            if (isNamedWildcard(token)) {
                key = wildcard;
            }
            TrieNode node = children.get(key);
            if (node == null) {
                T nodeValue = index == path.length - 1 ? value : null;
                node = new TrieNode(token, nodeValue, wildcard);
                addInnerChild(key, node);
            } else {
                if (isNamedWildcard(token)) {
                    node.updateKeyWithNamedWildcard(token);
                }
                /*
                 * If the target node already exists, but is without a value,
                 *  then the value should be updated.
                 */
                if (index == (path.length - 1)) {
                    if (node.value != null) {
                        throw new IllegalArgumentException("Path [" + String.join("/", path)+ "] already has a value ["
                                + node.value + "]");
                    } else {
                        node.value = value;
                    }
                }
            }

            node.insert(path, index + 1, value);
        }

        public synchronized void insertOrUpdate(String[] path, int index, T value, BiFunction<T, T, T> updater) {
            if (index >= path.length)
                return;

            String token = path[index];
            String key = token;
            if (isNamedWildcard(token)) {
                key = wildcard;
            }
            TrieNode node = children.get(key);
            if (node == null) {
                T nodeValue = index == path.length - 1 ? value : null;
                node = new TrieNode(token, nodeValue, wildcard);
                addInnerChild(key, node);
            } else {
                if (isNamedWildcard(token)) {
                    node.updateKeyWithNamedWildcard(token);
                }
                /*
                 * If the target node already exists, but is without a value,
                 *  then the value should be updated.
                 */
                if (index == (path.length - 1)) {
                    if (node.value != null) {
                        node.value = updater.apply(node.value, value);
                    } else {
                        node.value = value;
                    }
                }
            }

            node.insertOrUpdate(path, index + 1, value, updater);
        }

        private boolean isNamedWildcard(String key) {
            return key.indexOf('{') != -1 && key.indexOf('}') != -1;
        }

        private String namedWildcard() {
            return namedWildcard;
        }

        private boolean isNamedWildcard() {
            return namedWildcard != null;
        }

        public T retrieve(String[] path, int index, Map<String, String> params, TrieMatchingMode trieMatchingMode) {
            if (index >= path.length)
                return null;

            String token = path[index];
            TrieNode node = children.get(token);
            boolean usedWildcard;

            if (node == null) {
                if (trieMatchingMode == TrieMatchingMode.WILDCARD_NODES_ALLOWED) {
                    node = children.get(wildcard);
                    if (node == null) {
                        return null;
                    }
                    usedWildcard = true;
                } else if (trieMatchingMode == TrieMatchingMode.WILDCARD_ROOT_NODES_ALLOWED && index == 1) {
                    /*
                     * Allow root node wildcard matches.
                     */
                    node = children.get(wildcard);
                    if (node == null) {
                        return null;
                    }
                    usedWildcard = true;
                } else if (trieMatchingMode == TrieMatchingMode.WILDCARD_LEAF_NODES_ALLOWED && index + 1 == path.length) {
                    /*
                     * Allow leaf node wildcard matches.
                     */
                    node = children.get(wildcard);
                    if (node == null) {
                        return null;
                    }
                    usedWildcard = true;
                } else {
                    return null;
                }
            } else {
                if (index + 1 == path.length && node.value == null && children.get(wildcard) != null
                        && EXPLICIT_OR_ROOT_WILDCARD.contains(trieMatchingMode) == false) {
                    /*
                     * If we are at the end of the path, the current node does not have a value but
                     * there is a child wildcard node, use the child wildcard node.
                     */
                    node = children.get(wildcard);
                    usedWildcard = true;
                } else if (index == 1 && node.value == null && children.get(wildcard) != null
                        && trieMatchingMode == TrieMatchingMode.WILDCARD_ROOT_NODES_ALLOWED) {
                    /*
                     * If we are at the root, and root wildcards are allowed, use the child wildcard
                     * node.
                     */
                    node = children.get(wildcard);
                    usedWildcard = true;
                } else {
                    usedWildcard = token.equals(wildcard);
                }
            }

            put(params, node, token);

            if (index == (path.length - 1)) {
                return node.value;
            }

            T nodeValue = node.retrieve(path, index + 1, params, trieMatchingMode);
            if (nodeValue == null && !usedWildcard && trieMatchingMode != TrieMatchingMode.EXPLICIT_NODES_ONLY) {
                node = children.get(wildcard);
                if (node != null) {
                    put(params, node, token);
                    nodeValue = node.retrieve(path, index + 1, params, trieMatchingMode);
                }
            }

            return nodeValue;
        }

        private void put(Map<String, String> params, TrieNode node, String value) {
            if (params != null && node.isNamedWildcard()) {
                params.put(node.namedWildcard(), decoder.decode(value));
            }
        }

        @Override
        public String toString() {
            return key;
        }
    }

    public void insert(String path, T value) {
        String[] strings = path.split(SEPARATOR);
        if (strings.length == 0) {
            if (rootValue != null) {
                throw new IllegalArgumentException("Path [/] already has a value [" + rootValue + "]");
            }
            rootValue = value;
            return;
        }
        int index = 0;
        // Supports initial delimiter.
        if (strings[0].isEmpty()) {
            index = 1;
        }
        root.insert(strings, index, value);
    }

    /**
     * Insert a value for the given path. If the path already exists, replace the value with:
     * <pre>
     * value = updater.apply(oldValue, newValue);
     * </pre>
     * allowing the value to be updated if desired.
     */
    public void insertOrUpdate(String path, T value, BiFunction<T, T, T> updater) {
        String[] strings = path.split(SEPARATOR);
        if (strings.length == 0) {
            if (rootValue != null) {
                rootValue = updater.apply(rootValue, value);
            } else {
                rootValue = value;
            }
            return;
        }
        int index = 0;
        // Supports initial delimiter.
        if (strings[0].isEmpty()) {
            index = 1;
        }
        root.insertOrUpdate(strings, index, value, updater);
    }

    public T retrieve(String path) {
        return retrieve(path, null, TrieMatchingMode.WILDCARD_NODES_ALLOWED);
    }

    public T retrieve(String path, Map<String, String> params) {
        return retrieve(path, params, TrieMatchingMode.WILDCARD_NODES_ALLOWED);
    }

    public T retrieve(String path, Map<String, String> params, TrieMatchingMode trieMatchingMode) {
        if (path.length() == 0) {
            return rootValue;
        }
        String[] strings = path.split(SEPARATOR);
        if (strings.length == 0) {
            return rootValue;
        }
        int index = 0;

        // Supports initial delimiter.
        if (strings[0].isEmpty()) {
            index = 1;
        }

        return root.retrieve(strings, index, params, trieMatchingMode);
    }

    /**
     * Returns an iterator of the objects stored in the {@code PathTrie}, using
     * all possible {@code TrieMatchingMode} modes. The {@code paramSupplier}
     * is called between each invocation of {@code next()} to supply a new map
     * of parameters.
     */
    public Iterator<T> retrieveAll(String path, Supplier<Map<String, String>> paramSupplier) {
        return new PathTrieIterator<>(this, path, paramSupplier);
    }

    class PathTrieIterator<T> implements Iterator<T> {

        private final List<TrieMatchingMode> modes;
        private final Supplier<Map<String, String>> paramSupplier;
        private final PathTrie<T> trie;
        private final String path;

        PathTrieIterator(PathTrie trie, String path, Supplier<Map<String, String>> paramSupplier) {
            this.path = path;
            this.trie = trie;
            this.paramSupplier = paramSupplier;
            this.modes = new ArrayList<>(Arrays.asList(TrieMatchingMode.EXPLICIT_NODES_ONLY,
                    TrieMatchingMode.WILDCARD_ROOT_NODES_ALLOWED,
                    TrieMatchingMode.WILDCARD_LEAF_NODES_ALLOWED,
                    TrieMatchingMode.WILDCARD_NODES_ALLOWED));
            assert TrieMatchingMode.values().length == 4 : "missing trie matching mode";
        }

        @Override
        public boolean hasNext() {
            return modes.isEmpty() == false;
        }

        @Override
        public T next() {
            if (modes.isEmpty()) {
                throw new NoSuchElementException("called next() without validating hasNext()! no more modes available");
            }
            TrieMatchingMode mode = modes.remove(0);
            Map<String, String> params = paramSupplier.get();
            return trie.retrieve(path, params, mode);
        }
    }

    static final PathTrie.Decoder NO_DECODER = new PathTrie.Decoder() {
        @Override
        public String decode(String value) {
            return value;
        }
    };

    public static void main(String[] args) {
        PathTrie<String> trie = new PathTrie<>(NO_DECODER);
        trie.insert("/a/b/c", "walla");
        trie.insert("a/d/g", "kuku");
        trie.insert("x/b/c", "lala");
        trie.insert("a/x/*", "one");
        trie.insert("a/b/*", "two");
        trie.insert("*/*/x", "three");
        trie.insert("{index}/insert/{docId}", "bingo");

        System.out.println(trie.retrieve("a/b/c"));
        System.out.println(trie.retrieve("a/d/g"));
        System.out.println(trie.retrieve("x/b/c"));
        System.out.println(trie.retrieve("a/x/b"));
        System.out.println(trie.retrieve("a/b/d"));

        Map<String, String> params = new HashMap<>();
        System.out.println(trie.retrieve("index1/insert/12", params));
        System.out.println(params);
    }
}