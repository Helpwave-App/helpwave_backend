package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "role" }) })
public class Role {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRole;
	@Column(name = "role",length = 50, nullable = false)
	private String role;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public Role() {
	}
//GETTERS AND SETTERES

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public Role(Long idRole, String role, User user) {
		this.idRole = idRole;
		this.role = role;
		this.user = user;
	}
}