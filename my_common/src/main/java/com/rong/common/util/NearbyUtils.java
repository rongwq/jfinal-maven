package com.rong.common.util;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

/** 
* @author kejin.li
* @date 2017年6月14日 上午11:08:41
* @version 
* @description   查询附近的人
*/

public class NearbyUtils{

    //static String sql = "select * from xk_business "
    //        +" where latitude <> 0  "
    //        +" and longitude > ? "
    //        +" and longitude < ? "
    //        +" and latitude > ? "
    //        + " and latitude < ? "
    //        + " order by ACOS(SIN((? * 3.1415) / 180 ) * SIN((latitude * 3.1415) / 180 ) "
    //        +" + COS((? * 3.1415) / 180 ) * COS((latitude * 3.1415) / 180 ) "
    //        +" *COS((? * 3.1415) / 180 - (longitude * 3.1415) / 180 ) ) "
    //        +" * 6380 asc limit ?,? " ;

    //static String  orderBy =                 "    ROUND(\n" +
    //       "        6378.138 * 2 * ASIN(\n" +
    //       "            SQRT(\n" +
    //       "                POW(\n" +
    //       "                    SIN(\n" +
    //       "                        (\n" +
    //       "                            "+latitude+" * PI() / 180 -  b.latitude* PI() / 180\n" +
    //       "                        ) / 2\n" +
    //       "                    ),\n" +
    //       "                    2\n" +
    //       "                ) + COS("+latitude+" * PI() / 180) * COS( b.latitude* PI() / 180) * POW(\n" +
    //       "                    SIN(\n" +
    //       "                        (\n" +
    //       "                           "+longitude+" * PI() / 180 -    b.longitude * PI() / 180\n" +
    //       "                        ) / 2\n" +
    //       "                    ),\n" +
    //       "                    2\n" +
    //       "                )\n" +
    //       "            )\n" +
    //       "        ) * 1000\n" ;

	//lat ，lon  当前用户经纬度   raidus
	public static void main(String[] args) {
//		[31.012485760277922, 121.43276715768901, 31.030456239722074, 121.45373684231099]   1000米
		double[] around = getAround( 31.021471,  121.443252, 1000);
		for (double d : around) {
			System.out.println(d);
		}
		Map<String, double[]> map = returnLLSquarePoint(121.4432520000, 31.0214710000, 1);

		  for (String key : map.keySet()) {
			   System.out.println("key= "+ key + " and value= " + map.get(key));
				for (double ds : map.get(key)) {
				System.out.println(ds);
			}
			  }
		double distance = distance(121.4432520000, 31.0214710000, 121.4411514014, 31.0229044299);
		System.out.println(distance);
	}
	
	/**
	 * @param longitude  当前用户 经度
	 * @param latitude  当前用户 纬度
	 * @param raidus   半径（以米为单位）
	 * @return 附近的人集合     按照geohash计算两个经纬度之间的距离    返回数据  能精确到10米左右
     *
     *
     */
    public static Page<Record> getNearbyList(String table, Double longitude, Double latitude, Integer raidus, Integer pageNumber, Integer pageSize) {
        if (raidus == null) {
            raidus=100000;//默认为1000米
		}
		double[] around = getAround(latitude,longitude, raidus);
        //List<Record> find = Db.find(sql, around[0], around[1], around[2], around[3], latitude, latitude, longitude, (pageNumber - 1) * pageSize, pageNumber * pageSize - 1);


        //Page<Record> page = Db.paginateByFullSql(pageNumber, pageSize,totalRowSql, findSql);

        Kv cond = new Kv();
        cond.set("longitude", longitude).set("latitude", latitude).set("zero", around[0]).set("one", around[1]).set("two", around[2]).set("three", around[3]);

        SqlPara a = Db.getSqlPara("UserDao.totalDistance", cond);
        SqlPara b = Db.getSqlPara("UserDao.findDistance", cond);
        Page<Record> page = Db.paginateByFullSql(pageNumber, pageSize, a.getSql(), b.getSql(), b.getPara());
        //Page<Record> page = Db.paginate(pageNumber, pageSize, Db.getSqlPara("UserDao.byDistanceStore", cond));
        return page;
    }
	
	
	/**  
     * 生成以中心点为中心的四方形经纬度  
     *   
     * @param lat 纬度  
     * @param lon 精度  
     * @param raidus 半径（以米为单位）  
     * @return  
     */    
    public static double[] getAround(double lat, double lon, int raidus) {  
    
        Double latitude = lat;    
        Double longitude = lon;    
    
        Double degree = (24901 * 1609) / 360.0;    
        double raidusMile = raidus;    
    
        Double dpmLat = 1 / degree;    
        Double radiusLat = dpmLat * raidusMile;    
        Double minLat = latitude - radiusLat;    
        Double maxLat = latitude + radiusLat;    
    
        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));    
        Double dpmLng = 1 / mpdLng;                 
        Double radiusLng = dpmLng * raidusMile;     
        Double minLng = longitude - radiusLng;      
        Double maxLng = longitude + radiusLng;      
