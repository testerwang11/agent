package com.daxiang.action.appium;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by jiangyitao.
 */
@Slf4j
public class WebBasicAction {

    public static final int EXECUTE_JAVA_CODE_ID = 1;

    private RemoteWebDriver driver;

    public WebBasicAction(RemoteWebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    private By getByWeb(String findBy, String value) {
        By by;
        switch (findBy) {
            case "id":
                by = By.id(value);
                break;
            case "xpath":
                if (value.contains("")) {
                    value = value.replaceAll("\"", "'");
                }
                by = By.xpath(value);
                break;
            case "className":
                by = By.className(value);
                break;
            case "name":
                by = By.name(value);
                break;
            case "cssSelector":
                by = By.cssSelector(value);
                break;
            case "linkText":
                by = By.linkText(value);
                break;
            case "partialLinkText":
                by = By.partialLinkText(value);
                break;
            case "tagName":
                by = By.tagName(value);
                break;
            default:
                throw new RuntimeException("暂不支持: " + findBy);
        }
        return by;
    }

    /*****************************PC WEB*****************************/

    /**
     * url 跳转
     *
     * @param url
     */
    public void getUrl(Object url) {
        driver.get((String) url);
    }

    /**
     * 点击
     *
     * @param findBy
     * @param value
     */
    public void click(Object findBy, Object value) {
        driver.findElement(getByWeb((String) findBy, (String) value)).click();
    }

    /**
     * 输入
     *
     * @param findBy
     * @param value
     */
    public WebElement sendKeys(String findBy, String value, String content) {
        WebElement element = driver.findElement(getByWeb(findBy, value));
        element.clear();
        element.sendKeys(content);
        return element;
    }

    /**
     * 切换iframe
     *
     * @param findBy
     * @param value
     */
    public void selectFrame(Object findBy, Object value) {
        driver.switchTo().frame(driver.findElement(getByWeb((String) findBy, (String) value)));
    }

    /**
     * 切换到最外层frame
     */
    public void back2DefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * 执行js
     *
     * @param script
     * @return
     */
    public Object runCommond(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    /**
     * 获取元素的值
     *
     * @param findBy
     * @param value
     * @param attribute
     * @return
     */
    public String getElementValue(Object findBy, Object value, Object attribute) {
        WebElement element = driver.findElement(getByWeb((String) findBy, (String) value));
        return element.getAttribute((String) attribute);
    }

    /**
     * 等待元素出现
     *
     * @param findBy
     * @param value
     * @param maxWaitTimeInSeconds
     * @return
     */
    public WebElement waitForElementPresence(String findBy, String value, String maxWaitTimeInSeconds) {
        Assert.hasText(findBy, "findBy不能为空");
        Assert.hasText(value, "value不能为空");
        Assert.hasText(maxWaitTimeInSeconds, "最大等待时间不能为空");
        return new WebDriverWait(driver, Long.parseLong(maxWaitTimeInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(getByWeb(findBy, value)));
    }

    /**
     * 根据title切换窗口
     *
     * @param windowTitle
     * @return
     */
    public boolean switchToWindow(String windowTitle) {
        boolean flag = false;
        try {
            String currentHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(currentHandle))
                    continue;
                else {
                    driver.switchTo().window(s);
                    if (driver.getTitle().contains(windowTitle)) {
                        flag = true;
                        break;
                    } else
                        continue;
                }
            }
        } catch (NoSuchWindowException e) {
            log.error("未找到窗口{}", windowTitle);
            flag = false;
        }
        return flag;
    }

    /**
     * 新建tap页，并打开指定url
     *
     * @param url
     */
    public void createTap(String url) {
        WebElement element = driver.findElement(By.tagName("body"));
        element.sendKeys(Keys.CONTROL + "t");
        driver.get(url);
    }

    /**
     * 关闭标签页
     */
    public void closeTap() {
        driver.close();
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

}
