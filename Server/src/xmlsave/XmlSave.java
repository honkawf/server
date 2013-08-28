package xmlsave;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * XmlSave entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "xml_save", schema = "SYSTEM")
public class XmlSave implements java.io.Serializable {

	// Fields

	private String num;
	private String notes;
	private String xmldoc;

	// Constructors

	/** default constructor */
	public XmlSave() {
	}

	/** minimal constructor */
	public XmlSave(String num) {
		this.num = num;
	}

	/** full constructor */
	public XmlSave(String num, String notes, String xmldoc) {
		this.num = num;
		this.notes = notes;
		this.xmldoc = xmldoc;
	}

	// Property accessors
	@Id
	@Column(name = "num", unique = true, nullable = false, length = 10)
	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Column(name = "notes", length = 10)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "xmldoc")
	public String getXmldoc() {
		return this.xmldoc;
	}

	public void setXmldoc(String xmldoc) {
		this.xmldoc = xmldoc;
	}

}