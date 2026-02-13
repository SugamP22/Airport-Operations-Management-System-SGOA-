package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "avion")
public class Avion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "avion_id")
	private Integer avionId;

	@Column(name = "capacidad", nullable = false)
	private int capacidad;

	@ManyToOne
	@JoinColumn(name = "tipo_id", nullable = false)
	private TipoAvion tipoAvion;

	@ManyToOne
	@JoinColumn(name = "aerolinea_id", nullable = false)
	private Aerolinea aerolinea;

	@OneToMany(mappedBy = "avion")
	private List<Vuelo> listaVuelo;
}
