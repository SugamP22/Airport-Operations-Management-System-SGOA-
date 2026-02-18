package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtils;
import entities.Pasajero;

public class PasajeroDAO {

	public static List<Pasajero> getAllPasajeros() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Pasajero> pasajeros = session.createQuery("From Pasajero", Pasajero.class).list();
			return pasajeros;
		} finally {
			session.close();
		}
	}

	public static Pasajero getPasajeroById(Integer idPasajero) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			Pasajero p = session.get(Pasajero.class, idPasajero);
			return p;
		} finally {
			session.close();
		}

	}

	public static void createPasajero(Pasajero p) {
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			// Save main passenger
			session.save(p);

			if (p.getDetallesPasajeros() != null) {
				p.getDetallesPasajeros().setPasajero(p);
				p.getDetallesPasajeros().setPasajeroId(p.getPasajeroId());
				session.save(p.getDetallesPasajeros());
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

}
