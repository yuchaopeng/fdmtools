<#if datas?? && datas?size gt 0>
<#list datas as data>
<ProductCategory productCategoryId="${data.productCategoryId!""}"   productCategoryTypeId="${data.productCategoryTypeId!""}"  categoryName="${data.categoryName!""}"/> 
</#list>
</#if>