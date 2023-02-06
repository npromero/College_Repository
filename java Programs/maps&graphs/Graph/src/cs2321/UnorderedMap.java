package cs2321;
import net.datastructures.Entry;
import java.util.Iterator;
/*
 * Niklas Romero
 *CS2321/assignment6
 * The class makes an unordered map
 */	
public class UnorderedMap<K,V> extends AbstractMap<K,V> {

	/* Use ArrayList or DoublyLinked list for the Underlying storage for the map of entries.
	 * TODO:  Uncomment one of these two lines;
	 * private ArrayList<Entry<K,V>> table; 
	 */

	private ArrayList<mapEntry<K,V>> table;
	public UnorderedMap() {
		table = new ArrayList<>( );
	}


	@Override
	/*
	 * returns size of the map
	 * @return int size
	 */
	public int size() {
		return table.size();
	}

	@Override
	/*
	 * returns if the map is empty
	 * @return boolean isEmpty
	 */
	public boolean isEmpty() {
		return table.isEmpty();
	}

	/*
	 * finds an index of a key
	 * @param k key
	 * @return int index
	 * @Timecomplecity log n
	 */
	private int findIndex(K key) {
		int n = table.size( );
		for (int j=0; j < n; j++)
			if (table.get(j).getKey( ).equals(key))
				return j;
		return -1; // key was not found
	}

	@Override
	/*
	 * gets an value from the map
	 * @param k key
	 * @return value v
	 * @Timecomplecity log n
	 */
	public V get(K key) {
		int j = findIndex(key);
		if (j == -1) return null; // key not found
		return table.get(j).getValue( );
	}

	/*
	 * gets an element based off of key
	 * @param k key
	 * @return Entry
	 */
	public Entry<K,V> getEntry(K key){
		int n = table.size( );
		for (int j=0; j < n; j++)
			if (table.get(j).getKey( ).equals(key))
				return table.get(j);
		return null; //  key was not found
	}

	@Override
	/*
	 * adds or changes a value from the list
	 * @param k key, v value
	 * @return v value
	 * @Timecomplecity log n if updating , n otherwise
	 */
	public V put(K key, V value) {
		int j = findIndex(key);
		if (j == -1) {
			table.add(j+1,new mapEntry<>(key, value)); // add new entry
			return null;
		} else // key already exists
			return table.set(j,new mapEntry<K,V>(key,value)).getValue();
	}

	@Override
	/*
	 * removes an element
	 * @param k key
	 * @return v value
	 * @Timecomplecity log n
	 */
	public V remove(K key) {
		int j = findIndex(key);
		int n = size( );
		if (j == -1) return null; // not found
		V answer = table.get(j).getValue( );
		if (j != n - 1)
			table.set(j, table.get(n-1)); 
		table.remove(n-1);
		return answer;
	}

	public class EntryIterator implements Iterator<Entry<K,V>> {
		private int j=0;
		/*
		 * returns if the entry has a next value
		 * @return boolean hasNext
		 */
		public boolean hasNext( ) { return j < table.size( ); }
		/*
		 * gets next entry in the table
		 * @return entry 
		 */
		public Entry<K,V> next( ) {
			return table.get(j++);
		}
		/*
		 * needed to implement iterator but does nothing
		 * @throw UnsupportedOperationException
		 */
		public void remove( ) { throw new UnsupportedOperationException( ); }
	}

	public class EntryIterable implements Iterable<Entry<K,V>> {
		/*
		 * makes an iterator for entries
		 * @return iterator
		 */
		public Iterator<Entry<K,V>> iterator( ) { return new EntryIterator( ); }
	}
	@Override
	/*
	 * makes an iterable for entries
	 * @return iterable
	 */
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable( );
	}

}