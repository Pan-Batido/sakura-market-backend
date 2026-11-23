package cl.sakuramarket.sakura_market_backend.dto;

import java.util.List;

public class Boletas {

    public static class CrearBoletaRequest {
        private Integer idUsuario;
        private ClienteDTO cliente;
        private DireccionEntregaDTO direccion;
        private List<ItemBoletaDTO> items;
        private Integer total;

        public Integer getIdUsuario() { return idUsuario; }
        public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
        public ClienteDTO getCliente() { return cliente; }
        public void setCliente(ClienteDTO cliente) { this.cliente = cliente; }
        public DireccionEntregaDTO getDireccion() { return direccion; }
        public void setDireccion(DireccionEntregaDTO direccion) { this.direccion = direccion; }
        public List<ItemBoletaDTO> getItems() { return items; }
        public void setItems(List<ItemBoletaDTO> items) { this.items = items; }
        public Integer getTotal() { return total; }
        public void setTotal(Integer total) { this.total = total; }
    }

    public static class ClienteDTO {
        private String nombre;
        private String apellidos;
        private String correo;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
    }

    public static class DireccionEntregaDTO {
        private String calle;
        private String departamento;
        private String region;
        private String comuna;
        private String indicaciones;

        public String getCalle() { return calle; }
        public void setCalle(String calle) { this.calle = calle; }
        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        public String getComuna() { return comuna; }
        public void setComuna(String comuna) { this.comuna = comuna; }
        public String getIndicaciones() { return indicaciones; }
        public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    }

    public static class ItemBoletaDTO {
        private Integer id;
        private Integer cantidad;
        private Integer precio;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public Integer getPrecio() { return precio; }
        public void setPrecio(Integer precio) { this.precio = precio; }
    }
}