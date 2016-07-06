<#if datas?? && datas?size gt 0>
insert  into `PAYMENT_METHOD`(`PAYMENT_METHOD_ID`,`PAYMENT_METHOD_TYPE_ID`,`PARTY_ID`,`GL_ACCOUNT_ID`,`DESCRIPTION`,`FROM_DATE`,`THRU_DATE`,`LAST_UPDATED_STAMP`,`LAST_UPDATED_TX_STAMP`,`CREATED_STAMP`,`CREATED_TX_STAMP`) values 
<#list datas as data>
('${data.paymentMethodId!""}','${data.paymentMethodTypeId!""}',NULL,NULL,'${data.description!""}',NULL,NULL,'${data.fromDate!""}','${data.fromDate!""}','${data.fromDate!""}','${data.fromDate!""}')<#if data_index == datas?size - 1>;<#else>,</#if>
</#list>
</#if>