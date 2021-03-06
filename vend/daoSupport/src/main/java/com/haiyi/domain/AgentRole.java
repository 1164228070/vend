package com.haiyi.domain;
import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: AgentRole
  * @Company: 麦子科技
  * @Description: AgentRole实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="代理角色",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class AgentRole extends BaseDomain {
    //名称
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}    
}