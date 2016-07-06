<#if datas?? && datas?size gt 0>
<#list datas as data>
<AccountJournalRule variantId="${data.variantId!""}" accountJournalRuleId="${data.accountJournalRuleId!""}" accountingType="${data.accountingType!""}" accountingService="${data.accountingService!""}" seqId="${data.seqId!""}" currencyUomId="${data.currencyUomId!""}" assetsAccount="${data.assetsAccount!""}"  debtAccount="${data.debtAccount!""}" oppositeAccount="${data.oppositeAccount!""}" fromDate="${data.fromDate!""}"/>
</#list>
</#if>