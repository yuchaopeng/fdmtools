package com.yu.fdm.tools.rcp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

public class TreeDataManager {
	
	private String treeDataStr;
	
	private void init(){
		InputStream is = null;
		InputStreamReader fr = null;
		try {
			is = TreeDataManager.class.getResourceAsStream("/TreeMenuData.json");
			fr = new InputStreamReader(is,"UTF-8");  
			int ch = 0;    
			StringBuffer result = new StringBuffer();
			while((ch = fr.read())!=-1 ){    
			    result.append((char)ch);
			}
			treeDataStr = result.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private TreeDataManager(){
		
	}
	private static TreeDataManager treeData = null;
	public static TreeDataManager getInstance(){
		if(treeData == null){
			synchronized (TreeDataManager.class) {
				if(treeData == null){
					treeData = new TreeDataManager();
				}
			}
		}
		return treeData;
	}
	
	public TreeNode getTreeData(){
		if(StringUtils.isEmpty(treeDataStr)){
			init();
		}
		JSONObject json = JSONObject.fromObject(treeDataStr);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("children", TreeNode.class);
		return (TreeNode)JSONObject.toBean(json.getJSONObject("root"),TreeNode.class,classMap);
	}
	
}
