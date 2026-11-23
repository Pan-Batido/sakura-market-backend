package cl.sakuramarket.sakura_market_backend.dto;

public class Registro {
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private DireccionDTO direccion;

    public static class DireccionDTO {
        private String calle;
        private String departamento;
        private String region;
        private String comuna;

        public String getCalle() { return calle; }
        public void setCalle(String calle) { this.calle = calle; }
        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        public String getComuna() { return comuna; }
        public void setComuna(String comuna) { this.comuna = comuna; }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public DireccionDTO getDireccion() { return direccion; }
    public void setDireccion(DireccionDTO direccion) { this.direccion = direccion; }
}