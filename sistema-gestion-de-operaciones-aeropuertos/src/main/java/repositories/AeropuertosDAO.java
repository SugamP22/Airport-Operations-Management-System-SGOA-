package repositories;

import java.util.List;

import org.hibernate.Session;

import entities.Aeropuertos;
import utils.BoxedMessageUtils;
import config.HibernateUtils;
import utils.LanguageUtils;

public class AeropuertosDAO {

	public static void readAll() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Aeropuertos> list = session.createQuery("From Aeropuertos", Aeropuertos.class).list();
			if (list.isEmpty()) {
				throw new IllegalArgumentException(LanguageUtils.get("error.empty.aeropuerto"));
			}
			for (Aeropuertos aeropuertos : list) {
				System.out.println(aeropuertos.toString());
				BoxedMessageUtils.horizontalRow("-");
				System.out.println();
			}

		} finally {
			session.close();
		}

	}

	public static Aeropuertos getById(Integer id) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			Aeropuertos a = session.get(Aeropuertos.class, id);
			return a;
		} finally {
			session.close();
		}
	}

}
