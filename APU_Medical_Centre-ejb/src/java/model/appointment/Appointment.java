/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.appointment;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import model.customer.Customer;
import model.doctor.Doctor;

/**
 *
 * @author khong
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findById", query = "SELECT a FROM Appointment a WHERE a.id = :id"),
    @NamedQuery(name = "Appointment.findByVersionTime", query = "SELECT a FROM Appointment a WHERE a.versionTime = :versionTime"),
    @NamedQuery(name = "Appointment.findByCreationDatetime", query = "SELECT a FROM Appointment a WHERE a.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "Appointment.findByCreateBy", query = "SELECT a FROM Appointment a WHERE a.createBy = :createBy"),
    @NamedQuery(name = "Appointment.findByLastUpdateDatetime", query = "SELECT a FROM Appointment a WHERE a.lastUpdateDatetime = :lastUpdateDatetime"),
    @NamedQuery(name = "Appointment.findByLastUpdateBy", query = "SELECT a FROM Appointment a WHERE a.lastUpdateBy = :lastUpdateBy"),
    @NamedQuery(name = "Appointment.findByAppointmentStartDatetime", query = "SELECT a FROM Appointment a WHERE a.appointmentStartDatetime = :appointmentStartDatetime"),
    @NamedQuery(name = "Appointment.findByAppointmentEndDatetime", query = "SELECT a FROM Appointment a WHERE a.appointmentEndDatetime = :appointmentEndDatetime"),
    @NamedQuery(name = "Appointment.findByCharge", query = "SELECT a FROM Appointment a WHERE a.charge = :charge")})
public class Appointment implements Serializable {

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
    @Column(name = "APPOINTMENT_START_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentStartDatetime;
    @Column(name = "APPOINTMENT_END_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentEndDatetime;
    @Column(name = "CHARGE")
    private Long charge;
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Customer customer;
    @JoinColumn(name = "DOCTOR_ID", referencedColumnName = "ID")
    @ManyToOne
    private Doctor doctor;

    public Appointment() {
    }

    public Appointment(Integer id) {
        this.id = id;
    }

    public Appointment(Integer id, int versionTime, Date creationDatetime, String createBy, Date lastUpdateDatetime, String lastUpdateBy) {
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

    public Date getAppointmentStartDatetime() {
        return appointmentStartDatetime;
    }

    public void setAppointmentStartDatetime(Date appointmentStartDatetime) {
        this.appointmentStartDatetime = appointmentStartDatetime;
    }

    public Date getAppointmentEndDatetime() {
        return appointmentEndDatetime;
    }

    public void setAppointmentEndDatetime(Date appointmentEndDatetime) {
        this.appointmentEndDatetime = appointmentEndDatetime;
    }

    public Long getCharge() {
        return charge;
    }

    public void setCharge(Long charge) {
        this.charge = charge;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.appointment.Appointment[ id=" + id + " ]";
    }
    
}
