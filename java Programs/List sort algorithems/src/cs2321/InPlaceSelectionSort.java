/*
 * Niklas Romero
 *CS2321/assignment1
 * The class sorts an array of any type using the in place selection algorithm
 */
package cs2321;

@TimeComplexity("O(n^2)")
public class InPlaceSelectionSort<K extends Comparable<K>> implements Sorter<K> {
	/*
	 * sorts data in array
	 * 
	 * @param k array of data
	 */

	public void sort(K[] array) {
		DefaultComparator comp = new DefaultComparator();
		int n = array.length;
		int current;
		for (int i = 0; i <= (n - 2); i++) {
			current = i;
			for (int j = (i + 1); j <= n - 1; j++) {
				if (comp.compare(array[j], array[current]) < 0) {
					current = j;
				}
			}
			if (current != i) {
				swap(array, i, current);
			}
		}
	}
	/*
	 * swaps two data points
	 * 
	 * @param k array of data, int i of first index, int j of second index
	 */

	protected void swap(K[] array, int i, int j) {
		K temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
