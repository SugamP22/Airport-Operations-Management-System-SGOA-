package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Reserva;
import config.HibernateUtils;
import utils.LanguageUtils;

public class ReservaDAO {
	public static void removeReserva(Integer id) {
		// I use this to delete a reservation by ID with basic not-found validation
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Reserva r = session.get(Reserva.class, id);
			if (r == null)
				throw new IllegalArgumentException(LanguageUtils.get("error.reserva.notFound"));
			session.delete(r);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static List<Reserva> getAllReserva() {
		// Helper I use to list all reservations; throws if there are none
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Reserva> r = session.createQuery("From Reserva", Reserva.class).list();
			if (r.isEmpty())
				throw new IllegalArgumentException(LanguageUtils.get("error.empty.reservaList"));
			return r;
		} finally {
			session.close();
		}
	}

	public static Reserva getReservaByID(Integer id) {
		// I use this to fetch a single reservation by ID or throw a not-found error
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Reserva r where r.reservaId = :id";
			Reserva r = session.createQuery(hql, Reserva.class).setParameter("id", id).uniqueResult();
			if (r == null)
				throw new IllegalArgumentException(LanguageUtils.get("error.reserva.notFound"));
			return r;
		} finally {
			session.close();
		}
	}

	public static void updateReserva(Reserva reserva) {
		// Simple update of an existing reservation using merge, with not-found check
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Reserva r = session.get(Reserva.class, reserva.getReservaId());
			if (r == null)
				throw new IllegalArgumentException(LanguageUtils.get("error.reserva.notFound"));
			session.merge(reserva);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static void createReserva(Reserva reserva) {
		// Create and persist a new reservation, used from ReservationController
		Session session = HibernateUtils.getSession().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(reserva);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
