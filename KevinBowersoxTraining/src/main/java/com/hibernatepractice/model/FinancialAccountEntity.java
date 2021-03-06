package com.hibernatepractice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "accounts")
@Table(name = "FINANCIAL_ACCOUNT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
		@NamedQuery(name = "transactionAccounts", query = "select t.account " + "from transactions t "
				+ "where t.transactionAmount > 500.00 " + "and t.transactionType = 'Credit'"),
		@NamedQuery(name = "accountDetails", query = "select distinct a from transactions t " + "join t.account a "
				+ "where t.transactionAmount> :amount " + "and t.transactionType = 'Credit'") })
public class FinancialAccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userTableGen")
	@TableGenerator(name = "userTableGen", table = "USER_TABLE_GENERATOR", pkColumnName = "IDENTIFIER_NAME", valueColumnName = "IDENTIFIER_VALUE")
	@Column(name = "ACCOUNT_ID")
	private long accountId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ACCOUNTS", joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"))
	private Set<ComplexUser> users;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID", updatable = true)
	private BankEntity bank;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Enumerated(EnumType.STRING)
	@Column(name = "ACCOUNT_TYPE")
	private AccountType accountType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
	// @JoinColumn(name = "ACCOUNT_ID", nullable = false)
	List<TransactionEntity> transactions;

	@Column(name = "INITIAL_BALANCE", columnDefinition = "NUMBER(20,2)")
	private BigDecimal intialBalance;

	@Column(name = "CURRENT_BALANCE", columnDefinition = "NUMBER(20,2)")
	private BigDecimal currentBalance;

	@Column(name = "ACCOUNT_TYPE_ID")
	private String accountTypeId;

	@Column(name = "OPENED_DATE", updatable = false)
	private LocalDateTime openedDate;

	@Column(name = "CLOSED_DATE")
	private LocalDateTime closedDate;

	@Column(name = "LAST_UPDATED_DATE")
	private LocalDateTime lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;
}
