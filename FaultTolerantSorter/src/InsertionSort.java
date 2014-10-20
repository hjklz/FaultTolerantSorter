import java.util.Arrays;


public class InsertionSort extends Thread{
	private volatile int[] arr;
	private volatile int[] result;
	
	public InsertionSort(int [] data){
    	this.arr = data;
    }

	//[0] has number of memory accesses
	//[1] to [length-1] has array in sorted order
	private native int[] memoryAccessesAndSortedArray(int[] data);
	
	public int[] getSorted(){
    	return Arrays.copyOfRange(result, 1, result.length);
    }
	
	public int getMemoryAccesses(){
    	return result[0];
    }
	
	public void run() 
    {
		System.loadLibrary("InsertionSort");
        try {
        	result = memoryAccessesAndSortedArray(arr);
        } catch (ThreadDeath td) {
        	result = null;
			throw new ThreadDeath();
		}
    }
}
