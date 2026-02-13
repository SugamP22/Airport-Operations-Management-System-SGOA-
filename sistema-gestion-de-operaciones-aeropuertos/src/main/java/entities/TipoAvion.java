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

}
