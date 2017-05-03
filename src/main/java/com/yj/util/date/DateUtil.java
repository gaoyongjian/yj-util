package com.yj.util.date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.StringUtils;

/**
 * 
 * @class:        DateUtil.java
 * @package:      org.com.woaika.mvc.tools
 * @project_name: mobileApp  
 * @author:       bm
 * @date:         2014-11-8 下午03:28:32
 * @Description:  TODO(日期工具类)
 * @Company:   
 * @encode:       
 * @version:      V1.0
 */
public class DateUtil {

	public static SimpleDateFormat FMT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat FMT_STD_DATE = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat FMT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat FMT_SHORT_DATE = new SimpleDateFormat("yyMMdd");
	public static SimpleDateFormat TIME_STAMP = new SimpleDateFormat("yyyyMMddHHmmss");
	public static String DATE_REGEXP = "^[1-2]\\d{3}(\\-\\d{1,2}){2}( (\\d{1,2}:){2}\\d{1,2})?";
	private static Pattern DATE_Pattern = Pattern.compile(DATE_REGEXP);

	public final static DecimalFormat nf = new DecimalFormat("###0");
	static {
		nf.setMaximumIntegerDigits(5);
		nf.setMinimumIntegerDigits(4);
	}
	
	public static String currentTime() {
		return FMT_DATETIME.format(System.currentTimeMillis());
	}
	
	public static String fmtStdDate() {
		return FMT_STD_DATE.format(System.currentTimeMillis());
	}

	public static String datetime(Date date) {
		return date == null ? null : FMT_DATETIME.format(date);
	}

	public static String date(Date date) {
		return date == null ? null : FMT_DATE.format(date);
	}
	public static String StringDate(String date) throws Exception {
		Date dt = FMT_DATE.parse(date);
		return FMT_DATE.format(dt);
	}
	
	/** 
	* 字符串转换成日期 
	* @param str 
	* @return date 
	*/ 
	public static Date StrToDate(String str) {
		Date date = null;
		try {
			date = FMT_DATETIME.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	} 
	/**
     * 获得指定日期的前后日期 t为正表示后 负表示前
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDay(Date date, int t) {
    	if(date == null) return null;
    	
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, t);
        return c.getTime();
    }

    public static String getExpectedDate(Date date, int t) {
        return datetime(getSpecifiedDay(date, t));
    }

    public static boolean isDateStr(String dateStr) {
   		if(dateStr == null || dateStr.length() == 0) return false;
   		return DATE_Pattern.matcher(dateStr).matches();
    }
    
    /** 
    * 获得指定日期的前一天 
    * @param date
    * @return 
    * @throws Exception 
    */ 
    public static String getPreviousDay(String date){
    	Date d = getSpecifiedDay(StrToDate(date), -1);
    	return datetime(d);
    } 
    
    /** 
    * 获得指定日期的后一天 
    * @param date
    * @return 
    */ 
    public static String getNextDay(String date){ 
    	Date d = getSpecifiedDay(StrToDate(date), 1);
    	return datetime(d);
    }
    
    public static String getPlsqlToDate(Date date) {
    	String toDate = "to_date('" + datetime(date)+ "','yyyy-mm-dd hh24:mi:ss')";
    	return toDate;
    }
    
    public static String getPlsqlToDate(String date) {
    	return getPlsqlToDate(StrToDate(date));
    }
    
    /*public static void main(String[] args) throws Exception{
    	String dateStr = "2011-04-11";
		System.out.println(isDateStr(dateStr));
		
		dateStr= "2011-04-12 12:34:22";
		System.out.println(getNextDay(dateStr));
		System.out.println(getPreviousDay(dateStr));
		
		dateStr= "1403245684";
		System.out.println(getDate(dateStr));
    }*/
    //时间戳转化为日期
    public static String getDate(String unixDate) throws Exception {
    	String date = null;
    	long unixLong = 0;
 	   try {
 		  unixLong = Long.parseLong(unixDate) ;
 		  date = getDate(unixLong) ;
 	   } catch(Exception ex) {
 		  throw ex;
// 		  System.out.println("String转换Long错误，请确认数据可以转换！")
 	   }
 	   return date ;
 	}
 	public static String getDate(Long unixLong) {
 	   SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
 	   String date = null;
 	   try {
 		  date = fm2.format(new Date(unixLong*1000));
 	   } catch(Exception ex) {
// 		   System.out.println("日期转换错误！");
 	   }
 	   return date ;
 	}
 	
 	public static String getTimeStamp(Date date){
		return TIME_STAMP.format(date);
 	}
 	public static String[] getYearMonthDayOfDate(Date date){
 		return Until.getDateYearMonthDay(date).split("-");
 	}
 	/**
 	 * 取时间戳为用户名称
 	 * @param third
 	 * @return
 	 */
 	public static String getUserName(String third){
		
		long temp = new Date().getTime();//JAVA的时间戳长度是13位    
		return third+temp;
	}
 	
 // 将字符串转为时间戳 
 	public static String getTime(String user_time) { 
 	String re_time = null; 
 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
 	Date d; 
 	try { 
 	d = sdf.parse(user_time); 
 	long l = d.getTime(); 
 	String str = String.valueOf(l); 
 	re_time = str.substring(0, 10); 
 	} catch (ParseException e) { 
 	// TODO Auto-generated catch block 
 	e.printStackTrace(); 
 	} 
 	return re_time; 
 	} 
 	
 	public static String formatDate(String time){
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;
	if(StringUtils.isNotEmpty(time) && !"null".equalsIgnoreCase(time)){
		try {
			date = sdf.parse(time);
			time=sdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return time;
 	}
 	public static String formatDate(Date time){
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 		String s = sdf.format(time);
 		
 		return s;
 	 	}
 	 	
 	 	/**
 	 	 * 判断现在时间是否在开始和结束时间之间
 	 	 * @param startTime
 	 	 * @param endTime
 	 	 */
 	public static boolean isExpired(Date startTime,Date endTime){
 		
 		Date nowTime = new Date();
 	    Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Calendar nowCal = Calendar.getInstance();
        startCal.setTime(startTime);
        endCal.setTime(endTime);
        nowCal.setTime(nowTime);
        boolean a = nowCal.after(startCal);
        boolean b = nowCal.before(endCal);
        if(a&&b){
        	return true;
        }else{
        	return false;
        }
       
 	}
 	public static void main(String[] args) throws Exception{
// 		Calendar ca = Calendar.getInstance();
// 		ca.add(Calendar.DATE, -1);
// 		Date s = ca.getTime();
// 		ca.add(Calendar.DATE, 5);
// 		Date e = ca.getTime();
// 		isExpired(s,e);
 		System.out.println(DateUtil.currentTime());
		
	}
}
