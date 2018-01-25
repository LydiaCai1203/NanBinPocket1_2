package com.example.nkbhpocket2_0;


public class Stuinfo {

	// Fields

	private String stuId;
	private String stuName;
	private Integer stuImg;
	private String stuPwd;
	private String stuStatus;

	// Constructors

	/** default constructor */
	public Stuinfo(){
		
	}
	
	/** self define constructor */
	public Stuinfo(String stuId,String stuName,String stuPwd,int stuImg) {
		this.stuId=stuId;
		this.stuName=stuName;
		this.stuPwd=stuPwd;
		this.stuImg=stuImg;
	}

	/** minimal constructor */
	public Stuinfo(String stuName, String stuPwd) {
		this.stuName = stuName;
		this.stuPwd = stuPwd;
	}

	/** full constructor */
	public Stuinfo(String stuName, Integer stuImg, String stuPwd,
			String stuStatus) {
		this.stuName = stuName;
		this.stuImg = stuImg;
		this.stuPwd = stuPwd;
		this.stuStatus = stuStatus;
	}

	// Property accessors

	public String getStuId() {
		return this.stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return this.stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public Integer getStuImg() {
		return this.stuImg;
	}

	public void setStuImg(Integer stuImg) {
		this.stuImg = stuImg;
	}

	public String getStuPwd() {
		return this.stuPwd;
	}

	public void setStuPwd(String stuPwd) {
		this.stuPwd = stuPwd;
	}

	public String getStuStatus() {
		return this.stuStatus;
	}

	public void setStuStatus(String stuStatus) {
		this.stuStatus = stuStatus;
	}

	@Override
	public String toString() {
		return "Stuinfo [stuId=" + stuId + ", stuName=" + stuName + ", stuImg="
				+ stuImg + ", stuPwd=" + stuPwd + ", stuStatus=" + stuStatus
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stuId == null) ? 0 : stuId.hashCode());
		result = prime * result + ((stuImg == null) ? 0 : stuImg.hashCode());
		result = prime * result + ((stuName == null) ? 0 : stuName.hashCode());
		result = prime * result + ((stuPwd == null) ? 0 : stuPwd.hashCode());
		result = prime * result
				+ ((stuStatus == null) ? 0 : stuStatus.hashCode());
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
		Stuinfo other = (Stuinfo) obj;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		if (stuImg == null) {
			if (other.stuImg != null)
				return false;
		} else if (!stuImg.equals(other.stuImg))
			return false;
		if (stuName == null) {
			if (other.stuName != null)
				return false;
		} else if (!stuName.equals(other.stuName))
			return false;
		if (stuPwd == null) {
			if (other.stuPwd != null)
				return false;
		} else if (!stuPwd.equals(other.stuPwd))
			return false;
		if (stuStatus == null) {
			if (other.stuStatus != null)
				return false;
		} else if (!stuStatus.equals(other.stuStatus))
			return false;
		return true;
	}

	
}