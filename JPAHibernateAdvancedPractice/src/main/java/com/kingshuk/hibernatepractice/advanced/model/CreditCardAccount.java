package com.kingshuk.hibernatepractice.advanced.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kingshuk.hibernatepractice.advanced.enums.NetWork;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="credit_card_account")
public class CreditCardAccount extends Account{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8036997190322227104L;

	@Column(name="credit_card_number", length=20)
	private String creditCardNumber;
		
	@Enumerated(EnumType.STRING)
	private NetWork netWork;
	
	@Column(name="credit_balance", columnDefinition= "NUMBER(10,2)")
	private double creditBalance;
	
	@Column(name="next_closing_date")
	@Temporal(TemporalType.DATE)
	private Date nextStatementClosingDate;
	
	@Column(name="credit_card_limit", columnDefinition= "NUMBER(10,2)")
	private double creditCardLimit;

}
