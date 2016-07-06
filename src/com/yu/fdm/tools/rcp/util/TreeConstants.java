package com.yu.fdm.tools.rcp.util;

import java.util.HashMap;
import java.util.Map;

public class TreeConstants {
	public static final String TREE_SEARCH_TRANS_INFO = "TREE_SEARCH_TRANS_INFO";
	public static final String TREE_MERGE_TRANS_INFO = "TREE_MERGE_TRANS_INFO";
	public static final String TREE_ACCOUNT_JOURNAL_RULE_SET = "TREE_ACCOUNT_JOURNAL_RULE_SET";
	public static Map<String,String> treeData = new HashMap<String, String>();
	static{
		treeData.put(TREE_SEARCH_TRANS_INFO, "查询交易信息");
		treeData.put(TREE_MERGE_TRANS_INFO, "合并042交易信息");
		treeData.put(TREE_ACCOUNT_JOURNAL_RULE_SET, "核算规则比对");
	}
}
