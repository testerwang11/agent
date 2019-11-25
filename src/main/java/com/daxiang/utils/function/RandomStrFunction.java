package com.daxiang.utils.function;

import cn.hutool.core.util.RandomUtil;
import com.daxiang.utils.Function;
import com.daxiang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class RandomStrFunction implements Function {

    @Override
    public String execute(String[] args) {

        if (args.length == 0 || StringUtil.isEmpty(args[0])) {
            return String.format("%s", new Date().getTime());
        } else {
            return getRandomStr(args[0]);
        }
    }

    private String getRandomStr(String len) {
        Integer tempLen = 8;
        if (StringUtils.isBlank(len)) {
            tempLen = Integer.parseInt(len);
        }
        return RandomUtil.randomString(tempLen);
    }

    @Override
    public String getReferenceKey() {
        return "randomStr";
    }

}
