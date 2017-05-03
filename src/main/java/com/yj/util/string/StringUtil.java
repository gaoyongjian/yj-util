package com.yj.util.string;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通用方法实体类
 * @author SCORPIO
 * @since 2013-06-21
 */
public class StringUtil {

	 public  static String uploadBase = "D:\\Project\\BranchesP2PWeb\\WebRoot\\kaku\\";
	//static String uploadBase = "/data/sinocms/web/kaku/";
	//static String postURL = "http://kaku.51credit.com:2080/home/kaku/";
	public static String postURL = "http://localhost:8080";
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	 protected static Map<String,Object> map = new HashMap<String,Object>();
	 
	 public static String[][] bankkey = {
			{"icbc","7"},{"ccb","8"},{"abc","9"},{"hkbc","103"},
			{"boc","10"},{"cmb","22"},{"ceb","23"},{"cib","24"},
			{"cmbc","25"},{"bocomm","26"},{"citic","29"},{"sdb","32"},
			{"gdb","30"},{"hxb","31"},{"spd","33"},{"pingan","65"},
			{"bos","69"},{"psbc","70"},{"bccb","71"},{"nbcb","72"},
			{"bsb","73"},{"jsbk","74"},{ "njcb","75"},{"beai","76"},
			{"dlcb","80"},{"dgcb","79"},{"hrbb","78"},{"cqcb","77"},
			{"gzcb","81"},{"hccb","82"},{"hscb","83"},{"sjbc","84"},
			{"tccb","91"},{"srcb","100"},{"wzcb","101"},{"cscb","102"}			
		};
	/**
	 * 
	 * @param str
	 * @param subStr
	 * @param reStr
	 * @return
	 */
    public static String replaceEx(String str, String subStr, String reStr) {
    	if (str == null){
    	    return null;
    	}
    	if ((subStr == null) || (subStr.equals(""))|| (subStr.length() > str.length()) || (reStr == null)){
    	    return str;
    	}
    	StringBuffer sb = new StringBuffer();
    	String tmp = str;
    	int index = -1;
    	while (true) {
    	    index = tmp.indexOf(subStr);
    	    if (index < 0)
    		break;

    	    sb.append(tmp.substring(0, index));
    	    sb.append(reStr);
    	    tmp = tmp.substring(index + subStr.length());
    	}

    	sb.append(tmp);
    	return sb.toString();
    }
    /**
     * 通用分割字符串方法
     * @param str
     * @param spilter
     * @return
     */
    public static String[] splitEx(String str, String spilter) {
    	if (str == null){
    	    return null;
    	} 
    	if (spilter == null || spilter.equals("")|| str.length() < spilter.length()) {
    	    String t[] = { str };
    	    return t;
    	}
    	ArrayList<String> al = new ArrayList<String>();
    	char cs[] = str.toCharArray();
    	char ss[] = spilter.toCharArray();
    	int length = spilter.length();
    	int lastIndex = 0;
    	for (int i = 0; i <= str.length() - length;) {
    	  boolean notSuit = false;
    	  for (int j = 0; j < length; j++) {
    		if (cs[i + j] == ss[j])
    		    continue;
    		notSuit = true;
    		break;
    	  }

    	  if (!notSuit) {
    		al.add(str.substring(lastIndex, i));
    		i += length;
    		lastIndex = i;
    	   } else {
    		i++;
    	   }
    	}

	    if (lastIndex <= str.length())
	       al.add(str.substring(lastIndex, str.length()));
	       String t[] = new String[al.size()];
	    for (int i = 0; i < al.size(); i++)
	      t[i] = (String) al.get(i);

    	return t;
      }    
    /**
     * 处理卡币种类型方法
     * @param currencyid  单币类型
     * @param dcurrencyid 双币类型
     * @return
     */
    public static String returnCardcurType(String currencyid ,String dcurrencyid){
       	String tempStr = "";
    	String tempStr2 = "";    	
		tempStr = StringUtil.replaceEx(currencyid, ",", "");
		if (isNotEmpty(tempStr)&&tempStr.trim().length()!=0) {
		    tempStr = "单币";
		} else {
		    tempStr = "";
		}
		tempStr2 = StringUtil.replaceEx(dcurrencyid, ",", "");
		if (isNotEmpty(tempStr2)&&tempStr2.trim().length()!=0) {
		    tempStr = tempStr + " 双币";
		}    	
    	return tempStr ;
    } 
    
