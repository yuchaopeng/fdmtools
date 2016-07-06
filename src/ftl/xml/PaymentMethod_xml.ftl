<#if datas?? && datas?size gt 0>
<#list datas as data>
<PaymentMethod paymentMethodId="${data.paymentMethodId!""}" paymentMethodTypeId="${data.paymentMethodTypeId!""}" glAccountId="${data.glAccountId!""}" description="${data.description!""}"/>  
</#list>
</#if>