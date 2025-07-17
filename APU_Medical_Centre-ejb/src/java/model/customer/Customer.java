/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.customer;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import model.appointment.Appointment;
import model.codevalue.CodeValue;
import model.comment.Comment;

/**
 *
 * @author zihao
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByVersionTime", query = "SELECT c FROM Customer c WHERE c.versionTime = :versionTime"),
    @NamedQuery(name = "Customer.findByCreationDatetime", query = "SELECT c FROM Customer c WHERE c.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "Customer.findByCreateBy", query = "SELECT c FROM Customer c WHERE c.createBy = :createBy"),
    @NamedQuery(name = "Customer.findByLastUpdateDatetime", query = "SELECT c FROM Customer c WHERE c.lastUpdateDatetime = :lastUpdateDatetime"),
    @NamedQuery(name = "Customer.findByLastUpdateBy", query = "SELECT c FROM Customer c WHERE c.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "Customer.findByUsername", query = "SELECT c FROM Customer c WHERE c.username = :username"),
    @NamedQuery(name = "Customer.findByLastLoginDatetime", query = "SELECT c FROM Customer c WHERE c.lastLoginDatetime = :lastLoginDatetime"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByDateOfBirth", query = "SELECT c FROM Customer c WHERE c.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Customer.findByPhoneNumber", query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Customer.findByBloodType", query = "SELECT c FROM Customer c WHERE c.bloodType = :bloodType")})
public class Customer implements Serializable {

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
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "LAST_LOGIN_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDatetime;
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
    @Lob
    @Size(max = 16777215)
    @Column(name = "ALLERGIC")
    private String allergic;
    @Size(max = 255)
    @Column(name = "BLOOD_TYPE")
    private String bloodType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<Appointment> appointmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<Comment> commentCollection;
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "ID")
    @ManyToOne
    private CodeValue status;

    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }

    public Customer(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpdateDatetime, String lastUpdateBy, String username, String password) {
        this.id = id;
        this.versionTime = versionTime;
        this.creationDatetime = creationDatetime;
        this.createBy = createBy;
        this.lastUpdateDatetime = lastUpdateDatetime;
        this.lastUpdateBy = lastUpdateBy;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(Date lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
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

    public String getAllergic() {
        return allergic;
    }

    public void setAllergic(String allergic) {
        this.allergic = allergic;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    @XmlTransient
    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public CodeValue getStatus() {
        return status;
    }

    public void setStatus(CodeValue status) {
        this.status = status;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "module.Customer[ id=" + id + " ]";
    }
    
}
