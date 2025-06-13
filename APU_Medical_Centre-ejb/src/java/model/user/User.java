/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

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
import jakarta.persistence.OneToOne;
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
import model.customerdetail.CustomerDetail;
import model.staffdetail.StaffDetail;

/**
 *
 * @author khong
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByVersionTime", query = "SELECT u FROM User u WHERE u.versionTime = :versionTime"),
    @NamedQuery(name = "User.findByCreationDatetime", query = "SELECT u FROM User u WHERE u.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "User.findByCreateBy", query = "SELECT u FROM User u WHERE u.createBy = :createBy"),
    @NamedQuery(name = "User.findByLastUpdateDatetime", query = "SELECT u FROM User u WHERE u.lastUpdateDatetime = :lastUpdateDatetime"),
    @NamedQuery(name = "User.findByLastUpdateBy", query = "SELECT u FROM User u WHERE u.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByLastLoginDatetime", query = "SELECT u FROM User u WHERE u.lastLoginDatetime = :lastLoginDatetime"),
    @NamedQuery(name = "User.findByRoles", query = "SELECT u FROM User u WHERE u.role.code IN :roles AND u.role.status = 'ACTIVE' AND u.role.codeSet.code = :codeSet AND u.role.codeSet.status = 'ACTIVE'")})
public class User implements Serializable {

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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private CustomerDetail customerDetail;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private StaffDetail staffDetail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<Appointment> appointmentCollection;
    @OneToMany(mappedBy = "doctor")
    private Collection<Appointment> appointmentCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<Comment> commentCustomerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetUser")
    private Collection<Comment> commentTargetUserCollection;
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CodeValue role;
    @JoinColumn(name = "USER_STATUS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CodeValue userStatus;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpdateDatetime, String lastUpdateBy, String username, String password) {
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

    
    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public StaffDetail getStaffDetail() {
        return staffDetail;
    }

    public void setStaffDetail(StaffDetail staffDetail) {
        this.staffDetail = staffDetail;
    }

    @XmlTransient
    public Collection<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(Collection<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    @XmlTransient
    public Collection<Appointment> getAppointmentCollection1() {
        return appointmentCollection1;
    }

    public void setAppointmentCollection1(Collection<Appointment> appointmentCollection1) {
        this.appointmentCollection1 = appointmentCollection1;
    }

    @XmlTransient
    public Collection<Comment> getCommentCustomerCollection() {
        return commentCustomerCollection;
    }

    public void setCommentCustomerCollection(Collection<Comment> commentCustomerCollection) {
        this.commentCustomerCollection = commentCustomerCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentTargetUserCollection() {
        return commentTargetUserCollection;
    }

    public void setCommentCollection(Collection<Comment> commentTargetUserCollection) {
        this.commentTargetUserCollection = commentTargetUserCollection;
    }

    public CodeValue getRole() {
        return role;
    }

    public void setRole(CodeValue role) {
        this.role = role;
    }
    
    public CodeValue getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(CodeValue userStatus) {
        this.userStatus = userStatus;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.user.User[ id=" + id + " ]";
    }
    
}
