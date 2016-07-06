<#if datas?? && datas?size gt 0>
insert into PRODUCT_CATEGORY_MEMBER (PRODUCT_CATEGORY_ID, PRODUCT_ID, FROM_DATE, THRU_DATE, COMMENTS, SEQUENCE_NUM, QUANTITY, LAST_UPDATED_STAMP, LAST_UPDATED_TX_STAMP, CREATED_STAMP, CREATED_TX_STAMP) values 
<#list datas as data>
('${data.productCategoryId!""}', '${data.productId!""}', '${data.fromDate!""}', null, null, null, null, '${data.fromDate!""}', '${data.fromDate!""}', '${data.fromDate!""}', '${data.fromDate!""}')<#if data_index == datas?size - 1>;<#else>,</#if>
</#list>
</#if>