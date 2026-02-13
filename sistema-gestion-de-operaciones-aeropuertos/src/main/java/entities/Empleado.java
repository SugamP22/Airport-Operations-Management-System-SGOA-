package entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "empleado")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empleado_id")
	private Integer empleadoId;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellido", nullable = false)
	private String apellido;

	@Column(name = "fecha_nacimiento", nullable = false)
	private LocalDate fecha_nacimiento;

	@Column(name = "sexo", nullable = false)
	private char sexo;

	@Column(name = "calle", nullable = false)
	private String calle;

	@Column(name = "ciudad", nullable = false)
	private String ciudad;

	@Column(name = "pais", nullable = false)
	private String pais;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "salario", nullable = false)
	private String salario;

	@Enumerated(EnumType.STRING)
	@Column(name = "departamento")
	private Departamento departamento;

	@Column(name = "usuario", nullable = false)
	private String usuario;

	@Column(name = "clave", nullable = false)
	private String clave;

}
