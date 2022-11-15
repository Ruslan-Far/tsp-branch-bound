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
	int[][] scoresMatrix;
	int[] coordsMaxScore;
	ArrayList<Node> nodes;
	Node newNode;

	nodes = new ArrayList<>();
	newNode = new Node();
	newNode.setMatrix(Functions.duplicateInitialMatrix());
	di = Functions.searchMinRows(newNode.getMatrix());
	Functions.runReductionRows(newNode.getMatrix(), di);
	dj = Functions.searchMinColumns(newNode.getMatrix());
	Functions.runReductionColumns(newNode.getMatrix(), dj);
	System.out.print("di: ");
	Functions.printArray(di);
	System.out.print("dj: ");
	Functions.printArray(dj);
	System.out.println("Матрица после редукции строк и столбцов:");
	Functions.printMatrix(newNode.getMatrix());
	searchRootLowerBound(nodes, newNode, di, dj);
	System.out.println("Корневая нижняя граница: " + nodes.get(0).getLowerBound());
	System.out.println("Найденный путь:");
	Functions.printTruePath(nodes.get(0).getTruePath());
	System.out.println("\n\n");

	System.out.println("Ветка со включенным маршрутом");
	scoresMatrix = getScoresMatrix(nodes.get(0).getMatrix());
	System.out.println("Матрица оценок:");
	Functions.printMatrix(scoresMatrix);
	coordsMaxScore = getCoordsMaxScore(scoresMatrix);
	System.out.println("Координаты максимальной оценки: " + coordsMaxScore[0] + " " + coordsMaxScore[1]);
	matrix = Functions.duplicateMatrix(nodes.get(0).getMatrix());
	includeBranch(nodes, 0, coordsMaxScore, Functions.duplicateMatrix(matrix));
	System.out.println("Матрица после редукции строк и столбцов:");
//	Functions.printMatrix(nodes.get(1).getMatrix());
	Functions.printMatrixWithoutNegOne(nodes.get(1).getMatrix());
	System.out.println("Корневая нижняя граница: " + nodes.get(1).getLowerBound());
	System.out.println("Найденный путь:");
	Functions.printTruePath(nodes.get(1).getTruePath());
	System.out.println("\n\n");

	System.out.println("Ветка с НЕ включенным маршрутом");
	notIncludeBranch(nodes, 0, coordsMaxScore, scoresMatrix, matrix);
	System.out.println("Матрица:");
