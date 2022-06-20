package com.employee.sys.audit;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

	@CreatedBy
	protected T createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	protected Date createdDate;

	@LastModifiedBy
	protected T lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@LastModifiedDate
	protected Date lastModifiedDate;

	public T getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(T createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public T getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(T lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
