package com.yu.fdm.tools.rcp.util;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

import com.yu.fdm.tools.rcp.Activator;

public class ImagesUtil {
	private static ImageRegistry registry = new ImageRegistry();
	
	static{
		init();
	}

	private static void init() {
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url = bundle.getEntry("icons");
		try {
			url = FileLocator.toFileURL(url);
			File directory = new File(url.getPath());
			File[] files = directory.listFiles();
			for (File file : files) {
				String path = "icons" + File.separator + file.getName();
				registry.put(file.getName(), Activator.getImageDescriptor(path));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取图片的信息
	 * 
	 * @param key
	 * @return
	 */
	public static Image getImage(String key) {
		Image image = registry.get(key);
		if (image == null) {
			image = ImageDescriptor.getMissingImageDescriptor().createImage();
		}
		return image;
	}

	/**
	 * 获取的图片的资源的信息
	 * 
	 * @param key
	 * @return
	 */
	public static ImageDescriptor getDescriptor(String key) {
		ImageDescriptor descriptor = registry.getDescriptor(key);
		if (descriptor == null) {
			descriptor = ImageDescriptor.getMissingImageDescriptor();
		}
		return descriptor;
	}
}
