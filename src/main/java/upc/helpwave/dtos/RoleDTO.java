package upc.helpwave.dtos;
import upc.helpwave.entities.User;

public class RoleDTO {
    private Long idRole;
    private String role;

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
