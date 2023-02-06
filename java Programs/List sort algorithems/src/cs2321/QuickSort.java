/*
 * Niklas Romero
 *CS2321/assignment1
 * The class sorts an array of any type using the quick sort algorithm
 */
package cs2321;

@TimeComplexity("O(n^2)")
@TimeComplexityExpected("O(n lg n)")
public class QuickSort<E extends Comparable<E>> implements Sorter<E> {
	/*
	 * sorts data in array
	 * 
	 * @param E array of data
	 */
	public void sort(E[] array) {
		Qsort(array, 0, array.length - 1);
	}

	/*
	 * sorts data in array
	 * 
	 * @param E array of data,int low last index of the sorted part of the array or
	 * start of the array if nothing is sorted , int high last index in the array
	 */
	public void Qsort(E array[], int low, int high) {
		if (low < high) {
			int par = partition(array, low, high); // par is the partitioned index
			Qsort(array, low, par - 1);
			Qsort(array, par + 1, high);
		}
	}

	/*
	 * partitions the data into sorted and unsorted parts of the array
	 * 
	 * @param E array of data,int low starting index in the array or first non
	 * sorted part of array, int high last index in the array
	 */
	public int partition(E array[], int low, int high) {
		E pivot = array[high];
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			if (((Comparable<E>) array[j]).compareTo(pivot) <= 0) {
				i++;

				E temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}

		E temp = array[i + 1];
		array[i + 1] = array[high];
		array[high] = temp;

		return i + 1;
	}
}
