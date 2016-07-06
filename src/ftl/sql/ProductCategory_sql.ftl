<#if datas?? && datas?size gt 0>
insert into PRODUCT_CATEGORY (PRODUCT_CATEGORY_ID, PRODUCT_CATEGORY_TYPE_ID, PRIMARY_PARENT_CATEGORY_ID, CATEGORY_NAME, DESCRIPTION, LONG_DESCRIPTION, CATEGORY_IMAGE_URL, LINK_ONE_IMAGE_URL, LINK_TWO_IMAGE_URL, DETAIL_SCREEN, SHOW_IN_SELECT, LAST_UPDATED_STAMP, LAST_UPDATED_TX_STAMP, CREATED_STAMP, CREATED_TX_STAMP) values 
<#list datas as data>
('${data.productCategoryId!""}', '${data.productCategoryTypeId!""}', null, '${data.categoryName!""}', null, null, null, null, null, null, null, '${data.fromDate!""}', '${data.fromDate!""}', '${data.fromDate!""}', '${data.fromDate!""}')<#if data_index == datas?size - 1>;<#else>,</#if>
</#list>
</#if>