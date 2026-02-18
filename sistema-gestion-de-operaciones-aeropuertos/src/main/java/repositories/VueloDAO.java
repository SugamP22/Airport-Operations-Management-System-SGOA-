package repositories;

/**
 * DAO I use to access and manage flights (Vuelo) and their schedules
 * (HorarioVuelo) with Hibernate.
 */

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.HorarioVuelo;
import entities.Vuelo;
import config.HibernateUtils;
import utils.LanguageUtils;

public class VueloDAO {

	public static List<Vuelo> getAllFlights() {
		// I use this to get a list of all flights for listing and filters
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

	public static void createFlight(Vuelo vuelo) {
		// Create a new flight and also persist its HorarioVuelo if present
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Vuelo existing = session.get(Vuelo.class, vuelo.getNumeroVuelo());
			if (existing != null) {
				throw new IllegalArgumentException(LanguageUtils.get("error.flight.existence"));
			}
			// Save main flight
			session.save(vuelo);

			// Ensure HorarioVuelo is also persisted and linked
			HorarioVuelo horario = vuelo.getHorarioVuelo();
			if (horario != null) {
				horario.setVuelo(vuelo);
				horario.setNumeroVuelo(vuelo.getNumeroVuelo());
				session.save(horario);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static void updateFlight(Vuelo vuelo) {
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Vuelo existing = session.get(Vuelo.class, vuelo.getNumeroVuelo());
			if (existing == null) {
				throw new IllegalArgumentException(LanguageUtils.get("error.flight.existence"));
			}
			session.merge(vuelo);
			HorarioVuelo horario = vuelo.getHorarioVuelo();
			if (horario != null) {
				HorarioVuelo horarioExistente = session.get(HorarioVuelo.class, vuelo.getNumeroVuelo());
				if (horarioExistente == null) {
					horario.setVuelo(vuelo);
					horario.setNumeroVuelo(vuelo.getNumeroVuelo());
					session.save(horario);
				} else {
					horarioExistente.setSalida(horario.getSalida());
					horarioExistente.setLlegada(horario.getLlegada());
					session.merge(horarioExistente);
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
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

	public static List<Vuelo> getFlightsByAirline(String airline) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where lower(v.avion.aerolinea.nombreAerolinea) = :airline "
					+ "or lower(v.avion.aerolinea.iatachar) = :airline";
			return session.createQuery(hql, Vuelo.class).setParameter("airline", airline.toLowerCase()).list();
		} finally {
			session.close();
		}
	}

	public static List<Vuelo> getFlightsWithoutAirplane() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where v.avion is null";
			return session.createQuery(hql, Vuelo.class).list();
		} finally {
			session.close();
		}
	}

	public static List<Vuelo> getFlightsByDestination(String destination) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where lower(v.destino.nombre) = :destination "
					+ "or lower(v.destino.iata) = :destination or lower(v.destino.icao) = :destination";
			return session.createQuery(hql, Vuelo.class).setParameter("destination", destination.toLowerCase()).list();
		} finally {
			session.close();
		}
	}

	public static List<Vuelo> getFlightsByOrigin(String origin) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where lower(v.origen.nombre) = :origin "
					+ "or lower(v.origen.iata) = :origin or lower(v.origen.icao) = :origin";
			return session.createQuery(hql, Vuelo.class).setParameter("origin", origin.toLowerCase()).list();
		} finally {
			session.close();
		}
	}

	public static List<Vuelo> getFlightsByOperatingDay(String day) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String normalized = day.toLowerCase();
			String hql;
			switch (normalized) {
			case "lunes":
				hql = "From Vuelo v where v.lunes = true";
				break;
			case "martes":
				hql = "From Vuelo v where v.martes = true";
				break;
			case "miercoles":
			case "miércoles":
				hql = "From Vuelo v where v.miercoles = true";
				break;
			case "jueves":
				hql = "From Vuelo v where v.jueves = true";
				break;
			case "viernes":
				hql = "From Vuelo v where v.viernes = true";
				break;
			case "sabado":
			case "sábado":
				hql = "From Vuelo v where v.sabado = true";
				break;
			case "domingo":
				hql = "From Vuelo v where v.domingo = true";
				break;
			default:
				throw new IllegalArgumentException(LanguageUtils.get("error.invalid.day"));
			}
			return session.createQuery(hql, Vuelo.class).list();
		} finally {
			session.close();
		}
	}

	public static List<Vuelo> getFlightsByDayAndDestination(String day, String destination) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String normalized = day.toLowerCase();
			String dayCondition;
			switch (normalized) {
			case "lunes":
				dayCondition = "v.lunes = true";
				break;
			case "martes":
				dayCondition = "v.martes = true";
				break;
			case "miercoles":
			case "miércoles":
				dayCondition = "v.miercoles = true";
				break;
			case "jueves":
				dayCondition = "v.jueves = true";
				break;
			case "viernes":
				dayCondition = "v.viernes = true";
				break;
			case "sabado":
			case "sábado":
				dayCondition = "v.sabado = true";
				break;
			case "domingo":
				dayCondition = "v.domingo = true";
				break;
			default:
				throw new IllegalArgumentException(LanguageUtils.get("error.invalid.day"));
			}
			String hql = "From Vuelo v where " + dayCondition + " and "
					+ "(lower(v.destino.nombre) = :destination or lower(v.destino.iata) = :destination "
					+ "or lower(v.destino.icao) = :destination)";
			return session.createQuery(hql, Vuelo.class).setParameter("destination", destination.toLowerCase()).list();
		} finally {
			session.close();
		}
	}

}
