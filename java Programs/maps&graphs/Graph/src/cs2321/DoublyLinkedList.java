/**
 * Niklas Romero
 *CS2321/assignment1
 * The class implements a positional list to create a doubly linked list
 */
package cs2321;
import java.util.Iterator;
import net.datastructures.Position;
import net.datastructures.PositionalList;


public class DoublyLinkedList<E> implements PositionalList<E> {
	/*
	 * Creates an object for traversal through the list
	 */
	public static class Node<E> implements Position<E>
	{
		private E element;
		private Node<E> prev;
		private Node<E> next;
		/*
		 * creates node
		 * @param e data value, p previous node, n next node
		 */
		public Node(E e, Node<E> p, Node<E> n )
		{
			element = e;
			prev = p;
			next = n;
		}
		/*
		 * Gets the data of the node
		 * @return the data of current node
		 * @throws IllegalStateException
		 */
		public E getElement() throws IllegalStateException
		{
			if(next == null)
			{
				throw new IllegalStateException("Position no longer valid");
			}
			return element;
		}
		/*
		 * Gets the data stored at the previous node
		 * @return the data stored at previous node
		 */
		public Node<E> getPrev() {return prev;}
		/*
		 * Gets the data stored at next node
		 * @return the data stored at next node
		 */
		public Node<E> getNext() {return next;}
		/*
		 * sets the value of the node
		 * @param e data value
		 */
		public void setElement(E e) {element = e;}
		/*
		 * Sets the previous node
		 * @param p previous node
		 */
		public void setPrev(Node<E> p) {prev = p;}
		/*
		 * sets the data for the next node
		 * @param i index of array
		 * @return the data stored at that index
		 * @throws IndexOutOfBoundsException
		 */
		public void setNext(Node<E> n) {next = n;}
	}
	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;
	/*
	 * constructs a doubly linked list
	 */
	public DoublyLinkedList() {
		header = new Node<>(null,null,null);
		trailer = new Node<>(null,header,null);
		header.setNext(trailer);
	}
	/*
	 * gives the size of the list
	 * @return size of list
	 */
	@Override
	public int size() {
		return size;
	}
	/*
	 * checks if the list is empty
	 * @return true/false if the list is empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	/*
	 * gets the first position of the list
	 * @return the value at the first position
	 */
	@Override
	public Position<E> first() {
		if(isEmpty()) 
			{
			return null;
			}
		return position(header.getNext());
	}
	/*
	 * gets the last position of the list
	 * @return the value at the last position
	 */
	@Override
	public Position<E> last() {
		return position(trailer.getPrev());
	}
	/*
	 * gets the position before a specified point
	 * @param p position point
	 * @return position before p position
	 * @throws IllegalArgumentException
	 */
	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getPrev());
	}
	/*
	 * gets the position after a specified point
	 * @param p position point
	 * @return position after p position
	 * @throws IllegalArgumentException
	 */
	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getNext());
	}
	/*
	 * adds data to the start of the list
	 * @param e data point
	 * @return the newly created node
	 */
	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e,header,header.getNext());
	}
	/*
	 * adds data to the end of the list
	 * @param e data point
	 * @return the newly created node
	 */
	@Override
	public Position<E> addLast(E e) {
		return addBetween(e,trailer.getPrev(),trailer);
	}
	/*
	 * adds data to the before a position
	 * @param p position,e data point
	 * @return the newly created node
	 */
	@Override
	public Position<E> addBefore(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e,node.getPrev(),node);
	}
	/*
	 * adds data to the after a position
	 * @param p position,e data point
	 * @return the newly created node
	 */
	@Override
	public Position<E> addAfter(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e,node,node.getNext());
	}
	/*
	 * sets the position of a specified point
	 * @param p position point, e data value
	 * @return newly created node
	 * @throws IllegalArgumentException
	 */
	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E answer = node.getElement();
		node.setElement(e);
		return answer;
	}
	/*
	 * removes the node on a specified point
	 * @param p position point
	 * @return node that was removed
	 * @throws IllegalArgumentException
	 */
	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		 Node<E> predecessor = node.getPrev( );
		 Node<E> successor = node.getNext( );
		 predecessor.setNext(successor);
		 successor.setPrev(predecessor);
		 size--;
		 E answer = node.getElement( );
		 node.setElement(null); // help with garbage collection
		 node.setNext(null); // and convention for defunct node
		 node.setPrev(null);
		 return answer;
	}
	/*
	 * creates iterator
	 * @return new iterator
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	/*
	 * creates a new position iteratble
	 * @return a new position iterable
	 */
	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}
	/*
	 * removes first node in the position iterator
	 * @return header node
	 * @throws IllegalArgumentException
	 */
	public E removeFirst() throws IllegalArgumentException {
		if(isEmpty())
		{
			return null;
		}
		 return remove(header.getNext());
	}
	/*
	 * removes last node in the position iterator
	 * @param p position point
	 * @return trailer node
	 * @throws IllegalArgumentException
	 */
	public E removeLast() throws IllegalArgumentException {
		if(isEmpty())
		{
			return null;
		}
		 return remove(trailer.getPrev());
	}
	
	/*
	 * checks if a node can be put in the set location
	 * @param p position
	 * @return instance  of node at the position
	 * @throws IllegalArgumentException
	 */
	public Node<E> validate(Position<E> p) throws IllegalArgumentException
			{
		if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
		Node<E> node = (Node<E>)p;
		if(node.getNext() == null)
		{
			throw new IllegalArgumentException("p is no longer in the list");
		}
		return node;
			}
	/*
	 * gets the current node
	 * @param node current node 
	 * @return current node
	 */
	public Position<E> position(Node<E> node)
	{
		if(node == header || node == trailer)
		{
			return null;
		}
			
		return node;
	}
	/*
	 * adds a node between two nodes
	 * @param e data point, pred previous node, succ next node
	 * @return new node  
	 */
	public Position<E> addBetween(E e,Node<E> pred, Node<E> succ)
	{
		Node<E> newest = new Node<>(e,pred,succ);
		pred.setNext(newest);
		succ.setPrev(newest);
		size++;
		return newest;
	}
	
	/*
	 * traverses through nodes
	 */
	public class ElementIterator implements Iterator<E>
	{
		Iterator<Position<E>> posIterator = new PositionIterator( );
		/*
		 * checks if there is a next node to move through
		 * @return boolean if there is a next node
		 */
		 public boolean hasNext( ) { return posIterator.hasNext( ); }
		 /*
			 * iterates to the next node
			 * @returns e element of next node
			 */
		 public E next( ) { return posIterator.next( ).getElement( ); } 
		 /*
			 * removes the current node 
			 */
		public void remove( ) { posIterator.remove( ); }
	}	
	/*
	 * Defines the current position to iterate through
	 */
	public class PositionIterable implements Iterable<Position<E>> {
		/*
		 * creates position iterator
		 * @return new position iterator
		 */
		  public Iterator<Position<E>> iterator( ) { return new PositionIterator( ); }
	}
		  
	/*
	 * creates the position of the iterator
	 */
		  public class PositionIterator implements Iterator<Position<E>> {
			   private Position<E> cursor = first( ); // position of the next element to report
			   private Position<E> recent = null; // position of last reported element
			   /*
				 * checks if the iterator has a next step
				 * @return bool if the iterator has a next point
				 */
			@Override
			public boolean hasNext() {
				return (cursor != null);
			}
			
			/*
			 * gets the next position in the list
			 * @return next position in list
			 */
			@Override
			public Position<E> next() {
				recent = cursor; // element at this position might later be removed
				cursor = after(cursor);
				 return recent;
			}
		  }
}
