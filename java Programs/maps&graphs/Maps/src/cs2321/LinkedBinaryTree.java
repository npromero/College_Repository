package cs2321;
import java.util.Iterator;
import net.datastructures.*;
/*
 * Niklas Romero
 *CS2321/assignment6
 * The class makes linked binary tree
 */	

/**
 * @author ruihong-adm
 *
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>{
	public int size =0;

	//node class 
	protected static class Node<E> implements Position<E> {
		private E element; // an element stored at this node
		private Node<E> parent; // a reference to the parent node (if any)
		private Node<E> left; // a reference to the left child (if any)
		private Node<E> right; // a reference to the right child (if any)
		/** Constructs a node with the given element and neighbors. */
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}

		// accessor methods
		/*
		 * Gets element
		 * @return E element
		 */
		public E getElement( ) { return element; }
		/*
		 * Gets parent 
		 * @return node parent
		 */
		public Node<E> getParent( ) { return parent; }
		/*
		 * Gets left child 
		 * @return left node
		 */
		public Node<E> getLeft( ) { return left; }
		/*
		 * Gets right child 
		 * @return right node
		 */
		public Node<E> getRight( ) { return right; }
		// setter methods
		/*
		 * sets element
		 * @param E e
		 */
		public void setElement(E e) { element = e; }
		/*
		 * sets parent
		 * @param parent node
		 */
		public void setParent(Node<E> parentNode) { parent = parentNode; }
		/*
		 * sets left child
		 * @param left child node
		 */
		public void setLeft(Node<E> leftChild) { left = leftChild; }
		/*
		 * sets right child
		 * @param right child node
		 */
		public void setRight(Node<E> rightChild) { right = rightChild; }
	} //        end of nested Node class 

	/*
	 * makes a node
	 * @param E e, node parent
	 * @return node
	 */	
	protected Node<E> createNode(E e, Node<E> parent,
			Node<E> left, Node<E> right) {
		return new Node<E>(e, parent, left, right);
	}

	protected Node<E> root = null;
	@Override
	/*
	 * Gets root of tree
	 * @return node root
	 */
	public Position<E> root() {
		return root;
	}

	public  LinkedBinaryTree( ) {
	}
	/*
	 * checks if the key type is correct
	 * @param position p
	 * @return node
	 * @throw IllegalArgumentException
	 */
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node))
			throw new IllegalArgumentException("Not valid position type");
		Node<E> node = (Node<E>) p; // safe cast
		if (node.getParent( ) == node) //  defunct node
			throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}

	@Override
	/*
	 * gets parent
	 * @param position p
	 * @return position
	 * @throw IllegalArgumentException
	 */

	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent( );
	}

	@Override
	/*
	 * Iterator for the children of a node
	 * @param position p
	 * @return iterable
	 * @throw IllegalArgumentException
	 */
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		ArrayList<Position<E>> snapshot = new ArrayList<>(2); // max capacity of 2
		if (left(p) != null)
			snapshot.addLast(left(p));
		if (right(p) != null)
			snapshot.addLast(right(p));
		return snapshot;
	}


	@Override
	/*
	 *  count only direct child of the node, not further descendant.
	 *  @param position p
	 *  @return int childNum
	 *  @throw IllegalArgumentException
	 *   */
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		int childNum = 0;
		Node<E> node = validate(p);
		if (node.left != null) {
			childNum++;
		}
		if(node.right != null) {
			childNum++;
		}
		return childNum; 
	}

	@Override
	/*
	 *checks if the node isn't a sentinel node
	 *@param position p
	 *@return boolean isInternal
	 *@throw IllegalArgumentException
	 */
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(node.left !=null || node.right !=null) {
			return true;
		}
		return false;
	}

	@Override
	/*
	 * checks if the entry is a sentinel node
	 * @param position p
	 * @return boolean isExternal
	 * @throw IllegalArgumentException
	 */
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(node.left !=null || node.right !=null) {
			return false;
		}
		return true;
	}

	@Override
	/*
	 * checks if a node is the root 
	 * @param position p
	 * @return boolean isRoot
	 * @throw IllegalArgumentException
	 */		
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if (node.parent != null) {
			return false;
		}
		return true;
	}

	@Override
	/*
	 * returns size of tree
	 * @return int size
	 */
	public int size() {
		return size;
	}

	@Override
	/*
	 * returns if the tree is empty
	 * @return boolean isEmpty
	 */
	public boolean isEmpty() {
		return size==0;
	}

	/*
	 * sets up the order of how the data is iterated in an inorder traversal
	 * @param position p, ArrayList snapshot
	 */
	private void inorderSubtree(Position<E> p, ArrayList<Position<E>> snapshot) {
		if (left(p) != null)
			inorderSubtree(left(p), snapshot);
		snapshot.addLast(p);
		if (right(p) != null)
			inorderSubtree(right(p), snapshot);
	}
	/*
	 * Traverses thee tree in order
	 * @return iterable 
	 */
	public Iterable<Position<E>> inorder( ) {
		ArrayList<Position<E>> snapshot = new ArrayList<>( );
		if (!isEmpty( ))
			inorderSubtree(root( ), snapshot); // fill the snapshot recursively
		return snapshot;
	}

	/*
	 * makes iterable for each node
	 */
	private class ElementIterator implements Iterator<E> {
		Iterator<Position<E>> posIterator = positions( ).iterator( );
		public boolean hasNext( ) { return posIterator.hasNext( ); }
		public E next( ) { return posIterator.next( ).getElement( ); } // return element!
		public void remove( ) { posIterator.remove( ); }
	}

	@Override
	/*
		 Make iterator
		 @return iterator
	 */
	public Iterator<E> iterator() {
		return new ElementIterator( ); }


	@Override
	/*
		 makes in order iterable
		 @return iterable
	 */
	public Iterable<Position<E>> positions() {
		return inorder( );
	}

	@Override
	/*
		 gets the left child
		 @param position p
		 @return position of left node
	 */
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft( );
	}

	@Override
	/*
		 gets the right child
		 @param position p
		 @return position of right node
	 */
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight( );
	}

	@Override
	/*
		 gets the sibling of the node
		 @param position p
		 @return position of sibling node
	 */
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		Node<E> Pnode = node.parent;
		if(Pnode.getLeft().equals(node)) {
			return Pnode.right;
		}else if(Pnode.getRight().equals(node)) {
			return Pnode.left;
		}
		return null;
	}

	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root.
	 * @param E e
	 * @return position
	 * @throw IllegalStateException
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty( )) throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null);
		size = 1;
		return root;
	}

	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already.
	 * @param position p, E e
	 * @return position
	 * @throw IllegalArgumentException
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getLeft( ) != null)
			throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	} 

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 * @param position p, E e
	 * @return position
	 * @throw IllegalArgumentException
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getRight( ) != null)
			throw new IllegalArgumentException("p already has a right child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}

	/*
	 * sets the value and key of an element
	 * @param position p, E e
	 * @ return E
	 * @throw IllegalArgumentException
	 */

	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E temp = node.getElement( );
		node.setElement(e);
		return temp;
	}

	/* Attach trees t1 and t2 as left and right subtrees of external Position. 
	 * if p is not external, throw IllegalArgumentExeption.
	 * @param position p, LinkedBinaryTrees t1 & t2
	 */
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) {
		Node<E> node = validate(p);
		if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
		size += t1.size( ) + t2.size( );
		if (!t1.isEmpty( )) { // attach t1 as left subtree of node
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		if (!t2.isEmpty( )) { // attach t2 as right subtree of node
			t2.root.setParent(node);
			node.setRight(t2.root); 
			t2.root = null;
			t2.size = 0;
		}

	}

	/*
	 * removes an element from the tree and fixes the order
	 * @param position p
	 * @return E
	 * @throw IllegalArgumentException
	 * @Timecomplecity log n
	 */
	public E remove(Position<E> p)throws IllegalArgumentException {
		Node<E> node = validate(p);
		if (numChildren(p) == 2)
			throw new IllegalArgumentException("p has two children");
		Node<E> child = (node.getLeft( ) != null ? node.getLeft( ) : node.getRight( ) );
		if (child != null)
			child.setParent(node.getParent( )); // child's grandparent becomes its parent
		if (node == root)
			root = child; // child becomes root
		else {
			Node<E> parent = node.getParent( );
			if (node == parent.getLeft( ))
				parent.setLeft(child);
			else
				parent.setRight(child);
		}
		size--;
		E temp = node.getElement( );
		node.setElement(null); // help garbage collection
		node.setLeft(null); 
		node.setRight(null);
		node.setParent(node); // our convention for defunct node
		return temp;
	}


}

