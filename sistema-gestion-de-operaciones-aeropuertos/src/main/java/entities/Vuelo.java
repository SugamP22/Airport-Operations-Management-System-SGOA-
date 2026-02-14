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
	@JoinColumn(name = "origen", nullable = false)
	private Aeropuertos origen;

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

	public String getNumeroVuelo() {
		return numeroVuelo;
	}

	public void setNumeroVuelo(String numeroVuelo) {
		this.numeroVuelo = numeroVuelo;
	}

	public Aeropuertos getOrigen() {
		return origen;
	}

	public void setOrigen(Aeropuertos origen) {
		this.origen = origen;
	}

	public Aeropuertos getDestino() {
		return destino;
	}

	public void setDestino(Aeropuertos destino) {
		this.destino = destino;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public boolean isLunes() {
		return lunes;
	}

	public void setLunes(boolean lunes) {
		this.lunes = lunes;
	}

	public boolean isMartes() {
		return martes;
	}

	public void setMartes(boolean martes) {
		this.martes = martes;
	}

	public boolean isMiercoles() {
		return miercoles;
	}

	public void setMiercoles(boolean miercoles) {
		this.miercoles = miercoles;
	}

	public boolean isJueves() {
		return jueves;
	}

	public void setJueves(boolean jueves) {
		this.jueves = jueves;
	}

	public boolean isViernes() {
		return viernes;
	}

	public void setViernes(boolean viernes) {
		this.viernes = viernes;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}

	public HorarioVuelo getHorarioVuelo() {
		return horarioVuelo;
	}

	public void setHorarioVuelo(HorarioVuelo horarioVuelo) {
		this.horarioVuelo = horarioVuelo;
	}

	public Vuelo(String numeroVuelo, Aeropuertos origen, Aeropuertos destino, Avion avion, boolean lunes,
			boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado, boolean domingo,
			HorarioVuelo horarioVuelo) {
		super();
		this.numeroVuelo = numeroVuelo;
		this.origen = origen;
		this.destino = destino;
		this.avion = avion;
		this.lunes = lunes;
		this.martes = martes;
		this.miercoles = miercoles;
		this.jueves = jueves;
		this.viernes = viernes;
		this.sabado = sabado;
		this.domingo = domingo;
		this.horarioVuelo = horarioVuelo;
	}

	public Vuelo() {
		super();
	}

	public Vuelo(Aeropuertos origen, Aeropuertos destino, Avion avion, boolean lunes, boolean martes, boolean miercoles,
			boolean jueves, boolean viernes, boolean sabado, boolean domingo, HorarioVuelo horarioVuelo) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.avion = avion;
		this.lunes = lunes;
		this.martes = martes;
		this.miercoles = miercoles;
		this.jueves = jueves;
		this.viernes = viernes;
		this.sabado = sabado;
		this.domingo = domingo;
		this.horarioVuelo = horarioVuelo;
	}

	@Override
	public String toString() {
		String origenNombre = origen != null ? origen.getNombre() : "N/A";
		String destinoNombre = destino != null ? destino.getNombre() : "N/A";
		String avionId = avion != null ? String.valueOf(avion.getAvionId()) : "N/A";
		return "Vuelo [numeroVuelo=" + numeroVuelo + ", origen=" + origenNombre + ", destino=" + destinoNombre
				+ ", avion=" + avionId + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles
				+ ", jueves=" + jueves + ", viernes=" + viernes + ", sabado=" + sabado + ", domingo=" + domingo + "]";
	}

}
