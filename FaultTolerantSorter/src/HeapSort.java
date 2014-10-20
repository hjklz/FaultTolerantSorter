/*
 * Java Program to Implement Heap Sort
 * code adapted from 
 * http://www.sanfoundry.com/java-program-implement-heap-sort/
 * (c)2014 Sanfoundry, Accessed Oct 15, 2014
 * Modified by Andy Yao
 */

/* Class HeapSort */
public class HeapSort extends Thread {
	private static int N;
	private volatile int[] arr;
	private int count;

	public HeapSort(int[] data) {
		this.arr = data;
		this.count = 0;
	}

	/* Sort Function */
	public void sort(int arr[]) {
		heapify(arr);
		for (int i = N; i > 0; i--) {
			swap(arr, 0, i);
			N = N - 1;
			maxheap(arr, 0);
		}
	}

	/* Function to build a heap */
	public void heapify(int arr[]) {
		N = arr.length - 1;
		for (int i = N / 2; i >= 0; i--)
			maxheap(arr, i);
	}

	/* Function to swap largest element in heap */
	public void maxheap(int arr[], int i) {
		int left = 2 * i;
		int right = 2 * i + 1;
		int max = i;
		if (left <= N && arr[left] > arr[i]) {
			count += 2;
			max = left;
		}
		if (right <= N && arr[right] > arr[max]) {
			count += 2;
			max = right;
		}
		if (max != i) {
			swap(arr, i, max);
			maxheap(arr, max);
		}
	}

	/* Function to swap two numbers in an array */
	public void swap(int arr[], int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
		count += 4;
	}

	public int[] getSorted() {
		return arr;
	}

	public int getMemoryAccesses() {
		return count;
	}

	/* Main method */
	public void run() {
		try {
			sort(arr);
		} catch (ThreadDeath td) {
			arr = null;
			throw new ThreadDeath();
		}
	}
}