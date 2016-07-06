package com.yu.fdm.tools.rcp.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import com.yu.fdm.base.util.PropertiesUtil;
import com.yu.fdm.tools.rcp.util.ImagesUtil;
import com.yu.fdm.tools.rcp.util.TreeDataManager;
import com.yu.fdm.tools.rcp.util.TreeNode;


public class TreeMenuView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.yu.fdm.tools.rcp.view.TreeMenuView";

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;
	
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		viewer.expandAll();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "fdmtools.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	class TreeObject implements IAdaptable {
		private String id;
		private String name;
		private String viewId;
		private String image;
		private TreeParent parent;
		
		public TreeObject(String id,String name,String viewId,String image) {
			this.id = id;
			this.name = name;
			this.viewId = viewId;
			this.image = image;
		}
		public String getName() {
			return name;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public String getViewId() {
			return viewId;
		}
		public void setViewId(String viewId) {
			this.viewId = viewId;
		}
		public TreeParent getParent() {
			return parent;
		}
		public String toString() {
			return getName();
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Object getAdapter(Class key) {
			return null;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
	}
	
	class TreeParent extends TreeObject {
		private ArrayList<TreeObject> children;
		public TreeParent(String id,String name,String viewId,String image) {
			super(id,name,viewId,image);
			children = new ArrayList<TreeObject>();
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject [] getChildren() {
			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}

	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private TreeParent invisibleRoot;

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}

		private void initialize() {
			TreeNode node = TreeDataManager.getInstance().getTreeData();
			TreeObject tree = null;
			if(node != null){
				if(node.getChildren()== null || node.getChildren().size() == 0){
					tree = new TreeObject(node.getId(), node.getName(),node.getViewId(),node.getImage());
				}else{
					TreeParent parent = new TreeParent(node.getId(), node.getName(),node.getViewId(),node.getImage());
					tree = getTreeNodeChildren(node, parent);
				}
			}
			String systemName = PropertiesUtil.get("system.name");
			invisibleRoot = new TreeParent("ROOT",systemName,"","");
			invisibleRoot.addChild(tree);
		}
		
		private TreeParent getTreeNodeChildren(TreeNode parentNode,TreeParent parent){
			List<TreeNode> children = parentNode.getChildren();
			for(TreeNode node : children){
				if(node.getChildren() == null || node.getChildren().size() == 0){
					TreeObject obj = new TreeObject(node.getId(), node.getName(),node.getViewId(),node.getImage());
					parent.addChild(obj);
				}else{
					TreeParent treeParent = new TreeParent(node.getId(), node.getName(),node.getViewId(),node.getImage());
					treeParent = getTreeNodeChildren(node, treeParent);
					parent.addChild(treeParent);
				}
			}
			return parent;
		}
	}
	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			TreeObject tree = (TreeObject) obj;
			Image image = null;
			if(StringUtils.isNotEmpty(tree.getImage())){
				image = ImagesUtil.getImage(tree.getImage());
			}else{
				String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
				if (obj instanceof TreeParent){
					imageKey = ISharedImages.IMG_OBJ_FOLDER;
				}
				image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
			}
			return image;
		}
	}
	
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public TreeMenuView() {
	}


	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TreeMenuView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				TreeObject tree = (TreeObject) obj;
				if(StringUtils.isEmpty(tree.getViewId())){
					return;
				}
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(tree.getViewId());
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}