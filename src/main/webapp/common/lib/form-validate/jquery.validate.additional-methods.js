/* =========================================================
 * jquery.additional-methods.js
 * =========================================================
 * Copyright 2013 hik-caishiding
 *
 * used by jquery.validate.min.js
 * ========================================================= */
 
// 邮政编码验证   
jQuery.validator.addMethod("isZipCode", function(value, element) {   
	var tel = /^[0-9]{6}$/;
	return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");



// 数字、字母或下划线验证
jQuery.validator.addMethod("isNumCode", function(value, element) {   
	var tel = /^[A-Za-z0-9_]+$/;
	return this.optional(element) || (tel.test(value));
}, "请输入数字、字母或下划线");



// 存在空格或者特殊字符验证
jQuery.validator.addMethod("isUnCode", function(value, element) {   
	var tel = /^[\u4e00-\u9fa5\da-zA-Z\-\_]+$/;
	return this.optional(element) || (tel.test(value));
}, "存在空格或者特殊字符，请重新输入");