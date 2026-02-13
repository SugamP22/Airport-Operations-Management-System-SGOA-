package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pasejero")
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

	@OneToMany(mappedBy = "pasajero")
	private List<Reserva> listReserva;

	@OneToOne(mappedBy = "pasajero")
	private DetallePasajero detallesPasajeros;

}
