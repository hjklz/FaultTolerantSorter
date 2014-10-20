import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class dataSorter {
	
	public static int[] getArrayFromFile(File inFile){
		int[] data = null;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(inFile));
			String line;
			int index = 0;
			int len = (int) in.lines().count(); //get length
			
			data = new int[len];
			
			in.close();
			
			in = new BufferedReader(new FileReader(inFile));
			
			while ((line = in.readLine()) != null) {
				data[index] = Integer.parseInt(line);
				index++;			
			}
			
			in.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static void writeArrayToFile(File outFile, int[] data){
		try {			
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile.getAbsoluteFile()));
			
			for (int i = 0; i < data.length; i++) {
				bw.write(String.valueOf(data[i]));
				bw.newLine();
			}
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean acceptanceTest(int[] data){
		
		for (int i = 1; i < data.length; i++){
			if (data [i-1] > data[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean simulateMemFail(int numAccess, double probFail){
		double hazard = numAccess * probFail;
		double fail = Math.random();
		
		if (fail >= 0.5 && fail <= hazard) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		File inFile = new File(args[0]);		
		int[] data = getArrayFromFile(inFile);
		
		File outFile = new File(args[1]);
		
		double probPrimary = Double.parseDouble(args[2]);
		double probBackup = Double.parseDouble(args[3]);
		int timeLimit = Integer.parseInt(args[4]);
		
		HeapSort primary = new HeapSort(data.clone());
		Timer t = new Timer();
		Watchdog w = new Watchdog(primary);
		t.schedule(w, timeLimit);
		primary.start();
		try {
			primary.join();
			t.cancel();
		}
		catch (InterruptedException e){}
		
		/*for (int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
		}
		
		System.out.println("\n" + mythread.getMemoryAccesses());*/
		
		if (primary.getSorted() != null && acceptanceTest(data) && simulateMemFail(primary.getMemoryAccesses(), probPrimary)) {
			data = primary.getSorted();
			writeArrayToFile(outFile, data);
			return;
		}
		
		System.out.println("ERROR: Primary has failed! :( ");
		
		InsertionSort backup = new InsertionSort(data.clone());
		t = new Timer();
		w = new Watchdog(backup);
		t.schedule(w, timeLimit);
		backup.start();
		try {
			backup.join();
			t.cancel();
		}
		catch (InterruptedException e){}
		
		/*for (int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
		}
		
		System.out.println("\n" + mythread2.getMemoryAccesses());*/
		
		if ((data = backup.getSorted()) != null && acceptanceTest(data) && simulateMemFail(backup.getMemoryAccesses(), probBackup)){
			writeArrayToFile(outFile, data);
		} else {
			System.out.println("ERROR: Backup has failed! :( ");
			if (outFile.exists()) {
				outFile.delete();
			}
		}
	}
}
