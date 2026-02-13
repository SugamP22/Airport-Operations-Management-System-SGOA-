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

	@Column(name = "salario", precision = 8, scale = 2, nullable = false)
	private double salario;

	@Enumerated(EnumType.STRING)
	@Column(name = "departamento")
	private Departamento departamento;

	@Column(name = "usuario", nullable = false)
	private String usuario;

	@Column(name = "clave", nullable = false)
	private String clave;

	public Empleado(Integer empleadoId, String nombre, String apellido, LocalDate fecha_nacimiento, char sexo,
			String calle, String ciudad, String pais, String email, String telefono, double salario,
			Departamento departamento, String usuario, String clave) {
		super();
		this.empleadoId = empleadoId;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
		this.calle = calle;
		this.ciudad = ciudad;
		this.pais = pais;
		this.email = email;
		this.telefono = telefono;
		this.salario = salario;
		this.departamento = departamento;
		this.usuario = usuario;
		this.clave = clave;
	}

	public Empleado() {
		super();
	}

	public Empleado(String nombre, String apellido, LocalDate fecha_nacimiento, char sexo, String calle, String ciudad,
			String pais, String email, String telefono, double salario, Departamento departamento, String usuario,
			String clave) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
		this.calle = calle;
		this.ciudad = ciudad;
		this.pais = pais;
		this.email = email;
		this.telefono = telefono;
		this.salario = salario;
		this.departamento = departamento;
		this.usuario = usuario;
		this.clave = clave;
	}

	public Integer getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(Integer empleadoId) {
		this.empleadoId = empleadoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
