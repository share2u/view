package site.share2u.view.serviceInfo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.share2u.view.dao.SchemaMapper;
import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.Table;
import site.share2u.view.serviceInfo.SchemaService;

@Service("schemaService")
public class SchemaImpl implements SchemaService{
	
	@Autowired
	private SchemaMapper schemaDao;
	@Override
	public List<Table> getTables() {
		List<Table> tables = schemaDao.getTables();
		List<Table> tableNew= new ArrayList<>();
		
		ArrayList<String> tableName = new ArrayList<>();
        tableName.add("view_option");
        tableName.add("view_serietype");
        tableName.add("view_datatype");
        tableName.add("view_dashboard");
        tableName.add("view_aggmethod");
        tableName.add("view_dimension");
        tableName.add("date_dim");
		for (Table table: tables) {
			if(!tableName.contains(table.getTableName())){
				tableNew.add(new Table(table.getTableName()));
			}
		}
		return tableNew;
	}
	@Override
	public List<Column> getColumns(String tableName) {
		return schemaDao.getColumns(tableName);
	}

}
