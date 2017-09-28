package com.neil.survey.util;



import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator 业务逻辑层返回错误码说明， 所有错误码的值都是小于0的整数， 对于更新，删除操作，成功时返回NO_ERROR，
 *         添加操作，成功时返回添加数据在数据库中的主键id，是大于0的整数
 */
public class ErrorCode
{
    /**
     * 没有错误 0l
     */
    public static final Long NO_ERROR               = 0l;
    /**
     * 名称已存在 -2l
     */
    public static final Long NAME_EXIST             = -2l;
    /**
     * 参数错误， -3l
     */
    public static final Long INVALID_PARAMETER      = -3l;
    /**
     * 密码不可用 -4l
     */
    public static final Long INVALID_PASSWORD       = -4l;
   
    /**
     * Long INVALID_OPERATION 非法操作 -5l
     */
    public static final Long INVALID_OPERATION      = -5l;
    /**
     * @备注： 设备ip已存在 -6l
     */
    public static final Long DEVICE_IP_EXIST        = -6l;
    /**
     * Long SERVER_INTERNAL_ERROR 服务器内部错误 -7l
     */
    public static final Long SERVER_INTERNAL_ERROR  = -7l;
    /**
     * Long SESSION_USER_NOT_FOUND 会话用户信息不存在 -8l
     */
    public static final Long SESSION_USER_NOT_FOUND = -8l;
    /**
     * TIME_OUT 下发任务超时 -9
     */
    public static final Long TASK_ASSIGN_TIME_OUT   = -9l;
    /**
     * TIME_OUT 任务执行超时 -10
     */
    public static final Long TASK_RUNNING_TIME_OUT  = -10l;
    /**
     * 操作超时
     */
    public static final Long OPERATE_TIME_OUT       = -11l;
    /**
     * mq消息发送失败
     */
    public static final Long MQ_SERVER_ERROR        = -12l;
    /**
     * 目标不存在
     */
    public static final Long TARGET_NOT_FOUND = -13l;
    /**
     * 任务下发失败
     */
    public static final Long TASK_ASSIGN_FAIL = -14l;
    /**
     * 无效的认证，用户未登录，执行某些操作，会出现这个错误 -98l
     */
    public static final Long INVALID_CERTIFICATION  = -98l;
    /**
     * 数据库错误 -99l
     */
    public static final Long DB_ERROR               = -99l;
    /**
     * 未知错误 -100l
     */
    public static final Long UNKNOWN_ERROR          = -100l;
    
    /**
     * 资产编号已经存在 -1101
     */
    public static final Long CODE_EXIST          = -110l;

    //===========ADG
    public static final Long CODE_NOT_EXIST          = 404L;
    public static final Long RES_TYPE_ERROR			= 400L;
    
	//401,未授权,与http同
	public static final Long unauthorized = 401L;
	//403- Forbidden (禁止访问）
	public static final Long forbidden = 403L;
	//404,找不到 — 服务器找不到给定的资源，与http同  
	//405,不允许使用的方法，与http同
	public static final Long not_allowed = 405L;
	//408,请求超时，与http同
	public static final Long time_out = 408L;
	//415，不支持的媒体类型，与http同
	public static final Long unsupported_media_type = 415L;
	//450 (custom) ，请求失败
	public static final Long request_failed = 450L;
	//486,服务器忙
	public static final Long buzy = 486L;
    
	public static final Long ok = 200L;
	
    public static final Map<Long,String> errorDescriptions=new HashMap<Long,String>();
   
    static{
        errorDescriptions.put(ErrorCode.NO_ERROR, "没有错误");
        errorDescriptions.put(ErrorCode.NAME_EXIST, "名称已存在");
        errorDescriptions.put(ErrorCode.INVALID_PARAMETER, "参数不合法");
        errorDescriptions.put(ErrorCode.INVALID_PASSWORD, "密码错误");
        errorDescriptions.put(ErrorCode.INVALID_OPERATION, "当前工单状态下，无法执行此操作");
        errorDescriptions.put(DEVICE_IP_EXIST, "设备IP已存在");
        errorDescriptions.put(SERVER_INTERNAL_ERROR, "服务器内部数据错误");
        errorDescriptions.put(SESSION_USER_NOT_FOUND, "会话用户信息不存在");
        errorDescriptions.put(TASK_ASSIGN_TIME_OUT, "任务下发超时");
        errorDescriptions.put(TASK_RUNNING_TIME_OUT,"任务执行超时");
        errorDescriptions.put(OPERATE_TIME_OUT, "操作超时");
        errorDescriptions.put(ErrorCode.MQ_SERVER_ERROR, "MQ消息发送失败");
        errorDescriptions.put(TARGET_NOT_FOUND, "操作目标不存在");
        errorDescriptions.put(TASK_ASSIGN_FAIL, "任务下发失败");
        errorDescriptions.put(ErrorCode.INVALID_CERTIFICATION, "您没有权限执行此操作");
        errorDescriptions.put(ErrorCode.DB_ERROR, "服务器内部错误，请稍后再试");
        errorDescriptions.put(ErrorCode.UNKNOWN_ERROR, "未知错误");
        errorDescriptions.put(CODE_EXIST, "资产编号已经存在");
        errorDescriptions.put(CODE_NOT_EXIST, "设备不存在");
        errorDescriptions.put(RES_TYPE_ERROR, "设备编码错误");
        
        errorDescriptions.put(unauthorized, "401,未授权");
        errorDescriptions.put(forbidden, "403- Forbidden (禁止访问）");
        errorDescriptions.put(not_allowed, "405,不允许使用的方法");
        errorDescriptions.put(time_out, "408,请求超时");
        errorDescriptions.put(unsupported_media_type, "415，不支持的媒体类型");
        errorDescriptions.put(request_failed, "450 (custom) ，请求失败");
        errorDescriptions.put(buzy, "486,服务器忙");
        errorDescriptions.put(ok, "200,正常完成");
    }
     
}
