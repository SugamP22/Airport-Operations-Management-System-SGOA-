package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aeropuertos")
public class Aeropuertos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aeropuerto_id", columnDefinition = "SMALLINT")
	private Integer aeropuertoId;

	@Column(name = "iata", length = 3, nullable = false)
	private String iata;

	@Column(name = "icao", length = 4, nullable = false)
	private String icao;

	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;

	@Column(name = "ciudad", length = 50, nullable = false)
	private String ciudad;

	@Column(name = "pais", length = 50, nullable = false)
	private String pais;

	@Column(name = "latitud", nullable = false)
	private double latitud;

	@Column(name = "longitud", nullable = false)
	private double longitud;

	@Column(name = "geolocalization")
	private String geolocalization;

	@OneToMany(mappedBy = "origen")
	List<Vuelo> ListaOrigenes;

	@OneToMany(mappedBy = "destino")
	List<Vuelo> ListaDestino;

	@OneToMany(mappedBy = "aeropuertos")
	List<Aerolinea> listaAerolinea;

}
