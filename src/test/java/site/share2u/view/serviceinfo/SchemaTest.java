package site.share2u.view.serviceinfo;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import site.share2u.view.BaseJunit4Test;
import site.share2u.view.pojo.Table;
import site.share2u.view.pojo.Column;
import site.share2u.view.serviceInfo.SchemaService;

import java.util.List;

public class SchemaTest extends BaseJunit4Test{

	@Autowired
	private SchemaService schemaService;
	
	@Test
	public void getTablesTest(){
		List<Table> tables = schemaService.getTables();
		System.out.println(JSON.toJSONString(tables));
	}

	@Test
	public void getColumesTest(){
		List<Column> columns =	schemaService.getColumns("product_spec_key");
		System.out.printf(JSON.toJSONString(columns));
	}
}
