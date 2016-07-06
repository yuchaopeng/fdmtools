package com.yu.fdm.tools.rcp;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.yu.fdm.base.util.PropertiesUtil;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	//系统托盘
	private  SystemTrayMaster trayMaster;
	
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(1000, 600));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        String systemName = PropertiesUtil.get("system.name");
        configurer.setTitle(systemName);
    }
    
    @Override
	public void postWindowOpen() {
		super.postWindowOpen();
		Shell shell = getWindowConfigurer().getWindow().getShell();  
		Rectangle screenSize = Display.getDefault().getClientArea();  
		Rectangle frameSize = shell.getBounds();  
		shell.setLocation((screenSize.width - frameSize.width) / 2,  
		(screenSize.height - frameSize.height) / 2);
		
		//创建系统托盘
		createSystemTray();
	}
    

	@Override
	public boolean preWindowShellClose() {
		trayMaster.minimizeWindow();  
        return false;  
	}

	private void createSystemTray() {  
    	trayMaster = new  SystemTrayMaster();  
    	trayMaster.createSystemTray();  
    }  
}
