package com.daxiang.utils.function;

import com.daxiang.utils.Function;

public class RandomPhoneFunction implements Function {

	public static int randomNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}


	private static String[] telFirst = "130,134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
	/**
	 * 返回随机手机号码
	 */
	public static Object randomPhone() {
		int index = randomNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(randomNum(1, 888) + 10000).substring(1);
		String thrid = String.valueOf(randomNum(1, 9100) + 10000).substring(1);
		String phone = first + second + thrid;
		return phone;
	}

	@Override
	public Object execute(String[] args) {
		return randomPhone();
	}

	
	@Override
	public String getReferenceKey() {
		return "randomPhone";
	}

}
