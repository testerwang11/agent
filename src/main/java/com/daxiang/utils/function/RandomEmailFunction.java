package com.daxiang.utils.function;


import com.daxiang.utils.Function;

public class RandomEmailFunction implements Function {

	public static int randomNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}
	private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
	public static String baseStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


	/**
	 * 返回Email
	 *
	 * @param lMin 最小长度
	 * @param lMax 最大长度
	 * @return
	 */
	public String randomEmail(int lMin, int lMax) {
		int length = randomNum(lMin, lMax);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = (int) (Math.random() * baseStr.length());
			sb.append(baseStr.charAt(number));
		}
		sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
		return sb.toString();
	}

	@Override
	public String execute(String[] args) {
		return randomEmail(10, 10);
	}

	
	@Override
	public String getReferenceKey() {
		return "randomEmail";
	}

}
