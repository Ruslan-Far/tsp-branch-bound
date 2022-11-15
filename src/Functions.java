import java.util.ArrayList;
import java.util.Objects;

public class Functions {

	public static ArrayList<ArrayList<Integer>> duplicateInitialMatrix() {
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

		for (int i = 0; i < Main.INITIAL_MATRIX.length; i++) {
			matrix.add(new ArrayList<>());
			for (int j = 0; j < Main.INITIAL_MATRIX[i].length; j++) {
				matrix.get(i).add(Main.INITIAL_MATRIX[i][j]);
			}
		}
		return matrix;
	}

	public static ArrayList<Integer> searchMinRows(ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<Integer> di = new ArrayList<>();
		Integer min;

		for (int i = 0; i < matrix.size(); i++) {
			min = Main.INF_MAX;
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j) != -1 && matrix.get(i).get(j) < min)
					min = matrix.get(i).get(j);
			}
			if (!Objects.equals(min, Main.INF_MAX))
				di.add(min);
			else
				di.add(0);
		}
		return di;
	}

	public static void runReductionRows(ArrayList<ArrayList<Integer>> matrix, ArrayList<Integer> di) {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j) != -1 && !Objects.equals(matrix.get(i).get(j), Main.INF_MAX))
					matrix.get(i).set(j, matrix.get(i).get(j) - di.get(i));
			}
		}
	}

	public static ArrayList<Integer> searchMinColumns(ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<Integer> dj = new ArrayList<>();
		Integer min;

		for (int i = 0; i < matrix.get(0).size(); i++) {
			min = Main.INF_MAX;
			for (int j = 0; j < matrix.size(); j++) {
				if (matrix.get(j).get(i) != -1 && matrix.get(j).get(i) < min)
					min = matrix.get(j).get(i);
			}
			if (!Objects.equals(min, Main.INF_MAX))
				dj.add(min);
			else
				dj.add(0);
		}
		return dj;
	}

	public static void runReductionColumns(ArrayList<ArrayList<Integer>> matrix, ArrayList<Integer> dj) {
		for (int i = 0; i < matrix.get(0).size(); i++) {
			for (int j = 0; j < matrix.size(); j++) {
				if (matrix.get(j).get(i) != -1 && !Objects.equals(matrix.get(j).get(i), Main.INF_MAX))
					matrix.get(j).set(i, matrix.get(j).get(i) - dj.get(i));
			}
		}
	}

	public static Integer sum(ArrayList<Integer> arrayList) {
		Integer sum = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			sum += arrayList.get(i);
		}
		return sum;
	}

	public static ArrayList<ArrayList<Integer>> duplicateMatrix(ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<ArrayList<Integer>> duplicateMatrix;

		duplicateMatrix = new ArrayList<>(matrix);
		for (int i = 0; i < matrix.size(); i++) {
			duplicateMatrix.set(i, new ArrayList<>(matrix.get(i)));
		}
		return duplicateMatrix;
	}

	public static void printMatrix(ArrayList<ArrayList<Integer>> matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				System.out.printf("%8d\t", matrix.get(i).get(j));
			}
			System.out.println();
		}
	}

	public static void printMatrixWithoutNegOne(ArrayList<ArrayList<Integer>> matrix) {
//		int countNegOne;

		for (int i = 0; i < matrix.size(); i++) {
//			countNegOne = 0;
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j) != -1)
					System.out.printf("%8d\t", matrix.get(i).get(j));
//				else
//					countNegOne++;
//				if (countNegOne > 1)
//					break;
			}
//			if (countNegOne == 0 || countNegOne == 1)
				System.out.println();
		}
	}

	public static void printMatrix(int [][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%4d\t", matrix[i][j]);
			}
			System.out.println();
		}
	}

	public static void printArray(ArrayList<Integer> arrayList) {
		System.out.println(arrayList.toString());
	}

	public static void printTruePath(ArrayList<Node> truePath) {
		for (int i = 0; i < truePath.size(); i++) {
			if (truePath.get(i).getIncluded() == null)
				System.out.print("Корень ");
			else if (truePath.get(i).getIncluded())
				System.out.print("Включено ");
			else
				System.out.print("НЕ включено ");
			System.out.println(truePath.get(i).getRow() + " " + truePath.get(i).getColumn());
		}
	}
}
