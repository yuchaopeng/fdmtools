package com.yu.fdm.tools.rcp;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchWindow window;
	
	private IWorkbenchAction quitAction;
	
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	this.window = window;
    	quitAction = ActionFactory.QUIT.create(window);
    	quitAction.setText("退出");
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	
    	MenuManager showWindowsMenu = new MenuManager("系统",IWorkbenchActionConstants.M_WINDOW);
    	MenuManager showViewMenu = new MenuManager("显示视图",IWorkbenchActionConstants.SHOW_EXT);
    	showWindowsMenu.add(showViewMenu);
    	showWindowsMenu.add(quitAction);
    	IContributionItem showList = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
    	showViewMenu.add(showList);
    	menuBar.add(showWindowsMenu);
    }
}
