/*
 * 时间处理 
 * 

 */

//-----------------------------------------------------------------------
// Demo:
// new Date().format('yyyy-MM-dd HH:mm:ss') --> '2015-01-13 15:41:00'
// new Date().format('yyyy-MM-dd') --> '2015-01-13'
// new Date().format('yyyy/M/dd') --> '2015/1/13 16'
//-----------------------------------------------------------------------
Date.prototype.format = function(formatStr)   
{   
    var str = formatStr;   
  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
  
    str=str.replace(/MM/,this.getMonth()+1>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,this.getMonth()+1);   
  
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
  
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
  
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
  
    return str;   
}

//-----------------------------------------------------------------------
// Demo: 
// now: 2015-01-13 15:41:00
// now.addByPart('year', 1) --> '2016-01-13 15:41:00'
// now.addByPart('month', 1) --> '2015-02-13 15:41:00'
//-----------------------------------------------------------------------
Date.prototype.addByPart = function(partName, n) {   
    var dtTmp = this;  
    switch (partName) {   
        case 'second' :return new Date(Date.parse(dtTmp) + (1000 * n));  
        case 'minute' :return new Date(Date.parse(dtTmp) + (60000 * n));  
        case 'hour' :return new Date(Date.parse(dtTmp) + (3600000 * n));  
        case 'day' :return new Date(Date.parse(dtTmp) + (86400000 * n));
        case 'quarter' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + n*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
        case 'month' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + n, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
        case 'year' :return new Date((dtTmp.getFullYear() + n), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
    }
}


var ComsDateUtil = {};

//-----------------------------------------------------------------------
// Demo: 
// now: 2015-01-13 15:41:00
// ComsDateUtil.parseDateTime('2016-01-13 15:41:00')
// return: Date Object
//-----------------------------------------------------------------------
ComsDateUtil.parseDateTime = function(strDateTime){
	var temp = strDateTime.split(/ /);
	var strDate = temp[0];
	var strTime = temp[1];
	var tempDate = strDate.split(/-/);
	var tempTime = strTime.split(/:/);
	
	var year = tempDate[0];
	var month = tempDate[1];
	if("0" == month.substring(0,1)){
		month = month.substring(1,2);
	}
	var day = tempDate[2];
	
	var hour = tempTime[0];
	var minute = tempTime[1];
	var second = tempTime[2];
	
	var dateObj = new Date(year, month - 1, day, hour, minute, second);
	return dateObj;
}


//-----------------------------------------------------------------------
// Demo: 
// now: 2015-01-13 15:41:00
// ComsDateUtil.parseDate('2016-01-13')
// return: Date Object
//-----------------------------------------------------------------------
ComsDateUtil.parseDate = function(strDate){
	return ComsDateUtil.parseDateTime(strDate + " 00:00:00");
}
 