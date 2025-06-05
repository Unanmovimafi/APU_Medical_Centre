/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.appointmentmedicine;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author khong
 */
@Entity
@Table(name = "appointment_medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppointmentMedicine.findAll", query = "SELECT a FROM AppointmentMedicine a"),
    @NamedQuery(name = "AppointmentMedicine.findById", query = "SELECT a FROM AppointmentMedicine a WHERE a.id = :id"),
    @NamedQuery(name = "AppointmentMedicine.findByVersionTime", query = "SELECT a FROM AppointmentMedicine a WHERE a.versionTime = :versionTime"),
    @NamedQuery(name = "AppointmentMedicine.findByCreationDatetime", query = "SELECT a FROM AppointmentMedicine a WHERE a.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "AppointmentMedicine.findByCreateBy", query = "SELECT a FROM AppointmentMedicine a WHERE a.createBy = :createBy"),
    @NamedQuery(name = "AppointmentMedicine.findByLastUpdateDatetime", query = "SELECT a FROM AppointmentMedicine a WHERE a.lastUpdateDatetime = :lastUpdateDatetime"),
    @NamedQuery(name = "AppointmentMedicine.findByLastUpdateBy", query = "SELECT a FROM AppointmentMedicine a WHERE a.lastUpdateBy = :lastUpdateBy")})
public class AppointmentMedicine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION_TIME")
    private int versionTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CREATE_BY")
    private String createBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_UPDATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    public AppointmentMedicine() {
    }

    public AppointmentMedicine(Integer id) {
        this.id = id;
    }

    public AppointmentMedicine(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpdateDatetime, String lastUpdateBy) {
        this.id = id;
        this.versionTime = versionTime;
        this.creationDatetime = creationDatetime;
        this.createBy = createBy;
        this.lastUpdateDatetime = lastUpdateDatetime;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(int versionTime) {
        this.versionTime = versionTime;
    }

    public Date getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Date creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(Date lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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
        if (!(object instanceof AppointmentMedicine)) {
            return false;
        }
        AppointmentMedicine other = (AppointmentMedicine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.appointmentmedicine.AppointmentMedicine[ id=" + id + " ]";
    }
    
}
