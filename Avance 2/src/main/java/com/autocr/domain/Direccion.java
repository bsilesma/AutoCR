package com.autocr.domain;

import jakarta.persistence.*;

/**
 * Direccion guardada de un cliente (H12, Avance 3). La ubicacion
 * (latitud/longitud) se completa mediante Google Places Autocomplete al
 * guardar, como validacion de que la direccion existe realmente.
 */
@Entity
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long idDireccion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "etiqueta", nullable = false, length = 40)
    private String etiqueta;

    @Column(name = "nombre_entrega", nullable = false, length = 150)
    private String nombreEntrega;

    @Column(name = "telefono_entrega", nullable = false, length = 30)
    private String telefonoEntrega;

    @Column(name = "provincia", length = 60)
    private String provincia;

    @Column(name = "canton", length = 60)
    private String canton;

    @Column(name = "distrito", length = 60)
    private String distrito;

    @Column(name = "direccion_exacta", nullable = false, columnDefinition = "TEXT")
    private String direccionExacta;

    @Column(name = "nota_entrega", columnDefinition = "TEXT")
    private String notaEntrega;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "predeterminada", nullable = false)
    private Boolean predeterminada = false;

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getNombreEntrega() {
        return nombreEntrega;
    }

    public void setNombreEntrega(String nombreEntrega) {
        this.nombreEntrega = nombreEntrega;
    }

    public String getTelefonoEntrega() {
        return telefonoEntrega;
    }

    public void setTelefonoEntrega(String telefonoEntrega) {
        this.telefonoEntrega = telefonoEntrega;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccionExacta() {
        return direccionExacta;
    }

    public void setDireccionExacta(String direccionExacta) {
        this.direccionExacta = direccionExacta;
    }

    public String getNotaEntrega() {
        return notaEntrega;
    }

    public void setNotaEntrega(String notaEntrega) {
        this.notaEntrega = notaEntrega;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Boolean getPredeterminada() {
        return predeterminada;
    }

    public void setPredeterminada(Boolean predeterminada) {
        this.predeterminada = predeterminada;
    }
}
