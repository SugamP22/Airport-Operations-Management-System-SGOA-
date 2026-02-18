package repositories;

import java.util.List;

import org.hibernate.Session;

import entities.Aeropuertos;
import config.HibernateUtils;
import utils.LanguageUtils;
import utils.TablePrinter;

public class AeropuertosDAO {

	public static void readAll() {
		// I use this to print all airports so the user can see the available IDs
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Aeropuertos> list = session.createQuery("From Aeropuertos", Aeropuertos.class).list();
			if (list.isEmpty()) {
				throw new IllegalArgumentException(LanguageUtils.get("error.empty.aeropuerto"));
			}
			TablePrinter tp = new TablePrinter().headers("ID", "IATA", "ICAO", "Name", "City", "Country");
			for (Aeropuertos a : list) {
				tp.row(
						a.getAeropuertoId() != null ? a.getAeropuertoId().toString() : "",
						a.getIata() != null ? a.getIata() : "",
						a.getIcao() != null ? a.getIcao() : "",
						a.getNombre() != null ? a.getNombre() : "",
						a.getCiudad() != null ? a.getCiudad() : "",
						a.getPais() != null ? a.getPais() : "");
			}
			tp.print();

		} finally {
			session.close();
		}

	}

	public static Aeropuertos getById(Integer id) {
		// Simple helper to fetch one airport by its primary key
		Session session = HibernateUtils.getSession().openSession();
		try {
			Aeropuertos a = session.get(Aeropuertos.class, id);
			return a;
		} finally {
			session.close();
		}
	}

}
