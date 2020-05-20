package ${objectPackageName};
import com.haiyi.anno.ClassInfoAnno;
import com.haiyi.anno.FilterInfoAnno;
import com.haiyi.domain.base.BaseDomain;
import java.math.BigDecimal;
/**
  * 
  * @ClassName: ${className}
  * @Company: 麦子科技
  * @Description: ${className}实体
  * @author 技术部-谢维乐
  * @date 2016年5月1日 下午1:38:35
  *
 */
@ClassInfoAnno(msg="${msg}",resourceId="${objectPrimaryKey}",dbId = "${objectPrimaryKey}")
@FilterInfoAnno(fields = {""},sorts={""})
public class ${className} extends BaseDomain {
    <#list fieldList as var>
    <#if var[1] == 'DECIMAL'>
    //${var[6]}
    private <#if var[2]==1>Byte<#else>BigDecimal</#if> ${var[0]};  
    <#elseif var[1] =='DATETIME'>    
    //${var[6]}]                               
    private Date ${var[0]};                                        
    <#elseif var[1]=='INTEGER'>
     //${var[6]}
    private Integer ${var[0]};                                    
    <#elseif var[1]=='BIT'>
     //${var[6]}
    private Boolean ${var[0]};                                    
    <#elseif var[1]=='SMALLINT'>
    //${var[6]}
    private Byte ${var[0]};                                        
    <#else>
    //${var[6]}
    private String ${var[0]};                                      
    </#if>
    </#list>
}