    public static String returnCardcur(String currencyid,String dcurrencyid){
    	String[] org = null ;
		String tempStr2 = "";
		// 处理
		if (isNotEmpty(currencyid)) {
		    org = StringUtil.splitEx(currencyid, ",");
		    String curr ="";
		  // 分析卡组织
		  for (int j = 0; j < org.length; j++) {
			String temstr3 = org[j];
			temstr3	= temstr3.trim();
			if (isNotEmpty(temstr3)) {
				  curr = temstr3.split("/")[0];				  
				  if(curr.equals("元")){
					curr = "人民币";  
				  }			
				  org[j] = curr ;
			  if(isNotEmpty(tempStr2)){
			    tempStr2 = tempStr2 + "、"+org[j];
			  }else{
				tempStr2 =  org[j];
			  }
			}
		  }
		}
		//得到双币种
		// 处理
		if (isNotEmpty(dcurrencyid)) {
		    String curr ="";
		    String curr1 ="";
		    org = StringUtil.splitEx(dcurrencyid, ",");
		    // 分析卡组织
		    for (int j = 0; j < org.length; j++) {
			String temstr3 = org[j];
			temstr3	=	temstr3.trim();
			if (isNotEmpty(temstr3)) {
				  curr = temstr3.split("/")[0];
				  curr1 = temstr3.split("/")[1];
				  if(curr.equals("元")){
					curr = "人民币";  
				  }			
				  org[j] = curr+"/"+curr1 ;				
			  if(isNotEmpty(tempStr2)){
			    tempStr2 = tempStr2 + "、"+ org[j];
			  }else{				
				tempStr2 = org[j];
			  }
			}
		    }
		} 
		return tempStr2 ;
    }
    
