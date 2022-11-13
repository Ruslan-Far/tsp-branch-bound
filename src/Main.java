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
		int[] coordsMaxScore;
		ArrayList<Node> truePath;

		matrix = Functions.duplicateInitialMatrix();
		di = Functions.searchMinRows(matrix);
		Functions.runReductionRows(matrix, di);
		dj = Functions.searchMinColumns(matrix);
		Functions.runReductionColumns(matrix, dj);
		Functions.printMatrix(matrix);
		nodes = searchRootLowerBound(di, dj, matrix);
		scoresMatrix = getScoresMatrix(matrix);
		Functions.printMatrix(scoresMatrix);
		coordsMaxScore = getCoordsMaxScore(scoresMatrix);
		System.out.println(coordsMaxScore[0] + " " + coordsMaxScore[1]);
		truePath = new ArrayList<>();
		truePath.add(nodes.get(0));
		includeBranch(nodes, coordsMaxScore, Functions.duplicateMatrix(matrix), truePath);
		Functions.printMatrix(nodes.get(1).getMatrix());
//		System.out.println("-------------------------");
//		System.out.println("-------------------------");
//		Functions.printMatrix(matrix);
//		System.out.println(nodes.get(1).getLowerBound());
		notIncludeBranch(nodes, coordsMaxScore, scoresMatrix, Functions.duplicateMatrix(matrix), truePath);
		System.out.println("++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++");
		Functions.printMatrix(nodes.get(2).getMatrix());
		System.out.println(nodes.get(2).getLowerBound());
	}

	private static ArrayList<Node> searchRootLowerBound(ArrayList<Integer> di, ArrayList<Integer> dj,
														ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<Node> nodes;
		int sum;

		nodes = new ArrayList<>();
		sum = Functions.sum(di) + Functions.sum(dj);
		Node node = new Node(sum, true, -1, -1, null, Functions.duplicateMatrix(matrix));
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

	private static int[] getCoordsMaxScore(int[][] scoresMatrix) {
		int[] coordsMaxScore;
		int max;

		coordsMaxScore = new int[2];
		max = -1;
		for (int i = 0; i < scoresMatrix.length; i++) {
			for (int j = 0; j < scoresMatrix[i].length; j++) {
				if (scoresMatrix[i][j] > max) {
					max = scoresMatrix[i][j];
					coordsMaxScore[0] = i;
					coordsMaxScore[1] = j;
				}
			}
		}
		return coordsMaxScore;
	}

	private static void runReductionMatrix(int[] coordsMaxScore, ArrayList<ArrayList<Integer>> matrix) {
		matrix.remove(coordsMaxScore[0]);
		for (int i = 0; i < matrix.size(); i++) {
			matrix.get(i).remove(coordsMaxScore[1]);
		}
	}

	private static void includeBranch(ArrayList<Node> nodes, int[] coordsMaxScore,
									  ArrayList<ArrayList<Integer>> matrix, ArrayList<Node> truePath) {
		ArrayList<Integer> di;
		ArrayList<Integer> dj;
		Integer lowerBound;

		matrix.get(coordsMaxScore[1]).set(coordsMaxScore[0], INF_MAX);
		runReductionMatrix(coordsMaxScore, matrix);
		di = Functions.searchMinRows(matrix);
		Functions.runReductionRows(matrix, di);
		dj = Functions.searchMinColumns(matrix);
		Functions.runReductionColumns(matrix, dj);
		lowerBound = truePath.get(truePath.size() - 1).getLowerBound() + Functions.sum(di) + Functions.sum(dj);
		nodes.add(new Node(lowerBound, true, coordsMaxScore[0], coordsMaxScore[1],
				true, matrix));
		truePath.get(truePath.size() - 1).setLeaf(false);
	}

	private static void notIncludeBranch(ArrayList<Node> nodes, int[] coordsMaxScore, int[][] scoresMatrix,
										 ArrayList<ArrayList<Integer>> matrix, ArrayList<Node> truePath) {
		Integer lowerBound;

		lowerBound = truePath.get(truePath.size() - 1).getLowerBound()
				+ scoresMatrix[coordsMaxScore[0]][coordsMaxScore[1]];
		nodes.add(new Node(lowerBound, true, coordsMaxScore[0], coordsMaxScore[1], false, matrix));
		/////////////
		truePath.get(truePath.size() - 1).setLeaf(false);
		/////////////
	}
}
