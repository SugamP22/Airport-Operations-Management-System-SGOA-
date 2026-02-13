package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.HorarioVuelo;
import entities.Vuelo;
import utils.HibernateUtils;
import utils.LanguageUtils;

public class VueloDAO {

	public static List<Vuelo> getAllFlights() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Vuelo> vuelos = session.createQuery("From Vuelo", Vuelo.class).list();
			return vuelos;
		} finally {
			session.close();
		}

	}

	public static Vuelo getFLightByID(String id) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where v.numeroVuelo = :id";
			Vuelo v = session.createQuery(hql, Vuelo.class).setParameter("id", id).uniqueResult();
			return v;
		} finally {
			session.close();
		}
	}

	public static void deleteByID(String currentId) {
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Vuelo v = session.get(Vuelo.class, currentId);
			if (v == null) {
				throw new IllegalArgumentException(LanguageUtils.get("error.flight.existence"));
			}
			session.delete(v);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	public static HorarioVuelo getFlightSchedules(String numeroVuelo) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			Vuelo v = session.get(Vuelo.class, numeroVuelo);
			if (v == null) {
				throw new IllegalArgumentException(LanguageUtils.get("error.flight.existence"));
			}
			HorarioVuelo horario = v.getHorarioVuelo();
			return horario;
		} finally {
			session.close();
		}

	}

}
