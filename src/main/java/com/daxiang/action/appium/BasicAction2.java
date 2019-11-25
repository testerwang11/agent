package com.daxiang.action.appium;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.daxiang.utils.BamsUtil;
import com.daxiang.utils.StringUtil;
import com.daxiang.utils.function.RandomPhoneFunction;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Created by wangshisan
 */
@Slf4j
@Component
public class BasicAction2 {

    /**
     * 1.执行java代码
     * platform: Web
     *
     * @param javaCode
     */
    public void executeJavaCode(String javaCode) {
        Assert.hasText(javaCode, "javaCode不能为空");
    }

    /**
     * 14.休眠
     * platform: Android / iOS
     *
     * @param sleepTimeInSeconds
     * @throws InterruptedException
     */
    public void sleep(String sleepTimeInSeconds) throws InterruptedException {
        Assert.hasText(sleepTimeInSeconds, "休眠时长不能为空");
        long sleepTime = (long) (Float.parseFloat(sleepTimeInSeconds) * 1000);
        Thread.sleep(sleepTime);
    }

    /**
     * 随机手机号
     *
     * @return
     */
    public Object randomPhone() {
        return RandomPhoneFunction.randomPhone();
    }

    /**
     * 手机号添加至黑名单
     *
     * @param phone
     * @return
     * @throws IOException
     */
    public String addPhone2Black(Object phone) throws IOException {
        return BamsUtil.addPhone2BlackList((String) phone);
    }

    /**
     * 查询短信验证码
     *
     * @param phone
     * @return
     */
    public String queryMsgCode(Object phone) {
        return BamsUtil.queryMsgCode((String) phone);
    }

    /**
     * 断言
     *
     * @param expect
     * @param acture
     */
    public String equals(Object expect, Object acture) {
        try {
            assertEquals(expect, acture);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "断言成功";
    }

    /**
     * 获取指定时间
     *
     * @param day    正数加 负数减
     * @param format 日期格式
     * @return
     */
    public String getDate(String day, String format) {
        Assert.hasText(day, "加减天数不能为空");

        if (StringUtil.isEmpty(format)) {
            return DateUtil.offsetDay(DateUtil.date(), Integer.parseInt(day)).getTime() + "";
        } else {
            return DateUtil.format(DateUtil.offsetDay(DateUtil.date(), Integer.parseInt(day)), format);
        }
    }


    /**
     * 物流下单
     * @param stockCode
     * @param agentCode
     * @param deficit
     * @param unitPrice
     * @param driverPhone
     * @param step
     * @return
     */
    public String wucheOrder(String stockCode, String agentCode, String deficit, String unitPrice, String driverPhone, String step) throws IOException {
        //HashMap data = new HashMap();
        JSONObject data = new JSONObject();
        data.put("stockCode", stockCode);
        data.put("agentCode", agentCode);
        data.put("deficit", deficit);
        data.put("unitPrice", unitPrice);
        data.put("driverPhone", driverPhone);
        data.put("step", step);
        Connection.Response res = Jsoup.connect("http://192.168.0.187:5011/test/order4").header("Content-Type", "application/json").requestBody(data.toJSONString()).timeout(120000).method(Connection.Method.POST).ignoreContentType(true).execute();
        return JSONObject.parseObject(res.body()).getString("orderNo");
    }

    public static void main(String[] args) throws IOException {
        BasicAction2 basicAction2 = new BasicAction2();
        //basicAction2.sleep("2");
        //System.out.println(basicAction2.getDate("-10", "yyyy"));
        //System.out.println(basicAction2.getDate("-10", ""));
        //BasicAction2 basicAction3 = new WebAction(null);
        //((WebAction) basicAction3).getUrl("");
        String order = basicAction2.wucheOrder("5703cc881b9b431b8cd922bac52bcb85_330", "", "0", "11", "13203662031", "4");
        System.out.println(order);
    }

}
