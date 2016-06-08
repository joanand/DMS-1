package in.jdsoft.dms.model;

// default package
// Generated Apr 26, 2016 11:56:10 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * DocumentHistory generated by hbm2java
 */
@Entity
@Table(name = "dms_document_history", catalog = "mua_database")
public class DocumentHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer documentHistoryId;
	private Double version;
	private int documentId;
	private Date date;
	private String author;
	private byte[] content;

	public DocumentHistory() {
	}

	public DocumentHistory(int documentId, String author) {
		this.documentId = documentId;
		this.author = author;
	}

	public DocumentHistory(int documentId, Date date, String author, byte[] content) {
		this.documentId = documentId;
		this.date = date;
		this.author = author;
		this.content = content;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "document_history_id", unique = true, nullable = false)
	public Integer getDocumentHistoryId() {
		return this.documentHistoryId;
	}

	public void setDocumentHistoryId(Integer documentHistoryId) {
		this.documentHistoryId = documentHistoryId;
	}


	@Column(name = "version", precision = 22, scale = 0)
	public Double getVersion() {
		return this.version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	@Column(name = "document_id", nullable = false)
	public int getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", length = 19)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "author", nullable = false)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "content",columnDefinition="LONGBLOB")
	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	

}
