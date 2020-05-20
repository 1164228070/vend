package com.maizi.utils;
/**
 * 
  * @ClassName: ConstantUtil
  * @Company: 麦子科技
  * @Description: 常量类
  * @author 技术部-谢维乐
  * @date 2016-2-22 下午2:25:14
  *
 */
public class ConstantUtil {
    // "验证结果"参数名称
    public static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";
    
    // "瞬时消息"属性名称 
    public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = ".FLASH_MESSAGE";
    
    // 错误视图 
    public static final String ERROR_VIEW = "common/error";
    // 错误消息
    public static final Message ERROR_MESSAGE = Message.error("admin.message.error");
	 
	//系统繁忙提示
	public static final String SERVER_ERROR="系统繁忙,请稍后在!";
	
	//暂无数据提示
	public static final String NO_DATA_TIP="暂无数据";
	
	//默认密码
	public static final String DEFAULT_PASSWORD=MD5.GetMD5Code("123456");
	
	//默认密码明文
	public static final String DEFAULT_PASSWORD_MW="123456";
	
	public static final String ACTIVE_SUCCESS="激活成功";
	public static final String DEACTIVATE_SUCCESS="冻结成功";
	
	public static final String ONLINE_SUCCESS="上架成功";
	public static final String OFFLINE_SUCCESS="下架成功";
	
	public static final String CHARSET="UTF-8";
	
	public static final String MIN_MONEY="0.01";    //提现最小金额
	
	
	// 私钥 pkcs8格式的
	public static final String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfv12grkQ/Cm4sjWox55EhzG1h9Ary2lr9TU81YMLiT1j7SBo9MpkgF7KnMvuiLrvUPL3/ZVO7CNGEFJ6dAxY/aP1cbKNPY3VSLeiWUZlrHwO7xP6mceJLTksjM6vzXsfV16Sk6vxv7nxciZ7e+ofYY4Bu4VtoouidbXrYIeAXFkaUs5fEJsi0aXmGMN04pEgTUkrYY+bylbw8HOOnWeV0E0HTsbv4j9xhgQginvsiPN8NX62DMe5Tqdzjo714pDCn3QZtvWxq+WzIgLlWWvJLEA4r9RcloFP6fHMgiXf9ANwaV19AtrdBRZqNrfJnbmfy2CcSa1Lg2siE4hJhAEeRAgMBAAECggEAKUMky/w3ZJEtJBTi+UD3G+PR3RtMOK5uEj+F1iPIMuyL71wQav5GzyNP4iApHxszKP8HlJB3sDf25kbtMWE6veZAiz8Eb90g0LlK+ojZN/Iq5LVdkWCZMxpmDhGquJIiy/mHbZHE4qSXOs/RWYccz6fj5MAQ/FYX/5HnySv21f9hnzeEimCYkxq0XGJWPu1kHktswzCSVqTP8xyaYrHfiB/IQRwaeFWBHpOOmoUYb/9iXfdE7uGsn8dyh50+rAVqwHjKjM6TNPToAoLAgTUR2KrT6f6qQkexruL4tRRkfdmrmVuRUnKfMX6Dw01U22XcK0nYppw50ZdpCO1BwVuAAQKBgQDmbGa+WfpBpxqWY1LsXdeRgw16UDANLfGFsUjA6sVBEJaY471KjlzBaG0MnywuLsl0BUDICEbXR/capbv4lNerbvgxb5s5sw1aYeDzaBFzbK40x1g7SfkRfcRabAcH3PGoKGDtWcmOVkGycSqd9XR6MX8KR7wrSj71sp3CXXIdQQKBgQCxeqwghioQMNPbC0uC+6brno08tjdlg+2rxqV5Xv8S0N2uuyPvaXlf3x3yGxrsFmrp3Km9tGgDLrjF4rAFmLpFQREv4yMqcC1y1UgiyS6XV2pepLHYdMnNCL89mqm706A9Vgc4lgzVIrO8h6/4XbslJeo7g7bkHOGXSumn3lWGUQKBgD3u3wLEznX9AzFuyugLJRjQX7wDd5bjRWqIxyRLNe+SFNhNRiCW+Slm9wCQe+CB5NXRX6MkTl4o0qsP5uEeqIVEk2M+Ozub+7fSXRMvYplimcRH3Fg7Pr3Lzw36wT0EztNC3yKMopcmagwbTLgeNEtvqFTMCTCdiSSi+LI4SabBAoGAfQYUsUix4vvykhDP3fKW8M6dYhJezdV1HRfvw6NG/qNCNu+/7cyYYxAS0VWEzqEwKZR3wpqO1wcXxNbe9uJS/axLhjd0s1WYvpc+nLqqZpR+6zML0Zbb/eLWjXghb54x+GNc7ODPI2mxwfK35LdquU2PRuOPd/xccq6kEMxP5TECgYEAsuPwK9qZ/Yy18RyAk1afKJJW9Jq6fw7S2gF79tRjOaJfpt4cm/fsWXbs4Y7pD7eNmdiSMueg7cLEwp2s9IWNP5eA5p+NlIQN4sSKIpLKKsxZy4wModK+P4l92a6RhkFmv5Ui3xx7e8eboZ2xbaESXZstYi+s9XER/3epJ0xrag0=";
	
	public static void main(String[] args) {
		System.out.println(RSA_PRIVATE_KEY);
	}
}