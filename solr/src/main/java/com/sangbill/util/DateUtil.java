package com.sangbill.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 日期工具类
 * @author bill
 *
 */
public class DateUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat YEAR = new SimpleDateFormat("yyyy");
	public static SimpleDateFormat sdff = new SimpleDateFormat("MM-dd HH:mm");
	public static SimpleDateFormat sdy = new SimpleDateFormat("yy-MM-dd HH:mm");
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat sdfd = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMddHHmmss");
	static HashMap<String, String> dateRegFormat = new HashMap<String, String>();
	static{
		dateRegFormat.put("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$","yyyy-MM-dd-HH-mm-ss");//2014年3月12日 13时5分34秒，2014-03-12 12:05:34，2014/3/12 12:5:34
		    dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$","yyyy-MM-dd-HH-mm");//2014-03-12 12:05
		    dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$","yyyy-MM-dd-HH");//2014-03-12 12
		    dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");//2014-03-12
		    dateRegFormat.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");//2014-03
		    dateRegFormat.put("^\\d{4}$", "yyyy");//2014
		    dateRegFormat.put("^\\d{14}$", "yyyyMMddHHmmss");//20140312120534
		    dateRegFormat.put("^\\d{12}$", "yyyyMMddHHmm");//201403121205
		    dateRegFormat.put("^\\d{10}$", "yyyyMMddHH");//2014031212
		    dateRegFormat.put("^\\d{8}$", "yyyyMMdd");//20140312
		    dateRegFormat.put("^\\d{6}$", "yyyyMM");//201403
		    dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$","yyyy-MM-dd-HH-mm-ss");//13:05:34 拼接当前日期
		    dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm");//13:05 拼接当前日期
		    dateRegFormat.put("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}$", "yyyy-MM-dd");//2014.10.18(年.月.日)
		    dateRegFormat.put("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd");//14.10.18(年.月.日)
		    dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM");//30.12(日.月) 拼接当前年份
		    dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy");//12.21.2013(日.月.年)
	}
	/**
	 * 根据日期和输出格式，返回字符串
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String convertDateToStr(Date date, String formate) {
		SimpleDateFormat format = new SimpleDateFormat(formate);
		String dateStr = null;
		try{
			dateStr = format.format(date);
		}catch (Exception e) {	
			e.printStackTrace();
		}
		return dateStr;
	}
	/**
	 * 根据日期字符串和输出格式，返回日期
	 * @param date
	 * @param formate
	 * @return
	 */
	public static Date convertStrToDate(String dateStr,
			String formate) {
		SimpleDateFormat format = new SimpleDateFormat(formate);
		Date d = null;
		try{
			d = format.parse(dateStr);
		}catch (Exception e) {		
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 根据指定日期和日期间隔，求得新的日期
	 * 需要算出间隔的毫秒数
	 * 同理可以写出指定日期与小时间隔（分钟间隔，秒间隔）的方法
	 * @param d
	 * @param i
	 * @return
	 */
	public static Date addDate(Date d, int interval) {
		long l =  (long)(interval*24*60*60*1000);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.setTimeInMillis(c.getTimeInMillis() + l);
		return c.getTime();
	}
	/**
	 * 通过时间戳转换日期有两种方法，如下
	 * @param l
	 * @return
	 */
	public static Date convertTimestampToDate(Long l) {
		Timestamp ts = new Timestamp(l);   
        Date date = ts;
        return date;
	}
	
	/*public static Date convertTimestampToDate(Long l) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(l);
        return c.getTime();
	}*/
	
	/**
	 * 导入时，日期格式
	 * @param dateStr
	 * @return
	 */
	public static Date convertToDate(String dateStr) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String regex = "(\\d{4})([-/.]\\d{1,2})([-/.]\\d{1,2})?";
        Pattern p = Pattern.compile(regex); 
        Matcher m = p.matcher(dateStr);
        if(m.find()){
        	String yyyy = m.group(1);
        	String mm = m.group(2).substring(1);
        	String dd = (m.group(3) == null)?"01":m.group(3).substring(1);
        	String yyyyMMdd = yyyy+"-"+mm+"-"+dd;
        	try {
        		d = sdf.parse(yyyyMMdd);
        	} catch (ParseException e) {
        	}
        }
        return d;
	}
	/**
	 * 导入时，日期格式
	 * @param dateStr
	 * @return
	 */
	public static Date convertToDateTime(String dateStr) {
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    DateFormat formatter2;
	    String dateReplace;
		String strSuccess = "";
		Date d = null;
	    try {
	      for (String key : dateRegFormat.keySet()) {
	        if (Pattern.compile(key).matcher(dateStr).matches()) {
	          formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
	          if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$") || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//13:05:34 或 13:05 拼接当前日期
	            dateStr = curDate + "-" + dateStr;
	          } else if (key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//21.1 (日.月) 拼接当前年份
	            dateStr = curDate.substring(0, 4) + "-" + dateStr;
	          }
	          dateReplace = dateStr.replaceAll("\\D+", "-");
	          strSuccess = formatter1.format(formatter2.parse(dateReplace));
	          d = formatter1.parse(strSuccess);
	          break;
	        }
	      }
	    } catch (Exception e) {
	      System.err.println("-----------------日期格式无效:"+dateStr);
	    }
		return d;
	}

}
