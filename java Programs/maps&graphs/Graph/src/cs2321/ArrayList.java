package cs2321;



import java.util.Iterator;

import net.datastructures.List;

public class ArrayList<E> implements List<E> {
	
	/*
	 * The class allows traversal through the list to find the next and previous data point
	 */
	public class ArrayListIterator implements Iterator
	{
		public int cursor = 0;
		/*
		 * checks to see if the current position is the last data point
		 * @return true or false depending on if the data point has a 
		 * next one associated with it
		 */
		public boolean hasNext()
		{
			if(cursor >= size)
			{
				return false;
			}
			else 
			{
				return true;
			}
			
		}
		public E next()
		{
			E temp = get(cursor);
			cursor++;
			return temp;
		}
		
	}
	public static int capacity = 16;
	private E[] data;
	private int size = 0;
	/*
	 * constructs array list
	 */
	public ArrayList() {
		this(capacity);
	}
	/*
	 * constructs array list
	 * @param capacity of the array list
	 */
	public ArrayList(int capacity)
	{
		data = (E[]) new Object[capacity];
	}
	/*
	 * displays the size of the array list
	 * @size of the array list
	 */
	@Override
	public int size() {
		return size;
	}
	/*
	 * checks if the arrayList is empty
	 * @return true/false if the list is empty
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	/*
	 * Gets the data stored at position i
	 * @param i index of array
	 * @return the data stored at that index
	 * @throws IndexOutOfBoundsException
	 */

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		checkIndex(i,size);
		return data[i];
	}
	
	/*
	 * Sets the data stored at position i
	 * @param i index of array, data value e
	 * @return the data stored that was stored at 
	 * index i before changing it
	 * @throws IndexOutOfBoundsException
	 */

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		checkIndex(i,size);
		E temp = data[i];
		data[i] = e;
		return temp;
	}
	/*
	 * adds data to the list at the position
	 * @param i index of array and the data point e
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i,size + 1);
		if(size >= capacity)
		{
			increaseCapacity();
		}
		for(int k = size-1; k >= i; k--)
			{
				data[k +1] = data[k];
			}
		data[i] = e;
		size++;
		
		
		
	}
	/*
	 * Gets the data stored at position i and removes it.
	 * @param i index of array
	 * @return the data stored at that index
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		checkIndex(i,size);
		E temp = data[i];
		for(int k = i; k < size -1; k++)
		{
			data[k] = data[k + 1];
		}
		data[size -1 ] = null;
		size--;
		return temp;
	}

	/*
	 * goes through the data points and links them together 
	  */
	@Override
	public Iterator<E> iterator() {
		
		// TODO Auto-generated method stub
		return new ArrayListIterator();
	}
	
	/*
	 * adds a data point to the beginning of the list 
	 * @param e data point 
	 */
	public void addFirst(E e)  {
		if(size >= capacity)
		{
			increaseCapacity();
		}
		add(0, e);
		
		
	}
	/*
	 * adds data to the end of the list
	 * @param e data point
	 */
	public void addLast(E e)  {
		if(size >= capacity)
		{
			increaseCapacity();
		}
		add(size,e);
		
	}
	/*
	 * removes the first data point in the list
	 * @return e data at index 0
	 * @throws IndexOutOfBoundsException
	 */
	public E removeFirst() throws IndexOutOfBoundsException {
		E temp = get(0);
		remove(0);
		return temp;
	}
	/*
	 * removes the last data point in the list
	 * @return e data at the last index
	 * @throws IndexOutOfBoundsException
	 */
	public E removeLast() throws IndexOutOfBoundsException {
		E temp = get(size-1);
		remove(size-1);
		return temp;
	}
	
	/*
	 * gives the total number of data points that can be 
	 * stored in the list
	 * @return int  capacity, total number of 
	 * positions within the list
	 */
	public int capacity() {
		return capacity;
	}
	
	/*
	 *checks to see if the given index is valid
	 * @param i index of array, n size of array
	 * @throws IndexOutOfBoundsException
	 */
	public void checkIndex(int i, int n) throws IndexOutOfBoundsException
	{
		if(i<0|| i >= n)
		{
			throw new IndexOutOfBoundsException("Illegal index: " + i);
		}
		
	}
	
	/*
	 * doubles the capacity of the list
	 */
	public void increaseCapacity()
	{
			capacity = capacity * 2;
			
			E[] newArray = (E[]) new Object[capacity];
			for(int i = 0; i < size; i++)
			{
				newArray[i] = get(i);
			}
			data = newArray.clone();
	}
	
}
