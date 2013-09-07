package cn.edu.seu.persondepositinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PersonDepositInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PERSON_DEPOSIT_INFO", schema = "SYSTEM")
public class PersonDepositInfo implements java.io.Serializable {

	// Fields

	private String username;
	private String id;
	private String notes;
	private String depositway;
	private String interestrateway;
	private String amount;
	private String time;

	// Constructors

	/** default constructor */
	public PersonDepositInfo() {
	}

	/** minimal constructor */
	public PersonDepositInfo(String username, String id, String notes,
			String depositway, String interestrateway, String amount) {
		this.username = username;
		this.id = id;
		this.notes = notes;
		this.depositway = depositway;
		this.interestrateway = interestrateway;
		this.amount = amount;
	}

	/** full constructor */
	public PersonDepositInfo(String username, String id, String notes,
			String depositway, String interestrateway, String amount,
			String time) {
		this.username = username;
		this.id = id;
		this.notes = notes;
		this.depositway = depositway;
		this.interestrateway = interestrateway;
		this.amount = amount;
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

	@Column(name = "DEPOSITWAY", nullable = false, length = 10)
	public String getDepositway() {
		return this.depositway;
	}

	public void setDepositway(String depositway) {
		this.depositway = depositway;
	}

	@Column(name = "INTERESTRATEWAY", nullable = false, length = 10)
	public String getInterestrateway() {
		return this.interestrateway;
	}

	public void setInterestrateway(String interestrateway) {
		this.interestrateway = interestrateway;
	}

	@Column(name = "AMOUNT", nullable = false, length = 15)
	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Column(name = "TIME", length = 15)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}