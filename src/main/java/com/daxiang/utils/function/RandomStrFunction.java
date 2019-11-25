package com.daxiang.utils.function;

import com.daxiang.utils.Function;
import com.daxiang.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDateFunction implements Function {

	@Override
	public String execute(String[] args) {

		if (args.length == 0 || StringUtil.isEmpty(args[0])) {
			return String.format("%s", new Date().getTime());
		} else {
			return getCurrentDate(args[0]);
		}
	}

	private String getCurrentDate(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(new Date());
		return str;
	}
	
	@Override
	public String getReferenceKey() {
		return "randomDate";
	}

}
