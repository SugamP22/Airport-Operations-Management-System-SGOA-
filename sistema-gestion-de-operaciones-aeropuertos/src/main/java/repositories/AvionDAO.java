package repositories;

import java.util.List;

import org.hibernate.Session;

import entities.Avion;
import config.HibernateUtils;
import utils.LanguageUtils;
import utils.TablePrinter;

public class AvionDAO {

	public static void readAll() {
		// I use this to print all planes so the user can choose one by ID later
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Avion> list = session.createQuery("From Avion", Avion.class).list();
			if (list.isEmpty()) {
				System.out.println(LanguageUtils.get("error.empty.aeropuerto"));
				return;
			}
			TablePrinter tp = new TablePrinter().headers("ID", "Capacity", "Type", "Airline");
			for (Avion avion : list) {
				String tipo = avion.getTipoAvion() != null ? avion.getTipoAvion().getIdentificador() : "";
				String airline = avion.getAerolinea() != null ? avion.getAerolinea().getNombreAerolinea() : "";
				tp.row(
						avion.getAvionId() != null ? avion.getAvionId().toString() : "",
						String.valueOf(avion.getCapacidad()),
						tipo,
						airline);
			}
			tp.print();

		} finally {
			session.close();
		}

	}

	public static Avion getById(Integer id) {
		// Simple helper to fetch one plane by its primary key
		Session session = HibernateUtils.getSession().openSession();
		try {
			Avion a = session.get(Avion.class, id);
			return a;
		} finally {
			session.close();
		}
	}
}
