package com.neil.survey.util;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * @param <T>
 */
public class ResponseGenerator<T> {
	private static final Logger logger = Logger.getLogger(ResponseGenerator.class);

	/**
	 * 创建一个Http请求的返回结果对象
	 * 
	 * @param requestName
	 *            请求方法名 测试阶段不能为null
	 * @param result
	 *            请求结果
	 * @param errorCode
	 *            错误代码 没有错误则为0
	 * @param description
	 *            请求结果描述
	 * @param dataCount
	 *            数据的数量
	 * @param data
	 *            数据,数量为0时,可以为null
	 * @param extra
	 *            额外的数据,主要用于分页查询,返回的总页数,可以为null
	 * @return 结果对象
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:05:49
	 * @备注:
	 */
	private static <T> RestResponseEntity<T> createResponse(String requestName, boolean result, Long errorCode,
			String description, int dataCount, T data, Object extra) {
		RestResponseEntity<T> re = new RestResponseEntity<T>();
		re.setData(data);
		re.setDataCount(dataCount);
		re.setRequestName(requestName);
		re.setErrorCode(errorCode);
		re.setResult(result);
		re.setDescription(description);
		re.setExtra(extra);
		return re;
	}

	/**
	 * 创建一个Http请求,操作成功的返回结果对象
	 * 
	 * @param requestName
	 *            请求方法名 测试阶段不能为null
	 * @param description
	 *            请求结果描述
	 * @param dataCount
	 *            数据的数量
	 * @param data
	 *            数据,数量为0时,可以为null
	 * @param extra
	 *            额外的数据,主要用于分页查询,返回的总页数,可以为null
	 * @return 结果对象
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:12:29
	 * @备注:
	 */
	public static <T> RestResponseEntity<T> createSuccessResponse(String requestName, String description, int dataCount,
			T data, Object extra) {
		return createResponse(requestName, true, ErrorCode.NO_ERROR, description, dataCount, data, extra);
	}

	/**
	 * 创建一个Http请求,操作成功的返回结果对象
	 * 
	 * @param requestName
	 *            请求方法名 测试阶段不能为null
	 * @param description
	 *            请求结果描述
	 * @param dataCount
	 *            数据的数量
	 * @param data
	 *            数据,数量为0时,可以为null
	 * @return 结果对象
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:12:29
	 * @备注:
	 */
	public static <T> RestResponseEntity<T> createSuccessResponse(String requestName, String description, int dataCount,
			T data) {
		return createResponse(requestName, true, ErrorCode.NO_ERROR, description, dataCount, data, null);
	}

	/**
	 * 创建一个Http请求,操作成功的返回结果对象
	 * 
	 * @param description
	 *            请求结果描述
	 * @param dataCount
	 *            数据的数量
	 * @param data
	 *            数据,数量为0时,可以为null
	 * @return 结果对象
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:12:29
	 * @备注:
	 */
	public static <T> RestResponseEntity<T> createSuccessResponse(String description, int dataCount, T data) {
		// 获取函数调用栈
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String requestName = null;
		if (stackTraceElements.length > 2) {
			requestName = stackTraceElements[2].getMethodName();
		}
		return createResponse(requestName, true, ErrorCode.NO_ERROR, description, dataCount, data, null);
	}

	/**
	 * 创建一个Http请求,操作成功的返回结果对象
	 * 
	 * @param description
	 *            请求结果描述
	 * @param dataCount
	 *            数据的数量
	 * @param data
	 *            数据,数量为0时,可以为null
	 * @param extra
	 *            额外的数据,主要用于分页查询,返回的总页数,可以为null
	 * @return 结果对象
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:12:29
	 * @备注:
	 */
	public static <T> RestResponseEntity<T> createSuccessResponse(String description, int dataCount, T data,
			Object extra) {
		// 获取函数调用栈
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String requestName = null;
		if (stackTraceElements.length > 2) {
			requestName = stackTraceElements[2].getMethodName();
		}
		return createResponse(requestName, true, ErrorCode.NO_ERROR, description, dataCount, data, extra);
	}

	/**
	 * 创建一个Http请求,操作失败的返回结果对象
	 * 
	 * @param description
	 *            请求结果描述
	 * @param errorCode
	 *            错误代码
	 * @return
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:14:08
	 * @备注:
	 */
	public static <T> RestResponseEntity<T> createFailResponse(String description, Long errorCode) {
		// 获取函数调用栈
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String requestName = null;
		if (stackTraceElements.length > 2) {
			requestName = stackTraceElements[2].getMethodName();
		}
		return createResponse(requestName, false, errorCode, description, 0, null, null);
	}

	/**
	 * 创建一个Http请求,操作失败的返回结果对象
	 * 
	 * @param requestName
	 *            请求方法名 测试阶段不能为null
	 * @param description
	 *            请求结果描述
	 * @param errorCode
	 *            错误代码
	 * @param requestName
	 * @return
	 * @作者:Administrator
	 * @时间:2015年11月12日 下午3:14:08
	 * @备注:
	 */
	public static <T> RestResponseEntity<T> createFailResponse(String requestName, String description, Long errorCode) {
		return createResponse(requestName, false, errorCode, description, 0, null, null);
	}

	/**
	 * @param string
	 * @return
	 * @作者:Administrator
	 * @时间:2016年5月11日 下午3:14:35
	 * @备注:
	 */
	public static RestResponseEntity<Void> createSuccessResponse(String string) {
		return createSuccessResponse(string, 0, null);
	}
}
