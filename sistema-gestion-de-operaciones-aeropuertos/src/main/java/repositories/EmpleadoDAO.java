package repositories;

import org.hibernate.Session;

import entities.Empleado;
import utils.HibernateUtils;

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
}
