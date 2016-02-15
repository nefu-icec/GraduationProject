package edu.nefu.GraduationProject.bean;

public class Notification 
{
	private int nid;
	private String title;
	private String content;
	private DateTime publish;
	
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public DateTime getPublish() {
		return publish;
	}
	public void setPublish(DateTime release) {
		this.publish = release;
	}
	
	public Notification(String title, String content, DateTime publish)
	{
		super();
		this.title = title;
		this.content = content;
		this.publish = publish;
	}
	public Notification(int nid, String title, String content, DateTime publish) {
		super();
		this.nid = nid;
		this.title = title;
		this.content = content;
		this.publish = publish;
	}
	
}
