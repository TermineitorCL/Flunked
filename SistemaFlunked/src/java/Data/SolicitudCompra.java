/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matia
 */
@Entity
@Table(name = "solicitud_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudCompra.findAll", query = "SELECT s FROM SolicitudCompra s")
    , @NamedQuery(name = "SolicitudCompra.findById", query = "SELECT s FROM SolicitudCompra s WHERE s.id = :id")
    , @NamedQuery(name = "SolicitudCompra.findByNombreVendedor", query = "SELECT s FROM SolicitudCompra s WHERE s.nombreVendedor = :nombreVendedor")
    , @NamedQuery(name = "SolicitudCompra.findByApellidoVendedor", query = "SELECT s FROM SolicitudCompra s WHERE s.apellidoVendedor = :apellidoVendedor")
    , @NamedQuery(name = "SolicitudCompra.findByTelefono", query = "SELECT s FROM SolicitudCompra s WHERE s.telefono = :telefono")
    , @NamedQuery(name = "SolicitudCompra.findByNombreProducto", query = "SELECT s FROM SolicitudCompra s WHERE s.nombreProducto = :nombreProducto")
    , @NamedQuery(name = "SolicitudCompra.findByEstadoProducto", query = "SELECT s FROM SolicitudCompra s WHERE s.estadoProducto = :estadoProducto")
    , @NamedQuery(name = "SolicitudCompra.findByDescripcionProducto", query = "SELECT s FROM SolicitudCompra s WHERE s.descripcionProducto = :descripcionProducto")
    , @NamedQuery(name = "SolicitudCompra.findByUrl", query = "SELECT s FROM SolicitudCompra s WHERE s.url = :url")
    , @NamedQuery(name = "SolicitudCompra.findByPrecioMaximoCompra", query = "SELECT s FROM SolicitudCompra s WHERE s.precioMaximoCompra = :precioMaximoCompra")})
public class SolicitudCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nombre_vendedor")
    private String nombreVendedor;
    @Size(max = 45)
    @Column(name = "apellido_vendedor")
    private String apellidoVendedor;
    @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "estado_producto")
    private String estadoProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion_producto")
    private String descripcionProducto;
    @Size(max = 255)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_maximo_compra")
    private int precioMaximoCompra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudCompraId")
    private Collection<Orden> ordenCollection;

    public SolicitudCompra() {
    }

    public SolicitudCompra(Integer id) {
        this.id = id;
    }

    public SolicitudCompra(Integer id, String nombreProducto, String estadoProducto, String descripcionProducto, int precioMaximoCompra) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.estadoProducto = estadoProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioMaximoCompra = precioMaximoCompra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getApellidoVendedor() {
        return apellidoVendedor;
    }

    public void setApellidoVendedor(String apellidoVendedor) {
        this.apellidoVendedor = apellidoVendedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPrecioMaximoCompra() {
        return precioMaximoCompra;
    }

    public void setPrecioMaximoCompra(int precioMaximoCompra) {
        this.precioMaximoCompra = precioMaximoCompra;
    }

    @XmlTransient
    public Collection<Orden> getOrdenCollection() {
        return ordenCollection;
    }

    public void setOrdenCollection(Collection<Orden> ordenCollection) {
        this.ordenCollection = ordenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudCompra)) {
            return false;
        }
        SolicitudCompra other = (SolicitudCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Data.SolicitudCompra[ id=" + id + " ]";
    }
    
}
