package com.watermelon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/18 17:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
	
	private String title;
	private String img;
	private String price;
}
