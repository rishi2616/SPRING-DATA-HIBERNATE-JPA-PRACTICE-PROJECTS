package com.hibernatepractice.harness.hibernate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import com.hibernatepractice.config.HibernateConfigUtil;
import com.hibernatepractice.model.Address;
import com.hibernatepractice.model.BankEntity;
import com.hibernatepractice.model.BudgetEntity;
import com.hibernatepractice.model.FinancialAccountEntity;
import com.hibernatepractice.model.TransactionEntity;

public class BudgetAccountTransactionHarness {

	public static void main(String[] args) {
		try (Session session = HibernateConfigUtil.getSessionFactory().openSession();) {

			session.beginTransaction();

			Address address = Address.builder().addressLine1("1525 Busch Pkwy").addressLine2("Room No 327")
					.city("Buffalo Grove").state("IL").zipCode("60089").build();

			Address address2 = Address.builder().addressLine1("771 Shady Grove Ln").city("Buffalo Grove").state("IL")
					.zipCode("60089").build();

			Map<String, String> locations = new HashMap<>();
			locations.put("HCT", "Hartford CT");
			locations.put("CIL", "Chicago IL");

			BankEntity bank = BankEntity.builder().bankName("KevinsBankEntity of America")
					.establishedDate(LocalDate.of(1999, Month.OCTOBER, 16))
					.contactNames(Arrays.asList("Kingshuk Mukherjee", "Deeksha Banerjee"))
					.address(Arrays.asList(address, address2)).locations(locations).build();
			
			//session.save(bank);
			
			BudgetEntity budgetEntity =  BudgetEntity.builder()
					.goalAmount(BigDecimal.valueOf(3400.00))
					.period("May 2020")
					.build();

			TransactionEntity transaction1 = TransactionEntity.builder().transactionAmount(BigDecimal.valueOf(20.00))
					.createdBy("Kingshuk Mukherjee").createdDate(LocalDateTime.now()).transactionType("Debit")
					.transactionNotes("Shoe purchase")
					.build();

			TransactionEntity transaction2 = TransactionEntity.builder().transactionAmount(BigDecimal.valueOf(50.00))
					.createdBy("Deeksha Banerjee").createdDate(LocalDateTime.now()).transactionType("Debit")
					.transactionNotes("Grocery").build();

			FinancialAccountEntity account = FinancialAccountEntity.builder().accountName("BOA Account")
					.accountTypeId("3").bank(bank).createdBy("Kingshuk Mukherjee").createdDate(LocalDateTime.now())
					.currentBalance(BigDecimal.valueOf(10000.00)).openedDate(LocalDateTime.now())
					.transactions(Arrays.asList(transaction1, transaction2)).build();
			
			transaction1.setAccount(account);
			transaction2.setAccount(account);
			
			session.save(account);
			
			budgetEntity.setTransactions(Arrays.asList(transaction1, transaction2));
			
			session.save(budgetEntity);

			session.getTransaction().commit();

			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
