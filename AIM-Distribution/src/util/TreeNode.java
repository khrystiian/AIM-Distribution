package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The TreeNode interface from the util package.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class TreeNode<T> {
	public T data;
	public TreeNode<T> parent;
	public List<TreeNode<T>> children;

	/**
	 * The constructor of the class.
	 * 
	 * @param data
	 *            An Object of the type T for the data from the node.
	 */
	public TreeNode(T data) {
		this.data = data;
		children = new LinkedList<TreeNode<T>>();
	}

	/**
	 * This method will add a child.
	 */
	public void addChild(TreeNode<T> element) {
		children.add(element);
		element.setParent(this);
	}

	/**
	 * This method will remove a child.
	 */
	public void removeChild(TreeNode<T> element) {
		children.remove(element);
		element.setParent(null);
	}

	/**
	 * This method will set a parent.
	 */
	public void setParent(TreeNode<T> element) {
		parent = element;
	}

}
