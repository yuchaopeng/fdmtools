package com.yu.fdm.tools.rcp;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.yu.fdm.tools.rcp.view.AccountJournalRuleSetView;
import com.yu.fdm.tools.rcp.view.MerageTransInfoView;
import com.yu.fdm.tools.rcp.view.SearchTransInfoView;
import com.yu.fdm.tools.rcp.view.TreeMenuView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.addView(TreeMenuView.ID, IPageLayout.LEFT, 0.25f, editorArea);
		layout.setEditorAreaVisible(false);
		
//		IFolderLayout folder =layout.createFolder("messages", IPageLayout.RIGHT, 0.75f,AccountJournalRuleSetView.ID);
//        folder.addPlaceholder(AccountJournalRuleSetView.ID+ ":*");
//        
//        folder =layout.createFolder("messages", IPageLayout.RIGHT, 0.75f,MerageTransInfoView.ID);
//        folder.addPlaceholder(MerageTransInfoView.ID+ ":*");
//        
//        folder =layout.createFolder("messages", IPageLayout.RIGHT, 0.75f,SearchTransInfoView.ID);
//        folder.addPlaceholder(SearchTransInfoView.ID+ ":*");
        
		IFolderLayout folder = layout.createFolder("fdm_info_view", IPageLayout.RIGHT,0.75f, editorArea);
		
        folder.addPlaceholder(AccountJournalRuleSetView.ID+ ":*");
        folder.addPlaceholder(MerageTransInfoView.ID+ ":*");
        folder.addPlaceholder(SearchTransInfoView.ID+ ":*");
	}
}
