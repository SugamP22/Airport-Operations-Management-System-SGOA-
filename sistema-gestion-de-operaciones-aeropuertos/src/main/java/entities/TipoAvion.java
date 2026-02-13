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
@Table(name = "tipo_avion")
public class TipoAvion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tipo_id")
	private Integer tipoID;

	@Column(name = "identificador", length = 50, nullable = false)
	private String identificador;

	@Column(name = "descripcion")
	private String description;

	@OneToMany(mappedBy = "tipoAvion")
	List<Avion> listaAvions;

	public Integer getTipoID() {
		return tipoID;
	}

	public void setTipoID(Integer tipoID) {
		this.tipoID = tipoID;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Avion> getListaAvions() {
		return listaAvions;
	}

	public void setListaAvions(List<Avion> listaAvions) {
		this.listaAvions = listaAvions;
	}

	public TipoAvion(Integer tipoID, String identificador, String description, List<Avion> listaAvions) {
		super();
		this.tipoID = tipoID;
		this.identificador = identificador;
		this.description = description;
		this.listaAvions = listaAvions;
	}

	public TipoAvion(String identificador, String description, List<Avion> listaAvions) {
		super();
		this.identificador = identificador;
		this.description = description;
		this.listaAvions = listaAvions;
	}

	public TipoAvion() {
		super();
	}

}
