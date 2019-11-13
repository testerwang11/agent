package com.daxiang.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BamsUtil {

    private static int time_out = 50000;
    private static Map<String, String> bamsCookie = new HashMap<>();

    public synchronized static Map<String, String> loginBams() {
        // 判断是否登录过
        if (bamsCookie.size() != 0) {
            // 登录过直接返回cookie
            return bamsCookie;
        }

        try {
            String url = "http://cas.sijibao.co/cas/login?service=http://running.sijibao.co/bams/a/cas";
            Connection.Response res = Jsoup.connect(url).followRedirects(true).timeout(time_out).method(Connection.Method.GET).execute();
            String execution = Jsoup.parse(res.body()).select("#fm1 > input[type=hidden]:nth-child(5)").val();

            HashMap data = new HashMap();
            data.put("username", "admin");
            data.put("password", "admin");
            data.put("execution", execution);
            data.put("_eventId", "submit");

            HashMap headers = new HashMap();
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("Referer", "http://cas.sijibao.co/cas/login?service=http://running.sijibao.co/bams/a/cas");
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
            headers.put("Upgrade-Insecure-Requests", "1");

            Connection.Response res2 = Jsoup.connect(url).headers(headers).data(data).timeout(time_out).followRedirects(true).method(Connection.Method.POST).execute();
            bamsCookie = res2.cookies();
            return res2.cookies();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 查询短信验证码
     *
     * @param phone
     * @return
     */
    public static String queryMsgCode(String phone) {
        Map<String, String> cookies = loginBams();
        HashMap data = new HashMap();
        data.put("toPhones", phone);
        String code = "";
        try {
            Connection.Response res = Jsoup.connect("http://running.sijibao.co/bams/a/pres/qsug/queryRecords/search").data(data).cookies(cookies).timeout(time_out).method(Connection.Method.POST).execute();
            String msg = Jsoup.parse(res.body()).select("#content").text();
            String sendTime = Jsoup.parse(res.body()).select("#contentTable > tbody > tr:nth-child(1) > td:nth-child(2) > div").text();
            log.info("验证码发送时间:" + sendTime);
            log.info(msg);
            if (msg.startsWith("验证码为")) {
                code = msg.split("验证码为")[1].split("，")[0];
            } else if (msg.startsWith("注册验证码为")) {
                code = msg.split("注册验证码为")[1].split("，")[0];
            } else {
                code = StringUtil.getNumFromStr(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


    /**
     * 手机号添加至黑名单
     *
     * @param phone
     * @return
     * @throws IOException
     */
    public static String addPhone2BlackList(String phone) throws IOException {
        Map<String, String> cookies = loginBams();
        String url = "http://running.sijibao.co/bams/a/pres/blacklist/smsMobileBlacklist/save";
        HashMap data = new HashMap();
        data.put("mobile", phone);
        data.put("remark", "UI自动化测试添加");
        Connection.Response res = Jsoup.connect(url).data(data).cookies(cookies).timeout(time_out).method(Connection.Method.POST).execute();
        String msg = Jsoup.parse(res.body()).select("#messageBox").text();
        log.info(msg);
        return msg;
    }

    public static void main(String[] args) throws IOException {
        addPhone2BlackList("17612169015");
    }
}
