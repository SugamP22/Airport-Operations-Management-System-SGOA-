package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Empleado;
import entities.Reserva;
import utils.HibernateUtils;
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
			Reserva r = session.get(Reserva.class, id);
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

	public static List<Empleado> getAllReserva() {
		Session session = HibernateUtils.getSession().openSession();
		try {
			List<Empleado> r = session.createQuery("From Empleado", Empleado.class).list();
			if (r.isEmpty())
				throw new IllegalArgumentException("error.empty.empleadoList");
			return r;
		} finally {
			session.close();
		}
	}

	public static Empleado getEmpleadoByid(Integer id) {
		Session session = HibernateUtils.getSession().openSession();
		try {
			String hql = "From Empleado r where r.empleadoId = :id";
			Empleado r = session.createQuery(hql, Empleado.class).setParameter("id", id).uniqueResult();
			if (r == null)
				throw new IllegalArgumentException("error.empleado.notFound");
			return r;
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
			session.merge(r);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
