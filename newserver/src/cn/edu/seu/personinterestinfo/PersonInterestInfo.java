package cn.edu.seu.personinterestinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PersonInterestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PERSON_INTEREST_INFO", schema = "SYSTEM")
public class PersonInterestInfo implements java.io.Serializable {

	// Fields

	private String username;
	private String id;
	private String notes;
	private String financingway;
	private String time;

	// Constructors

	/** default constructor */
	public PersonInterestInfo() {
	}

	/** minimal constructor */
	public PersonInterestInfo(String username, String id, String notes,
			String financingway) {
		this.username = username;
		this.id = id;
		this.notes = notes;
		this.financingway = financingway;
	}

	/** full constructor */
	public PersonInterestInfo(String username, String id, String notes,
			String financingway, String time) {
		this.username = username;
		this.id = id;
		this.notes = notes;
		this.financingway = financingway;
		this.time = time;
	}

	// Property accessors
	@Id
	@Column(name = "USERNAME", unique = true, nullable = false, length = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "ID", nullable = false, length = 10)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NOTES", nullable = false, length = 16)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "FINANCINGWAY", nullable = false, length = 10)
	public String getFinancingway() {
		return this.financingway;
	}

	public void setFinancingway(String financingway) {
		this.financingway = financingway;
	}

	@Column(name = "TIME", length = 15)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}