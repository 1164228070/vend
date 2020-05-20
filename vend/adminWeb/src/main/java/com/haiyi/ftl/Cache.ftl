var cache = function(){};

//定义静态方法
cache.data={
<#list cacheList as itemMap>
    ${itemMap.type}:{
    <#list itemMap.value as item>
        ${item.value} : "${item.name}"<#if item_has_next>,</#if>
    </#list>
    }<#if itemMap_has_next>,</#if>
</#list>
};

/**
 * 获取缓存
 * @param type
 */
cache.get = function(type){
	return cache.data[type];
}

/**
 * 获取缓存明细值
 * @param type
 * @param cacheVal
 * @returns
 */
cache.getName = function(type,cacheVal){
	try {
		return cache.data[type][cacheVal];
	} catch (e) {
		return "--";
	}
}