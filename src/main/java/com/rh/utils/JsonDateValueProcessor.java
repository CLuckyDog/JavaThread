/**
 * @(#)JsonDateValueProcessor.java 2010-8-16
 *
 * Copyright 2009 LINKAGE, Inc. All rights reserved.
 * LINKAGE PROPRIETARY/CONFIDtheENTIAL. Use is subject to license terms.
 */
package com.rh.utils;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 
 * 
 * @author chenwei
 * @date 2010-8-16
 * @version 1.0.0
 * @since 1.0
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
    private String format = "yyyy-MM-dd HH:mm:ss";
    
    public JsonDateValueProcessor() {      
     
    }      
     
    public JsonDateValueProcessor(String format) {
        this.format = format;      
    }      
     
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        String[] obj = {};
        if (value instanceof Date[]) {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date[] dates = (Date[]) value;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++) {      
                obj[i] = sf.format(dates[i]);      
            }      
        }      
        return obj;      
    }      
     
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof Date) {
            String str = new SimpleDateFormat(format).format((Date) value);
            return str;      
        }      
        return value == null ? null : value.toString();      
    }      
     
    public String getFormat() {
        return format;      
    }      
     
    public void setFormat(String format) {
        this.format = format;      
    }      
     
}
