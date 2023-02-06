/*
 * Niklas Romero
 *CS2321/assignment1
 * The class sorts an array of any type using the in place insertion algorithm
 */
package cs2321;

@TimeComplexity("O(n^2)")
public class InPlaceInsertionSort<K extends Comparable<K>> implements Sorter<K> {
	/*
	 * sorts data in array
	 * 
	 * @param k array of data
	 */

	public void sort(K[] array) {
		DefaultComparator comp = new DefaultComparator();
		int n = array.length;
		int minIndex;
		K k;
		for (int i = 0; i <= n - 1; i++) {
			k = array[i];
			minIndex = i - 1;
			while (minIndex >= 0 && comp.compare(array[minIndex], k) > 0) {
				array[minIndex + 1] = array[minIndex];
				minIndex--;
			}
			array[minIndex + 1] = k;
		}
	}
}
