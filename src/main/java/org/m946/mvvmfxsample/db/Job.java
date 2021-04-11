package org.m946.mvvmfxsample.db;

public class Job {
	private String	jobCode;
	private int		jobGrade;
	private String	jobCountry;
	private String	jobTitle;
	private long	minSalary;
	private long	maxSalary;
	private String	jobRequirement;
	private String	languageReq;
	
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public int getJobGrade() {
		return jobGrade;
	}
	public void setJobGrade(int jobGrade) {
		this.jobGrade = jobGrade;
	}
	public String getJobCountry() {
		return jobCountry;
	}
	public void setJobCountry(String jobCountry) {
		this.jobCountry = jobCountry;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public long getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(long minSalary) {
		this.minSalary = minSalary;
	}
	public long getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(long maxSalary) {
		this.maxSalary = maxSalary;
	}
	public String getJobRequirement() {
		return jobRequirement;
	}
	public void setJobRequirement(String jobRequirement) {
		this.jobRequirement = jobRequirement;
	}
	public String getLanguageReq() {
		return languageReq;
	}
	public void setLanguageReq(String languageReq) {
		this.languageReq = languageReq;
	}
}
