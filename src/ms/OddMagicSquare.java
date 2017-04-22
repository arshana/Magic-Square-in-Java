package ms;

import java.util.Arrays;
import java.util.Scanner;

/** Represents a magic square
 * @author Arshana Jain
 * @version September 28, 2016
 */
public class OddMagicSquare {
	/** The magic square */
	private int[][] square;
	
	/**
	 * Create a magic square of given size n
	 * @param n size of magic square
	 * @throws IllegalArgumentException if n is invalid
	 */
	public OddMagicSquare(int n){
		if(n < 1 || n % 2 == 0){ //checks for odd, positive integer
			throw new IllegalArgumentException("Invalid size.");
		}
		
		//initialize size and magic square
		square = new int[n][n];
		
		int col = n - 1;
		int row = n / 2;
		square[row][col] = 1;
		//sets the values for the magic square
		for(int i = 2; i <= n * n; i++){
			int checkRow = row;
			int checkCol = col;
			if(checkRow < n - 1 && checkCol < n - 1){	//if next row & column are valid
				checkCol++;
				checkRow++;
			}
			else if(checkRow == n - 1 && checkCol < n - 1){	//if next column is valid, but not next row
				checkRow = 0;
				checkCol++;
			}
			else if(checkCol == n - 1 && checkRow < n - 1){	//if next row is valid, but not next column
				checkCol = 0;
				checkRow++;
			}
			else{	//if neither next row nor column are valid
				checkCol = 0;
				checkRow = 0;
			}
			if(square[checkRow][checkCol] > 0){	//if coordinates are previously filled
				col--;
				if(col < 0){
					col = n - 1;
				}
			}
			else{	//if coordinates are not previously filled
				row = checkRow;
				col = checkCol;
			}
			square[row][col] = i;	//set coordinates
		}
	}
	
