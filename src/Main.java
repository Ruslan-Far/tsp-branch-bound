import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static final Integer INF_MAX = 99999999;

//	public static final Integer[][] INITIAL_MATRIX = {
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
		ArrayList<Node> nodes;
		int[][] scoresMatrix;

		matrix = Functions.duplicateInitialMatrix();
		di = Functions.searchMinRows(matrix);
		Functions.runReductionRows(matrix, di);
		dj = Functions.searchMinColumns(matrix);
		Functions.runReductionColumns(matrix, dj);
		Functions.printMatrix(matrix);
		nodes = searchRootLowerBound(di, dj);
		scoresMatrix = getScoresMatrix(matrix);
		Functions.printMatrix(scoresMatrix);
	}

	private static ArrayList<Node> searchRootLowerBound(ArrayList<Integer> di, ArrayList<Integer> dj) {
		ArrayList<Node> nodes;
		int sum;

		nodes = new ArrayList<>();
		sum = Functions.sum(di) + Functions.sum(dj);
		Node node = new Node(sum, true, -1, -1, null);
		nodes.add(node);
		return nodes;
	}

	private static int[][] initScoresMatrix(ArrayList<ArrayList<Integer>> matrix) {
		int[][] scoresMatrix;

		scoresMatrix = new int[matrix.size()][matrix.get(0).size()];
		for (int i = 0; i < scoresMatrix.length; i++) {
			for (int j = 0; j < scoresMatrix[0].length; j++) {
				if (matrix.get(i).get(j) != 0)
					scoresMatrix[i][j] = -1;
			}
		}
		return scoresMatrix;
	}

	private static int minRow(int row, int col, ArrayList<ArrayList<Integer>> matrix) {
		int min;

		min = INF_MAX;
		for (int i = 0; i < matrix.get(row).size(); i++) {
			if (i != col) {
				if (matrix.get(row).get(i) < min)
					min = matrix.get(row).get(i);
			}
		}
		return min;
	}

	private static int minCol(int row, int col, ArrayList<ArrayList<Integer>> matrix) {
		int min;

		min = INF_MAX;
		for (int i = 0; i < matrix.size(); i++) {
			if (i != row) {
				if (matrix.get(i).get(col) < min)
					min = matrix.get(i).get(col);
			}
		}
		return min;
	}

	private static int[][] getScoresMatrix(ArrayList<ArrayList<Integer>> matrix) {
		int[][] scoresMatrix;

		scoresMatrix = initScoresMatrix(matrix);
		for (int i = 0; i < scoresMatrix.length; i++) {
			for (int j = 0; j < scoresMatrix[0].length; j++) {
				if (scoresMatrix[i][j] == 0)
					scoresMatrix[i][j] = minRow(i, j, matrix) + minCol(i, j, matrix);
			}
		}
		return scoresMatrix;
	}
}
