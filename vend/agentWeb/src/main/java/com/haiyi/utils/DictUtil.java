package com.haiyi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haiyi.domain.Dict;
import com.haiyi.domain.DictVal;

public class DictUtil {

	private static List<Dict> caches;

	public  static void init(List<Dict> caches){
		DictUtil.caches = caches;

	}
	/**
	 * 根据字典类型获取字典明细(从redis缓存中获取)
	 * @return
	 */
	public static Map<String,Object> getDictValByType(int type){
		Map<String,Object> dictVals = new HashMap<String,Object>();
		dictVals.put("0","否");
		dictVals.put("1","是");
		return dictVals;
	}


	public static List<DictVal> getDictValListByType(int type){
		List<DictVal> dictVals = null;
		for (Dict dict:DictUtil.caches){
			if(type==dict.getType()){
				dictVals = dict.getDictVals();
				break;
			}
		}
		return dictVals;
	}
	
	
	public static List<DictVal> getDictVal(){
		List<DictVal> dictVals = new ArrayList<DictVal>();
		DictVal dictVal = new DictVal();
		dictVal.setName("已激活");
		dictVal.setValue(1+"");
		dictVals.add(dictVal);
		
		DictVal dictVal2 = new DictVal();
		dictVal2.setName("未激活");
		dictVal2.setValue(0+"");
		dictVals.add(dictVal2);
		return dictVals;
	} 
}
