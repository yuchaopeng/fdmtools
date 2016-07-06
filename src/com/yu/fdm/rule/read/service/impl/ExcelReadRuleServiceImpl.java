package com.yu.fdm.rule.read.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yu.fdm.base.exception.MyException;
import com.yu.fdm.base.util.CommonUtil;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.rule.read.service.ReadRuleService;

public class ExcelReadRuleServiceImpl implements ReadRuleService{

	public List<AccountJournalRule> read(File file) throws MyException {
		InputStream is = null;
		HSSFWorkbook workBook = null;
		List<AccountJournalRule> rules = new ArrayList<AccountJournalRule>();
		try {
			is = new FileInputStream(file);
			workBook = new HSSFWorkbook(is);
			
			//直接读第4个sheet页
			HSSFSheet sheet = workBook.getSheetAt(3);
			HSSFRow row = null;
			HSSFCell systemIdCell = null;
			int size = sheet.getLastRowNum();
			for(int i = 0 ; i < size ; i++){
				row = sheet.getRow(i);
				//直接读到第18列
				systemIdCell = row.getCell(4);
				//如果这一单元格为空，或者这一单元格的值不为042、044，则不让其作为一条规则
				if(systemIdCell == null || !CommonUtil.getSystemList().contains(systemIdCell.getStringCellValue())){
					continue;
				}
				String variantId = "0101";
				String currencyUomId = "CNY";
				String systemId = CommonUtil.getCellValue(row.getCell(4));
				String accountingType = CommonUtil.getCellValue(row.getCell(5));
				String accountingTypeName = CommonUtil.getCellValue(row.getCell(6));
				String isAssets = CommonUtil.getCellValue(row.getCell(7));
				String accountingService = CommonUtil.getCellValue(row.getCell(8));
				String accountingServiceName = CommonUtil.getCellValue(row.getCell(9));
				String seqId = CommonUtil.getCellValue(row.getCell(10));
				if("".equals(seqId)){
					seqId = "1";
				}
				String assetsAccountName = CommonUtil.getCellValue(row.getCell(11));
				String assetsAccount = CommonUtil.getCellValue(row.getCell(12));
				String debtAccountName = CommonUtil.getCellValue(row.getCell(13));
				String debtAccount = CommonUtil.getCellValue(row.getCell(14));
				String oppositeAccountName = CommonUtil.getCellValue(row.getCell(15));
				String oppositeAccount = CommonUtil.getCellValue(row.getCell(16));
				String remark = CommonUtil.getCellValue(row.getCell(17));
				AccountJournalRule rule = new AccountJournalRule(i+1,variantId,systemId, accountingType, 
						accountingTypeName, isAssets, accountingService, 
						accountingServiceName, seqId, currencyUomId, 
						assetsAccountName, assetsAccount, debtAccountName, 
						debtAccount, oppositeAccountName, oppositeAccount,remark);
				rules.add(rule);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new MyException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException(e);
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rules;
	}
}
