package com.neil.survey.util;

public class RestResponseEntity<T> {
	/**
	 * 附加属性，目前用于分页查询时，保存总记录条数
	 */
	private Object extra;
	/**
	 * error 请求结果描述
	 */
	private String description;
	/**
	 * errorCode 请求结果代码 详情参考{@link ErrorCode}
	 */
	private Long errorCode;
	/**
	 * result 请求操作是否成功
	 */
	private boolean result;
	/**
	 * dataCount 返回的数据数量
	 */
	private int dataCount;
	/**
	 * data 数据
	 */
	private T data;
	/**
	 * 本次请求的接口，函数名称
	 */
	private String requestName;

	public Object getExtra() {
		return extra;
	}

	public void setExtra(Object extra) {
		this.extra = extra;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestType) {
		this.requestName = requestType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
