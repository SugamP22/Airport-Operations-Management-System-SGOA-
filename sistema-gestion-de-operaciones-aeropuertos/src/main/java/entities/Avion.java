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

	public Integer getAvionId() {
		return avionId;
	}

	public void setAvionId(Integer avionId) {
		this.avionId = avionId;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public TipoAvion getTipoAvion() {
		return tipoAvion;
	}

	public void setTipoAvion(TipoAvion tipoAvion) {
		this.tipoAvion = tipoAvion;
	}

	public Aerolinea getAerolinea() {
		return aerolinea;
	}

	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}

	public List<Vuelo> getListaVuelo() {
		return listaVuelo;
	}

	public void setListaVuelo(List<Vuelo> listaVuelo) {
		this.listaVuelo = listaVuelo;
	}

	public Avion(Integer avionId, int capacidad, TipoAvion tipoAvion, Aerolinea aerolinea, List<Vuelo> listaVuelo) {
		super();
		this.avionId = avionId;
		this.capacidad = capacidad;
		this.tipoAvion = tipoAvion;
		this.aerolinea = aerolinea;
		this.listaVuelo = listaVuelo;
	}

	public Avion(int capacidad, TipoAvion tipoAvion, Aerolinea aerolinea, List<Vuelo> listaVuelo) {
		super();
		this.capacidad = capacidad;
		this.tipoAvion = tipoAvion;
		this.aerolinea = aerolinea;
		this.listaVuelo = listaVuelo;
	}

	public Avion() {
		super();
	}

	@Override
	public String toString() {
		return "Avion [avionId=" + avionId + ", capacidad=" + capacidad + ", tipoAvion=" + tipoAvion + ", aerolinea="
				+ aerolinea + ", listaVuelo=" + listaVuelo + "]";
	}

}
