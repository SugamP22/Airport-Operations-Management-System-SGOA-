package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserva_id")
	private Integer reservaId;

	@ManyToOne
	@JoinColumn(name = "numero_vuelo", nullable = false)
	private HorarioVuelo horarioVuelo;

	@Column(name = "vuelo_id")
	private int vueloId;

	@Column(name = "asiento", nullable = false)
	private String asiento;

	@ManyToOne
	@JoinColumn(name = "pasajero_id", nullable = false)
	private Pasajero pasajero;

	@Column(name = "precio", nullable = false)
	private double precio;

	public Integer getReservaId() {
		return reservaId;
	}

	public void setReservaId(Integer reservaId) {
		this.reservaId = reservaId;
	}

	public HorarioVuelo getHorarioVuelo() {
		return horarioVuelo;
	}

	public void setHorarioVuelo(HorarioVuelo horarioVuelo) {
		this.horarioVuelo = horarioVuelo;
	}

	public int getVueloId() {
		return vueloId;
	}

	public void setVueloId(int vueloId) {
		this.vueloId = vueloId;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Reserva(Integer reservaId, HorarioVuelo horarioVuelo, int vueloId, String asiento, Pasajero pasajero,
			double precio) {
		super();
		this.reservaId = reservaId;
		this.horarioVuelo = horarioVuelo;
		this.vueloId = vueloId;
		this.asiento = asiento;
		this.pasajero = pasajero;
		this.precio = precio;
	}

	public Reserva() {
		super();
	}

	public Reserva(HorarioVuelo horarioVuelo, int vueloId, String asiento, Pasajero pasajero, double precio) {
		super();
		this.horarioVuelo = horarioVuelo;
		this.vueloId = vueloId;
		this.asiento = asiento;
		this.pasajero = pasajero;
		this.precio = precio;
	}
	

}