//        return new double[] { minLat, minLng, maxLat, maxLng };    
        return new double[] { minLng, maxLng, minLat, maxLat };    

    }  
    
    
    /**

     * 默认地球半径

     */

    private static double EARTH_RADIUS = 6371;
     
    /**

     * 计算经纬度点对应正方形4个点的坐标

     *与上面计算正方形坐标的结果 小数点后第5位才出现误差

     * @param longitude

     * @param latitude

     * @param distance    单位应该是/1千米

     * @return

     */

    public static Map<String, double[]> returnLLSquarePoint(double longitude,

            double latitude, double distance) {

        Map<String, double[]> squareMap = new HashMap<String, double[]>();

        // 计算经度弧度,从弧度转换为角度

        double dLongitude = 2 * (Math.asin(Math.sin(distance

                / (2 * EARTH_RADIUS))

                / Math.cos(Math.toRadians(latitude))));

        dLongitude = Math.toDegrees(dLongitude);

        // 计算纬度角度

        double dLatitude = distance / EARTH_RADIUS;

        dLatitude = Math.toDegrees(dLatitude);

        // 正方形

        double[] leftTopPoint = { latitude + dLatitude, longitude - dLongitude };

        double[] rightTopPoint = { latitude + dLatitude, longitude + dLongitude };

        double[] leftBottomPoint = { latitude - dLatitude,

                longitude - dLongitude };

        double[] rightBottomPoint = { latitude - dLatitude,

                longitude + dLongitude };

        squareMap.put("leftTopPoint", leftTopPoint);

        squareMap.put("rightTopPoint", rightTopPoint);

        squareMap.put("leftBottomPoint", leftBottomPoint);

        squareMap.put("rightBottomPoint", rightBottomPoint);

        return squareMap;
    }
    
    /**  
     * 计算中心经纬度与目标经纬度的距离（米）     没有下面方法精度高  误差在180米左右
     *   
     * @param centerLon  
     *            中心精度  
     * @param targetLon
     *            需要计算的精度  
     * @return 米
     */    
    public static double distance(double centerLon, double centerLat, double targetLon, double targetLat) {    
    
        double jl_jd = 102834.74258026089786013677476285;// 每经度单位米;    
        double jl_wd = 111712.69150641055729984301412873;// 每纬度单位米;    
        double b = Math.abs((centerLat - targetLat) * jl_jd);    
        double a = Math.abs((centerLon - targetLon) * jl_wd);    
        return Math.sqrt((a * a + b * b));    
    } 
    
    
    /** 
     * 转化为弧度(rad) 
     * */  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
    private static final  double EARTH_RADIUS_2 = 6371000;//赤道半径(单位m)  

    /** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @return 返回的距离，单位m
     * */  
    public static double getDistance(double lon1,double lat1,double lon2, double lat2)  
    {  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = rad(lon1) - rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS_2;  
       s = Math.round(s * 10000) / 10000;  
       return s;  
    } 

}
 