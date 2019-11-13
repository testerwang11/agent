set @findbyAndValue = '{"name":"findBy","type":"String","description":"查找方式","possibleValues":[{"value":"id","description":"MobileBy.id"},{"value":"AccessibilityId","description":"MobileBy.AccessibilityId"},{"value":"xpath","description":"MobileBy.xpath"},{"value":"AndroidUIAutomator","description":"MobileBy.AndroidUIAutomator"},{"value":"iOSClassChain","description":"MobileBy.iOSClassChain"},{"value":"iOSNsPredicateString","description":"MobileBy.iOSNsPredicateString"},{"value":"image","description":"MobileBy.image"},{"value":"className","description":"MobileBy.className"},{"value":"name","description":"MobileBy.name"},{"value":"cssSelector","description":"MobileBy.cssSelector"},{"value":"linkText","description":"MobileBy.linkText"},{"value":"partialLinkText","description":"MobileBy.partialLinkText"},{"value":"tagName","description":"MobileBy.tagName"}]},{"name":"value","type":"String","description":"查找值"}';
-- 1.executeJavaCode
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  1,
  '执行java代码[executeJavaCode]',
  '$.executeJavaCode',
  'void',
  '[{"name":"code","type":"String","description":"java代码"}]'
);

-- 2.uninstallApp
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  2,
  '卸载App[uninstallApp]',
  '$.uninstallApp',
  'void',
  '[{"name":"packageName","type":"String","description":"android: packageName, iOS: bundleId"}]'
);

-- 3.installApk
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  3,
  '安装App[installApp]',
  '$.installApp',
  'void',
  '[{"name":"appDownloadUrl","type":"String","description":"app下载地址"}]'
);

-- 4.clearApkData
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `platform`,
  `params`
)
VALUES
(
  4,
  '清除APK数据[clearApkData]',
  '$.clearApkData',
  'void',
  1,
  '[{"name":"packageName","type":"String","description":"包名"}]'
);

-- 5.restartApk
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `platform`,
  `params`
)
VALUES
(
  5,
  '启动/重启Apk[restartApk]',
  '$.restartApk',
  'void',
  1,
  '[{"name":"packageName","type":"String","description":"包名"},{"name":"launchActivity","type":"String","description":"启动Activity名"}]'
);

-- 6.restartIosApp
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `platform`,
  `params`
)
VALUES
(
  6,
  '启动/重启app[restartIosApp]',
  '$.restartIosApp',
  'void',
  2,
  '[{"name":"bundleId","type":"String","description": "app bundleId"}]'
);

-- 7.click
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  7,
  '点击[click]',
  '$.click',
  'WebElement',
  REPLACE('[#]','#',@findbyAndValue)
);

-- 8.findElement
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  8,
  '查找元素[findElement]',
  '$.findElement',
  'WebElement',
  REPLACE('[#]','#',@findbyAndValue)
);

-- 9.findElements
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  9,
  '查找元素[findElements]',
  '$.findElements',
  'List<WebElement>',
  REPLACE('[#]','#',@findbyAndValue)
);

-- 10.sendKeys
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  10,
  '输入[sendKeys]',
  '$.sendKeys',
  'WebElement',
  REPLACE('[#,{"name":"content","type":"String","description":"输入内容"}]','#',@findbyAndValue)
);

-- 11.setImplicitlyWaitTime
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  11,
  '设置隐式等待时间[setImplicitlyWaitTime]',
  '$.setImplicitlyWaitTime',
  'void',
  '[{"name":"implicitlyWaitTimeInSeconds","type":"String","description":"隐式等待时间(秒)"}]'
);

-- 12.waitForElementVisible
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  12,
  '等待元素可见[waitForElementVisible]',
  '$.waitForElementVisible',
  'WebElement',
  REPLACE('[#,{"name":"maxWaitTimeInSeconds","type":"String","description":"最大等待时间(秒)"}]','#',@findbyAndValue)
);

-- 13.switchContext
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  13,
  '切换context[switchContext]',
  '$.switchContext',
  'void',
  '[{"name":"context","type":"String","description":"context","possibleValues":[{"value":"NATIVE_APP","description":"原生"}, {"value":"WEB_VIEW","description":"原生"}]}]'
);

