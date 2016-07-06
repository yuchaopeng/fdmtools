<#if datas?? && datas?size gt 0>
insert  into `ACCOUNT_JOURNAL_RULE`(`ACCOUNT_JOURNAL_RULE_ID`,`VARIANT_ID`,`ACCOUNTING_TYPE`,`ACCOUNTING_SERVICE`,`SEQ_ID`,`CURRENCY_UOM_ID`,`ASSETS_ACCOUNT`,`DEBT_ACCOUNT`,`OPPOSITE_ACCOUNT`,`FROM_DATE`,`THRU_DATE`,`LAST_UPDATED_STAMP`,`LAST_UPDATED_TX_STAMP`,`CREATED_STAMP`,`CREATED_TX_STAMP`) values 
<#list datas as data>
('${data.accountJournalRuleId!""}','${data.variantId!""}','${data.accountingType!""}','${data.accountingService!""}','${data.seqId!""}','${data.currencyUomId!""}',<#if data.assetsAccount?? && data.assetsAccount != "">'${data.assetsAccount}'<#else>NULL</#if>,<#if data.debtAccount?? && data.debtAccount != "">'${data.debtAccount}'<#else>NULL</#if>,'${data.oppositeAccount!""}','${data.fromDate!""}',NULL,'${data.fromDate!""}','${data.fromDate!""}','${data.fromDate!""}','${data.fromDate!""}')<#if data_index == datas?size - 1>;<#else>,</#if>
</#list>
</#if>