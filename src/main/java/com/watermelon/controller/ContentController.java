package com.watermelon.controller;

import com.watermelon.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/18 18:05
 */

//请求编写
	@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@GetMapping("/parse/{keyword}")
	public Boolean parsr(@PathVariable("keyword") String keyword) throws IOException {
		return contentService.parseConotent(keyword);
	}

	@GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
	public List<Map<String,Object>> search(@PathVariable("keyword") String keyword,
											@PathVariable("pageNo") int pageNo,
										   @PathVariable("pageSize") int pageSize) throws IOException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = contentService.searchPage(keyword, pageNo, pageSize);
		return list;
	}
	
	
	
}
