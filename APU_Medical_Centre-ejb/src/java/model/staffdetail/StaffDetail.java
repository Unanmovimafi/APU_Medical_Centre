/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.staffdetail;

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
 * @author khong
 */
@Entity
@Table(name = "staff_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffDetail.findAll", query = "SELECT s FROM StaffDetail s"),
    @NamedQuery(name = "StaffDetail.findById", query = "SELECT s FROM StaffDetail s WHERE s.id = :id"),
    @NamedQuery(name = "StaffDetail.findByVersionTime", query = "SELECT s FROM StaffDetail s WHERE s.versionTime = :versionTime"),
    @NamedQuery(name = "StaffDetail.findByCreationDatetime", query = "SELECT s FROM StaffDetail s WHERE s.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "StaffDetail.findByCreateBy", query = "SELECT s FROM StaffDetail s WHERE s.createBy = :createBy"),
    @NamedQuery(name = "StaffDetail.findByLastUpadteDatetime", query = "SELECT s FROM StaffDetail s WHERE s.lastUpadteDatetime = :lastUpadteDatetime"),
    @NamedQuery(name = "StaffDetail.findByLastUpdateBy", query = "SELECT s FROM StaffDetail s WHERE s.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "StaffDetail.findByEmail", query = "SELECT s FROM StaffDetail s WHERE s.email = :email"),
    @NamedQuery(name = "StaffDetail.findByName", query = "SELECT s FROM StaffDetail s WHERE s.name = :name"),
    @NamedQuery(name = "StaffDetail.findByDateOfBirth", query = "SELECT s FROM StaffDetail s WHERE s.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "StaffDetail.findByPhoneNumber", query = "SELECT s FROM StaffDetail s WHERE s.phoneNumber = :phoneNumber")})
public class StaffDetail implements Serializable {

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
    @Column(name = "LAST_UPADTE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpadteDatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Size(max = 255)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Lob
    @Size(max = 16777215)
    @Column(name = "PROFILE_PICTURE")
    private String profilePicture;

    public StaffDetail() {
    }

    public StaffDetail(Integer id) {
        this.id = id;
    }

    public StaffDetail(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpadteDatetime, String lastUpdateBy) {
        this.id = id;
        this.versionTime = versionTime;
        this.creationDatetime = creationDatetime;
        this.createBy = createBy;
        this.lastUpadteDatetime = lastUpadteDatetime;
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

    public Date getLastUpadteDatetime() {
        return lastUpadteDatetime;
    }

    public void setLastUpadteDatetime(Date lastUpadteDatetime) {
        this.lastUpadteDatetime = lastUpadteDatetime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
        if (!(object instanceof StaffDetail)) {
            return false;
        }
        StaffDetail other = (StaffDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.staffdetail.StaffDetail[ id=" + id + " ]";
    }
    
}