    /**
     * 格式化现有时间
     */
    public static String getFormatNowTime(String formatStr){
       SimpleDateFormat myFormatter = new SimpleDateFormat(formatStr);
  	   String time = myFormatter.format(new Date());
  	   System.out.println(time);
  	   return time ;
     }
    /**
     * 获得过去和未来的日期
     * @return
     */
    public static String getBeforeAndAfterTime(int day ,String formatStr){
       SimpleDateFormat myFormatter = new SimpleDateFormat(formatStr);
 	   Calendar cal=Calendar.getInstance(); 
 	   cal.add(Calendar.DAY_OF_MONTH,day);
 	   String time = myFormatter.format(cal.getTime());
 	   System.out.println(time);
 	   return time ;
    }
	public static boolean postValidator(String articleUrl) throws IOException{
	   boolean flag = true;
	   URL url;
	   HttpURLConnection conn ;
		url = new URL("http://www.baidu.com/s?ie=utf-8&bs=23&f=8&rsv_bp=1&wd="+articleUrl+"&rsv_sug3=1&rsv_sug4=89&rsv_sug1=1&inputT=0");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); 
        conn.setDoOutput(true); 
        InputStream inStream = conn.getInputStream();
         
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        String content =  new String(data, "utf-8");
        System.out.println(content);
        if(content.contains("没有找到与")){
           	flag = false ;
        }else{
        	flag = true ;
        }
        return flag ;	 	   
	}    
	/*public static void main(String[] args) throws IOException {
	   StringUtil.getBeforeAndAfterTime(7,"yyyy-MM-dd");
	   StringUtil.getFormatNowTime("yyyy-MM-dd");
	   System.out.println(StringUtil.postValidator("http://daikuan.51credit.com/xedk/106922929999.shtml"));
	}*/
	public boolean createHtml(StringBuffer titledes, String url, String path) {

    	System.out.println("*************开始生成*************");
		System.out.println("*********" + path + "*********");
		url = postURL + url;
		path = uploadBase + path;
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String dir = path.substring(0, path.lastIndexOf(File.separator));
		System.out.println("*********" + dir + "*********");
		File fdir = new File(dir);
		fdir.mkdirs();
		fdir = null;

		File f = new File(path);
		// make proxy
		/*
		 * String proxy = "192.168.224.12";//防火墙地址 String port = "8080"; //防火墙端口
		 * Properties systemProperties = System.getProperties();
		 * systemProperties.setProperty("http.proxyHost",proxy);
		 * systemProperties.setProperty("http.proxyPort",port);
		 */
		try {
			urlfile = new URL(url);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();;
		}
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			byte[] buf = new byte[1024];
			int size = 0;
			int resLen = 0;
			if ((titledes != null) && (titledes.length() != 0)) {
				titledes = new StringBuffer(new String(titledes.toString().getBytes("GB2312"), "gbk"));
				System.out.println(titledes);
				bos.write(titledes.toString().getBytes());

			} else {
				// bos.write(titleb.toString().getBytes());
				// bos.write("<title>银行信用卡_申请_特色_功能_收费_银行信用卡库_我爱卡</title>\n".getBytes());
			}

			while ((size = bis.read(buf)) != -1) {
				resLen = resLen + size;
				bos.write(buf, 0, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.flush();
				bis.close();
				httpUrl.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
    
    /**
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str == null || str.trim().length() == 0)
			return true;
		return false;
	}
	
	public static boolean isNotEmpty(String str) {
		return (!(isEmpty(str)));
	}

	public static boolean isNumber(String arg0) {
		if (arg0 == null || arg0.length() == 0) return false;
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(arg0);
		return m.matches();
	}
	
	public static boolean isDouble(String arg0) {
		if (arg0 == null || arg0.length() == 0) return false;
		try{
			Double.parseDouble(arg0);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static String nullToEmpty(String arg) {
		if (arg==null || "null".equals(arg.toLowerCase())) {
			return "" ;
		} else {
			return arg;
		}
	}
	//过滤掉字符串中的html字符，使其变成纯文本
	public static String getText(String htmlStr) {
		if(htmlStr==null || "".equals(htmlStr)) return "";
	    String textStr ="";    
	    Pattern pattern;
	    Matcher matcher;
	    try {
		    String regEx_remark = "<!--.+?-->";
		    String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }    
		    String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }    
	        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式    
	        String regEx_html1 = "<[^>]+";
	        String regEx_specs[] = {"&nbsp;","&ldquo;","&rdquo;"};
	        htmlStr = htmlStr.replaceAll("\n","");
	        htmlStr = htmlStr.replaceAll("\t","");
	        pattern=Pattern.compile(regEx_remark);//过滤注释标签
	        matcher=pattern.matcher(htmlStr);
		    htmlStr=matcher.replaceAll("");
		       
		    pattern = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);    
		    matcher = pattern.matcher(htmlStr);    
	        htmlStr = matcher.replaceAll(""); //过滤script标签    

	        pattern = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);    
	        matcher = pattern.matcher(htmlStr);    
	        htmlStr = matcher.replaceAll(""); //过滤style标签    
	        
	        pattern = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);    
	        matcher = pattern.matcher(htmlStr);    
	        htmlStr = matcher.replaceAll(""); //过滤html标签    
	            
	        pattern = Pattern.compile(regEx_html1,Pattern.CASE_INSENSITIVE);    
	        matcher = pattern.matcher(htmlStr);    
	        htmlStr = matcher.replaceAll(""); //过滤html标签    
	          
	        for (String string : regEx_specs) {
		        pattern = Pattern.compile(string,Pattern.CASE_INSENSITIVE);    
		        matcher = pattern.matcher(htmlStr);    
		        htmlStr = matcher.replaceAll(""); //过滤html标签    
	          }
	       htmlStr = htmlStr.replaceAll("[\n|\r|\t]*", "");//去掉制表符，换行符等特殊符号
	       //htmlStr = htmlStr.replace("　　", "");
	       htmlStr = htmlStr.replaceAll("\\“","\'");//去除双引号
	       htmlStr = htmlStr.replaceAll("\\”","\'");
	       htmlStr = htmlStr.replaceAll("\"","\'");
	       textStr = htmlStr.trim();    
	   }catch(Exception e) {
		   System.out.println("获取HTML中的text出错:");
		   e.printStackTrace();
	    }    
	   return textStr;//返回文本字符串
	} 
	/** 
     * 验证输入的邮箱格式是否符合 
     * @param email 
     * @return 是否合法 
     */ 
	public static boolean emailFormat(String email){
	        boolean tag = true; 
	        final String pattern1 = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";//"^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$"; 
	        final Pattern pattern = Pattern.compile(pattern1); 
	        final Matcher mat = pattern.matcher(email); 
	        if (!mat.find()){
	            tag = false; 
	        } 
	        return tag; 
	 } 
	
	/**
	 * 取str 的hashcode 如果为负数 则取正
	 * @param str
	 * @return
	 */
	
	public static int getHashCode(String str){
		
		int  code = str.hashCode();
		if(code<0){
			code = Math.abs(code);
		}
		return code;
	}
	/**
	 * 时间显示  **分钟前    **小时前
	 */
	 public static String changTimeType(String time){
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		  String timeString ="" ;
		  try {
			  long millionSeconds = format.parse(time).getTime();//毫秒
			  Date date = new Date();
			  long newSeconds = format.parse(format.format(date)).getTime();
			  long  subtraction = newSeconds - millionSeconds ;
			  long days = subtraction / (1000 * 60 * 60 * 24);  //获得天数
			  long hours = (subtraction % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//获得余下的小时数
			  long minutes = (subtraction % (1000 * 60 * 60)) / (1000 * 60); //获得余下的分钟数
			  long seconds = (subtraction % (1000 * 60)) / 1000; // 获得余下的秒数
	          if(minutes >5){
	        	timeString = minutes + "分 前" ;  
	          }else{
				timeString = "刚刚" ;
		      }
	          if(hours >0){
	        	timeString = hours + "小时 前";	
	          }
	          if(days >0){
	        	timeString = days + "天 前";	
	          }          
	          
		  } catch (Exception e) {  
			  // TODO Auto-generated catch block   
			  e.printStackTrace();
		  }	  
		  return timeString ;
	  }
	 
	//文章内容处理
		public static String getImageUrl(String htmlStr,String siteId) {
			if(htmlStr==null || "".equals(htmlStr)) return "";
		    String textStr ="";    
		    Pattern pattern;
		    Matcher matcher;
		    htmlStr = htmlStr.replaceAll("\\“","\'");//去除双引号
		    htmlStr = htmlStr.replaceAll("\\”","\'");
		    htmlStr = htmlStr.replaceAll("\"","\'");
		    try {
		       if(siteId.equals("206")){
			      htmlStr = htmlStr.replaceAll("/zcms/wwwroot/credit/","http://www.51credit.com/");
			   }
		       if(siteId.equals("218")){
		    	   htmlStr = htmlStr.replaceAll("/zcms/wwwroot/daikuan/","http://daikuan.51credit.com/");
		       }
		       if(siteId.equals("220")){
		    	   htmlStr = htmlStr.replaceAll("/zcms/wwwroot/licai/","http://licai.51credit.com/");
		       }
		       if(siteId.equals("214")){
		    	   htmlStr = htmlStr.replaceAll("/zcms/wwwroot/news/","http://news.51credit.com/");
		       }
		       textStr = htmlStr.trim();    
		   }catch(Exception e) {
			   System.out.println("获取HTML中的text出错:");
			   e.printStackTrace();
		    }    
		   return textStr;//返回文本字符串
		} 
	 //验证
	public static boolean username(String username){
		boolean tag = false; 
		String reg="^[\\_a-zA-Z0-9]{5,20}$";
		Pattern p = Pattern.compile(reg);
		Matcher ma = p.matcher(username);
		boolean find=ma.find();
		if(find){
			String reg2="^\\d{5,20}$";
			Pattern p2 = Pattern.compile(reg2);
			Matcher m2 = p2.matcher(username);
			boolean find2=m2.find();
			if(!find2){
				tag=true;
			}
		}
        return tag; 
	}
	
	/*public static void main(String[] args){
		String username = "11111aa";
		System.out.println(username(username));
		
		String pwd="3333666";
		String reg="^[\\_a-zA-Z0-9]{5,20}$";
		Pattern p = Pattern.compile(reg);
		Matcher ma = p.matcher(pwd);
		boolean find=ma.find();
		System.out.println(find);
		String reg2="^\\d{5,20}$";
		Pattern p2 = Pattern.compile(reg2);
		Matcher m2 = p2.matcher(pwd);
		boolean find2=m2.find();
		System.out.println(find2);
	}*/
	/** 
     * 验证输入的手机号码是否符合 
     * @param mobile 
     * @return 是否合法 
     */ 
	
	public static boolean mobileFormat(String mobile){
        boolean tag = true; 
        final String pattern1 = "^[1][3,4,5,7,8][0-9]{9}$";//"^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$"; 
        final Pattern pattern = Pattern.compile(pattern1); 
        final Matcher mat = pattern.matcher(mobile); 
        if (!mat.find()){
            tag = false; 
        } 
        return tag; 
	} 
	public static void main(String[] args){
		String ss = "17012635263";
		System.out.println(mobileFormat(ss));
	}
	/**
	 * 换算账单日
	 * @param arg
	 * @throws ParseException 
	 */
	public static String stringBufferTime(String arg) throws ParseException{
		int day = 0;
		Calendar cal=Calendar.getInstance(); 
		day=cal.get(Calendar.DAY_OF_MONTH);
    	SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
    	String str = null;
    	if(Integer.valueOf(arg)<day){
    		str  =String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH)+2)+"-"+ arg;
		}else{
			str  =String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH)+1)+"-"+ arg;
		}
    	Date date = myFormatter.parse(str);
	    cal.setTime(date);
	    String bufferTime = myFormatter.format(cal.getTime());
	    cal.add(Calendar.DAY_OF_YEAR,-20);
	    String postDate = myFormatter.format(cal.getTime());
//	    System.out.println(bufferTime);	
//	    System.out.println(postDate);	
    	return postDate;
	}
	/**
	 * 系统时间当前月的总天数
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getDaysOfNextMonth(Date date) throws ParseException{
		int days = 0;
		Calendar cal=Calendar.getInstance(); 
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+2;
    	days = getDayCountFromYearMonth(year,month);
		return days;
	}
	/**
	 * 下个月的总天数
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	
	public static int getDaysOfTheMonth(Date date) throws ParseException{
		Calendar cal=Calendar.getInstance(); 
    	cal.setTime(date);
    	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	/**
	 * 最长免息期计算
	 * @param repayDay
	 * @return
	 * @throws ParseException
	 */
	
	public static int maxFreeDay(String billDay,String repayDay) throws ParseException{
		//int day = 0;
		int date = 0;
		Calendar cal=Calendar.getInstance(); 
		//day=cal.get(Calendar.DAY_OF_MONTH)+1;
		date=cal.get(Calendar.DATE); 
    	SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
    	int str = 0;
    	int bDay = Integer.valueOf(billDay);
    	int rDay = Integer.valueOf(repayDay);
    	if(date>bDay){
    		if(date>=rDay){
    			if(bDay>=rDay){//(当前日期>账单日且当前日期>还款日)账单日>=还款日
    				str = getDaysOfTheMonth(new Date()) - date + getDaysOfNextMonth(new Date()) + rDay;//当月天数-当日+下月天数+还款日
    			}else{//(当前日期>账单日且当前日期>还款日)账单日<还款日
    				str = getDaysOfTheMonth(new Date()) - date + rDay;//当月天数-当日+还款日
    			}
    		}else{//(当前日期>账单日且当前日期<还款日)
    			str = getDaysOfTheMonth(new Date()) - date + rDay;//当月天数-当日+还款日
    		}
		}else{
			if(date<=rDay){
				if(bDay>=rDay){//(当前日期<账单日且当前日期<=还款日)账单日>=还款日
					str = getDaysOfTheMonth(new Date())-date+rDay;//当月天数-当日+还款日
				}else{//(当前日期<账单日且当前日期<=还款日)账单日<还款日
					str = rDay - date;//还款日-当日
				}
			}else{//(当前日期<账单日且当前日期>还款日)
				str = getDaysOfTheMonth(new Date())-date+rDay;//当月天数-当日+还款日
			}
		}	
    	return str;
	}
	/*public static void main(String[] args) throws ParseException{
		String billDay = "22";
		String repayDay = "10";
		System.out.println(maxFreeDay(billDay,repayDay));
		Date date = new Date();
		//System.out.println(getDaysOfTheMonth(date));
	}*/
	/**
	 * 当前月份的总天数
	 * @param nYear
	 * @param nMonth
	 * @return
	 */

	public static int getDayCountFromYearMonth(int nYear, int nMonth)
	{
	  Calendar c= Calendar.getInstance();
	  c.set(Calendar.YEAR, nYear);
	  c.set(Calendar.MONTH, nMonth -1);
	 
	  return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 计算剩余天数
	 */
	public static int lastDay(String billDay,String repayDay) throws ParseException{
		int day = 0;
		Calendar cal=Calendar.getInstance(); 
		day=cal.get(Calendar.DATE);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
		int bDay = Integer.valueOf(billDay);
		int rDay = Integer.valueOf(repayDay);
		int str = 0;
		if(day>bDay){
			str = getDaysOfTheMonth(new Date())- day + rDay;//当月天数-当日+还款日
		}else{
			if(day<=rDay){
				str = rDay - day;//当前时间小于等于账单日，同时大于等于还款日时，还款日-当日
			}else{
				str = getDaysOfTheMonth(new Date())- day + rDay;//当月天数-当日+还款日
			}
		}
		return str;
	}
	/*public static void main(String[] args) throws Exception{
		String billDay = "1";
		String repayDay = "15";
		System.out.println(lastDay(billDay,repayDay));
	}*/
	/**
	 * 信用卡有限期
	 * @throws ParseException 
	 */
	public static boolean isValid(String valid){
		boolean trueOfFalse = false;
		if (valid == null || valid.length() == 0) return trueOfFalse;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date date;
			try {
				date = sdf.parse(valid);
				if(valid.equals(sdf.format(date))){
					trueOfFalse = true;
				}
			} catch (ParseException e) {
				trueOfFalse = false;
			}
			return trueOfFalse;
	}
	/**
	 * 将String中的null值转为""
	 * @param arg
	 * @throws ParseException
	 */
	public static String zhuanyinull(String arg){
		if(StringUtil.isEmpty(arg)){
			arg = "";
		}
		return arg;
	}
	/**
	 * 判断不同类型的url  (10388)0:焦点、(10368)1:百科、(10381)2:专题
	 * @param itemStyle
	 * @param itemId
	 * @return
	 */
	public static String getArticleUrl(String itemStyle, String itemId,String redirectUrl) {
		String url = "";
		if(itemStyle.equals("10388")){
			url = "http://licai.51credit.com/lcsytj/"+itemId+".shtml";
		}
		if(itemStyle.equals("10368")){
			url = "http://licai.51credit.com/lcbk/yhlcbk/"+itemId+".shtml";
		}
		if(itemStyle.equals("10381")){
			url = redirectUrl;
		}
		return url;
	}
	public static String getArticleContent(String itemStyle,String type){
		String id = null;
		if(type.equals("10388")){
			id = itemStyle.substring(itemStyle.length()-12, itemStyle.length()-6);
		}
		return id;
	}
	public static String userGender(String gender){
		String userGender = "";
		if(StringUtil.isNotEmpty(gender)){
			if(gender.equals("F")){
				userGender = "女";
			}
			if(gender.equals("M")){
				userGender = "男";
			}
			if(gender.equals("A")){
				userGender = "保密";
			}
		}
		return userGender;
	}
	
	public static boolean userBirth(String birth){
		boolean trueOfFalse = false;
		if (birth == null || birth.length() == 0) return trueOfFalse;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
			Date date;
			try {
				date = sdf.parse(birth);
				if(birth.equals(sdf.format(date))){
					trueOfFalse = true;
				}
			} catch (ParseException e) {
				trueOfFalse = false;
			}
			return trueOfFalse;
	}
	public static float annual_revenue(){
		int T= 10000; //总投资(元)
		float R=1.2f; //日万份收益(元)
		int C= 10; //理财周期(天)
		//-----------------------------------------
		float S= 0; //总复值收益(元)
	
		//算法构造
		for(int i =1;i<=C;i++){
			S+=(T+S)/10000*R;
			
		}
		C = C-1;
		return S;
//		function ZZSY($t,$r,$c) //总复值收益函数
//		{
//			for($i=1;$i<=$c;$i++)
//			{
//				$s+=($t+$s)/10000*$r;
//				$arr[]=number_format($s,4, '.', '');
//			}
//		  $c=$c-1; 
//		  printf($arr[$c]);
//		}
//		//函数调用
//		echo "总投资(元)=".$T."<br>";
//		echo "总投资(元)=".$R."<br>";
//		echo "总投资(元)=".$C."<br>-----------------------<br>";
//		echo "总复值收益(元)=";
//		ZZSY($T,$R,$C);
//		?>
	}
	/**
	 * 小数点后两位
	 * @param multiple
	 * @return
	 */
	public static String multiple(double multiple) {
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		return dcmFmt.format(multiple);
	}
	/**
	 * 计算投资期限（生效日期到当前时间）
	 * @param incomeDay
	 * @throws ParseException 
	 */
	public static long chaTime(String incomeDay) throws ParseException {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");   
		Date date= myFormatter.parse(incomeDay);
		Date mydate= new Date();
		long  day=(mydate.getTime()-date.getTime())/(24*60*60*1000);
		if(day<0){
			day = 0;
		}
		return day;
	}
	
	
	/**
	 * 活期购买时间到当前时间天数
	 * @param buyTime
	 * @return
	 * @throws Exception
	 */
	public static long gzhaiTime(String buyTime) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");   
		Date date= myFormatter.parse(buyTime);
		Date mydate= new Date();
		long  day=(mydate.getTime()-date.getTime())/(24*60*60*1000)+1;
		if(day<0){
			day = 0;
		}
		return day;
	}
	/**
	 * 定期利息
	 * @param qx
	 * @param bj
	 * @param lv
	 * @param3个月，ID：0；
		6个月，ID：1；
		1年，ID：2；
		2年，ID：3；
		3年，ID：4；
		5年，ID：5
	 * @return
	 */
	public static double getQxLV(double bj, double lv,String qx) {
		double income = 0.0;
		if(qx.equals("3个月")){
			income = bj*lv/4;
		}
		if(qx.equals("6个月")){
			income = bj*lv/2;
		}
		if(qx.equals("1年")){
			income = bj*lv;
		}
		if(qx.equals("2年")){
			income = bj*lv*2;
		}
		if(qx.equals("3年")){
			income = bj*lv*3;
		}
		if(qx.equals("5年")){
			income = bj*lv*5;
		}
		return income;
	}
	/**
	 * 通知存款利率计算（存入金额*年利率*间隔天数/360天）
	 * @param bj
	 * @param lv
	 * @param timeLimit
	 * @return
	 */
	public static double getTZCKLX(double bj, double lv, double timeLimit) {
		double incomeAmount = 0.0;
		if(bj!=0.0&&lv!=0.0&timeLimit!=0.0){
			incomeAmount = bj*lv*timeLimit/360;
		}
		return incomeAmount;
	}
	/**
	 * 活期利率计算(本金*利率（日）*期限（天数）)
	 * @param bj
	 * @param timeLimit
	 * @return
	 */
	public static double getHQLV(double bj, double lv,double timeLimit) {
		double incomeAmount = 0.0;
		if(bj!=0.0&&timeLimit!=0.0){
			incomeAmount = bj*lv*timeLimit/360;
		}
		return incomeAmount;
	}
	/**
	 * P2p利息(本金*年利率*期限/365)
	 * @param bj
	 * @param lv
	 * @param day
	 * @return
	 */
	public static double P2PLX(double bj, double lv, long day) {
		double incomeAmount = 0.0;
		if(bj!=0.0&&lv!=0.0&&day!=0){
		  incomeAmount = bj*lv*day/365;
		}
		return incomeAmount;
	}
	  /**
     * @param htmlStr
     * @return
     *  删除Html标签
     
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
    }*/
	
	
	/** 
     * 验证输入的邮箱格式是否符合 
     * @param email 
     * @return 是否合法 
     */ 
	public static boolean checkPhoneNumber(String phoneNumber){
	        final String reg = "^1\\d{10}$"; 
	        final Pattern pattern = Pattern.compile(reg);
	        final Matcher mat = pattern.matcher(phoneNumber); 
	        if (mat.find()){
	            return true;
	        } else{
	        	return false;
	        }
	 }
}