-- 14.sleep
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  14,
  '休眠[sleep]',
  '$.sleep',
  'void',
  '[{"name":"sleepTimeInSeconds","type":"String","description": "休眠时长(秒)，支持小数，如: 1.5"}]'
);

-- 15.swipeInScreen
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  15,
  '滑动屏幕[swipeInScreen]',
  '$.swipeInScreen',
  'void',
  '[{"name":"startPoint","type":"String","description":"起点，如: {x:0.5,y:0.5} => 屏幕中心点"},{"name":"endPoint","type":"String","description":"终点，如: {x:0.5,y:0.5} => 屏幕中心点"}]'
);

-- 16.swipeInScreenAndFindElement
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  16,
  '滑动屏幕查找元素[swipeInScreenAndFindElement]',
  '$.swipeInScreenAndFindElement',
  'WebElement',
  REPLACE('[#,{"name":"startPoint","type":"String","description":"起点，如: {x:0.5,y:0.5} => 屏幕中心点"},{"name":"endPoint","type":"String","description":"终点，如: {x:0.5,y:0.5} => 屏幕中心点"},{"name":"maxSwipeCount","type":"String","description":"最大滑动次数"}]','#',@findbyAndValue)
);

-- 17.swipeInContainerElement
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  17,
  '容器元素内滑动[swipeInContainerElement]',
  '$.swipeInContainerElement',
  'void',
  '[{"name":"containerElement","type":"WebElement","description":"容器元素"},{"name":"startPoint","type":"String","description":"起点，如: {x:0.5,y:0.5} => 容器中心点"},{"name":"endPoint","type":"String","description":"终点，如: {x:0.5,y:0.5} => 容器中心点"}]'
);

-- 18.swipeInContainerElementAndFindElement
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  18,
  '容器元素内滑动查找元素[swipeInContainerElementAndFindElement]',
  '$.swipeInContainerElementAndFindElement',
  'WebElement',
  REPLACE('[{"name":"containerElement","type":"WebElement","description":"容器元素"},#,{"name":"startPoint","type":"String","description":"起点，如: {x:0.5,y:0.5} => 容器中心点"},{"name":"endPoint","type":"String","description":"终点，如: {x:0.5,y:0.5} => 容器中心点"},{"name":"maxSwipeCount","type":"String","description":"最大滑动次数"}]','#',@findbyAndValue)
);

-- 19.switchWindow
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  19,
  '[web]切换窗口[switchWindow]',
  '$.switchWindow',
  'void',
  '[{"name":"window","type":"String","description":"窗口"}]'
);

-- 20.waitForElementPresence
INSERT INTO `action` (
    `id`,
    `name`,
    `description`,
    `invoke`,
    `return_value`,
    `params`
    )
VALUES
       (
           20,
           '等待元素呈现[waitForElementPresence]',
           '等待元素在Page DOM里出现，移动端可用于检测toast',
           '$.waitForElementPresence',
           'WebElement',
           REPLACE('[#,{"name":"maxWaitTimeInSeconds","type":"String","description":"最大等待时间(秒)"}]','#',@findbyAndValue)
           );

-- 100.查找短信验证码
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  100,
  '查找短信验证码',
  '$.queryMsgCode',
  'String',
  '[{"name": "phone", "type":"String","description":"接收验证码手机号"}]'
  );

-- 101.手机号添加黑名单
INSERT INTO `action` (
  `id`,
  `name`,
  `invoke`,
  `return_value`,
  `params`
)
VALUES
(
  101,
  '手机号添加黑名单',
  '$.addPhone2Black',
  'void',
  '[{"name": "phone", "type":"String", "description":"手机号"}]'
  );

-- 102.ElementIsExist
INSERT INTO `action` (
	`id`,
	`name`,
	`invoke`,
	`return_value`,
	`params`
)
VALUES
(
	102,
	'判断元素是否存在',
	'$.elementIsExist',
	'WebElement',
  REPLACE('[#,{"name":"findBy","type":"WebElement","description":"查找方式"}]','#',@findbyAndValue)
);

