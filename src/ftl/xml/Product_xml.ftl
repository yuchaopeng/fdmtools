<#if datas?? && datas?size gt 0>
<#list datas as data>
<Product productId="${data.productId!""}" productTypeId="${data.productTypeId!""}" productName="${data.productName!""}" description="${data.description!""}"/> 
</#list>
</#if>