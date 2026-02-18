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
