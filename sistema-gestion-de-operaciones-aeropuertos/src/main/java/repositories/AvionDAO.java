package repositories;

import java.util.List;

import org.hibernate.Session;

import entities.Avion;
import utils.BoxedMessageUtils;
import utils.HibernateUtils;
import utils.LanguageUtils;

public class AvionDAO {

	public static void readAll() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Avion> list = session.createQuery("From Avion", Avion.class).list();
			if (list.isEmpty()) {
				System.out.println(LanguageUtils.get("error.empty.aeropuerto"));
				return;
			}
			for (Avion avion : list) {
				System.out.println(avion.toString());
				BoxedMessageUtils.horizontalRow("-");
				System.out.println();
			}

		} finally {
			session.close();
		}

	}

	public static Avion getById(Integer id) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			Avion a = session.get(Avion.class, id);
			return a;
		} finally {
			session.close();
		}
	}
}
