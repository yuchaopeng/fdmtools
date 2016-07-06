package com.yu.fdm.tools.rcp.test;

import com.yu.fdm.tools.rcp.util.TreeDataManager;
import com.yu.fdm.tools.rcp.util.TreeNode;

public class Test {
	public static void main(String[] args) {
		TreeNode obj = TreeDataManager.getInstance().getTreeData();
		System.out.println(obj.getChildren().get(0));
	}
}

