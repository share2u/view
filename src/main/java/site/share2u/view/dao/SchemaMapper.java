package site.share2u.view.dao;

import java.util.List;

import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.Table;

public interface SchemaMapper {
	List<Table> getTables();
	List<Column> getColumns(String tableName);
}
