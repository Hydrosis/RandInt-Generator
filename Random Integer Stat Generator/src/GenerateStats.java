import java.util.Random;


public class GenerateStats implements Runnable{

	Random rand = new Random();
	int sampleSize = 1000000;
	long trials = 1000000;
	double percent = 10.0;
	int array[] = new int[sampleSize];
	public GenerateStats(){}
	
	public GenerateStats(int[] array, int sampleSize,long trials, double percent)
	{
		this.sampleSize = sampleSize;
		this.trials = trials;
		this.percent = percent;
		this.array = array;
	}
	
	@Override
	public void run() {
		
		@SuppressWarnings("unused")
		int amount = (int) (sampleSize*percent/100);
		for(long i = 0; i<trials; i++)
		{
			int value = rand.nextInt(sampleSize);
			array[value]++;
		}
	}

}
