package entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalles_pasajero")
public class DetallePasajero {

	@Id
	private Integer pasajeroId;

	@MapsId
	@OneToOne
	@JoinColumn(name = "pasajero_id")
	private Pasajero pasajero;

	@Column(name = "fecha_nacimiento", nullable = false)
	private LocalDate fecha_nacimiento;

	@Column(name = "sexo", nullable = false)
	private char sexo;

	@Column(name = "calle", nullable = false)
	private String calle;

	@Column(name = "ciudad", nullable = false)
	private String ciudad;

	@Column(name = "ciudad_postal", nullable = false)
	private int ciudad_postal;

	@Column(name = "pais", nullable = false)
	private String pais;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "telefono")
	private String telefono;

	public Integer getPasajeroId() {
		return pasajeroId;
	}

	public void setPasajeroId(Integer pasajeroId) {
		this.pasajeroId = pasajeroId;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
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

	public int getCiudad_postal() {
		return ciudad_postal;
	}

	public void setCiudad_postal(int ciudad_postal) {
		this.ciudad_postal = ciudad_postal;
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

	public DetallePasajero(Pasajero pasajero, LocalDate fecha_nacimiento, char sexo, String calle, String ciudad,
			int ciudad_postal, String pais, String email, String telefono) {
		super();
		this.pasajero = pasajero;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
		this.calle = calle;
		this.ciudad = ciudad;
		this.ciudad_postal = ciudad_postal;
		this.pais = pais;
		this.email = email;
		this.telefono = telefono;
	}

	public DetallePasajero(Integer pasajeroId, Pasajero pasajero, LocalDate fecha_nacimiento, char sexo, String calle,
			String ciudad, int ciudad_postal, String pais, String email, String telefono) {
		super();
		this.pasajeroId = pasajeroId;
		this.pasajero = pasajero;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
		this.calle = calle;
		this.ciudad = ciudad;
		this.ciudad_postal = ciudad_postal;
		this.pais = pais;
		this.email = email;
		this.telefono = telefono;
	}

	public DetallePasajero() {
		super();
	}

}
