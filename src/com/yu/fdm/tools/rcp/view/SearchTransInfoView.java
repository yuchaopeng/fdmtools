package com.yu.fdm.tools.rcp.view;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.yu.fdm.transinfo.facade.TransInfoFacade;
import com.yu.fdm.transinfo.model.SearchModel;


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

public class SearchTransInfoView extends ViewPart {

	private static Logger logger = Logger.getLogger(SearchTransInfoView.class);
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.yu.fdm.tools.rcp.view.SearchTransInfoView";
	private Text text042;
	private Text text044;

	private Text text_result;
	private Text text_global_seq_no;
	
	/**
	 * The constructor.
	 */
	public SearchTransInfoView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(4, false);
				
		parent.setLayout(layout);
		Label label_042 = new Label(parent, SWT.NONE);
		label_042.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_042.setText("042\u4EA4\u6613\u4FE1\u606F\u76EE\u5F55\uFF1A");
		
		text042 = new Text(parent, SWT.BORDER);
		text042.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		final Shell shell = parent.getShell();
		Button button_042 = new Button(parent, SWT.NONE);
		button_042.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("选择042目录开始");
				DirectoryDialog dialog = new DirectoryDialog(shell,SWT.NONE);
				String filePath042 = dialog.open();
				if(filePath042 != null){
					text042.setText(filePath042);
					logger.info("选择042目录结束，已选择目录："+filePath042);
				}else{
					logger.info("选择042目录结束，未选择目录");
				}
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_042.setText("\u9009\u62E9042\u76EE\u5F55");
		
		Label label_044 = new Label(parent, SWT.NONE);
		label_044.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_044.setText("044\u4EA4\u6613\u4FE1\u606F\u76EE\u5F55\uFF1A");
		
		text044 = new Text(parent, SWT.BORDER);
		text044.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button button_044 = new Button(parent, SWT.NONE);
		button_044.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_044.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("选择044目录开始");
				DirectoryDialog dialog = new DirectoryDialog(shell,SWT.NONE);
				String filePath044 = dialog.open();
				if(filePath044 != null){
					text044.setText(filePath044);
					logger.info("选择044目录结束，已选择目录："+filePath044);
				}else{
					logger.info("选择044目录结束，未选择目录");
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_044.setText("\u9009\u62E9044\u76EE\u5F55");
		
		Label label_global_seq_no = new Label(parent, SWT.NONE);
		label_global_seq_no.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_global_seq_no.setText("\u5168\u5C40\u6D41\u6C34\u53F7\uFF1A");
		
		text_global_seq_no = new Text(parent, SWT.BORDER);
		text_global_seq_no.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, true, false, 3, 1));
		
		new Label(parent, SWT.NONE);
		Composite button_search_panel = new Composite(parent, SWT.NONE);
		button_search_panel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1));
		button_search_panel.setLayout(new RowLayout());
		
		Button button_search = new Button(button_search_panel, SWT.NONE);
		RowData rd_button_search = new RowData();
		rd_button_search.width = 80;
		button_search.setLayoutData(rd_button_search);
		button_search.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("查询交易信息开始");
				if(StringUtils.isEmpty(text042.getText().trim())){
					MessageDialog.openWarning(
							shell,
							"提示",
							"请选择042目录");
					logger.info("查询交易信息：未选择042目录");
					return;
				}
				
				if(StringUtils.isEmpty(text044.getText().trim())){
					MessageDialog.openWarning(
							shell,
							"提示",
							"请选择044目录");
					logger.info("查询交易信息：未选择044目录");
					return;
				}
				
				if(StringUtils.isEmpty(text_global_seq_no.getText().trim())){
					MessageDialog.openWarning(
							shell,
							"提示",
							"请输入全局流水号");
					logger.info("查询交易信息：未输入全局流水号");
					return;
				}
				
				SearchModel model = new SearchModel();
				model.setGlobalSeqNo(text_global_seq_no.getText().trim());
				try {
					logger.info("查询交易信息，042目录："+text042+"，044目录："+text044+"，全局流水号："+model.getGlobalSeqNo());
					File dir042 = new File(text042.getText().trim());
					File dir044 = new File(text044.getText().trim());
					StringBuffer result = TransInfoFacade.search(dir042, dir044, model);
					text_result.setText(result.toString());
					logger.info("查询交易信息结束，结果："+result.toString());
				} catch (Exception e) {
					MessageDialog.openError(
							shell,
							"出错",
							"出错："+e.getMessage());
					e.printStackTrace();
					logger.error("查询交易信息出错", e);
				}
				logger.info("查询交易信息结束");
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_search.setText("\u67E5\u8BE2");
		
		Button button_reset = new Button(button_search_panel, SWT.NONE);
		button_reset.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("重置开始");
				text042.setText("");
				text044.setText("");
				text_result.setText("");
				text_global_seq_no.setText("");
				logger.info("重置结束");
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		button_reset.setText("\u91CD\u7F6E");
		RowData rd_button_reset = new RowData();
		rd_button_reset.width = 80;
		button_reset.setLayoutData(rd_button_reset);
		new Label(parent, SWT.NONE);
		
		
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1));
		group.setText("\u7ED3\u679C");
		group.setLayout(new GridLayout(1,true));
		text_result = new Text(group, SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData gd_text_result = new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1);
		gd_text_result.minimumHeight = 300;
		text_result.setLayoutData(gd_text_result);
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}