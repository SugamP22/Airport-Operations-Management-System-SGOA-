package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vuelo")
public class Vuelo {
	@Id
	@Column(name = "numero_vuelo", length = 8)
	private String numeroVuelo;

	@ManyToOne
	@JoinColumn(name = "origin", nullable = false)
	private Aeropuertos origin;

	@ManyToOne
	@JoinColumn(name = "destino", nullable = false)
	private Aeropuertos destino;

	@ManyToOne
	@JoinColumn(name = "avion_id")
	private Avion avion;

	@Column(name = "lunes", nullable = false)
	private boolean lunes;

	@Column(name = "martes", nullable = false)
	private boolean martes;

	@Column(name = "miercoles", nullable = false)
	private boolean miercoles;

	@Column(name = "jueves", nullable = false)
	private boolean jueves;

	@Column(name = "viernes", nullable = false)
	private boolean viernes;

	@Column(name = "sabado", nullable = false)
	private boolean sabado;

	@Column(name = "domingo", nullable = false)
	private boolean domingo;

	@OneToOne(mappedBy = "vuelo")
	private HorarioVuelo horarioVuelo;
}
