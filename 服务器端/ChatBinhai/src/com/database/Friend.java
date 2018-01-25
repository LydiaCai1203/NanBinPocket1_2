package com.database;

/**
 * Friend entity. @author MyEclipse Persistence Tools
 */

public class Friend implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer friendImgId;
	private String friendName;
	private String friendOfWho;

	// Constructors

	/** default constructor */
	public Friend() {
	}

	/** minimal constructor */
	public Friend(Integer friendImgId, String friendName) {
		this.friendImgId = friendImgId;
		this.friendName = friendName;
	}

	/** full constructor */
	public Friend(Integer friendImgId, String friendName, String friendOfWho) {
		this.friendImgId = friendImgId;
		this.friendName = friendName;
		this.friendOfWho = friendOfWho;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFriendImgId() {
		return this.friendImgId;
	}

	public void setFriendImgId(Integer friendImgId) {
		this.friendImgId = friendImgId;
	}

	public String getFriendName() {
		return this.friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendOfWho() {
		return this.friendOfWho;
	}

	public void setFriendOfWho(String friendOfWho) {
		this.friendOfWho = friendOfWho;
	}

	@Override
	public String toString() {
		return "Friend [id=" + id + ", friendImgId=" + friendImgId
				+ ", friendName=" + friendName + ", friendOfWho=" + friendOfWho
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((friendImgId == null) ? 0 : friendImgId.hashCode());
		result = prime * result
				+ ((friendName == null) ? 0 : friendName.hashCode());
		result = prime * result
				+ ((friendOfWho == null) ? 0 : friendOfWho.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Friend other = (Friend) obj;
		if (friendImgId == null) {
			if (other.friendImgId != null)
				return false;
		} else if (!friendImgId.equals(other.friendImgId))
			return false;
		if (friendName == null) {
			if (other.friendName != null)
				return false;
		} else if (!friendName.equals(other.friendName))
			return false;
		if (friendOfWho == null) {
			if (other.friendOfWho != null)
				return false;
		} else if (!friendOfWho.equals(other.friendOfWho))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}