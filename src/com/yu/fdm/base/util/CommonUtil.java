package com.yu.fdm.base.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.yu.fdm.rule.model.AccountJournalRule;

public class CommonUtil {
	
	public static DecimalFormat decimalFormat = new DecimalFormat("#");
	
	public static final String PAYMENT_METHOD_TYPE_ID = "FDM_ACCOUNT_SERVICE";
	
	public static final String GL_ACCOUNT_TYPE_ID = "FDM_GL_ACCOUNT";
	
	public static final String PRODUCT_TYPE_ID = "FDM_ACCOUNTING";
	
	public static final String PRODUCT_CATEGORY_TYPE_ID = "FDM_CATEGORY";
	
	public static final String FDM_ASSETS = "FDM_ASSETS";

	public static final String FDM_NOT_ASSETS = "FDM_NOT_ASSETS";
	
	/**
	 * 核算规则配置比对时，定义了一个系统列表。
	 * 如果读取的excel的行中，系统编号一列都为此列表中的值时，才读取这一行数据
	 */
	private static List<String> systemList = new ArrayList<String>();
	
	static{
		systemList.add("042");
		systemList.add("044");
	}
	
	public static List<String> getSystemList(){
		return systemList;
	}
	
	public static String getCellValue(HSSFCell cell){
		String result = "";
		if(cell != null){
			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
				result = decimalFormat.format(cell.getNumericCellValue());
			}else{
				result = cell.getStringCellValue();
			}
		}
		return result;
	}
	
	public static String getCompareMapKey(AccountJournalRule rule){
		String key = objToString(rule.getVariantId())+"|"+objToString(rule.getAccountingType())+"|"+objToString(rule.getAccountingService())
						+"|"+objToString(rule.getCurrencyUomId())+"|"+objToString(rule.getAssetsAccount())+"|"+objToString(rule.getDebtAccount())+"|"+objToString(rule.getOppositeAccount());
		return key;
	}
	
	public static String objToString(Object obj){
		return obj == null ? "" : obj.toString();
	}
	
	/**
	 * 根據系统中已存在最新的核算规则编码，获取新插入最新的编码（现有最新编码+1的方式）
	 * @param accountJournalRuleId
	 * @return
	 */
	public static String getNewRuleId(String accountJournalRuleId){
		String ruleId = accountJournalRuleId.substring(accountJournalRuleId.length()-3-1);
		int newId = Integer.parseInt(ruleId);
		newId++;
		String resultId = newId+"";
		if(newId < 100){
			resultId = "0"+newId;
		}
		return "04500"+resultId;
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static boolean isEmpty(String str){
		return str == null || "".equals(str);
	}
}
