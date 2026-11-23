package cl.sakuramarket.sakura_market_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boleta")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    private Integer id;

    @Column(name = "numero_orden", nullable = false, unique = true, length = 30)
    private String numeroOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "apellidos_cliente", nullable = false, length = 100)
    private String apellidosCliente;

    @Column(name = "correo_cliente", nullable = false, length = 255)
    private String correoCliente;

    @Column(name = "calle_entrega", nullable = false, length = 150)
    private String calleEntrega;

    @Column(name = "depto_entrega", length = 50)
    private String deptoEntrega;

    @Column(name = "region_entrega", nullable = false, length = 100)
    private String regionEntrega;

    @Column(name = "comuna_entrega", nullable = false, length = 100)
    private String comunaEntrega;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false, length = 20)
    private String estado = "PAGADA";

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleBoleta> detalles = new ArrayList<>();

    public Boleta() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNumeroOrden() { return numeroOrden; }
    public void setNumeroOrden(String numeroOrden) { this.numeroOrden = numeroOrden; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getApellidosCliente() { return apellidosCliente; }
    public void setApellidosCliente(String apellidosCliente) { this.apellidosCliente = apellidosCliente; }
    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }
    public String getCalleEntrega() { return calleEntrega; }
    public void setCalleEntrega(String calleEntrega) { this.calleEntrega = calleEntrega; }
    public String getDeptoEntrega() { return deptoEntrega; }
    public void setDeptoEntrega(String deptoEntrega) { this.deptoEntrega = deptoEntrega; }
    public String getRegionEntrega() { return regionEntrega; }
    public void setRegionEntrega(String regionEntrega) { this.regionEntrega = regionEntrega; }
    public String getComunaEntrega() { return comunaEntrega; }
    public void setComunaEntrega(String comunaEntrega) { this.comunaEntrega = comunaEntrega; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    public List<DetalleBoleta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleBoleta> detalles) { this.detalles = detalles; }
}
