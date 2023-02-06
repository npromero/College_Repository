/*
 * Niklas Romero
 *CS2321/assignment1
 * The class sorts an array of any type using the in place heap algorithm
 */
package cs2321;

@TimeComplexity("O(n lg n)")
public class InPlaceHeapSort<K extends Comparable<K>> implements Sorter<K> {
	DefaultComparator comp = new DefaultComparator();

	/**
	 * sort - Perform an in-place heap sort
	 * 
	 * @param array - Array to sort
	 */
	/*
	 * sorts the array
	 * 
	 * @param k array of data
	 */
	public void sort(K[] array) {
		// adds all values to the tree and than removes resulting in n times the height
		// of the tree
		int n = array.length;
		for (int i = n / 2 - 1; i >= 0; i--) {
			maxHeap(array, n, i);
		}
		for (int i = n - 1; i >= 1; i--) {
			swap(array, 0, i);
			maxHeap(array, i, 0);
		}
	}

	/*
	 * arranges the data within the array
	 * 
	 * @param k array of data, int last index of the array , int i root
	 */
	public void maxHeap(K array[], int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if (l < n && comp.compare(array[l], array[largest]) > 0)
			largest = l;
		if (r < n && comp.compare(array[r], array[largest]) > 0)
			largest = r;
		if (i != largest) {
			swap(array, i, largest);
			maxHeap(array, n, largest);
		}
	}

	/*
	 * swaps data entries
	 * 
	 * @param k array of data, int i index of first element, int j index of second
	 * element
	 */
	protected void swap(K[] array, int i, int j) {
		K temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
