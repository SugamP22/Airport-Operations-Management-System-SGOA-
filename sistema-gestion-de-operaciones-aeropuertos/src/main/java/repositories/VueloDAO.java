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

	/**
	 * Returns the schedule associated with a specific flight id.
	 * 
	 * I load the Vuelo first to reuse the existing mapping instead of querying
	 * HorarioVuelo directly, and I reuse the generic "flight not found" message when
	 * the id does not exist.
	 */
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

	/**
	 * Lists flights filtered by airline name.
	 * 
	 * I only compare against the airline name (case-insensitive) because in the UI
	 * I ask the user for the airline name, not the IATA code.
	 */
	public static List<Vuelo> getFlightsByAirline(String airline) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where lower(v.avion.aerolinea.nombreAerolinea) = :airline";
			return session.createQuery(hql, Vuelo.class).setParameter("airline", airline.toLowerCase()).list();
		} finally {
			session.close();
		}
	}

	/**
	 * Lists all flights that still do not have an airplane assigned.
	 */
	public static List<Vuelo> getFlightsWithoutAirplane() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Vuelo v where v.avion is null";
			return session.createQuery(hql, Vuelo.class).list();
		} finally {
			session.close();
		}
	}

	/**
	 * Lists flights by destination, allowing search by name, IATA or ICAO.
	 * 
	 * I normalize the user input to lowercase so I can compare against the three
	 * destination fields without worrying about case.
	 */
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

	/**
	 * Lists flights by origin airport (name, IATA or ICAO).
	 */
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

	/**
	 * Lists flights by operating day.
	 * 
	 * I accept both Spanish and English day names (e.g. lunes/monday) and map them
	 * to the corresponding boolean flags on the Vuelo entity.
	 */
	public static List<Vuelo> getFlightsByOperatingDay(String day) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String normalized = day.toLowerCase();
			String hql;
			switch (normalized) {
			case "lunes":
			case "monday":
				hql = "From Vuelo v where v.lunes = true";
				break;
			case "martes":
			case "tuesday":
				hql = "From Vuelo v where v.martes = true";
				break;
			case "miercoles":
			case "miércoles":
			case "wednesday":
				hql = "From Vuelo v where v.miercoles = true";
				break;
			case "jueves":
			case "thursday":
				hql = "From Vuelo v where v.jueves = true";
				break;
			case "viernes":
			case "friday":
				hql = "From Vuelo v where v.viernes = true";
				break;
			case "sabado":
			case "sábado":
			case "saturday":
				hql = "From Vuelo v where v.sabado = true";
				break;
			case "domingo":
			case "sunday":
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

	/**
	 * Lists flights by operating day and destination together.
	 * 
	 * I build the day condition first using the same mapping as
	 * getFlightsByOperatingDay and then combine it with the destination filters
	 * (name, IATA or ICAO).
	 */
	public static List<Vuelo> getFlightsByDayAndDestination(String day, String destination) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String normalized = day.toLowerCase();
			String dayCondition;
			switch (normalized) {
			case "lunes":
			case "monday":
				dayCondition = "v.lunes = true";
				break;
			case "martes":
			case "tuesday":
				dayCondition = "v.martes = true";
				break;
			case "miercoles":
			case "miércoles":
			case "wednesday":
				dayCondition = "v.miercoles = true";
				break;
			case "jueves":
			case "thursday":
				dayCondition = "v.jueves = true";
				break;
			case "viernes":
			case "friday":
				dayCondition = "v.viernes = true";
				break;
			case "sabado":
			case "sábado":
			case "saturday":
				dayCondition = "v.sabado = true";
				break;
			case "domingo":
			case "sunday":
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
