package cs2321;
import net.datastructures.Entry;
import net.datastructures.Position;
import net.datastructures.SortedMap;
import java.util.Comparator;

/**
 * Niklas Romero
 *CS2321/assignment6
 * The class makes a binary search tree
 */
public class BinarySearchTree<K extends Comparable<K>,V> extends AbstractMap<K,V> implements SortedMap<K,V> {
	private Comparator<K> c;
	//all the data will be stored in tree
	LinkedBinaryTree<Entry<K,V>> tree; 
	int size;  //the number of entries (mappings)


	//default constructor 
	public BinarySearchTree() {
		tree = new LinkedBinaryTree<Entry<K,V>>();
		tree.addRoot(null);
		c = new DefaultComparator<>();
	}


	/*
	The purpose of this method is purely for testing. 
	@Return the tree.
	 */
	public LinkedBinaryTree<Entry<K,V>> getTree() {
		return tree;
	}
	/*
	finds an entry in the tree
	@param p position, k key 
	@return position of item
	@Timecomplecity log n
	 */	 
	private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
		if (tree.isExternal(p))
			return p; 
		int comp = c.compare(key, p.getElement( ).getKey());
		if (comp == 0)
			return p; // key found; return its position
		else if (comp < 0)
			return treeSearch(tree.left(p), key); // search left subtree
		else
			return treeSearch(tree.right(p), key); // search right subtree
	}

	@Override
	/*
	 returns the size of the tree
	 @return int size of tree
	 */
	public int size(){
		return (tree.size()-1)/2;
	}
	@Override
	/*
	 gets the value of an entry based off of a key
	 @param k key
	 @return v value in the tree
	 @Timecomplecity log n
	 */
	public V get(K key) {
		checkKey(key); // may throw IllegalArgumentException
		Position<Entry<K,V>> p = treeSearch(tree.root(), key);
		if (tree.isExternal(p)) return null; 
		return p.getElement( ).getValue( );
	}

	@Override
	/*
	 makes a new entry or change an entry
	 @param k key, v value
	 @return v value
	@Timecomplecity log n if updating , n otherwise
	 */	
	public V put(K key, V value) {
		checkKey(key); // may throw IllegalArgumentException
		Entry<K,V> newEntry = new mapEntry<>(key, value);
		Position<Entry<K,V>> p = treeSearch(tree.root( ), key);
		if (tree.isExternal(p)) { 
			expandExternal(p, newEntry);
			return null;
		} else { 
			V old = p.getElement( ).getValue( );
			tree.set(p, newEntry);
			return old;
		}
	}
	/*
	 creates new sentinel nodes at the leaf node
	 @param position p, Entry e
	 */
	private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
		tree.set(p, entry); // store new entry at p
		tree.addLeft(p, null); // add new sentinel leaves as children
		tree.addRight(p, null);
	}
	/*
	 checks if the key is allowed in the list
	 @param key k
	 @return boolean
	 @throws IllegalArgumentException
	 */
	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			return (c.compare(key, key) == 0); 
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incompatible key");
		}
	}

	@Override
	/*
	 removes an element from the tree
	 @param key k
	 @return v value
	@Timecomplecity n log n
	 */
	public V remove(K key) {
		checkKey(key);                          // may throw IllegalArgumentException
		Position<Entry<K,V>> p = treeSearch(tree.root(), key);
		if (tree.isExternal(p)) {                   
			return null;
		} else {
			V old = p.getElement().getValue();
			if (tree.isInternal(tree.left(p)) && tree.isInternal(tree.right(p))) { // both children are internal
				Position<Entry<K,V>> replacement = treeMax(tree.left(p));
				tree.set(p, replacement.getElement());
				p = replacement;
			} 
			Position<Entry<K,V>> leaf = (tree.isExternal(tree.left(p)) ? tree.left(p) : tree.right(p));
			Position<Entry<K,V>> sibling = tree.sibling(leaf);
			tree.remove(leaf);
			tree.remove(p); 
			return old;
		}
	}
	/*
	 looks for the max element in the list
	 @param position p
	 @return position
	@Timecomplecity log n
	 */
	protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
		Position<Entry<K,V>> move = p;
		while (tree.isInternal(move))
			move = tree.right(move);
		return tree.parent(move); 
	}

	@Override
	/*
	 setup for iterator
	 @return Iterable
	 */	
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size( ));
		return buffer;
	}

	@Override
	/*
	 returns root
	 @return entry
	 */	
	public Entry<K, V> firstEntry() {
		if (isEmpty()) return null;
		return treeMin(tree.root()).getElement();
	}

	@Override
	/*
	 find a leaf node an returns it
	 @return entry
	 */
	public Entry<K, V> lastEntry() {
		if (isEmpty( )) return null;
		return treeMax(tree.root( )).getElement( );
	}

	@Override
	/*
	 Gets top entry
	 @param key k
	 @return entry
	 */
	public Entry<K, V> ceilingEntry(K key) {
		checkKey(key);                              // may throw IllegalArgumentException
		Position<Entry<K,V>> p = treeSearch(tree.root(), key);
		if (tree.isInternal(p)) return p.getElement();   
		while (!tree.isRoot(p)) {
			if (p == tree.left(tree.parent(p)))
				return tree.parent(p).getElement();          
			else
				p = tree.parent(p);
		}
		return null;                               
	}

	@Override
	/*
	 gets bottom entry
	 @param k key
	 @return entry
	 */
	public Entry<K, V> floorEntry(K key)  {
		checkKey(key); // may throw IllegalArgumentException
		Position<Entry<K,V>> p = treeSearch(tree.root( ), key);
		if (tree.isInternal(p)) return p.getElement( ); 
		while (!tree.isRoot(p)) {
			if (p == tree.right(tree.parent(p)))
				return tree.parent(p).getElement( ); 
			else
				p = tree.parent(p);
		}
		return null;
	}

	@Override
	/*
	Gets next lower entry
	@param key k
	@return entry 
	 */
	public Entry<K, V> lowerEntry(K key) {
		checkKey(key); // may throw IllegalArgumentException
		Position<Entry<K,V>> p = treeSearch(tree.root( ), key);
		if (tree.isInternal(p) && tree.isInternal(tree.left(p)))
			return treeMax(tree.left(p)).getElement( ); // this is the predecessor to p
		// otherwise, we had failed search, or match with no left child
		while (!tree.isRoot(p)) {
			if (p == tree.right(tree.parent(p)))
				return tree.parent(p).getElement( ); // parent has next lesser key
			else
				p = tree.parent(p);
		}
		return null;
	}
	/*
	 Gets min in the tree
	 @param position p
	 @return position p
	 */
	protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p) {
		Position<Entry<K,V>> move = p;
		while (tree.isInternal(move))
			move = tree.left(move);
		return tree.parent(move);              // we want the parent of the leaf
	}

	@Override
	/*
	 Gets next higher entry
	 @param key k
	 @return Entry 
	 */
	public Entry<K, V> higherEntry(K key){
		checkKey(key);                               // may throw IllegalArgumentException
		Position<Entry<K,V>> p = treeSearch(tree.root(), key);
		if (tree.isInternal(p) && tree.isInternal(tree.right(p)))
			return treeMin(tree.right(p)).getElement();     // this is the successor to p
		// otherwise, we had failed search, or match with no right child
		while (!tree.isRoot(p)) {
			if (p == tree.left(tree.parent(p)))
				return tree.parent(p).getElement();           // parent has next lesser key
			else
				p = tree.parent(p);
		}
		return null;                                 // no such greater key exists
	}
	/*
	recurses through map
	@param keys fromKey & toKey, position 
	 */
	private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p,
			ArrayList<Entry<K,V>> buffer) {
		int i = 0;
		if (tree.isInternal(p))
			if (c.compare(p.getElement( ).getKey(), fromKey) < 0)
				// p's key is less than fromKey, so any relevant entries are to the right
				subMapRecurse(fromKey, toKey, tree.right(p), buffer);
			else {
				subMapRecurse(fromKey, toKey, tree.left(p), buffer); // first consider left subtree
				if (c.compare(p.getElement( ).getKey(), toKey) < 0) { // p is within range
					buffer.addLast(p.getElement( )); // so add it to buffer, and consider
					i++;
					subMapRecurse(fromKey, toKey, tree.right(p), buffer); // right subtree as well
				}
			}
	}

	@Override
	/*
	 iterable that contains next node and previous node
	 @param keys fromKey, toKey
	 @return iterable 
	 */
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey)
			throws IllegalArgumentException {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size( ));
		if (c.compare(fromKey, toKey) < 0) // ensure that fromKey < toKey
			subMapRecurse(fromKey, toKey, tree.root( ), buffer);
		return buffer;
	}

	@Override
	/*
	 returns if the tree is empty
	 @return boolean is empty
	 */
	public boolean isEmpty() {
		return tree.isEmpty();
	}



}