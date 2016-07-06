package com.yu.fdm.tools.rcp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.PlatformUI;

import com.yu.fdm.base.util.PropertiesUtil;
import com.yu.fdm.tools.rcp.util.ImagesUtil;

public class SystemTrayMaster implements SelectionListener, Listener {

	private final class RestoreWindowListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			restoreWindow();
		}
	}

	private Menu menu;

	private MenuItem[] menuItems = new MenuItem[0];

	private RestoreWindowListener restoreWindowListener;

	public SystemTrayMaster() {
		this.restoreWindowListener = new RestoreWindowListener();
	}

	// Closes the Application
	protected void closeApplication() {
		PlatformUI.getWorkbench().close();
	}

	// click the tray
	public void widgetSelected(SelectionEvent e) {
	}

	// double click the tray
	public void widgetDefaultSelected(SelectionEvent e) {
		restoreWindow();
	}

	// Gets system shell
	private Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	// Minimizes the Window
	public void minimizeWindow() {
		getShell().setMinimized(true);
		getShell().setVisible(false);
	}

	// Restores the window
	protected void restoreWindow() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		shell.open();
		shell.setMinimized(false);
		shell.forceActive();
		shell.forceFocus();
	}

	public void showMenu() {
		clearItems();
		MenuItem openItem;
		MenuItem closeItem;
		openItem = new MenuItem(this.menu, SWT.PUSH);
		closeItem = new MenuItem(this.menu, SWT.NONE);
		closeItem.setText("退出");
		closeItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				closeApplication();
			}
		});
		this.menuItems = new MenuItem[] { openItem, closeItem };

		openItem.setText("主界面");
		openItem.addSelectionListener(this.restoreWindowListener);
		this.menu.setVisible(true);
	}

	private void clearItems() {
		for (int i = 0; i < this.menuItems.length; i++) {
			MenuItem item = this.menuItems[i];
			item.removeSelectionListener(this.restoreWindowListener);
			this.menuItems[i].dispose();
		}
	}

	public void handleEvent(Event event) {
		showMenu();
	}

	public void createSystemTray() {
		Tray tray = Display.getDefault().getSystemTray();
		TrayItem item = new TrayItem(tray, SWT.NONE);
		String systemName = PropertiesUtil.get("system.name");
		item.setText(systemName);
		item.setToolTipText(systemName);
		Image image = ImagesUtil.getImage("product128.png");
		item.setImage(image);
		item.addSelectionListener(this);
		item.addListener(SWT.MenuDetect, this);

		menu = new Menu(getShell(), SWT.POP_UP);
	}

}
