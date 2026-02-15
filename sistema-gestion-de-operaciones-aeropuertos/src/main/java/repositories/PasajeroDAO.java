package repositories;

import java.util.List;

import org.hibernate.Session;

import entities.Pasajero;
import utils.HibernateUtils;

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

}
