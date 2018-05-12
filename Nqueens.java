package laioffer_practice;

import java.util.*;

public class Nqueens {
	private static List<List<Integer>> nqueens(int n) {
		List<List<Integer>> result = new ArrayList<>();
	    nqueenHelper(result, new ArrayList<>(), n, 0);
	    System.out.println(result.size());
	    return result;
	}
	 private static void nqueenHelper(List<List<Integer>> result, List<Integer> temp, int n, int level) {
		 if (level == n) {
			 result.add(new ArrayList<>(temp));
		     return;
		 }
		 for (int i = 0; i < n; i++) {
			 if (isValidBoard(i, temp)) {
		        temp.add(i);
		        nqueenHelper(result, temp, n, level + 1);
		        temp.remove(temp.size() - 1);
		     }
		 }
	 }
	 private static boolean isValidBoard(int col, List<Integer> temp) {
		 int row = temp.size();
		 for (int i = 0; i < temp.size(); i++) {
			 if (col == temp.get(i) || row + col == i + temp.get(i) ||
				 row - col == i - temp.get(i)) {
		         return false;  
		     }
		 }
		 return true;
	 }
	public static void main(String[] args) {
		int test = 4;
		System.out.println(nqueens(test));
	}
}
