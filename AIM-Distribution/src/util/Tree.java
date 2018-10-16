package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Tree<T> {

	private TreeNode<T> root;
	private List<TreeNode<T>> elements;
	
	public Tree(TreeNode<T> root) {
		elements = new LinkedList<TreeNode<T>>();
		elements.add(root);
	}
	
	public void add(TreeNode<T> element) {
		this.root = element;
		elements.add(element);
		element.children.forEach(n -> elements.add(n));
	}
	
	public void remove(TreeNode<T> element) {
		elements.remove(element);
		element.children.forEach(n -> elements.remove(n));
	}
	
	public List<TreeNode<T>> getTree(TreeNode<T> node) {
		List<TreeNode<T>> result = new ArrayList<TreeNode<T>>();
		result.add(node);

		//System.out.println(node);
		for(TreeNode n : node.children) {
			result.add(n);
			try {
				result.addAll(getTree(n));				
			} catch(NullPointerException e) {
				continue;
			}
		}
		return result;		
	}
	
	public List<TreeNode<T>> getAll() {
		return getTree(root);
	}
	
}
