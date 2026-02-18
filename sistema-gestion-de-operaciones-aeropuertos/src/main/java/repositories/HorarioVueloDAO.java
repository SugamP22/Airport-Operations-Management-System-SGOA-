package repositories;

import java.util.List;

import org.hibernate.Session;

import entities.HorarioVuelo;
import config.HibernateUtils;

public class HorarioVueloDAO {
	public static HorarioVuelo getSchedulesbyFlight(String numeroVuelo) {
		// I use this to get the schedule for a given flight number (or null if none)
		Session session = HibernateUtils.getSession().openSession();
		try {
			HorarioVuelo horario = session.get(HorarioVuelo.class, numeroVuelo);
			return horario;
		} finally {
			session.close();
		}

	}

	public static List<HorarioVuelo> getAllSchedules() {
		// Helper I use to list all schedules when I need to show available options
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From HorarioVuelo";
			List<HorarioVuelo> horario = session.createQuery(hql, HorarioVuelo.class).list();
			return horario;
		} finally {
			session.close();
		}

	}

}
