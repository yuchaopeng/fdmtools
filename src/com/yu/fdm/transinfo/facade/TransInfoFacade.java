package com.yu.fdm.transinfo.facade;

import java.io.File;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.transinfo.model.SearchModel;
import com.yu.fdm.transinfo.service.TransInfoService;

public class TransInfoFacade {
	public static boolean merge(File selectFile,File saveFile) throws MyException{
		return TransInfoService.merge(selectFile, saveFile);
	}
	
	public static StringBuffer search(File dir042,File dir044,SearchModel model) throws MyException{
		return TransInfoService.search(dir042, dir044, model);
	}
}
