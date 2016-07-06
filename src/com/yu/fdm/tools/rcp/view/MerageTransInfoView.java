package com.yu.fdm.tools.rcp.view;


import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.yu.fdm.transinfo.facade.TransInfoFacade;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class MerageTransInfoView extends ViewPart {
	public static final String ID = "com.yu.fdm.tools.rcp.view.MerageTransInfoView";
	
	private static Logger logger = Logger.getLogger(MerageTransInfoView.class);
	
	private Text text_042;
	private Text text_save;


	/**
	 * The constructor.
	 */
	public MerageTransInfoView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		
		Label label_042 = new Label(parent, SWT.NONE);
		label_042.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_042.setText("042\u4EA4\u6613\u4FE1\u606F\u76EE\u5F55\uFF1A");
		
		text_042 = new Text(parent, SWT.BORDER);
		text_042.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button button_042 = new Button(parent, SWT.NONE);
		final Shell shell = parent.getShell();
		button_042.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("合并交易信息，选择042目录开始");
				DirectoryDialog dialog = new DirectoryDialog(shell,SWT.NONE);
				String filePath042 = dialog.open();
				if(filePath042 != null){
					text_042.setText(filePath042);
					logger.info("合并交易信息，选择042目录结束，选中目录："+filePath042);
				}else{
					logger.info("合并交易信息，选择042目录结束，未选中目录");
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_042.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		button_042.setText("\u9009\u62E9042\u4EA4\u6613\u4FE1\u606F\u76EE\u5F55");
		
		Label label_save = new Label(parent, SWT.NONE);
		label_save.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_save.setText("\u5408\u5E76\u540E\u4FDD\u5B58\u76EE\u5F55\uFF1A");
		
		text_save = new Text(parent, SWT.BORDER);
		text_save.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button button_save = new Button(parent, SWT.CENTER);
		button_save.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("合并交易信息，选择保存目录开始");
				DirectoryDialog dialog = new DirectoryDialog(shell,SWT.NONE);
				String savePath = dialog.open();
				if(savePath != null){
					text_save.setText(savePath);
					logger.info("合并交易信息，选择保存目录结束，选中目录："+savePath);
				}else{
					logger.info("合并交易信息，选择保存目录结束，未选中目录");
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_save.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		button_save.setText("\u9009\u62E9\u5408\u5E76\u540E\u5B58\u50A8\u8DEF\u5F84");
		new Label(parent, SWT.NONE);
		
		Button button_merge = new Button(parent, SWT.CENTER);
		button_merge.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("合并交易信息开始");
				if(StringUtils.isEmpty(text_042.getText().trim())){
					MessageDialog.openWarning(shell, "提示", "请选择042目录");
					logger.info("合并交易信息未选中042目录");
					return;
				}
				if(StringUtils.isEmpty(text_save.getText().trim())){
					MessageDialog.openWarning(shell, "提示", "请选择合并后保存的目录");
					logger.info("合并交易信息未选中保存目录");
					return;
				}
				try {
					logger.info("合并交易信息，042目录："+text_042.getText().trim()+"，保存目录："+text_save.getText().trim());
					File file042 = new File(text_042.getText().trim());
					File fileSave = new File(text_save.getText().trim());
					boolean flag = TransInfoFacade.merge(file042, fileSave);
					if(flag){
						MessageDialog.openInformation(
								shell,
								"提示",
								"合并成功");
					}
					logger.info("合并交易信息成功");
				} catch (Exception e) {
					MessageDialog.openError(shell, "出错", "出错："+e.getMessage());
					e.printStackTrace();
					logger.error("合并交易信息失败",e);
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		button_merge.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		button_merge.setText("\u5F00\u59CB\u5408\u5E76");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
	}



	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}