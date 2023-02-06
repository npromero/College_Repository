/*
 * Niklas Romero
 *CS2321/assignment6
 * The class makes a hash map
 */
package cs2321;
import java.util.Comparator;
import net.datastructures.*;

public class HashMap<K, V> extends AbstractMap<K,V> implements Map<K, V> {

	/* 
	 * Use Array of UnorderedMap<K,V> for the Underlying storage for the map of entries. 
	 */
	private UnorderedMap<K,V>[]  table;
	int 	size = 0;  // number of entries 
	int 	capacity; // The size of the hash table. 
	int     DefaultCapacity = 17; 
	private Comparator<K> comp;

	/* Maintain the load factor <= 0.75. 
	 * If the load factor is greater than 0.75, 
	 * then double the table, rehash the entries, and put then into new places. 
	 */
	double  loadfactor= 0.75;  
	/*
	 finds the index based off of it's key
	 @param key k, int low, int high
	 @return int index
	@Timecomplecity log n
	 */

	private int findIndex(K key, int low, int high) {
		if (high < low) return high+1; // no entry qualifies
		int mid = (low + high) / 2;
		int comp1 = comp.compare(key, table[mid].getEntry(key).getKey());
		if (comp1 == 0)
			return mid; // found exact match
		else if (comp1 < 0)
			return findIndex(key, low, mid - 1); // answer is left of mid (or possibly mid)
		else
			return findIndex(key, mid + 1, high); // answer is right of mid
	} 
	/** Version of findIndex that searches the entire table */
	private int findIndex(K key) { return findIndex(key, 0, table.length - 1); }
	/**
	 * Constructor that takes a hash size
	 * @param hashtable size: the number of buckets to initialize 
	 * @return hash map
	 */
	public HashMap(int hashtablesize) {
		if (hashtablesize == 0) {
			capacity = DefaultCapacity; 
		}
		capacity = hashtablesize;
		comp = new DefaultComparator<>();
		table = new UnorderedMap[capacity];
	}

	/**
	 * Default constructor
	 * Initialize the hash table with default hash table size: 17
	 * @return hashmap
	 */
	public HashMap() {
		comp = new DefaultComparator<>();
		capacity = DefaultCapacity;  
		table = new UnorderedMap[capacity];
	}

	/* 
	 * This method should be called by map an integer to the index range of the hash table 
	 * @param key k
	 * @return int value
	 */
	private int hashValue(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}
	/*
	 * returns the value of an element
	 * @param int h, K k
	 * @return value v
	 * @Timecomplecity O(1)
	 */

	protected V bucketGet(int h, K k) {
		UnorderedMap<K,V> bucket = table[h];
		if (bucket == null) return null;
		return bucket.get(k);
	}

	/*
	 * inserts a new entry or changes one
	 * @param int h, K k, value v
	 * @return value v
	 * @Timecomplecity O(1)
	 */
	protected V bucketPut(int h, K k, V v) {
		UnorderedMap<K,V> bucket = table[h];
		if (bucket == null)
			bucket = table[h] = new UnorderedMap<>( );
		int oldSize = bucket.size( );
		V answer = bucket.put(k,v);
		size += (bucket.size( ) - oldSize); // size may have increased
		return answer;
	}

	/*
	 * remove an entry from the list
	 * @param int h, K k
	 * @return value v
	 * @Timecomplecity O(1)
	 */
	protected V bucketRemove(int h, K k) {
		UnorderedMap<K,V> bucket = table[h];
		if (bucket == null) return null;
		int oldSize = bucket.size( );
		V answer = bucket.remove(k);
		size -= (oldSize - bucket.size( )); // size may have decreased
		return answer;
	}

	/*
	 * The purpose of this method is for testing if the table was doubled when rehashing is needed. 
	 * Return the the size of the hash table. 
	 * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
	 * @return int size
	 */
	public int tableSize() {
		if(size> (size * .75)) {
			resize(capacity*2-1);
		}
		return size;
	}


	@Override
	/*
	 * returns size
	 * @return size
	 */

	public int size() {
		return size;
	}

	@Override
	/*
	 * returns size
	 * @return size
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	/*
	 * calls the bucket get to pull the correct information
	 * @param key k
	 * @return value v
	 * @Timecomplecity O(1) 
	 */
	public V get(K key) {
		return bucketGet(hashValue(key), key); 
	}

	@Override
	/*
	 * adds new entry or edits existing one
	 * @param k key, value v
	 * @return value v
	 * @Timecomplecity O(1)
	 */


	public V put(K key, V value) {
		V answer = bucketPut(hashValue(key), key, value);
		if (size > capacity / 2) // keep load factor <= 0.5
			resize(2 * capacity - 1); // (or find a nearby prime)
		return answer;
	}
	/*
	 * resizes the list if more room is needed
	 * @param int newCap
	 * @Timecomplecity n log n
	 */	
	private void resize(int newCap) {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size);
		int i =0;
		for (Entry<K,V> e : entrySet( ))
			buffer.addLast(e);
		capacity = newCap;
		createTable( ); // based on updated capacity
		size = 0; // will be recomputed while reinserting entries
		for (Entry<K,V> e : buffer)
			put(e.getKey( ), e.getValue( ));
	}

	/*
	 * creates new table
	 */
	protected void createTable( ) {
		table = (UnorderedMap<K,V>[ ]) new UnorderedMap[capacity];
	}

	@Override
	/*
	 * removes an element from the list
	 * @param k key
	 * @return value v
	 */
	public V remove(K key) {
		return bucketRemove(hashValue(key), key);
	}

	@Override
	/*
	 iterable that contains next node and previous node
	 @return iterable 
	 */
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>( );
		for (int h=0; h < capacity; h++)
			if (table[h] != null)
				for (Entry<K,V> entry : table[h].entrySet( ))
					buffer.addLast(entry);
		return buffer;
	}
}