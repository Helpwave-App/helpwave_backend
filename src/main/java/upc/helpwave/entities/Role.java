package upc.helpwave.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "role" }) })
public class Role {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRole;

	@Column(name = "role",length = 50, nullable = false)
	private String role;

	@OneToMany(mappedBy = "role")
	@JsonIgnore
	private List<User> users;

	public Role() {
	}

	public Role(String role) {
		this.role = role;
	}

	public Role(Long idRole, String role) {
		this.idRole = idRole;
		this.role = role;
	}
//GETTERS AND SETTERES

	public List<User> getUsers() { return users; }

	public void setUsers(List<User> users) { this.users = users; }

	public String getRole() {
		return role;
	}

	public void setRol(String role) {
		this.role = role;
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}
}