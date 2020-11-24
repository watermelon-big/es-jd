package com.watermelon.service;

import com.alibaba.fastjson.JSON;
import com.watermelon.pojo.Content;
import com.watermelon.utils.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/18 18:05
 */
//业务编写
	@Service
public class ContentService {
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	//1 解析数据放入ES索引中
	public Boolean parseConotent(String keyword) throws IOException {
		List<Content> contentList = new HtmlParseUtil().parseJD(keyword);

		//把查询的数据放到索引中
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("3s");
		
		for(int i=0;i<contentList.size();i++){
			bulkRequest.add(new IndexRequest("jd_goods").
						source(JSON.toJSONString(contentList.get(i)),XContentType.JSON));
		}

		BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		return !bulkResponse.hasFailures();
	}
	
	//获取这些结果，实现搜索功能
	public List<Map<String,Object>> searchPage(String keyword,int pageNum,int pageSize) throws IOException {
		if(pageNum<=1){
			pageNum=1;
		}
		SearchRequest searchRequest = new SearchRequest("jd_goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		//分页
		sourceBuilder.from(pageNum);
		sourceBuilder.size(pageSize);
		
		//精准匹配
		TermQueryBuilder queryBuilder = QueryBuilders.termQuery("title", keyword);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		sourceBuilder.query(queryBuilder);
		
		//执行搜索
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

		//解析结果
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(SearchHit documentFields:searchResponse.getHits().getHits()){
			list.add(documentFields.getSourceAsMap());
		}
		
		return list;
	}

	//获取这些结果，实现搜索高亮功能
	public List<Map<String,Object>> searchHighlightPage(String keyword,int pageNum,int pageSize) throws IOException {
		if(pageNum<=1){
			pageNum=1;
		}
		SearchRequest searchRequest = new SearchRequest("jd_goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		//分页
		sourceBuilder.from(pageNum);
		sourceBuilder.size(pageSize);

		//精准匹配
		TermQueryBuilder queryBuilder = QueryBuilders.termQuery("title", keyword);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		sourceBuilder.query(queryBuilder);
		
		//构建高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("title");
		highlightBuilder.preTags("<span style='color:red'>");
		highlightBuilder.postTags("</span>");
		highlightBuilder.requireFieldMatch(false);//多个高亮显示1
		sourceBuilder.highlighter(highlightBuilder);

		//执行搜索
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

		//解析结果
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(SearchHit documentFields:searchResponse.getHits().getHits()){
			//解析高亮字段
			Map<String, HighlightField> fieldMap = new HashMap<>();
			HighlightField highlightField = fieldMap.get("title");
			
			Map<String,Object> sourceAsMap = documentFields.getSourceAsMap();
			if(highlightField!=null){
				Text[] fragments = highlightField.getFragments();
				String newTitle = "";
				for(Text text:fragments){
					newTitle += text;
				}
				sourceAsMap.put("title",newTitle);
			}
			list.add(documentFields.getSourceAsMap());
		}

		return list;
	}
}
