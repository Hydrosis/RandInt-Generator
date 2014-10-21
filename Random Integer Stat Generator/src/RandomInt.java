import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class RandomInt{
	static int sampleSize = 1000;
	static long trials = 1000000000;
	static int numThreads = 8;
	public static int[][] array; //use multiple arrays instead of sychronizing due to too much overhead
	private static int[] finalArray = new int[sampleSize];
	
	public static void main(String args[]) throws IOException, InterruptedException
	{
		Thread[] threads = new Thread[numThreads];
		double avg = 0;
		int threadTrials = 100;
		System.out.println("Starting Trials...");
		for(int a=0; a<threadTrials;a++)
		{
			array = new int[numThreads][sampleSize];
			long start = System.currentTimeMillis();
			for(int i=0; i<numThreads; i++)
			{
				threads[i]= new Thread(new GenerateStats(array[i],sampleSize, trials/numThreads, 10.0), "Thread "+i);
				threads[i].start();
//				System.out.println("Running thread: " + threads[i].getName());
			}
			for(int i=0; i<numThreads; i++)
			{
				threads[i].join();
//				System.out.println("Thread " + threads[i].getName() + " has finished!");
			}
			for(int i=0; i<sampleSize; i++)
			{
				for(int j=0; j<numThreads; j++)
					finalArray[i] += array[j][i];
			}
			long end = System.currentTimeMillis();
			double totalTime = ((double)(end-start))/1000;
			System.out.printf("Trial %d Time: (%.4fs)\n", a, totalTime);
			avg+= totalTime;
		}
		FileWriter writer = new FileWriter(new File("randomint.csv"));
		System.out.println("Saving file...");
		for(int i=0; i<sampleSize; i++)
		{
			writer.write(""+i+","+finalArray[i]+"\n");
		}
		writer.flush();
		writer.close();
		System.out.printf("Done. Average Time: (%.4fs)\n",avg/threadTrials);
	}

}
