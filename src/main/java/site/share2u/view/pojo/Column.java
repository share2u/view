package site.share2u.view.pojo;


public class Column {
	private String columnName;
	private String dataType;
	public String getColumnName() {
		return columnName;
	}
	
	public Column() {
	}

	public Column(String columnName, String dataType) {
		super();
		this.columnName = columnName;
		this.dataType = dataType;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
