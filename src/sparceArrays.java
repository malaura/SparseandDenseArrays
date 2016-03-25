import java.util.ArrayList;

import java.util.Random;
import java.util.InputMismatchException;
import java.util.Scanner;




public class sparceArrays {

	
	/* 
	  Creates sparse and dense arrays. Some comments:
	   Finding the max of the sparse array tends to be much faster than finding the max 
	   of the dense array generally in tests
	   The converting methods also tend to be faster than the creating ones. 
	   Creating a dense array tends to be a bit slower than creating a sparse array
	   However, there do tend to be some exceptions where it was faster to create a dense array,
	   which was when the density was higher. 	
	*/
	
	// ************ Main *************

	public static void main(String[] args) {
		
		//asks the user for Length and Density of Array
		int len = askLength();
		double den = askDensity();
		//creates the Dense Array
		int[] denseArray = createDenseArray(len, den);
		//--converts Dense Array to Sparse Array
		convertToSparseArray(denseArray);
		//creates the Sparse Array
		ArrayList<int[]> sparseArray = createSparseArray(len, den);
		//--converts Sparse Array to Dense Array
		convertDenseArray(sparseArray);
		//find Max Dense
		findMaxDense(denseArray);	
		//find Max Sparse
		findMaxSparse(sparseArray);

	}	
	
	
	// ************ Length *************
	
	public static int askLength(){		
		Scanner scanner = new Scanner(System.in);  
		boolean invalidInput;
		int len = 0;
		
		do {
			invalidInput = false;
			try{
				System.out.println("Enter array length ");
				len = scanner.nextInt();
			}
			catch (InputMismatchException ex) {
				System.out.println("Invalid Input");
				invalidInput = true;
				scanner.next();
			}
		}
		
		while(invalidInput);
		return len;
	
	}
	
	
	// ************ Density *************

	public static double askDensity(){
		Scanner scanner = new Scanner(System.in);
		boolean invalidInput;
		double den = 0;
		
		do {
			invalidInput = false;
			try{
				System.out.println("Enter array density ");
				den = scanner.nextDouble();
			}
			catch (InputMismatchException ex) {
				System.out.println("Invalid Input");
				invalidInput = true;
				scanner.next();
			}
		}
		
		while(invalidInput);
		scanner.close(); // closes scanner
		return den;
	
	}
		
	
	// ************ Dense Array *************

	public static int[] createDenseArray(int len, double den){
		long startTime = System.nanoTime();
		int denseArray[] = new int[len];      //creates Array with len as the length
		Random random = new Random(); 
		double newNum;
		
		for(int i= 0; i < len; i++) {
			newNum = random.nextDouble();
			if(newNum < den) {               //uses density to determine if entry will be 0 or value from [1, 1000000]
				denseArray[i] = random.nextInt(1000000) + 1;
			}
			else {
				denseArray[i] = 0;
			}
		}
		
		long endTime = System.nanoTime();
		double duration = (endTime - startTime) / 1000000.0;
		System.out.println("create dense length: " + len + " time: " + duration);
		return denseArray;
		
	}
	
	
	// ************ Sparse Array *************

	public static ArrayList<int[]> createSparseArray(int len, double den) {
		long startTime = System.nanoTime();
		double newNum;
		Random random = new Random();
		ArrayList<int[]> sparseArray = new ArrayList<int[]>(len);  // creates a new ArrayList with len being the size
		
		for (int i = 0; i < len; i++) {
			newNum = random.nextDouble();
			if (newNum < den) {
				int[] list = new int[2];
				list[0] = i; // index
				list[1] = random.nextInt(1000000) + 1; // value
				sparseArray.add(list);
			}
		}
		
		long endTime = System.nanoTime();
		double duration = (endTime - startTime) / 1000000.0;
		System.out.println("create sparse length: " + sparseArray.size() + " time: " + duration);
		if(sparseArray.size() == 0){ 		//if array is zero return null
			return null;
		}
		return sparseArray;
		
	}

	
	// ************ Dense Array to Sparse Array *************
	
	public static ArrayList<int[]> convertToSparseArray(int[] denseArray) {
		long startTime = System.nanoTime();
		ArrayList<int[]> sparseArray = new ArrayList<int[]>(denseArray.length);
		
		for(int i = 0; i < denseArray.length ; i++) {
			if (denseArray[i] != 0) {
				int[] list = new int[2];
				list[0] = i; //index
				list[1] = denseArray[i]; // value
				sparseArray.add(list);
			}
		}
		
		long endTime = System.nanoTime();
		double duration = (endTime - startTime) / 1000000.0;
		System.out.println("convert to sparse length: " + sparseArray.size() + " time: " + duration);
		return sparseArray;
		
	}
	
	
	// ************ Sparse Array to Dense Array *************
		
		public static int[] convertDenseArray(ArrayList<int[]> sparseArray){
			long startTime = System.nanoTime();
			
			if(sparseArray == null){         //checks if sparseArray is null
				long endTime = System.nanoTime();
				double duration = (endTime - startTime) / 1000000.0;
				System.out.println("convert to dense length: " + null + " time: " + duration);
				return null;
			}
			
			int len = sparseArray.size();   //gets the sparseArray size
			int[] index = sparseArray.get(len-1); 
			int[] denseArray = new int[index[0]+1];  
			
			for(int i = 0; i < len ; i++){  //converts the sparseArray to dense Array
				int [] check = sparseArray.get(i);
				int newIndex = check[0];
				int app = check[1];
				denseArray[newIndex] = app;
			}
			
			long endTime = System.nanoTime();
			double duration = (endTime - startTime) / 1000000.0;
			System.out.println("convert to dense length: " + denseArray.length + " time: " + duration);
			return denseArray;
			
			
		}
		
		
		// ************ Find Max Dense *************
 
		public static void findMaxDense(int[] DenseArray){
			long startTime = System.nanoTime();
			int max = 0;
			int counter = 0;
			
			for(int i=0; i < DenseArray.length; i++){    //finds the max value and its index
				if(DenseArray[i] > max){
					max = DenseArray[i];
					counter = i;
				}
			}
			
			System.out.println("find max (dense): " + max + " at: " + counter );
			long endTime = System.nanoTime();
			double duration = (endTime - startTime) / 1000000.0;
			System.out.println("dense find time: " + duration ); //doesn't return anything, only prints
			
			
		}
		
		
		// ************ Find Max Sparse *************
		
		public static void findMaxSparse(ArrayList<int[]> sparseArray){
			long startTime = System.nanoTime();
			int max = 0;
			int counter = 0;
			
			if (sparseArray == null) {    //checks if sparseArray is null first
				System.out.println("find max (sparse): " + null + " at: " + null );
				long endTime = System.nanoTime();
				double duration = (endTime - startTime) / 1000000.0;
				System.out.println("sparse find time: " + duration);
				return;
			}
			
			for(int i=0; i < sparseArray.size(); i++){  //if it is not null, it finds the max value and index
				int[] current = sparseArray.get(i);
				if(current[1] > max){
					max = current[1];
					counter = current[0];
				}
			}
			
			System.out.println("find max (sparse): " + max + " at: " + counter );
			long endTime = System.nanoTime();
			double duration = (endTime - startTime) / 1000000.0;
			System.out.println("sparse find time: " + duration );   //doesn't return anything, only prints
		}

}


