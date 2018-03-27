/**  
 * @Title: XMLRightLoader.java
 * @Package com.edu.cas.client.common.org
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月14日 下午6:20:24
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.right;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.edu.cas.client.common.util.RightUtils;

/**
 * @ClassName: RightLoader
 * @Description: 权限JSON加载初始化类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月14日 下午6:20:24
 * 
 */
@Service(value = "RightLoader")
@Scope("singleton")
public class RightLoader implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = Logger.getLogger(RightLoader.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("begin to read right.");
		}

		URL jsonFile = RightLoader.class
				.getResource("/plugins/right/menus-rev.json");
		if(jsonFile == null || StringUtils.isEmpty(jsonFile.getFile())){
			jsonFile = RightLoader.class
					.getResource("/plugins/right/menus.json");
			if (jsonFile == null || StringUtils.isEmpty(jsonFile.getFile())) {
				log.error("menus.json is not found.");
				return;
			}
		}
		
		File file = null;
		try {
			file = new File(jsonFile.toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			log.error("error found ,see:", e1);
			return;
		}

		if (file.exists() && file.canRead()) {
			Scanner scanner = null;
			StringBuilder buffer = new StringBuilder();
			try {

				scanner = new Scanner(file, "utf-8");
				while (scanner.hasNextLine()) {
					buffer.append(scanner.nextLine());
				}
				JSONObject jsonObject = JSONObject
						.fromObject(buffer.toString());
				RightUtils.getInstance().loadRightModel(jsonObject);
				log.debug("read right,see:"
						+ RightUtils.getInstance().getRoot());
			} catch (FileNotFoundException e) {
				log.error("error found ,see:", e);
			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}

		}

		if (log.isDebugEnabled()) {
			log.debug("end to read right.");
		}
	}

}
