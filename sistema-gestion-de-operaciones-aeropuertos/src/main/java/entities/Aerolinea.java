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

	public Integer getAerolineaId() {
		return aerolineaId;
	}

	public void setAerolineaId(Integer aerolineaId) {
		this.aerolineaId = aerolineaId;
	}

	public String getIatachar() {
		return iatachar;
	}

	public void setIatachar(String iatachar) {
		this.iatachar = iatachar;
	}

	public String getNombreAerolinea() {
		return nombreAerolinea;
	}

	public void setNombreAerolinea(String nombreAerolinea) {
		this.nombreAerolinea = nombreAerolinea;
	}

	public Aeropuertos getAeropuertos() {
		return aeropuertos;
	}

	public void setAeropuertos(Aeropuertos aeropuertos) {
		this.aeropuertos = aeropuertos;
	}

	public List<Avion> getListaAvion() {
		return listaAvion;
	}

	public void setListaAvion(List<Avion> listaAvion) {
		this.listaAvion = listaAvion;
	}

	public Aerolinea(Integer aerolineaId, String iatachar, String nombreAerolinea, Aeropuertos aeropuertos,
			List<Avion> listaAvion) {
		super();
		this.aerolineaId = aerolineaId;
		this.iatachar = iatachar;
		this.nombreAerolinea = nombreAerolinea;
		this.aeropuertos = aeropuertos;
		this.listaAvion = listaAvion;
	}

	public Aerolinea(String iatachar, String nombreAerolinea, Aeropuertos aeropuertos, List<Avion> listaAvion) {
		super();
		this.iatachar = iatachar;
		this.nombreAerolinea = nombreAerolinea;
		this.aeropuertos = aeropuertos;
		this.listaAvion = listaAvion;
	}

	public Aerolinea() {
		super();
	}

}
