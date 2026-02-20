package controllers;

import java.time.LocalDate;
import java.util.List;

import authService.CurrentUser;
import entities.Departamento;
import entities.Empleado;
import repositories.EmpleadoDAO;
import utils.BoxedMessageUtils;
import utils.LanguageUtils;
import utils.TablePrinter;
import utils.ValidationUtils;

/**
 * Class used by administrators to manage Employee
 */
public class EmployeeCRUDController {
	private boolean empty;

	private Integer employeeID;

	public void createEmployee() {
		String nombre = ValidationUtils.readString(LanguageUtils.get("empleado.input.nombre"));
		String apellido = ValidationUtils.readString(LanguageUtils.get("empleado.input.apellido"));
		LocalDate dated = ValidationUtils.readLocalDate(LanguageUtils.get("empleado.input.fechaNacimiento"));
		char sexo = ValidationUtils.readSexo(LanguageUtils.get("empleado.input.sexo"));
		String calle = ValidationUtils.readString(LanguageUtils.get("empleado.input.calle"));
		String ciudad = ValidationUtils.readString(LanguageUtils.get("empleado.input.ciudad"));
		String pais = ValidationUtils.readString(LanguageUtils.get("empleado.input.pais"));
		String email = ValidationUtils.readEmail(LanguageUtils.get("empleado.input.email"));
		String telefono = ValidationUtils.readTelefono(LanguageUtils.get("empleado.input.telefono"));
		double salario = ValidationUtils.readDouble(LanguageUtils.get("empleado.input.salario"));
		Departamento departmento = ValidationUtils.readDepartmento(LanguageUtils.get("empleado.input.departamento"));
		String usuario = ValidationUtils.readString(LanguageUtils.get("empleado.input.usuario"));
		String clave = ValidationUtils.readClaveWithConfirm(LanguageUtils.get("empleado.input.clave"),
				LanguageUtils.get("empleado.input.clave.confirm"));
		Empleado empledo = new Empleado(nombre, apellido, dated, sexo, calle, ciudad, pais, email, telefono, salario,
				departmento, usuario, clave);
		try {
			EmpleadoDAO.createEmpleado(empledo);
			System.out.println(LanguageUtils.get("empleado.create.success"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(LanguageUtils.get("empleado.create.error"));

		}
	}

	public void readAll() {
		empty = false;
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("empleado.all.title"), "=");
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();
		try {
			List<Empleado> list = EmpleadoDAO.getAllEmpleado();
			TablePrinter tp = new TablePrinter().headers("ID", "Name", "Surname", "Dept", "User", "Salary");
			for (Empleado empleado : list) {
				String dept = empleado.getDepartamento() != null ? empleado.getDepartamento().name() : "";
				String salary = String.format("%.2f", empleado.getSalario());
				tp.row(empleado.getEmpleadoId() != null ? empleado.getEmpleadoId().toString() : "",
						empleado.getNombre() != null ? empleado.getNombre() : "",
						empleado.getApellido() != null ? empleado.getApellido() : "", dept,
						empleado.getUsuario() != null ? empleado.getUsuario() : "", salary);
			}
			tp.print();
		} catch (Exception e) {
			empty = true;
			System.out.println(e.getMessage());

		}

	}

	public void modifyEmployee() {
		readAll();
		employeeID = null;
		if (!empty) {
			String username = ValidationUtils.readString(LanguageUtils.get("empleado.modify.username"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			try {
				Empleado e = EmpleadoDAO.getEmpleadoByUsername(username);
				if (e == null) {
					System.out.println(LanguageUtils.get("error.empleado.notFound"));
					return;
				}
				System.out.println(LanguageUtils.get("empleado.found"));
				BoxedMessageUtils.horizontalRow("*");
				System.out.println();
				employeeID = e.getEmpleadoId();

				String email = ValidationUtils.readEmail(LanguageUtils.get("empleado.modify.email"));
				e.setEmail(email);
				String telefono = ValidationUtils.readTelefono(LanguageUtils.get("empleado.modify.telefono"));
				e.setTelefono(telefono);
				double salario = ValidationUtils.readDouble(LanguageUtils.get("empleado.modify.salario"));
				e.setSalario(salario);
				if (!employeeID.equals(CurrentUser.empleado.getEmpleadoId())) {
					Departamento departmento = ValidationUtils
							.readDepartmento(LanguageUtils.get("empleado.modify.departamento"));
					e.setDepartamento(departmento);
				}
				EmpleadoDAO.updateEmpleado(e);
				System.out.println(LanguageUtils.get("empleado.modify.success"));

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void searchEmployee() {
		readAll();
		employeeID = null;
		if (!empty) {
			Departamento departamento = ValidationUtils
					.readDepartmento(LanguageUtils.get("empleado.search.departamento"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			try {
				List<Empleado> list = EmpleadoDAO.getEmpleadoByDepartamento(departamento);
				if (list.isEmpty()) {
					System.out.println(LanguageUtils.get("error.empleado.notFound"));
					return;
				}
				System.out.println(LanguageUtils.get("empleado.found"));
				System.out.println();
				TablePrinter tp = new TablePrinter().headers("ID", "Name", "Surname", "Dept", "User", "Salary");
				for (Empleado empleado : list) {
					String dept = empleado.getDepartamento() != null ? empleado.getDepartamento().name() : "";
					String salary = String.format("%.2f", empleado.getSalario());
					tp.row(empleado.getEmpleadoId() != null ? empleado.getEmpleadoId().toString() : "",
							empleado.getNombre() != null ? empleado.getNombre() : "",
							empleado.getApellido() != null ? empleado.getApellido() : "", dept,
							empleado.getUsuario() != null ? empleado.getUsuario() : "", salary);
				}
				tp.print();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void removeEmployee() {
		readAll();
		employeeID = null;
		if (!empty) {
			String username = ValidationUtils.readString(LanguageUtils.get("empleado.delete.username"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			try {
				Empleado e = EmpleadoDAO.getEmpleadoByUsername(username);
				if (e == null) {
					System.out.println(LanguageUtils.get("error.empleado.notFound"));
					return;
				}
				System.out.println(LanguageUtils.get("empleado.found"));
				System.out.println();
				System.out.println(e.toString());
				employeeID = e.getEmpleadoId();
				BoxedMessageUtils.horizontalRow("*");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println();
			if (employeeID != null) {
				char letra = ValidationUtils.readChar(LanguageUtils.get("empleado.delete.confirm"));
				BoxedMessageUtils.horizontalRow("-");
				System.out.println();
				char upper = Character.toUpperCase(letra);
				// Accept 'S' (Si) and 'Y' (Yes) as confirmation
				if (upper == 'S' || upper == 'Y') {
					try {
						EmpleadoDAO.removeEmpleado(employeeID);
						System.out.println(LanguageUtils.get("empleado.delete.success"));
						return;
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println(LanguageUtils.get("empleado.delete.error"));

					}
				}
				System.out.println(LanguageUtils.get("empleado.delete.cancel"));
			}
		}
	}
}
