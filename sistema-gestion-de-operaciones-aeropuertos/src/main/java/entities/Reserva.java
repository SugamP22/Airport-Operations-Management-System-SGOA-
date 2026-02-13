package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {

	@Column(name = "reserva_id", nullable = false)
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

}
