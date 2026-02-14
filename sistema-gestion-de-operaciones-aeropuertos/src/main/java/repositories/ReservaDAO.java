package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Reserva;
import utils.HibernateUtils;
import utils.LanguageUtils;

public class ReservaDAO {
	public static void removeReserva(Integer id) {
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
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Reserva r = session.get(Reserva.class, reserva.getReservaId());
			if (r == null)
				throw new IllegalArgumentException(LanguageUtils.get("error.reserva.notFound"));
			session.merge(r);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
