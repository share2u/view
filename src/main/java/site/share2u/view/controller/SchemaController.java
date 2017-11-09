package site.share2u.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import site.share2u.view.pojo.ResponseBO;
import site.share2u.view.pojo.Table;
import site.share2u.view.serviceInfo.SchemaService;

@Controller
@RequestMapping("/schema")
public class SchemaController {
	@Autowired
	private SchemaService schemaService;

	@RequestMapping(value = {"/tables"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseBO getAllTables() {
		List<Table> tables = schemaService.getTables();
		ResponseBO responseBO = new ResponseBO();
		responseBO.setData(tables);
		return responseBO;
	}
}
