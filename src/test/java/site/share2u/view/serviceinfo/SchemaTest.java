package site.share2u.view.serviceinfo;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import site.share2u.view.BaseJunit4Test;
import site.share2u.view.pojo.Table;
import site.share2u.view.serviceInfo.SchemaService;

public class SchemaTest extends BaseJunit4Test{

	@Autowired
	private SchemaService schemaService;
	
	@Test
	public void getTablesTest(){
		List<Table> tables = schemaService.getTables();
		System.out.println(tables.toString());
	}
}
