<#if datas?? && datas?size gt 0>
<#list datas as data>
<GlAccount glAccountId="${data.glAccountId!""}" glAccountTypeId="${data.glAccountTypeId!""}" accountCode="${data.accountCode!""}" accountName="${data.accountName!""}" lastUpdatedStamp="${data.fromDate!""}" lastUpdatedTxStamp="${data.fromDate!""}" createdStamp="${data.fromDate!""}" createdTxStamp="${data.fromDate!""}"/>
</#list>
</#if>