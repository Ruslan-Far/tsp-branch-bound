import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static final Integer INF_MAX = 99999999;

//	private static final Integer[][] INITIAL_MATRIX = {
//		{ INF_MAX, 13, 11, 11, 11, 14, 19, 13, 8 },
//		{ 13, INF_MAX, 8, 8, 8, 11, 16, 16, 10 },
//		{ 11, 8, INF_MAX, 10, 10, 3, 8, 13, 8 },
//		{ 11, 8, 10, INF_MAX, 10, 3, 8, 13, 8 },
//		{ 11, 8, 10, 10, INF_MAX, 3, 8, 13, 8 },
//		{ 14, 11, 3, 3, 3, INF_MAX, 5, 14, 11 },
//		{ 19, 16, 8, 8, 8, 5, INF_MAX, 9, 14 },
//		{ 13, 16, 13, 13, 13, 14, 9, INF_MAX, 6 },
//		{ 8, 10, 8, 8, 8, 11, 14, 6, INF_MAX },
//	};

	public static final Integer[][] INITIAL_MATRIX = {
		{ INF_MAX, 20, 18, 12, 8 },
		{ 5, INF_MAX, 14, 7, 11 },
		{ 12, 18, INF_MAX, 6, 11 },
		{ 11, 17, 11, INF_MAX, 12 },
		{ 5, 5, 5, 5, INF_MAX }
	};

	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> matrix;
		ArrayList<Integer> di;
		ArrayList<Integer> dj;

		matrix = Functions.duplicateInitialMatrix();
		di = Functions.searchMinRows(matrix);
		Functions.runReductionRows(matrix, di);
		dj = Functions.searchMinColumns(matrix);
		Functions.runReductionColumns(matrix, dj);
		Functions.printMatrix(matrix);
	}
}