-- 103.ToastIsExist
INSERT INTO `action` (
	`id`,
	`name`,
	`invoke`,
	`return_value`,
	`params`
)
VALUES
(
	103,
	'判断toast是否存在',
	'$.toastIsExist',
	'WebElement',
	'[{"name":"value","type":"String","description":"查找文本值"}]'
);

-- 104.SendKeyCode2Phone
INSERT INTO `action` (
	`id`,
	`name`,
	`invoke`,
  `platform`,
	`return_value`,
	`params`
)
VALUES
(
	104,
	'发送keycode到android手机执行',
	'$.pressKeyCode',
  1,
	'void',
	'[{"name": "androidKeyCode", "description": "AndroidKeyCode https://www.jianshu.com/p/f7ec856ff56f"}]'
);
-- 105.longClick
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `return_value`,
    `params`
    )
VALUES
       (
           105,
           '长按',
           '$.longClick',
           'void',
           REPLACE('[#,{"name":"findBy","type":"WebElement","description":"查找方式"}]','#',@findbyAndValue)
           );

-- 106.通过相对坐标点击
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `return_value`,
    `params`
    )
VALUES
       (
           106,
           '坐标点击',
           '$.clickByCoordinate',
           'void',
           '[{"name":"point","type":"String","description":"点击位置，如: {x:0.5,y:0.5} => 屏幕中心点"}]'
           );
-- 107.判断相等
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `platform`,
    `return_value`,
    `params`
    )
VALUES
       (
           107,
           '[Mobile]判断相等',
           '$.equals',
           3,
           'String',
           '[{"name": "expect", "type": "String","description": "期望值"},{"name": "acture", "type": "String", "description": "实际值"}]'
           );

-- 108.获取元素指定属性值
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `return_value`,
    `params`
    )
VALUES
       (
           108,
           '[Mobile]获取元素属性值',
           '$.getElementAttribute',
           'String',
           REPLACE('[#,{"name":"attribute","type":"String","description":"属性值"}]','#',@findbyAndValue)
           );


-- 200.getUrl
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 200,
					 '打开url',
					 '$.getUrl',
					 3,
					 'void',
					 '[{"name": "url", "description": "输入URL地址"}]'
					 );

-- 201.click
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 201,
					 '点击',
					 '$.click',
					 3,
					 'void',
					 '[{"name": "findBy", "type": "String", "description": "查找方式", "possibleValues": [{"value": "id", "description": "id"}, {"value": "xpath", "description": "xpath"},{"value": "className", "description": "className"}, {"value": "name", "description": "name"}, {"value": "cssSelector", "description": "cssSelector"}, {"value": "linkText", "description": "linkText"}, {"value": "partialLinkText", "description": "partialLinkText"}, {"value": "tagName", "description": "tagName"}]}, {"name": "value", "type": "String", "description": "查找值"}]'
					 );
-- 202.input
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 202,
					 '[Web]输入',
					 '$.sendKeys',
					 3,
					 'WebElement',
					 '[{"name": "findBy", "type": "String", "description": "查找方式", "possibleValues": [{"value": "id", "description": "id"}, {"value": "xpath", "description": "xpath"},{"value": "className", "description": "className"}, {"value": "name", "description": "name"}, {"value": "cssSelector", "description": "cssSelector"}, {"value": "linkText", "description": "linkText"}, {"value": "partialLinkText", "description": "partialLinkText"}, {"value": "tagName", "description": "tagName"}]}, {"name": "value", "type": "String", "description": "查找值"},{"name":"content","type":"String","description":"输入内容"}]'
					 );




-- 203.selectIframe
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 203,
					 '选择iframe',
					 '$.selectFrame',
					 3,
					 'void',
					 '[{"name": "findBy", "type": "String", "description": "查找方式", "possibleValues": [{"value": "id", "description": "id"}, {"value": "xpath", "description": "xpath"},{"value": "className", "description": "className"}, {"value": "name", "description": "name"}, {"value": "cssSelector", "description": "cssSelector"}, {"value": "linkText", "description": "linkText"}, {"value": "partialLinkText", "description": "partialLinkText"}, {"value": "tagName", "description": "tagName"}]}, {"name": "value", "type": "String", "description": "查找值"}]'
					 );
