package com.yu.fdm.rule.result;

import java.io.IOException;

import com.yu.fdm.base.exception.MyException;

import freemarker.template.TemplateException;



public interface CompareResult {
	
	String getResult(Object dataModel) throws TemplateException,IOException,MyException;
}
