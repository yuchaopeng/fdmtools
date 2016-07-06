package com.yu.fdm.tools.rcp.dialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import com.yu.fdm.rule.model.AccountJournalRule;
import com.yu.fdm.tools.rcp.util.ImagesUtil;
import com.yu.fdm.tools.rcp.view.AccountJournalRuleSetView;

public class ViewCompareResultDialog extends ApplicationWindow {
	private static Logger logger = Logger.getLogger(ViewCompareResultDialog.class);
	
	public static final int DIALOG_WIDTH = 500;
	
	public static final int DIALOG_HEIGHT = 300;
	
	private static List<TableColumnInfo> tableColumnInfo = new ArrayList<TableColumnInfo>();
	
	static{
		tableColumnInfo.add(new TableColumnInfo("index", "NO.",40));
		tableColumnInfo.add(new TableColumnInfo("rowNum", "所在行数",60));
		tableColumnInfo.add(new TableColumnInfo("systemId", "系统编码",60));
		tableColumnInfo.add(new TableColumnInfo("accountingType", "产品编码",160));
		tableColumnInfo.add(new TableColumnInfo("accountingTypeName", "产品名称"));
		tableColumnInfo.add(new TableColumnInfo("isAssets", "是否资产类",80));
		tableColumnInfo.add(new TableColumnInfo("accountingService", "交易编码",140));
		tableColumnInfo.add(new TableColumnInfo("accountingServiceName", "编码名称"));
		tableColumnInfo.add(new TableColumnInfo("assetsAccountName", "资产方GL科目"));
		tableColumnInfo.add(new TableColumnInfo("assetsAccount", "资产方GL科目编号",115));
		tableColumnInfo.add(new TableColumnInfo("debtAccountName", "负债方GL科目"));
		tableColumnInfo.add(new TableColumnInfo("debtAccount", "负债方GL科目编号",115));
		tableColumnInfo.add(new TableColumnInfo("oppositeAccountName", "对方GL科目"));
		tableColumnInfo.add(new TableColumnInfo("oppositeAccount", "对方GL科目编号",100));
		tableColumnInfo.add(new TableColumnInfo("remark", "备注"));
	}
	
	
	public ViewCompareResultDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); 
	}

	@Override  
    protected void configureShell(Shell shell) {  
        shell.setText("查看比对结果");  
        shell.setActive();  
        shell.setImage(ImagesUtil.getImage("view_result_16.png"));
        Rectangle parent = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getBounds();
		shell.setLocation(parent.x - (parent.width - DIALOG_WIDTH) / 2,parent.y+(parent.height - DIALOG_HEIGHT) / 2 - 50);
    }  
	
	
  
    @Override  
    protected Control createContents(Composite parent) {
    	parent.setLayout(new GridLayout(1, false));
    	Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));
		group.setText("需要新增的规则");
		group.setLayout(new GridLayout(1,true));
		
		TableViewer tableViewer = new TableViewer(group, SWT.NUM_LOCK | SWT.FULL_SELECTION | SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
    	Table table = tableViewer.getTable();
    	GridData gd_table = new GridData(SWT.CENTER, SWT.TOP, true, true, 1, 1);
    	gd_table.minimumHeight = 300;
    	table.setLayoutData(gd_table);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        for(int i=0;i<tableColumnInfo.size();i++){
        	TableColumn tableColumn = new TableColumn(table, SWT.NONE);
        	tableColumn.setWidth(100);
        	TableColumnInfo columnInfo = tableColumnInfo.get(i);
        	tableColumn.setText(columnInfo.getTitleName());
        	if(columnInfo.getColumnWidth() != 0){
        		tableColumn.setWidth(columnInfo.getColumnWidth());
        	}
        }
        table.pack();
        tableViewer.setContentProvider(new ContentProvider());
        tableViewer.setLabelProvider(new TableLabelProvider());
        
        List<AccountJournalRuleResultFo> fos = getResultTableDatas(AccountJournalRuleSetView.getCompareResult());
        
        tableViewer.setInput(fos);
        parent.pack();
    	return parent;
    }  
    
    private List<AccountJournalRuleResultFo> getResultTableDatas(List<AccountJournalRule> rules){
    	List<AccountJournalRuleResultFo> fos = new ArrayList<ViewCompareResultDialog.AccountJournalRuleResultFo>();
    	AccountJournalRuleResultFo fo = null;
    	int i=1;
    	for(AccountJournalRule rule : rules){
    		fo = new AccountJournalRuleResultFo();
    		fo.setIndex(i);
    		fo.setRule(rule);
    		fos.add(fo);
    		i++;
    	}
    	return fos;
    }
    
    public class ContentProvider implements IStructuredContentProvider {
        public Object[] getElements(Object inputElement) {
            if(inputElement instanceof List){
                return ((List<AccountJournalRuleResultFo>)inputElement).toArray();
            }else{
                return new Object[0];
            }
        }
        public void dispose() {
        }
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }
	
	public class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof AccountJournalRuleResultFo){
            	AccountJournalRuleResultFo fo = (AccountJournalRuleResultFo) element;
            	
            	TableColumnInfo info = tableColumnInfo.get(columnIndex);
    			String fieldName = info.getFieldName();
            	
            	
            	Field fields[] = fo.getClass().getDeclaredFields();
            	
            	for(Field f : fields){
            		try {
            			f.setAccessible(true);
						Object obj = f.get(fo);
						if(obj instanceof AccountJournalRule){
							AccountJournalRule rule = (AccountJournalRule) obj;
							Field ruleFields[] = rule.getClass().getDeclaredFields();
							for(Field ruleField : ruleFields){
								ruleField.setAccessible(true);
								if(fieldName.equals(ruleField.getName())){
									return String.valueOf(ruleField.get(rule));
								}
							}
						}else{
							if(fieldName.equals(f.getName())){
								return String.valueOf(obj);
							}
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
            	}
            }
            return null;
        }
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
    }
	
	private static class TableColumnInfo{
		/**
		 * 对应实体里的字段名
		 */
		private String fieldName;
		
		/**
		 * table的 表头
		 */
		private String titleName;
		
		private int columnWidth;
		
		public TableColumnInfo(String fieldName, String titleName,int columnWidth) {
			super();
			this.fieldName = fieldName;
			this.titleName = titleName;
			this.columnWidth = columnWidth;
		}
		
		public TableColumnInfo(String fieldName, String titleName) {
			super();
			this.fieldName = fieldName;
			this.titleName = titleName;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getTitleName() {
			return titleName;
		}
		public void setTitleName(String titleName) {
			this.titleName = titleName;
		}
		public int getColumnWidth() {
			return columnWidth;
		}
		public void setColumnWidth(int columnWidth) {
			this.columnWidth = columnWidth;
		}
	}
	
	
	private class AccountJournalRuleResultFo{
		//序号
		private int index;
		
		private AccountJournalRule rule;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public AccountJournalRule getRule() {
			return rule;
		}

		public void setRule(AccountJournalRule rule) {
			this.rule = rule;
		}
		
	}
}