-- 204.back2Default
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`
		)
VALUES
			 (
					 204,
					 '返回默认窗口',
					 '$.back2DefaultContent',
					 3,
					 'void'
					 );
-- 205.runJS
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 205,
					 '执行js',
					 '$.runCommond',
					 3,
					 'String',
					 '[{"name": "js", "type": "String", "description": "输入js脚本"}]'
					 );

-- 206.runJS
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 206,
					 '获取元素属性值',
					 '$.getElementValue',
					 3,
					 'String',
					 '[{"name": "findBy", "type": "String", "description": "查找方式", "possibleValues": [{"value": "id", "description": "id"}, {"value": "xpath", "description": "xpath"},{"value": "className", "description": "className"}, {"value": "name", "description": "name"}, {"value": "cssSelector", "description": "cssSelector"}, {"value": "linkText", "description": "linkText"}, {"value": "partialLinkText", "description": "partialLinkText"}, {"value": "tagName", "description": "tagName"}]}, {"name": "value", "type": "String", "description": "查找值"},{"name": "attribute", "type": "String", "description": "获取属性的名称"}]'
					 );

-- 207.断言相等
INSERT INTO `action` (
		`id`,
		`name`,
		`invoke`,
		`platform`,
		`return_value`,
		`params`
		)
VALUES
			 (
					 207,
					 '断言',
					 '$.equals',
					 3,
					 'String',
					 '[{"name": "expect", "type": "String","description": "期望值"},{"name": "acture", "type": "String", "description": "实际值"}]'
					 );

-- 208.切换窗口
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `platform`,
    `return_value`,
    `params`
    )
VALUES
       (
           208,
           '切换窗口',
           '$.switchToWindow',
           3,
           'void',
           '[{"name": "title", "type": "String", "description": "根据页面title切换"},{"name": "index", "type": "String", "description": "根据页面顺序切换从1开始"}]'
           );
-- 209.
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `platform`,
    `return_value`,
    `params`
    )
VALUES
       (
           209,
           '新建tap页',
           '$.createTap',
           3,
           'void',
           '[{"name": "windowTitle", "type": "String","description": "页面title"}]'
           );
-- 210.close关闭tap页
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `platform`,
		`return_value`
		)
VALUES
       (
           210,
           '[Web]关闭标签页',
           '$.closeTap',
            '3',
           'void'
           );
-- 211.runJS
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `platform`,
    `return_value`,
    `params`
    )
VALUES
       (
           211,
           '获取元素数量',
           '$.getElementSize',
           3,
           'Integer',
           '[{"name": "findBy", "type": "String", "description": "查找方式", "possibleValues": [{"value": "id", "description": "id"}, {"value": "xpath", "description": "xpath"},{"value": "className", "description": "className"}, {"value": "name", "description": "name"}, {"value": "cssSelector", "description": "cssSelector"}, {"value": "linkText", "description": "linkText"}, {"value": "partialLinkText", "description": "partialLinkText"}, {"value": "tagName", "description": "tagName"}]}, {"name": "value", "type": "String", "description": "查找值"}]'
           );
-- 212.等待元素消失
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `platform`,
    `return_value`,
    `params`
    )
VALUES
       (
           212,
           '[Web]等待元素消失',
           '$.waitForElementNotPresence',
            3,
           'void',
           REPLACE('[#,{"name":"maxWaitTimeInSeconds","type":"String","description":"最大等待时间(秒)"}]','#',@findbyAndValue)
           );
-- 213.日期加减
INSERT INTO `action` (
    `id`,
    `name`,
    `invoke`,
    `return_value`,
    `params`
    )
VALUES
       (
           213,
           '获取时间',
           '$.getDate',
           'String',
           '[{"name": "day", "type": "String","description": "当前日期为0，正数当前加多少天，负数反之"},{"name": "format", "type": "String", "description": "时间格式：yyyy-MM-dd HH:mm:ss,不传则返回时间戳"}]'
           );