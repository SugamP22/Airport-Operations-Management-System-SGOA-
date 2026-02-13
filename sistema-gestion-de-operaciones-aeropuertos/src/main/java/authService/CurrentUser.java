package authService;

import entities.Empleado;

public class CurrentUser {
	private Empleado empleado;

	public CurrentUser(Empleado empleado) {
		this.empleado = empleado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

}
