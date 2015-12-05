package com.baiduad.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
	
	public static final String LOG_PATH = "LOG_PATH";
	public final static String LOG_FOLDER_NAME = "logs";
	public static enum LogLevel {
		FATAL,ERROR,WARN,INFO,DEBUG,TRACE
	}

	public Log logger = null;

    public Logger(String className) {
        logger = LogFactory.getLog(className);
    }
    
    public Logger(Class<?> clazz) {
    	logger = LogFactory.getLog(clazz);
    }

    public void trace(Object message) {
        if (logger.isTraceEnabled()) {
            logger.trace(message);
        }
    }
    public void debug(Object message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void info(Object message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }
    
    public void warn(Object message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    public void error(Object message, Exception e) {
        if (logger.isErrorEnabled()) {
        	StringWriter stringWriter = new StringWriter();
        	PrintWriter write = new PrintWriter(stringWriter);
        	if(e!=null){
            	e.printStackTrace(write);
        	}
            logger.error(message + "/" + stringWriter.toString());
            write.close();
            write = null;
            stringWriter = null;
        }
    }

    public void fatal(Object message) {
        if (logger.isFatalEnabled()) {
            logger.fatal(message);
        }
    }
    
    public void exception(Object message,Exception e){
    	warn(message+": Exception->"+e.toString());
    }
    
}
