package com.example.nkbhpocket2_0;

public class StuScore {

	private String courseName;
	
	private String startTerm;
	
	private String courseType;
	
	private String credit;
	
	private String socreOne;
	
	private String scoreTwo;
	
	private String finalScore;
	
	public StuScore() {
		super();
	}

	public StuScore(String courseName, String startTerm, String courseType, String credit, String socreOne,
			String scoreTwo, String finalScore) {
		super();
		this.courseName = courseName;
		this.startTerm = startTerm;
		this.courseType = courseType;
		this.credit = credit;
		this.socreOne = socreOne;
		this.scoreTwo = scoreTwo;
		this.finalScore = finalScore;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStartTerm() {
		return startTerm;
	}

	public void setStartTerm(String startTerm) {
		this.startTerm = startTerm;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getSocreOne() {
		return socreOne;
	}

	public void setSocreOne(String socreOne) {
		this.socreOne = socreOne;
	}

	public String getScoreTwo() {
		return scoreTwo;
	}

	public void setScoreTwo(String scoreTwo) {
		this.scoreTwo = scoreTwo;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}
	
	
	
}
