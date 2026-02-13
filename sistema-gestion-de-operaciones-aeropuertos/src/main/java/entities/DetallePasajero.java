package entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

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

}
