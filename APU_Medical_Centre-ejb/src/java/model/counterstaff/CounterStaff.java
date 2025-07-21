/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.counterstaff;

import jakarta.persistence.Basic;
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
import model.comment.Comment;

/**
 *
 * @author zihao
 */
@Entity
@Table(name = "counter_staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CounterStaff.findAll", query = "SELECT c FROM CounterStaff c"),
    @NamedQuery(name = "CounterStaff.findById", query = "SELECT c FROM CounterStaff c WHERE c.id = :id"),
    @NamedQuery(name = "CounterStaff.findByVersionTime", query = "SELECT c FROM CounterStaff c WHERE c.versionTime = :versionTime"),
    @NamedQuery(name = "CounterStaff.findByCreationDatetime", query = "SELECT c FROM CounterStaff c WHERE c.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "CounterStaff.findByCreateBy", query = "SELECT c FROM CounterStaff c WHERE c.createBy = :createBy"),
    @NamedQuery(name = "CounterStaff.findByLastUpdateDatetime", query = "SELECT c FROM CounterStaff c WHERE c.lastUpdateDatetime = :lastUpdateDatetime"),
    @NamedQuery(name = "CounterStaff.findByLastUpdateBy", query = "SELECT c FROM CounterStaff c WHERE c.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "CounterStaff.findByUsername", query = "SELECT c FROM CounterStaff c WHERE c.username = :username"),
    @NamedQuery(name = "CounterStaff.findByLastLoginDatetime", query = "SELECT c FROM CounterStaff c WHERE c.lastLoginDatetime = :lastLoginDatetime"),
    @NamedQuery(name = "CounterStaff.findByEmail", query = "SELECT c FROM CounterStaff c WHERE c.email = :email"),
    @NamedQuery(name = "CounterStaff.findByName", query = "SELECT c FROM CounterStaff c WHERE c.name = :name"),
    @NamedQuery(name = "CounterStaff.findByDateOfBirth", query = "SELECT c FROM CounterStaff c WHERE c.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "CounterStaff.findByPhoneNumber", query = "SELECT c FROM CounterStaff c WHERE c.phoneNumber = :phoneNumber")})
public class CounterStaff implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "counterStaff")
    private Collection<Comment> commentCollection;

    public CounterStaff() {
    }

    public CounterStaff(Integer id) {
        this.id = id;
    }

    public CounterStaff(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpdateDatetime, String lastUpdateBy, String username, String password) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
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
        if (!(object instanceof CounterStaff)) {
            return false;
        }
        CounterStaff other = (CounterStaff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "module.CounterStaff[ id=" + id + " ]";
    }
    
}