	/**
	 * Displays magic square
	 * @return the magic square with each row on a separate line and a space between each number in the row
	 */
	public String toString(){
		int size = square.length;
		int maxLength = String.valueOf(size * size).length();	//the maximum length any number in the magic square is
		String squareNums = "";	//the variable that will contain the entire MagicSquare
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				squareNums += Integer.toString(square[i][j]);
				if(j < size - 1){
					for(int k = String.valueOf(square[i][j]).length(); k <= maxLength; k++){
						squareNums += " ";
					}
				}
				else if(i < size - 1){
					squareNums = String.format("%s%s\n"
							, squareNums
							, "");
				}
			}
		}
		return squareNums;
	}	
	
	/**
	 * Checks if a given 2-D array is a magic square
	 * @param inputArr a 2-D array that might be a magic square
	 * @return whether the 2-D array is a magic square or not
	 */
	public static boolean isMagic(int[][] inputArr){
		//checks existence and whether odd or even row-size
		if(inputArr == null || inputArr.length % 2 == 0){
			return false;
		}
		
		int[] arr = new int[inputArr.length * inputArr.length];	//will be used for checking duplicates
		int count = 0;	//will be used as counter for int[] arr
		//checks column size
		for(int i = 0; i < inputArr.length; i++){
			if(inputArr[i] == null || inputArr[i].length != inputArr.length){
				return false;
			}
			//checks whether each digit is less than 1
			for(int j = 0; j < inputArr[i].length; j++){
				if(inputArr[i][j] <= 0){
					return false;
				}
				arr[count] = inputArr[i][j];
				count++;
			}
		}
		//checks for duplicates
		Arrays.sort(arr);
		for(int i = 0; i < arr.length - 1; i++){
			if(arr[i] == arr[i + 1]){
				return false;
			}
		}
		
		int sum = inputArr.length * (inputArr.length * inputArr.length + 1) / 2;
		
		//checks for horizontal and vertical sums
		if(!isCorrectHorVertSums(inputArr, sum)){
			return false;
		}
		
		//checks for diagonal sums
		if(!isCorrectDiagSums(inputArr, sum)){
			return false;
		}

		return true;
	}
	
	/**
	 * Determines whether or not the horizontal and vertical sums of the given 2-D array equate to sum
	 * @param inputArr the 2-D array whose rows and columns sums will be checked
	 * @param sum the value the rows and columns should add up to
	 * @return whether the horizontal and vertical sums are correct
	 */
	private static boolean isCorrectHorVertSums(int[][] inputArr, int sum){
		int sumHoriz = 0;
		int sumVert = 0;
		for(int i = 0; i < inputArr.length; i++){
			for(int j = 0; j < inputArr.length; j++){
				sumHoriz += inputArr[i][j];
				sumVert += inputArr[j][i];
			}
			if(sumHoriz != sum || sumVert != sum){
				return false;
			}
			sumHoriz = 0;
			sumVert = 0;
		}
		return true;
	}
	
	/**
	 * Determines whether or not the diagonal sums of the given 2-D array equate to sum
	 * @param inputArr the 2-D array whose diagonals' sums will be checked
	 * @param sum the value both diagonals should add up to
	 * @return whether the diagonal sums are correct
	 */
	private static boolean isCorrectDiagSums(int[][] inputArr, int sum){
		int sumDiagLtoR = 0;
		int sumDiagRtoL = 0;
		for(int i = 0; i < inputArr.length; i++){
			sumDiagLtoR += inputArr[i][i];
			sumDiagRtoL += inputArr[i][inputArr.length - i - 1];
		}
		if(sumDiagLtoR != sum || sumDiagRtoL != sum){
			return false;
		}
		return true;
	}
	
	/**
	 * Keeps asking client for a valid size for the magic square
	 * @param entry possible magic square size
	 * @return valid magic square size or 0
	 */
	private static String validateSize(String entry){
		Scanner console = new Scanner(System.in);
		while(!isValidEntry(entry)){
			System.out.println();
			System.out.println("That value was invalid.");
			System.out.println("A MagicSquare can only have a positive, odd integer size.");
			System.out.print("Try again. Please enter your MagicSquare's size: ");
			if(console.hasNext()){
				entry = console.nextLine();
			}
		}
		return entry;
	}
	
	/**
	 * Determines whether an entry is a valid size for a magic square
	 * @param entry possible magic square size
	 * @return whether the entry is a valid size
	 */
	private static boolean isValidEntry(String entry){
		//checks that each character in entry was an int
		for(int i = 0; i < entry.length(); i++){
			char entryPart = entry.charAt(i);
			if(entryPart <  48 || entryPart > 57){
				return false;
			}
		}
		//checks that the int is odd or 0 & greater than 0
		int val = Integer.parseInt(entry);
		if((val % 2 == 0 && val != 0)|| val < 0){
			return false;
		}
		return true;
	}
	
	/**
	 * UI
	 * Allows a client to make a magic square via use of console
	 * @param args command-line arguments
	 */
	public static void main(String[] args){
		Scanner console = new Scanner(System.in);
		String entry = "";	//the value the user enters
		System.out.println("Hello! Welcome to MagicSquare Creator.");
		System.out.println("You may quit at any time by typing the number 0.");	
		System.out.println("A MagicSquare can only have a positive, odd integer size.");
		System.out.print("Please enter your MagicSquare's size: ");
		if(console.hasNext()){
			entry = console.nextLine();
		}
		entry = validateSize(entry);	//waits for validity
		int val = Integer.parseInt(entry);	//entry in int-form
		//displays square & asks for next value until 0 entered
		while(val != 0){
			OddMagicSquare newSquare = new OddMagicSquare(val);
			System.out.println(newSquare.toString());
			System.out.println();
			System.out.println("Would you like to make another square?");
			System.out.print("Please enter your MagicSquare's size: ");
			if(console.hasNext()){
				entry = console.nextLine();
			}
			entry = validateSize(entry);
			val = Integer.parseInt(entry);
		}
		//quits at 0
		System.out.print("Bye!");
	}
}