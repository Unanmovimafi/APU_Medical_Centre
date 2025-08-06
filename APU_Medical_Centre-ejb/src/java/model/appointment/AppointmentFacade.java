/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.appointment;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.AbstractFacade;
import model.customer.Customer;
import model.doctor.Doctor;

/**
 *
 * @author khong
 */
@Stateless
public class AppointmentFacade extends AbstractFacade<Appointment> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppointmentFacade() {
        super(Appointment.class);
    }
    
    public List<Appointment> findByStatus(String status) {
        TypedQuery<Appointment> query = em.createNamedQuery("Appointment.findByStatus", Appointment.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    public List<Appointment> findByStatuses(List<String> statuses) {
        return em.createNamedQuery("Appointment.findByMultipleStatus", Appointment.class)
                 .setParameter("statuses", statuses)
                 .getResultList();
    }
    
    public List<Appointment> findByDoctor(Doctor doctor) {
        return em.createNamedQuery("Appointment.findByDoctor", Appointment.class)
                 .setParameter("doctor", doctor)
                 .getResultList();
    }
    
    public List<Customer> findCustomersByDoctor(Doctor doctor) {
        return em.createNamedQuery("Appointment.findCustomersByDoctor", Customer.class)
                 .setParameter("doctor", doctor)
                 .getResultList();
    }
    
    public List<Appointment> findByCustomer(Customer customer) {
        return em.createNamedQuery("Appointment.findByCustomer", Appointment.class)
                 .setParameter("customer", customer)
                 .getResultList();
    }
}
