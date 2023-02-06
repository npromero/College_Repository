package cs2321;
import java.util.Comparator;
import net.datastructures.*;
/*
 * Niklas Romero
 *CS2321/assignment6
 * The class makes a look up table
 */	
public class LookupTable<K extends Comparable<K>, V> extends AbstractMap<K,V> implements SortedMap<K, V> {

	/* 
	 * Use Sorted ArrayList for the Underlying storage for the map of entries.
	 * TODO: Uncomment this line;
	 * private ArrayList<Entry<K,V>> table; 
	 */

	private ArrayList<Entry<K,V>> table; 
	private Comparator<K> comp;

	public LookupTable(){
		table = new ArrayList<Entry<K,V>>(); 
		comp = new DefaultComparator<>();
	}
	/** Returns the smallest index for range table[low..high] inclusive storing an entry
	 with a key greater than or equal to k (or else index high+1, by convention). 
	 @param k key, ints low & high
	 @return int index
	 */
	private int findIndex(K key, int low, int high) {
		if (high < low) return high + 1; // no entry qualifies
		int mid = (low + high) / 2;
		int comp1 = comp.compare(key, table.get(mid).getKey());
		if (comp1 == 0)
			return mid; // found exact match
		else if (comp1 < 0)
			return findIndex(key, low, mid - 1); // answer is left of mid (or possibly mid)
		else
			return findIndex(key, mid + 1, high); // answer is right of mid
	}
	/*
	 * Finds index for a specified key
	 * @param k key
	 * @return int index for k key
	 */
	private int findIndex(K key) { return findIndex(key, 0, table.size( ) - 1); }

	@Override
	/*
	 * gets the size of the table
	 * @return int table size
	 */
	public int size() {
		return table.size();
	}

	@Override
	/*
	 * returns if the table is empty
	 * @return boolean isEmpty
	 */
	public boolean isEmpty() {
		return table.isEmpty();
	}

	@Override
	/*
	 * gets an element based off of it's key
	 * @param k key
	 * @return value v
	 * @Timecomplecity log n
	 */
	public V get(K key) {
		int j = findIndex(key);
		if (j == size( ) || comp.compare(key, table.get(j).getKey()) != 0) return null; // no match
		return table.get(j).getValue( );
	}

	@Override
	/*
	 * adds or changes an element
	 * @param k key, v value
	 * @return v value
	 * @Timecomplecity log n if updating , n otherwise
	 */
	public V put(K key, V value) {
		int j = findIndex(key);
		if (j < size( ) && comp.compare(key, table.get(j).getKey()) == 0) // match exists
			return table.set(j,new mapEntry<K,V>(key,value)).getValue(); //table.get(j).setValue(value);
		table.add(j, new mapEntry<K,V>(key,value)); // otherwise new
		return null;
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
		if (j == size( ) || comp.compare(key, table.get(j).getKey()) != 0) return null; // no match
		return table.remove(j).getValue( );
	}

	/*
	 * gets entry in the table
	 * @param int j
	 * @return entry
	 */
	private Entry<K,V> safeEntry(int j) {
		if (j < 0 || j >= table.size( )) return null;
		return table.get(j);
	}


	@Override
	/*
	gets the set of entries
	 @return iterable 
	 */
	public Iterable<Entry<K, V>> entrySet() {
		return snapshot(0, null);
	}

	@Override
	/*
	 * returns the first entry in the table
	 * @return entry
	 */
	public Entry<K, V> firstEntry() {
		return safeEntry(0);
	}

	@Override
	/*
	 * returns the last entry in the table
	 * @return entry
	 */
	public Entry<K, V> lastEntry() {
		return safeEntry(table.size( )-1);
	}

	@Override
	/*
	 * returns the top entry of a specified entry
	 * @param k key
	 * @return entry
	 */
	public Entry<K, V> ceilingEntry(K key)  {
		return safeEntry(findIndex(key));
	}

	@Override
	/*
	 * returns the bottom entry of a specified entry
	 * @param k key
	 * @return entry
	 */
	public Entry<K, V> floorEntry(K key)  {
		int j = findIndex(key);
		if (j == size( ) || ! key.equals(table.get(j).getKey( )))
			j--; // look one earlier (unless we had found a perfect match)
		return safeEntry(j);
	}

	@Override
	/*
	 * returns the lowest entry of a specified entry and stop point
	 * @param int startIndex, k stop
	 * @return entry
	 */
	public Entry<K, V> lowerEntry(K key) {
		return safeEntry(findIndex(key) - 1);
	}

	/*
	 * returns array list of ordered table
	 * @param k key
	 * @return iterable
	 */
	private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>( );
		int j = startIndex;
		while (j < table.size( ) && (stop == null || comp.compare(stop, table.get(j).getKey()) > 0))
			buffer.addLast(table.get(j++));
		return buffer;
	}

	@Override
	/*
	 * returns the highest entry of a specified entry and stop point
	 * @param k key
	 * @return entry
	 */
	public Entry<K, V> higherEntry(K key) {
		int j = findIndex(key);
		if (j < size( ) && key.equals(table.get(j).getKey( )))
			j++; // go past exact match
		return safeEntry(j);
	}
	/*
	 * gets a portion of the table
	 * @param k fromKey, k toKey
	 * @return iterable
	 */
	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey){
		return snapshot(findIndex(fromKey), toKey);
	}


}