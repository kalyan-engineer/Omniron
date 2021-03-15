package com.bank.user.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Account {

	 @javax.persistence.Id
	    private String accountid;
	 
	 	@Column
	    private String accounttype;

		/*
		 * @Type(type ="org.jadira.usertype.dateandtime.joda.persistentdatetime")
		 * 
		 * @Column private DateTime createddate ;
		 */
	 	
	 	@Column	
	    @Temporal(TemporalType.DATE)
	   // @JsonFormat(pattern="yyyy-MM-dd")
	    private Date openedDate;
		
		  @Column(nullable = false, columnDefinition = "TINYINT(1)")
		  @Type(type = "org.hibernate.type.NumericBooleanType") 
		  private boolean minor;
		 

	    @Column
	    private String branch;
	    
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "userid", referencedColumnName = "userid")
	    private User user;
	    
		/*
		 * @Type(type ="org.jadira.usertype.dateandtime.joda.persistentdatetime")
		 * 
		 * @Column private DateTime dateofbirth;
		 */

		public String getAccountid() {
			return accountid;
		}

		public void setAccountid(String accountid) {
			this.accountid = accountid;
		}

		public String getAccounttype() {
			return accounttype;
		}

		public void setAccounttype(String accounttype) {
			this.accounttype = accounttype;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}

		public boolean isMinor() {
			return minor;
		}

		public void setMinor(boolean minor) {
			this.minor = minor;
		}

		public Date getOpenedDate() {
			return openedDate;
		}

		public void setOpenedDate(Date openedDate) {
			this.openedDate = openedDate;
		}

	 
}
