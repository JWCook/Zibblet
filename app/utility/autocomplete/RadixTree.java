/*
The MIT License

Copyright (c) 2008 Tahseen Ur Rehman, Javid Jamae

http://code.google.com/p/radixtree/

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package utility.autocomplete;

import java.util.*;

public class RadixTree<T> {

    protected RadixTreeNode<T> root;

    protected long size;

    /**
     * Create a Radix Tree with only the default node root.
     */
    public RadixTree() {
        root = new RadixTreeNode<T>();
        root.setKey("");
        size = 0;
    }

    /**
     * Find a value based on its corresponding key.
     * @param key The key for which to search the tree.
     * @return The value corresponding to the key. null if iot can not find the key
     */
    public T find(String key) {
        Visitor<T, T> visitor = new Visitor<T, T>() {

            public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
                if (node.isReal())
                    result = node.getValue();
            }
        };

        visit(key, visitor);

        return visitor.getResult();
    }

    /**
     * Delete a key and its associated value from the tree.
     * @param key The key of the node that need to be deleted
     * @return
     */
    public boolean delete(String key) {
        Visitor<T, Boolean> visitor = new Visitor<T, Boolean>(Boolean.FALSE) {
            public void visit(String key, RadixTreeNode<T> parent,
                              RadixTreeNode<T> node) {
                result = node.isReal();

                // if it is a real node
                if (result) {
                    // If there no children of the node we need to
                    // delete it from the its parent children list
                    if (node.getChildren().size() == 0) {
                        Iterator<RadixTreeNode<T>> it = parent.getChildren()
                                .iterator();
                        while (it.hasNext()) {
                            if (it.next().getKey().equals(node.getKey())) {
                                it.remove();
                                break;
                            }
                        }

                        // if parent is not real node and has only one child
                        // then they need to be merged.
                        if (parent.getChildren().size() == 1
                                && !parent.isReal()) {
                            mergeNodes(parent, parent.getChildren().get(0));
                        }
                    } else if (node.getChildren().size() == 1) {
                        // we need to merge the only child of this node with
                        // itself
                        mergeNodes(node, node.getChildren().get(0));
                    } else { // we jus need to mark the node as non real.
                        node.setReal(false);
                    }
                }
            }

            /**
             * Merge a child into its parent node. Operation only valid if it is
             * only child of the parent node and parent node is not a real node.
             *
             * @param parent The parent Node
             * @param child The child Node
             */
            private void mergeNodes(RadixTreeNode<T> parent,
                                    RadixTreeNode<T> child) {
                parent.setKey(parent.getKey() + child.getKey());
                parent.setReal(child.isReal());
                parent.setValue(child.getValue());
                parent.setChildren(child.getChildren());
            }

        };

        visit(key, visitor);

        if (visitor.getResult()) {
            size--;
        }
        return visitor.getResult();
    }

    /**
     * Insert a new string key and its value to the tree.
     * @param key The string key of the object
     * @param value The value that need to be stored corresponding to the given key.
     */
    public void insert(String key, T value) {
        insert(key, root, value);
        size++;
    }

    /**
     * Recursively insert the key in the radix tree.
     * @param key   The key to be inserted
     * @param node  The current node
     * @param value The value associated with the key
     */
    private void insert(String key, RadixTreeNode<T> node, T value) {

        int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(key);

        // we are either at the root node or we need to go down the tree
        if (node.getKey().equals("") || numberOfMatchingCharacters == 0 || (numberOfMatchingCharacters < key.length() && numberOfMatchingCharacters >= node.getKey().length())) {
            boolean flag = false;
            String newText = key.substring(numberOfMatchingCharacters, key.length());
            for (RadixTreeNode<T> child : node.getChildren()) {
                if (child.getKey().startsWith(newText.charAt(0) + "")) {
                    flag = true;
                    insert(newText, child, value);
                    break;
                }
            }

            // just add the node as the child of the current node
            if (!flag) {
                RadixTreeNode<T> n = new RadixTreeNode<>();
                n.setKey(newText);
                n.setReal(true);
                n.setValue(value);

                node.getChildren().add(n);
            }
        }
        // there is a exact match just make the current node as data node
        else if (numberOfMatchingCharacters == key.length() && numberOfMatchingCharacters == node.getKey().length()) {
            if (node.isReal()) {
                throw new IllegalStateException("Duplicate key: " + key);
            }

            node.setReal(true);
            node.setValue(value);
        }
        // This node need to be split as the key to be inserted
        // is a prefix of the current node key
        else if (numberOfMatchingCharacters > 0 && numberOfMatchingCharacters < node.getKey().length()) {
            RadixTreeNode<T> n1 = new RadixTreeNode<>();
            n1.setKey(node.getKey().substring(numberOfMatchingCharacters, node.getKey().length()));
            n1.setReal(node.isReal());
            n1.setValue(node.getValue());
            n1.setChildren(node.getChildren());

            node.setKey(key.substring(0, numberOfMatchingCharacters));
            node.setReal(false);
            node.setChildren(new ArrayList<RadixTreeNode<T>>());
            node.getChildren().add(n1);

            if (numberOfMatchingCharacters < key.length()) {
                RadixTreeNode<T> n2 = new RadixTreeNode<>();
                n2.setKey(key.substring(numberOfMatchingCharacters, key.length()));
                n2.setReal(true);
                n2.setValue(value);

                node.getChildren().add(n2);
            } else {
                node.setValue(value);
                node.setReal(true);
            }
        }
        // this key need to be added as the child of the current node
        else {
            RadixTreeNode<T> n = new RadixTreeNode<>();
            n.setKey(node.getKey().substring(numberOfMatchingCharacters, node.getKey().length()));
            n.setChildren(node.getChildren());
            n.setReal(node.isReal());
            n.setValue(node.getValue());

            node.setKey(key);
            node.setReal(true);
            node.setValue(value);

            node.getChildren().add(n);
        }
    }

    /**
     * Search for all the keys that start with given prefix. limiting the results based on the supplied limit.
     * @param key The prefix for which keys need to be search
     * @param recordLimit The limit for the results
     * @return The list of values those key start with the given prefix
     */
    public ArrayList<T> searchPrefix(String key, int recordLimit) {
        ArrayList<T> keys = new ArrayList<>();

        RadixTreeNode<T> node = searchPrefix(key, root);

        if (node != null) {
            if (node.isReal()) {
                keys.add(node.getValue());
            }
            getNodes(node, keys, recordLimit);
        }

        return keys;
    }

    private void getNodes(RadixTreeNode<T> parent, ArrayList<T> keys, int limit) {
        Queue<RadixTreeNode<T>> queue = new LinkedList<>();

        queue.addAll(parent.getChildren());

        while (!queue.isEmpty()) {
            RadixTreeNode<T> node = queue.remove();
            if (node.isReal()) {
                keys.add(node.getValue());
            }

            if (keys.size() == limit) {
                break;
            }

            queue.addAll(node.getChildren());
        }
    }

    private RadixTreeNode<T> searchPrefix(String key, RadixTreeNode<T> node) {
        RadixTreeNode<T> result = null;

        int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(key);

        if (numberOfMatchingCharacters == key.length() && numberOfMatchingCharacters <= node.getKey().length()) {
            result = node;
        } else if (node.getKey().equals("")
                || (numberOfMatchingCharacters < key.length() && numberOfMatchingCharacters >= node.getKey().length())) {
            String newText = key.substring(numberOfMatchingCharacters, key.length());
            for (RadixTreeNode<T> child : node.getChildren()) {
                if (child.getKey().startsWith(newText.charAt(0) + "")) {
                    result = searchPrefix(newText, child);
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Check if the tree contains any entry corresponding to the given key.
     * @param key The key that need to be searched in the tree.
     * @return return true if the key is present in the tree otherwise false
     */
    public boolean contains(String key) {
        Visitor<T, Boolean> visitor = new Visitor<T, Boolean>(Boolean.FALSE) {
            public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
                result = node.isReal();
            }
        };

        visit(key, visitor);

        return visitor.getResult().booleanValue();
    }

    /**
     * Visit the node those key matches the given key
     * @param key     The key that need to be visited
     * @param visitor The visitor object
     */
    public <R> void visit(String key, Visitor<T, R> visitor) {
        if (root != null) {
            visit(key, visitor, null, root);
        }
    }

    /**
     * Recursively visit the tree based on the supplied "key". calls the Visitor
     * for the node those key matches the given prefix
     * @param prefix  The key o prefix to search in the tree
     * @param visitor The Visitor that will be called if a node with "key" as its key is found
     * @param node    The Node from where onward to search
     */
    private <R> void visit(String prefix, Visitor<T, R> visitor,
                           RadixTreeNode<T> parent, RadixTreeNode<T> node) {

        int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(prefix);

        // if the node key and prefix match, we found a match!
        if (numberOfMatchingCharacters == prefix.length() && numberOfMatchingCharacters == node.getKey().length()) {
            visitor.visit(prefix, parent, node);
        } else if (node.getKey().equals("") // either we are at the root
                || (numberOfMatchingCharacters < prefix.length() && numberOfMatchingCharacters >= node.getKey().length())) { // OR we need to traverse the childern
            String newText = prefix.substring(numberOfMatchingCharacters, prefix.length());
            for (RadixTreeNode<T> child : node.getChildren()) {
                // recursively search the child nodes
                if (child.getKey().startsWith(newText.charAt(0) + "")) {
                    visit(newText, visitor, node, child);
                    break;
                }
            }
        }
    }

    /**
     * Return the size of the Radix tree
     * @return the size of the tree
     */
    public long getSize() {
        return size;
    }

    /**
     * Complete the a prefix to the point where ambiguity starts.
     *
     * Example:
     * If a tree contain "blah1", "blah2"
     * complete("b") -> return "blah"
     *
     * @param prefix The prefix we want to complete
     * @return The unambiguous completion of the string.
     */
    public String complete(String prefix) {
        return complete(prefix, root, "");
    }

    private String complete(String key, RadixTreeNode<T> node, String base) {
        int i = 0;
        int keyLen = key.length();
        int nodeLen = node.getKey().length();

        while (i < keyLen && i < nodeLen) {
            if (key.charAt(i) != node.getKey().charAt(i)) {
                break;
            }
            i++;
        }

        if (i == keyLen && i <= nodeLen) {
            return base + node.getKey();
        } else if (nodeLen == 0 || (i < keyLen && i >= nodeLen)) {
            String beginning = key.substring(0, i);
            String ending = key.substring(i, keyLen);
            for (RadixTreeNode<T> child : node.getChildren()) {
                if (child.getKey().startsWith(ending.charAt(0) + "")) {
                    return complete(ending, child, base + beginning);
                }
            }
        }

        return "";
    }

}
