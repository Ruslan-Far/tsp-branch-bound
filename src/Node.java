public class Node {

	private Integer lowerBound;
	private Boolean isLeaf;
	private Integer row;
	private Integer column;
	private Boolean isIncluded;

	public Node(Integer lowerBound, Boolean isLeaf, Integer row, Integer column, Boolean isIncluded) {
		this.lowerBound = lowerBound;
		this.isLeaf = isLeaf;
		this.row = row;
		this.column = column;
		this.isIncluded = isIncluded;
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
}
