package com.yu.fdm.tools.rcp.view;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.yu.fdm.base.jdbc.JdbcConfig;
import com.yu.fdm.rule.facade.RuleCompareFacade;
import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.tools.rcp.dialog.JDBCConfigDialog;
import com.yu.fdm.tools.rcp.dialog.ViewCompareResultDialog;

import freemarker.template.TemplateException;


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

public class AccountJournalRuleSetView extends ViewPart {
	
	private static Logger logger = Logger.getLogger(AccountJournalRuleSetView.class);

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.yu.fdm.tools.rcp.view.AccountJournalRuleSetView";
	private Text text_jdbc_url;
	private Text ruleXLSFilePath;
	
	private String selectFilePath = "";
	private Text text_result;
	
	private static List<AccountJournalRule> compareResult = new ArrayList<AccountJournalRule>();

	/**
	 * The constructor.
	 */
	public AccountJournalRuleSetView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		
		final Shell shell = parent.getShell();
		Label label_title = new Label(parent, SWT.NONE);
		label_title.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_title.setText("当前比对的数据库：");
		
		text_jdbc_url = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text_jdbc_url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		text_jdbc_url.setText(JdbcConfig.getUrl());
		
		Button button_jdbc_config = new Button(parent, SWT.NONE);
		button_jdbc_config.setText("配置比对数据库");
		button_jdbc_config.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("打开配置数据库对话框");
				JDBCConfigDialog dialog = new JDBCConfigDialog(shell);
				dialog.setBlockOnOpen(true);
				dialog.open();
				text_jdbc_url.setText(JdbcConfig.getUrl());
				logger.info("配置完毕，数据库路径："+JdbcConfig.getUrl());
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("过账引擎场景核算规则映射文件：");
		
		ruleXLSFilePath = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		ruleXLSFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite button_panel = new Composite(parent, SWT.NONE);
		button_panel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));
		button_panel.setLayout(new RowLayout());
		
		Button button_choose = new Button(button_panel, SWT.NONE);
		button_choose.setLayoutData(new RowData());
		button_choose.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("选择核算规则文件开始");
				FileDialog dialog = new FileDialog(shell,SWT.NONE);
				selectFilePath = dialog.open();
				if(StringUtils.isNotEmpty(selectFilePath)){
					ruleXLSFilePath.setText(selectFilePath);
					logger.info("选择核算规则文件结束，选中路径："+selectFilePath);
				}else{
					logger.info("选择核算规则文件结束，未选择文件");
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_choose.setText("\u9009\u62E9\u6587\u4EF6");
		
		Button button_compare = new Button(button_panel, SWT.NONE);
		button_compare.setLayoutData(new RowData());
		button_compare.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_compare.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("核算规则比对开始：");
				if(StringUtils.isEmpty(selectFilePath)){
					logger.info("提示未选择核算规则映射文件");
					MessageDialog.openInformation(
							shell,
							"提示",
							"请选择“过账引擎场景核算规则映射”文件");
					return;
				}
				String result = "";
				String errorMessage = "";
				try {
					logger.info("核算规则文件路径："+selectFilePath+"，数据库："+JdbcConfig.getUrl());
					compareResult = RuleCompareFacade.compareRule(new File(selectFilePath));
					result = RuleCompareFacade.buildCompareResult(compareResult);
					logger.info("核算规则比对结束，结果："+result);
				} catch (InstantiationException e) {
					errorMessage = "出错："+e.getMessage();					
					e.printStackTrace();
					logger.error("出错", e);
				} catch (IllegalAccessException e) {
					errorMessage = "出错："+e.getMessage();
					e.printStackTrace();
					logger.error("出错", e);
				} catch (ClassNotFoundException e) {
					errorMessage = "出错："+e.getMessage();
					e.printStackTrace();
					logger.error("出错", e);
				} catch (TemplateException e) {
					errorMessage = "出错："+e.getMessage();
					e.printStackTrace();
					logger.error("出错", e);
				} catch (IOException e) {
					errorMessage = "出错："+e.getMessage();
					e.printStackTrace();
					logger.error("出错", e);
				} catch (Exception e) {
					errorMessage = "出错："+e.getMessage();
					e.printStackTrace();
					logger.error("出错", e);
				}
				
				if(StringUtils.isNotEmpty(result)){
					text_result.setText(result);
				}
				if(StringUtils.isNotEmpty(errorMessage)){
					MessageDialog.openError(shell, "出错", errorMessage);
					logger.error("出错："+errorMessage);
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		button_compare.setText("\u8FDB\u884C\u6BD4\u5BF9");
		
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		Button button_view_result = new Button(parent, SWT.NONE);
		button_view_result.setText("查看比对结果记录");
		
		button_view_result.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				ViewCompareResultDialog v = new ViewCompareResultDialog(shell);
				v.setBlockOnOpen(false);
				v.open();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
		
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1));
		group.setText("种子数据");
		group.setLayout(new GridLayout(1,true));
		text_result = new Text(group, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData gd_text_result = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_text_result.minimumHeight = 380;
		text_result.setLayoutData(gd_text_result);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public static String getId() {
		return ID;
	}

	public static List<AccountJournalRule> getCompareResult() {
		return compareResult;
	}
}