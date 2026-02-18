package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Departamento;
import entities.Empleado;
import config.HibernateUtils;
import utils.LanguageUtils;

public class EmpleadoDAO {
	public Empleado findByUsuarioYClave(String Usuario, String clave) {
		// I use this at login to find one employee by username and hashed password
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Empleado e Where e.usuario = :usuario AND e.clave = :clave";
			Empleado empleado = session.createQuery(hql, Empleado.class).setParameter("usuario", Usuario)
					.setParameter("clave", clave).uniqueResult();
			return empleado;

		} finally {
			session.close();
		}

	}

	public static void removeEmpleado(Integer id) {
		// Delete employee by ID with a not-found check before removing
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Empleado r = session.get(Empleado.class, id);
			if (r == null)
				throw new IllegalArgumentException(LanguageUtils.get("error.empleado.notFound"));
			session.delete(r);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static List<Empleado> getAllEmpleado() {
		// Helper I use to list all employees; throws if there are none
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Empleado> r = session.createQuery("From Empleado", Empleado.class).list();
			if (r.isEmpty())
				throw new IllegalArgumentException(LanguageUtils.get("error.empty.empleadoList"));
			return r;
		} finally {
			session.close();
		}
	}

	public static Empleado getEmpleadoByUsername(String username) {
		// Fetch one employee by username (used in modify/search flows)
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Empleado r where r.usuario = :username";
			Empleado r = session.createQuery(hql, Empleado.class).setParameter("username", username).uniqueResult();
			return r;
		} finally {
			session.close();
		}
	}

	public static List<Empleado> getEmpleadoByDepartamento(Departamento departamento) {
		// Get all employees that belong to a given department
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Empleado e where e.departamento = :departamento";
			List<Empleado> list = session.createQuery(hql, Empleado.class).setParameter("departamento", departamento)
					.list();
			return list;
		} finally {
			session.close();
		}
	}

	public static Empleado createEmpleado(Empleado empleado) {
		// Persist a new employee in a transaction and return the managed instance
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(empleado);
			tx.commit();
			return empleado;
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static void updateEmpleado(Empleado empleado) {
		// Update an existing employee with a simple merge, after checking it exists
		Session session = HibernateUtils.getSession().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Empleado r = session.get(Empleado.class, empleado.getEmpleadoId());
			if (r == null)
				throw new IllegalArgumentException(LanguageUtils.get("error.empleado.notFound"));
			session.merge(empleado);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
