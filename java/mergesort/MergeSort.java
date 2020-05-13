import java.util.Arrays;

// https://github.com/davidliutest/fun-projects

public class MergeSort {
	
	// Test code
	public static void main(String[] args) throws Exception {
		int[] arr = {1, 44, 2, 2, 43, 123, 13, 4, 5, 6, 3, 5, 2, 88, 4, 5, 33, 333, 3333, -33333, 1, 0 , -23, 23};
		System.out.println(Arrays.toString(mergeSort(arr)));
	}
	
	// Sorts an integer array using the merge sort algorithm
	public static int[] mergeSort(int[] arr) {
		if(arr.length == 1)
			return arr;
		int[] a = mergeSort(sect(arr, 0, (arr.length-1)/2));
		int[] b = mergeSort(sect(arr, (arr.length-1)/2+1, arr.length-1));
		int pa = 0, pb = 0, p = 0;
		while(pa < a.length || pb < b.length) {
			if(pa < a.length && pb < b.length) {
				if(a[pa] < b[pb]) {
					arr[p] = a[pa];
					pa++;
				} else {
					arr[p] = b[pb];
					pb++;
				}
			} else if(pa < a.length) {
				arr[p] = a[pa];
				pa++;
			} else {
				arr[p] = b[pb];
				pb++;
			}
			p++;
		}
		return arr;
	}
	
	public static int[] sect(int[] arr, int a, int b) {
		int[] narr = new int[b-a+1]; 
		for(int i = a; i <= b; i++)
			narr[i-a] = arr[i];
		return narr;
	}
	
}
