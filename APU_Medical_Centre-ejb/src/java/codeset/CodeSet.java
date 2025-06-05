/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeset;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
 * @author zihao
 */
@Entity
@Table(name = "code_set")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodeSet.findAll", query = "SELECT c FROM CodeSet c"),
    @NamedQuery(name = "CodeSet.findById", query = "SELECT c FROM CodeSet c WHERE c.id = :id"),
    @NamedQuery(name = "CodeSet.findByVersionTime", query = "SELECT c FROM CodeSet c WHERE c.versionTime = :versionTime"),
    @NamedQuery(name = "CodeSet.findByCreationDatetime", query = "SELECT c FROM CodeSet c WHERE c.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "CodeSet.findByCreateBy", query = "SELECT c FROM CodeSet c WHERE c.createBy = :createBy"),
    @NamedQuery(name = "CodeSet.findByLastUpdateDatetime", query = "SELECT c FROM CodeSet c WHERE c.lastUpdateDatetime = :lastUpdateDatetime"),
    @NamedQuery(name = "CodeSet.findByLastUpdateBy", query = "SELECT c FROM CodeSet c WHERE c.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "CodeSet.findByCode", query = "SELECT c FROM CodeSet c WHERE c.code = :code"),
    @NamedQuery(name = "CodeSet.findByStatus", query = "SELECT c FROM CodeSet c WHERE c.status = :status"),
    @NamedQuery(name = "CodeSet.findByName", query = "SELECT c FROM CodeSet c WHERE c.name = :name"),
    @NamedQuery(name = "CodeSet.findByMaintainable", query = "SELECT c FROM CodeSet c WHERE c.maintainable = :maintainable")})
public class CodeSet implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CODE")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Lob
    @Size(max = 16777215)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MAINTAINABLE")
    private String maintainable;

    public CodeSet() {
    }

    public CodeSet(Integer id) {
        this.id = id;
    }

    public CodeSet(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpdateDatetime, String lastUpdateBy, String code, String status, String maintainable) {
        this.id = id;
        this.versionTime = versionTime;
        this.creationDatetime = creationDatetime;
        this.createBy = createBy;
        this.lastUpdateDatetime = lastUpdateDatetime;
        this.lastUpdateBy = lastUpdateBy;
        this.code = code;
        this.status = status;
        this.maintainable = maintainable;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaintainable() {
        return maintainable;
    }

    public void setMaintainable(String maintainable) {
        this.maintainable = maintainable;
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
        if (!(object instanceof CodeSet)) {
            return false;
        }
        CodeSet other = (CodeSet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "codeset.CodeSet[ id=" + id + " ]";
    }
    
}
