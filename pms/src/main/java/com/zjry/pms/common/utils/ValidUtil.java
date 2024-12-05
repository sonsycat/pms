package com.zjry.pms.common.utils;


import com.zjry.common.utils.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 校验工具类
 * @author Li Yongqiang
 * @version 2018-05-11
 */
public class ValidUtil {

    private ValidUtil(){}

    /**
     * 判断对象是否为空
     * @param obj
     * @return boolean
     */
    public static <T> boolean isEmpty(T obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return "undefined".equals(obj) || "null".equals(obj) || "NULL".equals(obj) || StringUtils.isBlank((String)obj);
        } else if (obj instanceof CharSequence) {
            return StringUtils.isBlank((String)obj);
        } else if (obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else {
            Object[] object;
            if (!(obj instanceof Object[])) {
                object = null;
                String newInput = "";

                Method method;
                try {
                    method = obj.getClass().getMethod("size");
                    newInput = String.valueOf(method.invoke(obj));
                    return Integer.parseInt(newInput) == 0;
                } catch (Exception var10) {
                    try {
                        method = obj.getClass().getMethod("getItemCount");
                        newInput = String.valueOf(method.invoke(obj));
                        return Integer.parseInt(newInput) == 0;
                    } catch (Exception var9) {
                        try {
                            return Array.getLength(obj) == 0;
                        } catch (Exception var8) {
                            try {
                                method = Iterator.class.getMethod("hasNext");
                                newInput = String.valueOf(method.invoke(obj));
                                return !Boolean.valueOf(newInput);
                            } catch (Exception var7) {
                                return false;
                            }
                        }
                    }
                }
            } else {
                object = (Object[])((Object[])obj);
                if (object.length == 0) {
                    return true;
                } else {
                    boolean empty = true;

                    for(int i = 0; i < object.length; ++i) {
                        if (!isEmpty(object[i])) {
                            empty = false;
                            break;
                        }
                    }
                    return empty;
                }
            }
        }
    }

    public static <T> boolean isNotEmpty(T value) {
        return !isEmpty(value);
    }

    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }
    
    /**
     * 判断当天是否是该打卡规则
     * @param date 当天日期
     * @param str 打卡日期  1,2,3,4,5 为星期一到五
     * @return 结果
     */
    public static Boolean decideDatePunch(Date date,String str) {
    	Calendar cl = Calendar.getInstance();
    	cl.setTime(date);
    	int weekDay = cl.get(Calendar.DAY_OF_WEEK)-1;
	    if(weekDay == 0){  
	        weekDay = 7;  
	    } 
	    boolean status = str.contains(weekDay+"");
		return status;
    }
}
