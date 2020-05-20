package com.haiyi.domain;

import com.haiyi.domain.base.BaseDomain;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.FilterInfoAnno;

import java.math.BigDecimal;
/**
 *
 * @ClassName: Product
 * @Company: 麦子科技
 * @Description: Product实体
 * @author 技术部-谢维乐
 * @date 2016年5月1日 下午1:38:35
 *
 */
@ClassInfoAnno(msg="商品",resourceId="id",dbId = "id")
@FilterInfoAnno(fields = {""},sorts={""})
public class Product extends BaseDomain {
    //名称
    private String name;
    //商品编号
    private String productNum;
    //单价
    private BigDecimal price;
    //成本
    private BigDecimal cost;
    //库存
    private Integer storeCount;
    //图片
    private String img;
    //商品组
    private ProductGroup productGroup;


    private Integer agentId;
    private String agentName;
    private Integer userId;
    private String userName;

    //商品过道
    private Integer orderNum;

    private Integer threshold;
    //缺货状态
    private Integer countStatus;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getCountStatus() {
        return countStatus;
    }

    public void setCountStatus(Integer countStatus) {
        this.countStatus = countStatus;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", productNum='" + productNum + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", storeCount=" + storeCount +
                ", img='" + img + '\'' +
                ", productGroup=" + productGroup +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", orderNum=" + orderNum +
                ", threshold=" + threshold +
                ", countStatus=" + countStatus +
                ", id=" + id +
                '}';
    }
}