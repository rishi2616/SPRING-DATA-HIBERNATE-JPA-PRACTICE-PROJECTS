package com.kingshuk.hibernateandjpa.dateandtime.testharnesses;

import java.time.ZoneId;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kingshuk.hibernateandjpa.dateandtime.config.ConfigurationUtil;
import com.kingshuk.hibernateandjpa.dateandtime.model.SupportedDateTimeEntity;

public class SupportedDateTimeGetDataHarness {
	public static void main(String[] args) {

		Transaction transaction = null;
		try (SessionFactory sessionFactory = ConfigurationUtil.buildSessionFactory();

				Session session = sessionFactory.openSession();) {

			transaction = session.beginTransaction();

			SupportedDateTimeEntity entity = session.get(SupportedDateTimeEntity.class, 116l);
			
			System.out.println(entity);
			
			System.out.println(entity.getZonedDateTime().withZoneSameInstant(ZoneId.of("Asia/Kolkata")));

			transaction.commit();

		} catch (Exception exception) {
			exception.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}

	}

}
