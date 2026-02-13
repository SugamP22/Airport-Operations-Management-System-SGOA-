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

	@OneToMany(mappedBy = "origen")
	List<Vuelo> ListaOrigenes;

	@OneToMany(mappedBy = "destino")
	List<Vuelo> ListaDestino;

	@OneToMany(mappedBy = "aeropuertos")
	List<Aerolinea> listaAerolinea;

	public Integer getAeropuertoId() {
		return aeropuertoId;
	}

	public void setAeropuertoId(Integer aeropuertoId) {
		this.aeropuertoId = aeropuertoId;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public List<Vuelo> getListaOrigenes() {
		return ListaOrigenes;
	}

	public void setListaOrigenes(List<Vuelo> listaOrigenes) {
		ListaOrigenes = listaOrigenes;
	}

	public List<Vuelo> getListaDestino() {
		return ListaDestino;
	}

	public void setListaDestino(List<Vuelo> listaDestino) {
		ListaDestino = listaDestino;
	}

	public List<Aerolinea> getListaAerolinea() {
		return listaAerolinea;
	}

	public void setListaAerolinea(List<Aerolinea> listaAerolinea) {
		this.listaAerolinea = listaAerolinea;
	}

	public Aeropuertos() {
		super();
	}

	public Aeropuertos(Integer aeropuertoId, String iata, String icao, String nombre, String ciudad, String pais,
			double latitud, double longitud, List<Vuelo> listaOrigenes, List<Vuelo> listaDestino,
			List<Aerolinea> listaAerolinea) {
		super();
		this.aeropuertoId = aeropuertoId;
		this.iata = iata;
		this.icao = icao;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.pais = pais;
		this.latitud = latitud;
		this.longitud = longitud;
		ListaOrigenes = listaOrigenes;
		ListaDestino = listaDestino;
		this.listaAerolinea = listaAerolinea;
	}

	public Aeropuertos(String iata, String icao, String nombre, String ciudad, String pais, double latitud,
			double longitud, List<Vuelo> listaOrigenes, List<Vuelo> listaDestino, List<Aerolinea> listaAerolinea) {
		super();
		this.iata = iata;
		this.icao = icao;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.pais = pais;
		this.latitud = latitud;
		this.longitud = longitud;
		ListaOrigenes = listaOrigenes;
		ListaDestino = listaDestino;
		this.listaAerolinea = listaAerolinea;
	}

	@Override
	public String toString() {
		return "Aeropuertos [aeropuertoId=" + aeropuertoId + ", iata=" + iata + ", icao=" + icao + ", nombre=" + nombre
				+ ", ciudad=" + ciudad + ", pais=" + pais + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}

}
