import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class dataGenerator {

	public static void main(String[] args) {		
		try {
		
			File file = new File(args[0]);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			
			Random rand = new Random();
			
			for (int i = 0; i < Integer.parseInt(args[1]); i++) {
				bw.write(String.valueOf(rand.nextInt(1000)));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
