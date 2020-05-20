/**
 * Project Name:payment
 * File Name:SignUtil.java
 * Package Name:cn.swiftpass.utils.payment.sign
 * Date:2014-6-27下午3:22:33
 *
*/

package com.haiyi.utils;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haiyi.pay.PayConfig;
import org.apache.commons.codec.binary.Base64;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;



/**
 * ClassName:SignUtil
 * Function: 签名用的工具箱
 * Date:     2014-6-27 下午3:22:33 
 * @author    
 */
public class SignUtil {

    /** <一句话功能简述>
     * <功能详细描述>验证返回参数
     * @param params
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkParam(Map<String,String> params,String key){
        boolean result = false;
        if(params.containsKey("sign")){
            String sign = params.get("sign");
            params.remove("sign");
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            SignUtil.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String signRecieve = MD5.sign(preStr, "&key=" + key, "utf-8");
            result = sign.equalsIgnoreCase(signRecieve);
        }
        return result;
    }
    
    /**
     * 过滤参数
     * @author  
     * @param sArray
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>(sArray.size());
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    /** <一句话功能简述>
     * <功能详细描述>将map转成String
     * @param payParams
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String payParamsToString(Map<String, String> payParams){
        return payParamsToString(payParams,false);
    }
    
    public static String payParamsToString(Map<String, String> payParams,boolean encoding){
        return payParamsToString(new StringBuilder(),payParams,encoding);
    }
    /**
     * @author 
     * @param payParams
     * @return
     */
    public static String payParamsToString(StringBuilder sb,Map<String, String> payParams,boolean encoding){
        buildPayParams(sb,payParams,encoding);
        return sb.toString();
    }
    
    /**
     * @author 
     * @param payParams
     * @return
     */
    public static void buildPayParams(StringBuilder sb,Map<String, String> payParams,boolean encoding){
        List<String> keys = new ArrayList<String>(payParams.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(key).append("=");
            if(encoding){
                sb.append(urlEncode(payParams.get(key)));
            }else{
                sb.append(payParams.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
    }
    
    public static String urlEncode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            return str;
        } 
    }
    
    
    public static Element readerXml(String body, String encode) throws DocumentException {
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new StringReader(body));
        source.setEncoding(encode);
        Document doc = reader.read(source);
        Element element = doc.getRootElement();
        return element;
    }

    //请求时根据不同签名方式去生成不同的sign
    public static String getSign(String signType,String preStr){
        if("RSA_1_256".equals(signType)){
            try {
                return SignUtil.sign(preStr,"RSA_1_256",PayConfig.zx_mchPrivateKey);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }else{
            return MD5.sign(preStr, "&key=" + PayConfig.key, "utf-8");
        }
        return null;
    }

    //对返回参数的验证签名
    public static boolean verifySign(String sign,String signType,Map<String,String> resultMap) throws Exception{
        if("RSA_1_256".equals(signType)){
            Map<String,String> Reparams = SignUtil.paraFilter(resultMap);
            StringBuilder Rebuf = new StringBuilder((Reparams.size() +1) * 10);
            SignUtil.buildPayParams(Rebuf,Reparams,false);
            String RepreStr = Rebuf.toString();
            if(SignUtil.verifySign(RepreStr,sign, "RSA_1_256", PayConfig.zx_platPublicKey)){
                return true;
            }
        }else if("MD5".equals(signType)){
            if(SignUtil.checkParam(resultMap, PayConfig.key)){
                return true;
            }
        }
        return false;
    }
    public static boolean verifySign(String preStr,String sign,String signType, String platPublicKey) throws Exception {
        // 调用这个函数前需要先判断是MD5还是RSA
        // 商户的验签函数要同时支持MD5和RSA
        RSAUtil.SignatureSuite suite = null;

        if ("RSA_1_1".equals(signType)) {
            suite = RSAUtil.SignatureSuite.SHA1;
        } else if ("RSA_1_256".equals(signType)) {
            suite = RSAUtil.SignatureSuite.SHA256;
        } else {
            throw new Exception("不支持的签名方式");
        }

        boolean result = RSAUtil.verifySign(suite, preStr.getBytes("UTF8"), Base64.decodeBase64(sign.getBytes("UTF8")),
                platPublicKey);

        return result;
    }

    public static String sign(String preStr, String signType, String mchPrivateKey) throws Exception {
        RSAUtil.SignatureSuite suite = null;
        if ("RSA_1_1".equals(signType)) {
            suite = RSAUtil.SignatureSuite.SHA1;
        } else if ("RSA_1_256".equals(signType)) {
            suite = RSAUtil.SignatureSuite.SHA256;
        } else {
            throw new Exception("不支持的签名方式");
        }
        byte[] signBuf = RSAUtil.sign(suite, preStr.getBytes("UTF8"),
                mchPrivateKey);
        return new String(Base64.encodeBase64(signBuf), "UTF8");
    }

}

