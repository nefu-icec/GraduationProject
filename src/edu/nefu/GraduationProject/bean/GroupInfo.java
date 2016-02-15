package edu.nefu.GraduationProject.bean;

public class GroupInfo
{
	private int number;
	private int sid;
	private String sname;
	private String ctitle;
	private String tname;
	private String origin;
	private int startPassed;
	private int endPassed;
	private String startQuestion;
	private String endQuestion;
	private int score;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getStartQuestion() {
		return startQuestion;
	}
	public void setStartQuestion(String startQuestion) {
		this.startQuestion = startQuestion;
	}
	public String getEndQuestion() {
		return endQuestion;
	}
	public void setEndQuestion(String endQuestion) {
		this.endQuestion = endQuestion;
	}
	public int getEndPassed() {
		return endPassed;
	}
	public void setEndPassed(int endPassed) {
		this.endPassed = endPassed;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getCtitle() {
		return ctitle;
	}
	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public int getStartPassed() {
		return startPassed;
	}
	public void setStartPassed(int startPassed) {
		this.startPassed = startPassed;
	}
	
	public GroupInfo(int number, int sid, String sname, String ctitle,
			String tname, String origin, int startPassed, int endPassed,
			String startQuestion, String endQuestion,int score) {
		super();
		this.number = number;
		this.sid = sid;
		this.sname = sname;
		this.ctitle = ctitle;
		this.tname = tname;
		this.origin = origin;
		this.startPassed = startPassed;
		this.endPassed = endPassed;
		this.startQuestion = startQuestion;
		this.endQuestion = endQuestion;
		this.score=score;
	}
	
}
