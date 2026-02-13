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
@Table(name = "aerolinea")
public class Aerolinea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aerolinea_id")
	private Integer aerolineaId;

	@Column(name = "iata", length = 2, nullable = false)
	private String iatachar;

	@Column(name = "nombre_aerolinea", length = 30, nullable = false)
	private String nombreAerolinea;

	@ManyToOne
	@JoinColumn(name = "aeropuerto_base", nullable = false)
	private Aeropuertos aeropuertos;

	@OneToMany(mappedBy = "aerolinea")
	List<Avion> listaAvion;

}
