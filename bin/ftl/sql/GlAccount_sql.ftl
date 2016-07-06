<#if datas?? && datas?size gt 0>
insert into GL_ACCOUNT (GL_ACCOUNT_ID, GL_ACCOUNT_TYPE_ID, GL_ACCOUNT_CLASS_ID, GL_RESOURCE_TYPE_ID, GL_XBRL_CLASS_ID, PARENT_GL_ACCOUNT_ID, ACCOUNT_CODE, ACCOUNT_NAME, DESCRIPTION, PRODUCT_ID, POSTED_BALANCE, LAST_UPDATED_STAMP, LAST_UPDATED_TX_STAMP, CREATED_STAMP, CREATED_TX_STAMP) values 
<#list datas as data>
('${data.glAccountId!""}', '${data.glAccountTypeId!""}', null, null, null, null, '${data.accountCode!""}', '${data.accountName!""}', null, null, null, '${data.fromDate!""}', '${data.fromDate!""}', '${data.fromDate!""}', '${data.fromDate!""}')<#if data_index == datas?size - 1>;<#else>,</#if>
</#list>
</#if>