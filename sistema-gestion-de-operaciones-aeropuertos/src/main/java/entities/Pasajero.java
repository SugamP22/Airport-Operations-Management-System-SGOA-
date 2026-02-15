package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pasajero")
public class Pasajero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pasajero_id")
	private Integer pasajeroId;

	@Column(name = "pasaporte", nullable = false)
	private String passport;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellido", nullable = false)
	private String apellido;

	@OneToMany(mappedBy = "pasajero", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Reserva> listReserva;

	@OneToOne(mappedBy = "pasajero", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	private DetallePasajero detallesPasajeros;

	public Integer getPasajeroId() {
		return pasajeroId;
	}

	public void setPasajeroId(Integer pasajeroId) {
		this.pasajeroId = pasajeroId;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
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

	public List<Reserva> getListReserva() {
		return listReserva;
	}

	public void setListReserva(List<Reserva> listReserva) {
		this.listReserva = listReserva;
	}

	public DetallePasajero getDetallesPasajeros() {
		return detallesPasajeros;
	}

	public void setDetallesPasajeros(DetallePasajero detallesPasajeros) {
		this.detallesPasajeros = detallesPasajeros;
	}

	public Pasajero(String passport, String nombre, String apellido, List<Reserva> listReserva,
			DetallePasajero detallesPasajeros) {
		super();
		this.passport = passport;
		this.nombre = nombre;
		this.apellido = apellido;
		this.listReserva = listReserva;
		this.detallesPasajeros = detallesPasajeros;
	}

	public Pasajero(Integer pasajeroId, String passport, String nombre, String apellido, List<Reserva> listReserva,
			DetallePasajero detallesPasajeros) {
		super();
		this.pasajeroId = pasajeroId;
		this.passport = passport;
		this.nombre = nombre;
		this.apellido = apellido;
		this.listReserva = listReserva;
		this.detallesPasajeros = detallesPasajeros;
	}

	public Pasajero() {
		super();
	}

}
