package entities;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "horario_vuelo")
public class HorarioVuelo {
	@Id
	private String numeroVuelo;

	@MapsId
	@OneToOne
	@JoinColumn(name = "numero_vuelo")
	private Vuelo vuelo;

	@Column(name = "salida", nullable = false)
	private LocalTime salida;

	@Column(name = "llegada", nullable = false)
	private LocalTime llegada;

	@OneToMany(mappedBy = "horarioVuelo")
	private List<Reserva> listaReserva;

}
