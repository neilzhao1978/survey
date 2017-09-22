package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ANSWER")
public class Answer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6984068216709107924L;
	@Id
	@Column(length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String answerId;
	private String replyerName;
	private String replyerPosition;
	private String replyTime;
	
	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", replyerName=" + replyerName + ", replyerPosition=" + replyerPosition
				+ ", replyTime=" + replyTime + "]";
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getReplayerName() {
		return replyerName;
	}
	public void setReplayerName(String replayerName) {
		this.replyerName = replayerName;
	}
	public String getReplayerPosition() {
		return replyerPosition;
	}
	public void setReplayerPosition(String replayerPosition) {
		this.replyerPosition = replayerPosition;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}


}
