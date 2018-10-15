package com.text.core.beetl;

import com.text.core.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	public void initOther() {

		groupTemplate.registerFunctionPackage("tool", new ToolUtil());

	}

}
