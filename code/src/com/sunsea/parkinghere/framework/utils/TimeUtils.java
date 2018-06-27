package com.sunsea.parkinghere.framework.utils;

import java.text.SimpleDateFormat;
/**
 * 时间格式化工具类，非线程安全，多线程中勿用
 * @author ym
 *
 */
public class TimeUtils {

	/**MM-dd*/
	public static final SimpleDateFormat MM_dd = new SimpleDateFormat("MM-dd");
	/**yyyy-MM-dd*/
	public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	/**MM-dd HH:mm:ss*/
	public static final SimpleDateFormat MM_dd_HH_mm_ss = new SimpleDateFormat("MM-dd HH:mm:ss");
	/**yyyy-MM-dd HH:mm:ss*/
	public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**HH:mm:ss*/
	public static final SimpleDateFormat HH_mm_ss = new SimpleDateFormat("HH:mm:ss");
	/**yyyyMMdd*/
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	/**yyyyMMddHHmmss*/
	public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	/**EEEE 星期*/
	public static final SimpleDateFormat EEEE = new SimpleDateFormat("EEEE");
	/**yyyy-MM-dd HH:mm:ss SSS*/
	public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	/**yyyy年MM月dd日 HH:mm*/
	public static final SimpleDateFormat yyyy年MM月dd日HH_mm = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	/**yyyy年MM月dd日HH点mm分*/
	public static final SimpleDateFormat M月dd日HH点mm分 = new SimpleDateFormat("M月dd日 HH点mm分");
	
}
