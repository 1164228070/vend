<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectDao}">

  <resultMap id="BaseResultMap" type="${objectName}" >
  <#list fieldList as var>
  <#if var[5] == "true">
      <id column="${var[0]}" property="${var[0]}" jdbcType="INTEGER" />
  <#else>
      <result column="${var[0]}" property="${var[0]}" jdbcType="${var[1]}" />
  </#if>
  </#list>
  </resultMap>
  
  <sql id="Base_Column_List" >
  <#list fieldList as var>
      ${var[0]}<#if var_has_next>,</#if>
  </#list>
  </sql>
  
  <!--根据Id查询-->
  <select id="findById" resultMap="BaseResultMap" parameterType="java.lang.String" >
      select <include refid="Base_Column_List" /> from ${objectTable} where ${objectPrimaryKey} = ${r"#{"}${objectPrimaryKey},jdbcType=INTEGER${r"}"}
  </select>
  
  
  <!-- 多条件查询 -->
  <select id="findBySelective" resultMap="BaseResultMap" parameterType="${queryName}">
      select <include refid="Base_Column_List" /> from ${objectTable}
      <trim prefix="where" prefixOverrides="and |or ">
         <if test="keyWords != null and keyWords != ''">
            <bind name="likeKeyWords" value="'%' + _parameter.keyWords + '%'" />
            and name like ${r"#{"}likeKeyWords,jdbcType=VARCHAR${r"}"}
         </if>
      </trim>
      order by id DESC
  </select>
  
  
  <!-- 删除 -->
  <delete id="deleteById" parameterType="java.lang.String" >
      delete from ${objectTable} where ${objectPrimaryKey} = ${r"#{"}${objectPrimaryKey},jdbcType=INTEGER${r"}"}
  </delete>
  
  <!-- 批量删除 -->
  <delete id="deleteByIds" parameterType="java.lang.String">
      delete from ${objectTable} where ${objectPrimaryKey} in
      <foreach item="id" collection="array" open="(" separator="," close=")">
          ${r"#{id}"}
      </foreach>
  </delete>
  
  <#if "${objectAllowNull}" == "false">
  <!--全部字段不允许空-->
  <insert id="add" parameterType="${objectName}" keyColumn="id" keyProperty="id" useGeneratedKeys="true">
      insert into ${objectTable}(
  	  <#list fieldList as var>
          ${var[0]}<#if var_has_next>,</#if>
      </#list>
          )values (
  	  <#list fieldList as var>
          ${r"#{"}${var[0]},jdbcType=${var[1]}${r"}"}<#if var_has_next>,<#else>)</#if>
      </#list>
  </insert> 
  <#else>
  <!--部分字段允许为空-->
  <insert id="add" parameterType="${objectName}" >
    insert into ${objectTable}
    <trim prefix="(" suffix=")" suffixOverrides="," >
    <#list fieldList as var>
    <#if "${var[4]}"=="false">
        ${var[0]}<#if var_has_next>,</#if>
    <#else>
    <#if "${var[1]}"=='CHAR' || "${var[1]}"=='VARCHAR'>
        <if test="${var[0]} != null and ${var[0]} != ''" >
            ${var[0]}<#if var_has_next>,</#if>
        </if>
    <#else>
        <if test="${var[0]} != null" >
            ${var[0]}<#if var_has_next>,</#if>
        </if>
    </#if>
    </#if>
    </#list> 
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
    <#list fieldList as var>
    <#if "${var[4]}"=="false">
        ${r"#{"}${var[0]},jdbcType=${var[1]}${r"}"}<#if var_has_next>,</#if>
    <#else>
    <#if "${var[1]}"=='CHAR' || "${var[1]}"=='VARCHAR'>
        <if test="${var[0]} != null and ${var[0]} != ''" >
            ${r"#{"}${var[0]},jdbcType=${var[1]}${r"}"}<#if var_has_next>,</#if>
        </if>
    <#else>
        <if test="${var[0]} != null" >
            ${r"#{"}${var[0]},jdbcType=${var[1]}${r"}"}<#if var_has_next>,</#if>
        </if>
    </#if>
    </#if>
    </#list> 
    </trim>
  </insert>
  </#if>
  
  <!--更新--> 
  <update id="update" parameterType="${objectName}" >
      update ${objectTable}
      <set>
      <#list fieldList as var>
      <#if "${var[0]}"!="${objectPrimaryKey}">
      <#if "${var[1]}"=='CHAR' || "${var[1]}"=='VARCHAR'>
          <if test="${var[0]} != null and ${var[0]} != ''" >
              ${var[0]} = ${r"#{"}${var[0]},jdbcType=${var[1]}${r"}"}<#if var_has_next>,</#if>
          </if>
	  <#else>
          <if test="${var[0]} != null" >
              ${var[0]} = ${r"#{"}${var[0]},jdbcType=${var[1]}${r"}"}<#if var_has_next>,</#if>
          </if>
      </#if>  
      </#if>  
      </#list>
      </set>
      where ${objectPrimaryKey} = ${r"#{"}${objectPrimaryKey},jdbcType=INTEGER${r"}"}
  </update>
	
</mapper>