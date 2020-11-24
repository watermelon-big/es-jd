package com.watermelon.utils;

import com.watermelon.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/18 16:30
 */
@Component
public class HtmlParseUtil {
	
//	public static void main(String[] args){
//		new HtmlParseUtil().parseJD("码出高效").forEach(System.out::println);
//		
//	}
	
	public List<Content> parseJD(String keyword){
		//1 获取请求  https://search.jd.com/Search?keyword=java

		String url= "https://search.jd.com/Search?keyword="+keyword;

		List<Content> contents =new ArrayList<Content>();
		//解析网页  Document返回的就是浏览器的Document页面对象，所有你在JS中可以使用的方法都可以在这都可以使用
		try {
			Document document = Jsoup.parse(new URL(url), 30000);
			Element element = document.getElementById("J_goodsList");
			//获取素有的li标签
			Elements elements = element.getElementsByTag("li");
			
			
			//获取元素中的内容
			for(Element el:elements){
				if (!el.attr("class").equalsIgnoreCase("ps-item")) {
					String img = el.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
					String price = el.getElementsByClass("p-price").eq(0).text();
					String title = el.getElementsByClass("p-name").eq(0).text();
					Content content = new Content(title,img,price);
					contents.add(content);
					System.out.println("==============================================");
					System.out.println(title);
					System.out.println(img);
					System.out.println(price);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return contents;
	}
}
