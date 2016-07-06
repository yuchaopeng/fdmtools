<#if datas?? && datas?size gt 0>
<#list datas as data>
<ProductCategoryMember productCategoryId="${data.productCategoryId!""}" productId="${data.productId!""}" fromDate="${data.fromDate!""}"/>
</#list>
</#if>