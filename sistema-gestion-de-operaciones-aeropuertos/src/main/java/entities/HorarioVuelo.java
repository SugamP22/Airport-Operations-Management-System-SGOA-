package entities;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
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

	@OneToMany(mappedBy = "horarioVuelo", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Reserva> listaReserva;

	public String getNumeroVuelo() {
		return numeroVuelo;
	}

	public void setNumeroVuelo(String numeroVuelo) {
		this.numeroVuelo = numeroVuelo;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}

	public LocalTime getSalida() {
		return salida;
	}

	public void setSalida(LocalTime salida) {
		this.salida = salida;
	}

	public LocalTime getLlegada() {
		return llegada;
	}

	public void setLlegada(LocalTime llegada) {
		this.llegada = llegada;
	}

	public List<Reserva> getListaReserva() {
		return listaReserva;
	}

	public void setListaReserva(List<Reserva> listaReserva) {
		this.listaReserva = listaReserva;
	}

	public HorarioVuelo(String numeroVuelo, Vuelo vuelo, LocalTime salida, LocalTime llegada,
			List<Reserva> listaReserva) {
		super();
		this.numeroVuelo = numeroVuelo;
		this.vuelo = vuelo;
		this.salida = salida;
		this.llegada = llegada;
		this.listaReserva = listaReserva;
	}

	public HorarioVuelo(Vuelo vuelo, LocalTime salida, LocalTime llegada, List<Reserva> listaReserva) {
		super();
		this.vuelo = vuelo;
		this.salida = salida;
		this.llegada = llegada;
		this.listaReserva = listaReserva;
	}

	public HorarioVuelo() {
		super();
	}

	@Override
	public String toString() {
		return "HorarioVuelo [numeroVuelo=" + numeroVuelo + ", salida=" + salida + ", llegada=" + llegada + "]";
	}

}
