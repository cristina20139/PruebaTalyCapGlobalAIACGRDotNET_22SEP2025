package com.clinicos.backend.api.rest.domain.model;

/**
 * Representa un cliente en el sistema.
 * <p>
 * Esta clase pertenece a la capa de dominio y define la entidad Cliente
 * con sus atributos y métodos de acceso (getters/setters).
 * <p>
 * Principios aplicados:
 * <ul>
 *   <li><b>Single Responsibility Principle (SRP):</b> esta clase solo representa la información de un cliente.</li>
 *   <li><b>Encapsulamiento:</b> los atributos son privados y se acceden mediante getters y setters.</li>
 *   <li><b>Clean Code:</b> nombres claros y consistentes para atributos y métodos.</li>
 *   <li><b>Arquitectura Hexagonal:</b> esta entidad no depende de infraestructura ni frameworks, solo define el modelo de dominio.</li>
 * </ul>
 * <p>
 * Se recomienda que las operaciones de negocio sobre clientes se manejen en la capa de servicio,
 * mientras que esta clase solo contiene datos y comportamientos relacionados con la representación del cliente.
 *
 * @author Aura
 * Cristina Garzon Rodriguez
 * @since 23 Sep 2025
 */
public class Cliente {

    private String tipoDocumento;
    private long numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private String direccion;
    private String ciudadResidencia;

    /**
     * Constructor completo para crear un cliente con todos sus atributos.
     *
     * @param tipoDocumento Tipo de documento (CC, CE, Pasaporte, etc.)
     * @param numeroDocumento Número de documento del cliente
     * @param primerNombre Primer nombre del cliente
     * @param segundoNombre Segundo nombre del cliente
     * @param primerApellido Primer apellido del cliente
     * @param segundoApellido Segundo apellido del cliente
     * @param telefono Teléfono de contacto
     * @param direccion Dirección de residencia
     * @param ciudadResidencia Ciudad de residencia
     */
    public Cliente(String tipoDocumento, long numeroDocumento, String primerNombre, String segundoNombre,
                   String primerApellido, String segundoApellido, String telefono, String direccion,
                   String ciudadResidencia) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudadResidencia = ciudadResidencia;
    }

    // Getters y Setters con documentación clara

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }
}
