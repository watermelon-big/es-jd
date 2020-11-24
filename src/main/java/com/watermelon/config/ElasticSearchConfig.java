package com.watermelon.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/17 16:58
 */

@Configuration
public class ElasticSearchConfig {
	
	@Bean
	public RestHighLevelClient restHighLevelClient(){
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));
		
		return client;

	}
}
