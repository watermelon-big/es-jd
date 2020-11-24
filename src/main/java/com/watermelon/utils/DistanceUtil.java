package com.watermelon.utils;

import java.io.UnsupportedEncodingException;

/**
 * @author cuilei
 * @version 1.0
 * @date 2020/9/27 17:32
 */
public class DistanceUtil {
	
	//private static double EARTH_RADIUS = 6378.137;
	private static double EARTH_RADIUS = 6378.137;//地球半径
	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算两个经纬度之间的距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
				Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 1000);
		return s;
	}


	static int c =2;
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(DistanceUtil.GetDistance(116.303167,40.050816,116.296839,40.044713));


		double radLat1 = rad(116.303167);
		double radLat2 = rad(116.296839);
		double a = radLat1 - radLat2;
		double b = rad(40.050816) - rad(40.044713);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		System.out.println(s);
		
//		assert c == 44;
//		System.out.println("如果断言正常，我就被打印");

		String name=java.net.URLEncoder.encode("锟角凤拷锟斤拷址,锟斤拷止锟斤拷锟斤拷!", "gbk");
		System.out.println(name);
		name=java.net.URLEncoder.encode(name,"gbk");
		System.out.println(name);
		name=java.net.URLDecoder.decode(name, "gbk");
		System.out.println(name);
		System.out.println(java.net.URLDecoder.decode(name, "gbk"));
		
	}
	
}
