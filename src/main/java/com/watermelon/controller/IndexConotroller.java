package com.watermelon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/18 16:06
 */
@Controller
public class IndexConotroller {
	
	
	@GetMapping({"/","/index"})
	public String index(){
		return "index";
	}
	
}