//	Functions.printMatrix(nodes.get(2).getMatrix());
	Functions.printMatrixWithoutNegOne(nodes.get(2).getMatrix());
	System.out.println("Корневая нижняя граница: " + nodes.get(2).getLowerBound());
	System.out.println("Найденный путь:");
	Functions.printTruePath(nodes.get(2).getTruePath());
	System.out.println("\n\n");
	selectBranch(nodes);
}

	private static void searchRootLowerBound(ArrayList<Node> nodes, Node newNode,
											 ArrayList<Integer> di, ArrayList<Integer> dj) {
		newNode.setLowerBound(Functions.sum(di) + Functions.sum(dj));
		newNode.setLeaf(true);
		newNode.setRow(-1);
		newNode.setColumn(-1);
		newNode.setIncluded(null);
		newNode.setTruePath(new ArrayList<>());
		newNode.getTruePath().add(newNode);
		nodes.add(newNode);
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
				if (matrix.get(row).get(i) != -1 && matrix.get(row).get(i) < min)
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
				if (matrix.get(i).get(col) != -1 && matrix.get(i).get(col) < min)
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
		for (int j = 0; j < matrix.get(coordsMaxScore[0]).size(); j++) {
			matrix.get(coordsMaxScore[0]).set(j, -1);
		}
		for (int i = 0; i < matrix.size(); i++) {
			matrix.get(i).set(coordsMaxScore[1], -1);
		}
	}

	private static void includeBranch(ArrayList<Node> nodes, int indexNode, int[] coordsMaxScore,
									  ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<Node> truePath;
		ArrayList<Integer> di;
		ArrayList<Integer> dj;
		Integer lowerBound;
		Node newNode;

		matrix.get(coordsMaxScore[1]).set(coordsMaxScore[0], INF_MAX);
		runReductionMatrix(coordsMaxScore, matrix);
		System.out.println("Матрица после редукции матрицы:");
//		Functions.printMatrix(matrix);
		Functions.printMatrixWithoutNegOne(matrix);
		di = Functions.searchMinRows(matrix);
		Functions.runReductionRows(matrix, di);
		dj = Functions.searchMinColumns(matrix);
		Functions.runReductionColumns(matrix, dj);
		System.out.print("di: ");
		Functions.printArray(di);
		System.out.print("dj: ");
		Functions.printArray(dj);
		lowerBound = nodes.get(indexNode).getLowerBound()
				+ Functions.sum(di) + Functions.sum(dj);
		newNode = new Node(lowerBound, true, coordsMaxScore[0], coordsMaxScore[1],
				true, matrix);
		truePath = new ArrayList<>(nodes.get(indexNode).getTruePath());
		truePath.add(newNode);
		newNode.setTruePath(truePath);
		nodes.get(indexNode).setLeaf(false);
		nodes.add(newNode);
	}

	private static void notIncludeBranch(ArrayList<Node> nodes, int indexNode,
										 int[] coordsMaxScore, int[][] scoresMatrix,
										 ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<Node> truePath;
		Integer lowerBound;
		Node newNode;

		lowerBound = nodes.get(indexNode).getLowerBound()
				+ scoresMatrix[coordsMaxScore[0]][coordsMaxScore[1]];
		newNode = new Node(lowerBound, true, coordsMaxScore[0], coordsMaxScore[1], false, matrix);
		truePath = new ArrayList<>(nodes.get(indexNode).getTruePath());
		truePath.add(newNode);
		newNode.setTruePath(truePath);
		nodes.add(newNode);
	}

	private static void selectBranch(ArrayList<Node> nodes) {
		ArrayList<ArrayList<Integer>> matrix;
		int min;
		int indexNode;
		int[][] scoresMatrix;
		int[] coordsMaxScore;
		Node localRoot;

		while (true) {
			min = INF_MAX;
			indexNode = -1;
			for (int i = 0; i < nodes.size(); i++) {
				if (nodes.get(i).getLeaf() && nodes.get(i).getLowerBound() < min) {
					min = nodes.get(i).getLowerBound();
					indexNode = i;
				}
			}
			localRoot = nodes.get(indexNode);
			if (localRoot.getTruePath().size() - 2 == INITIAL_MATRIX.length)
				break;
			System.out.println("------------------------------------------------------------------------------\n\n\n");
			if (localRoot.getIncluded()) {
				System.out.println("Ветвление начнется с ветки со включенным маршрутом под индексом: " + indexNode);
				matrix = Functions.duplicateMatrix(localRoot.getMatrix());
				System.out.println("Ветка со включенным маршрутом");
				scoresMatrix = getScoresMatrix(matrix);
				System.out.println("Матрица оценок:");
				Functions.printMatrix(scoresMatrix);
				coordsMaxScore = getCoordsMaxScore(scoresMatrix);
				System.out.println("Координаты максимальной оценки: " + coordsMaxScore[0] + " " + coordsMaxScore[1]);
				includeBranch(nodes, indexNode, coordsMaxScore, Functions.duplicateMatrix(matrix));
				System.out.println("Матрица после редукции строк и столбцов:");
//				Functions.printMatrix(nodes.get(nodes.size() - 1).getMatrix());
				Functions.printMatrixWithoutNegOne(nodes.get(nodes.size() - 1).getMatrix());
				System.out.println("Корневая нижняя граница: " + nodes.get(nodes.size() - 1).getLowerBound());
				System.out.println("Найденный путь:");
				Functions.printTruePath(nodes.get(nodes.size() - 1).getTruePath());
				System.out.println("\n\n");
				System.out.println("Ветка с НЕ включенным маршрутом");
				notIncludeBranch(nodes, indexNode, coordsMaxScore, scoresMatrix, matrix);
				System.out.println("Матрица:");
//				Functions.printMatrix(nodes.get(nodes.size() - 1).getMatrix());
				Functions.printMatrixWithoutNegOne(nodes.get(nodes.size() - 1).getMatrix());
				System.out.println("Корневая нижняя граница: " + nodes.get(nodes.size() - 1).getLowerBound());
				System.out.println("Найденный путь:");
				Functions.printTruePath(nodes.get(nodes.size() - 1).getTruePath());
				System.out.println("\n\n");
			} else {
				System.out.println("Ветвление начнется с ветки с НЕ включенным маршрутом под индексом: " + indexNode);
				ArrayList<Integer> di;
				ArrayList<Integer> dj;

				matrix = Functions.duplicateMatrix(localRoot.getMatrix());
				matrix.get(localRoot.getRow()).set(localRoot.getColumn(), INF_MAX);
				System.out.println("Матрица после установки бесконечности в рассматриваемый маршрут:");
//				Functions.printMatrix(matrix);
				Functions.printMatrixWithoutNegOne(matrix);
				di = Functions.searchMinRows(matrix);
				Functions.runReductionRows(matrix, di);
				dj = Functions.searchMinColumns(matrix);
				Functions.runReductionColumns(matrix, dj);
				System.out.print("di: ");
				Functions.printArray(di);
				System.out.print("dj: ");
				Functions.printArray(dj);
				System.out.println("Матрица после редукции строк и столбцов:");
//				Functions.printMatrix(matrix);
				Functions.printMatrixWithoutNegOne(matrix);
				System.out.println("Ветка со включенным маршрутом");
				scoresMatrix = getScoresMatrix(matrix);
				System.out.println("Матрица оценок:");
				Functions.printMatrix(scoresMatrix);
				coordsMaxScore = getCoordsMaxScore(scoresMatrix);
				System.out.println("Координаты максимальной оценки: " + coordsMaxScore[0] + " " + coordsMaxScore[1]);
				includeBranch(nodes, indexNode, coordsMaxScore, Functions.duplicateMatrix(matrix));
				System.out.println("Матрица после редукции строк и столбцов:");
//				Functions.printMatrix(nodes.get(nodes.size() - 1).getMatrix());
				Functions.printMatrixWithoutNegOne(nodes.get(nodes.size() - 1).getMatrix());
				System.out.println("Корневая нижняя граница: " + nodes.get(nodes.size() - 1).getLowerBound());
				System.out.println("Найденный путь:");
				Functions.printTruePath(nodes.get(nodes.size() - 1).getTruePath());
				System.out.println("\n\n");
				System.out.println("Ветка с НЕ включенным маршрутом");
				notIncludeBranch(nodes, indexNode, coordsMaxScore, scoresMatrix, matrix);
				System.out.println("Матрица:");
//				Functions.printMatrix(nodes.get(nodes.size() - 1).getMatrix());
				Functions.printMatrixWithoutNegOne(nodes.get(nodes.size() - 1).getMatrix());
				System.out.println("Корневая нижняя граница: " + nodes.get(nodes.size() - 1).getLowerBound());
				System.out.println("Найденный путь:");
				Functions.printTruePath(nodes.get(nodes.size() - 1).getTruePath());
				System.out.println("\n\n");
			}
//			break;
		}
	}
}
