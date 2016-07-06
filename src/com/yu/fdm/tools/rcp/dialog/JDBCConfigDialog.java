package com.yu.fdm.tools.rcp.dialog;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.yu.fdm.base.jdbc.JdbcConfig;
import com.yu.fdm.base.jdbc.JdbcUtil;
import com.yu.fdm.tools.rcp.util.ImagesUtil;

public class JDBCConfigDialog extends ApplicationWindow {
	private static Logger logger = Logger.getLogger(JDBCConfigDialog.class);
	
	public static final int JDBC_CONFIG_DIALOG_WIDTH = 200;
	
	public static final int JDBC_CONFIG_DIALOG_HEIGHT = 200;

	private Text driverClassName_text;
	private Text url_text;
	private Text username_text;
	private Text password_text;
	
	public JDBCConfigDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); 
	}

	@Override  
    protected void configureShell(Shell shell) {  
        super.configureShell(shell);  
        shell.setText("修改比对数据库");  
        shell.setActive();  
        shell.setImage(ImagesUtil.getImage("db_16.png"));
        Rectangle parent = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getBounds();
		shell.setLocation(parent.x + (parent.width - JDBC_CONFIG_DIALOG_WIDTH) / 2,parent.y+(parent.height - JDBC_CONFIG_DIALOG_HEIGHT) / 2);
    }  
	
	
  
    @Override  
    protected Control createContents(Composite parent) {  
    	GridLayout layout = new GridLayout(2, false);  
    	Composite comp = new Composite(parent, SWT.NONE);
    	comp.setLayout(layout);  
    	
        Label driverClassName = new Label(comp, SWT.RIGHT);
        driverClassName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        driverClassName.setAlignment(SWT.RIGHT);
        driverClassName.setText("驱动名：");  
        driverClassName.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, false, false, 1, 1));
        driverClassName_text = new Text(comp, SWT.BORDER | SWT.READ_ONLY);
        driverClassName_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        Label url = new Label(comp, SWT.NONE);  
        url.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        url.setAlignment(SWT.RIGHT);
        url.setText("连接URL：");  
        url.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, false, false, 1, 1));
        url_text = new Text(comp, SWT.BORDER);
        url_text.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, true, false, 1, 1));
        
        Label username = new Label(comp, SWT.NONE);  
        username.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        username.setAlignment(SWT.RIGHT);
        username.setText("用户名：");  
        username.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, false, false, 1, 1));
        username_text = new Text(comp, SWT.BORDER);
        username_text.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, true, false, 1, 1));
        
        Label password = new Label(comp, SWT.NONE);  
        password.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        password.setAlignment(SWT.RIGHT);
        password.setText("密码：");  
        password.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, false, false, 1, 1));
        password_text = new Text(comp, SWT.BORDER);
        password_text.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, true, false, 1, 1));
        
        driverClassName_text.setText(JdbcConfig.getDriverClassName());
        url_text.setText(JdbcConfig.getUrl());
        username_text.setText(JdbcConfig.getUsername());
        password_text.setText(JdbcConfig.getPassword());
        
        new Label(comp,SWT.NONE);
        
        Composite composite = new Composite(comp, SWT.NONE);
        composite.setLayout(new RowLayout(SWT.HORIZONTAL));
        
        Button saveButton = new Button(composite, SWT.NONE);
        saveButton.setLayoutData(new RowData(50, SWT.DEFAULT));
        saveButton.setText("保存");
        saveButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("修改核算规则比对数据库开始");
				StringBuffer errorMessage = new StringBuffer();
				if(StringUtils.isEmpty(driverClassName_text.getText().trim())){
					errorMessage.append("数据库驱动名称不能为空！");
				}else if(StringUtils.isEmpty(url_text.getText().trim())){
					errorMessage.append("数据库连接url不能为空！");
				}else if(StringUtils.isEmpty(username_text.getText().trim())){
					errorMessage.append("数据库用户名不能为空！");
				}else if(StringUtils.isEmpty(password_text.getText().trim())){
					errorMessage.append("数据库密码不能为空！");
				}
				if(!"".equals(errorMessage.toString())){
					MessageDialog.openError(getShell(), "提示", errorMessage.toString());
					logger.error("修改核算规则比对数据库出错："+errorMessage.toString());
				}else{
					try {
						logger.info("修改核算规则比对数据库，driverClassName：+"+driverClassName_text.getText()+"，url："+url_text.getText()+"，username："+username_text.getText()+"，password："+password_text.getText());
						JdbcConfig.setDriverClassName(driverClassName_text.getText());
						JdbcConfig.setUrl(url_text.getText());
						JdbcConfig.setUsername(username_text.getText());
						JdbcConfig.setPassword(password_text.getText());
						MessageDialog.openInformation(getShell(), "提示", "保存成功");
						logger.info("修改核算规则比对数据库成功");
					} catch (Exception e) {
						MessageDialog.openError(getShell(), "提示", "出错："+e.getMessage());
						e.printStackTrace();
						logger.error("修改核算规则比对数据库失败", e);
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
        
        Button closeButton = new Button(composite, SWT.NONE);
        closeButton.setLayoutData(new RowData(50, SWT.DEFAULT));
        closeButton.setText("关闭");
        closeButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				getShell().close();
				logger.info("关闭核算规则比对数据库对话框");
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				
			}
		});
        
        Button testButton = new Button(composite, SWT.NONE);
        testButton.setText("测试连接");
        testButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				logger.info("测试连接开始");
				boolean flag = false;
				try {
					flag = JdbcUtil.testConnection();
					if(flag){
						MessageDialog.openInformation(getShell(), "提示", "连接成功");
						logger.info("测试连接-连接成功");
					}else{
						MessageDialog.openError(getShell(), "出错", "连接异常");
						logger.error("测试连接-连接异常");
					}
				} catch (SQLException e) {
					MessageDialog.openError(getShell(), "出错", "连接错误："+e.getMessage());
					e.printStackTrace();
					logger.error("测试连接-连接失败",e);
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        parent.pack();  
        return parent;  
    }  
}
