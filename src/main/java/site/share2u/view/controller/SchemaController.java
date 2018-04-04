package site.share2u.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import site.share2u.view.pojo.Column;
import site.share2u.view.pojo.ResponseBO;
import site.share2u.view.pojo.Table;
import site.share2u.view.serviceInfo.SchemaService;

import java.util.List;

@Controller
@RequestMapping("/schema")
public class SchemaController {
	@Autowired
	private SchemaService schemaService;

	/**
	 *  获取工作表
	 */
	@RequestMapping(value = {"/tables"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseBO getAllTables() {
		List<Table> tables = schemaService.getTables();
		ResponseBO responseBO = new ResponseBO();
		System.out.println("hahhhhhh");
		responseBO.setData(tables);
		return responseBO;
	}
	/**
	 * 获取维度列表
	 */
	@RequestMapping(value={"/tables/{tableName}"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseBO getColumes(@PathVariable String tableName){
		List<Column> columns = schemaService.getColumns(tableName);
		ResponseBO responseBO = new ResponseBO();
		responseBO.setData(columns);
		return responseBO;
	}
}
