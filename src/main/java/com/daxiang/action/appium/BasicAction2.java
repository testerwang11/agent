package com.daxiang.action.appium;

import cn.hutool.core.date.DateUtil;
import com.daxiang.utils.BamsUtil;
import com.daxiang.utils.StringUtil;
import com.daxiang.utils.function.RandomPhoneFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Created by jiangyitao.
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
     * @param day 正数加 负数减
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

    public static void main(String[] args) {
        BasicAction2 basicAction2 = new BasicAction2();
        //basicAction2.sleep("2");
        System.out.println(basicAction2.getDate("-10", "yyyy"));
        System.out.println(basicAction2.getDate("-10", ""));
        BasicAction2 basicAction3 = new WebAction(null);
        ((WebAction) basicAction3).getUrl("");

    }

}
