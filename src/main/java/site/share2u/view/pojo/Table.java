package site.share2u.view.pojo;

import org.apache.ibatis.type.Alias;

public class Table {
	/**
	 * 表名
	 */
	private String TableName;

	
	public Table() {
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	@Override
	public String toString() {
		return "Table [TableName=" + TableName + "]";
	}
	
	
}
