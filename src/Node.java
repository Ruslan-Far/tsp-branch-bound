import java.util.ArrayList;

public class Node {

	private Integer lowerBound;
	private Boolean isLeaf;
	private Integer row;
	private Integer column;
	private Boolean isIncluded;
	private ArrayList<ArrayList<Integer>> matrix;
	private ArrayList<Node> truePath;

	public Node() {}

	public Node(Integer lowerBound, Boolean isLeaf, Integer row,
				Integer column, Boolean isIncluded, ArrayList<ArrayList<Integer>> matrix) {
		this.lowerBound = lowerBound;
		this.isLeaf = isLeaf;
		this.row = row;
		this.column = column;
		this.isIncluded = isIncluded;
		this.matrix = matrix;
	}

	public Integer getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Integer lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Boolean getIncluded() {
		return isIncluded;
	}

	public void setIncluded(Boolean included) {
		isIncluded = included;
	}

	public ArrayList<ArrayList<Integer>> getMatrix() {
		return matrix;
	}

	public void setMatrix(ArrayList<ArrayList<Integer>> matrix) {
		this.matrix = matrix;
	}

	public ArrayList<Node> getTruePath() {
		return truePath;
	}

	public void setTruePath(ArrayList<Node> truePath) {
		this.truePath = truePath;
	}
}
