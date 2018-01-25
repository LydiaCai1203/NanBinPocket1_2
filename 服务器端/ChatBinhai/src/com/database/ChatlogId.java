package com.database;

/**
 * ChatlogId entity. @author MyEclipse Persistence Tools
 */

public class ChatlogId implements java.io.Serializable {

	// Fields

	private Integer logId;
	private Stuinfo stuinfo;
	private Stuinfo stuinfo_1;

	// Constructors

	/** default constructor */
	public ChatlogId() {
	}

	/** full constructor */
	public ChatlogId(Integer logId, Stuinfo stuinfo, Stuinfo stuinfo_1) {
		this.logId = logId;
		this.stuinfo = stuinfo;
		this.stuinfo_1 = stuinfo_1;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Stuinfo getStuinfo() {
		return this.stuinfo;
	}

	public void setStuinfo(Stuinfo stuinfo) {
		this.stuinfo = stuinfo;
	}

	public Stuinfo getStuinfo_1() {
		return this.stuinfo_1;
	}

	public void setStuinfo_1(Stuinfo stuinfo_1) {
		this.stuinfo_1 = stuinfo_1;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ChatlogId))
			return false;
		ChatlogId castOther = (ChatlogId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null
				&& castOther.getLogId() != null && this.getLogId().equals(
				castOther.getLogId())))
				&& ((this.getStuinfo() == castOther.getStuinfo()) || (this
						.getStuinfo() != null && castOther.getStuinfo() != null && this
						.getStuinfo().equals(castOther.getStuinfo())))
				&& ((this.getStuinfo_1() == castOther.getStuinfo_1()) || (this
						.getStuinfo_1() != null
						&& castOther.getStuinfo_1() != null && this
						.getStuinfo_1().equals(castOther.getStuinfo_1())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37 * result
				+ (getStuinfo() == null ? 0 : this.getStuinfo().hashCode());
		result = 37 * result
				+ (getStuinfo_1() == null ? 0 : this.getStuinfo_1().hashCode());
		return result;
	}

}