package com.database;

import java.sql.Timestamp;

/**
 * Chatlog entity. @author MyEclipse Persistence Tools
 */

public class Chatlog implements java.io.Serializable {

	// Fields

	private Integer logId;
	private String senderId;
	private String receiverId;
	private String content;
	private Timestamp pubTime;

	// Constructors

	/** default constructor */
	public Chatlog() {
	}

	/** full constructor */
	public Chatlog(String senderId, String receiverId, String content,
			Timestamp pubTime) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
		this.pubTime = pubTime;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getPubTime() {
		return this.pubTime;
	}

	public void setPubTime(Timestamp pubTime) {
		this.pubTime = pubTime;
	}

	@Override
	public String toString() {
		return "Chatlog [logId=" + logId + ", senderId=" + senderId
				+ ", receiverId=" + receiverId + ", content=" + content
				+ ", pubTime=" + pubTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((pubTime == null) ? 0 : pubTime.hashCode());
		result = prime * result
				+ ((receiverId == null) ? 0 : receiverId.hashCode());
		result = prime * result
				+ ((senderId == null) ? 0 : senderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chatlog other = (Chatlog) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (pubTime == null) {
			if (other.pubTime != null)
				return false;
		} else if (!pubTime.equals(other.pubTime))
			return false;
		if (receiverId == null) {
			if (other.receiverId != null)
				return false;
		} else if (!receiverId.equals(other.receiverId))
			return false;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		return true;
	}

	
}