/*
 * Niklas Romero
 *CS2321/assignment1
 * The class sorts an array of any type using the merge sort algorithm
 */
package cs2321;

import java.util.Arrays;
import static java.util.Arrays.copyOfRange;

@TimeComplexityExpected("O(n lg n)")
public class MergeSort<E extends Comparable<E>> implements Sorter<E> {
	/*
	 * merges the two halves of the array that where put into smaller arrays
	 * 
	 * @param E S1 first small array set, E S2 second small array set, E array, the
	 * big array that the smaller ones are being merged into
	 */

	public void merge(E[] S1, E[] S2, E[] array) {

		int i = 0;
		int j = 0;

		while (i + j < array.length) {
			if (j == S2.length || (i < S1.length && S1[i].compareTo(S2[j]) < 0)) {
				array[i + j] = S1[i++];
			} else {
				array[i + j] = S2[j++];
			}
		}
	}

	/*
	 * sorts data in array
	 * 
	 * @param E array of data
	 */

	public void sort(E[] array) {

		int n = array.length;
		if (n < 2)
			return;

		int midPoint = n / 2;
		E[] S1 = Arrays.copyOfRange(array, 0, midPoint);
		E[] S2 = Arrays.copyOfRange(array, midPoint, n);

		// Recursive calls
		sort(S1);
		sort(S2);

		merge(S1, S2, array);

	}
}