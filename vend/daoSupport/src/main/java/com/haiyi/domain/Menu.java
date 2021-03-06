package com.haiyi.domain;
import java.util.ArrayList;
import java.util.List;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;
/**
  * 
  * @ClassName: Menu
  * @Company: 麦子科技
  * @Description: Menu实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="菜单",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class Menu extends BaseDomain {
    private String URL;
    private String icon;
    private Boolean leaf;
    private String name;
    private Integer parentId;
    private String accessToken;
    
    private List<Menu> children = new ArrayList<Menu>();
    
    
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "Menu [URL=" + URL + ", icon=" + icon + ", leaf=" + leaf + ", name=" + name + ", parentId=" + parentId
				+ ", accessToken=" + accessToken + "]";
	}
	
	
	
}