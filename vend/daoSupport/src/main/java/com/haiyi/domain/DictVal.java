package com.haiyi.domain;
import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: DictVal
  * @Company: 麦子科技
  * @Description: DictVal实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="字典明细",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class DictVal extends BaseDomain {
    //名称
    private String name;                                      
    //值
    private String value;      
    
    private Dict dict;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Dict getDict() {
		return dict;
	}
	public void setDict(Dict dict) {
		this.dict = dict;
	}
}