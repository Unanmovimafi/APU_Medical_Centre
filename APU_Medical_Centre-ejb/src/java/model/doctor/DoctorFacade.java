/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.doctor;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.AbstractFacade;
import model.appointment.Appointment;
import model.manager.Manager;

/**
 *
 * @author zihao
 */
@Stateless
public class DoctorFacade extends AbstractFacade<Doctor> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DoctorFacade() {
        super(Doctor.class);
    }
    
//    public Doctor approveAppointment(Doctor doctor, Integer appointmentId, String newCustomerName) {
//        // Find the appointment using the appointmentId
//        Appointment appointmentToUpdate = null;
//
//        for (Appointment appointment : doctor.getAppointmentCollection()) {
//            if (appointment.getId().equals(appointmentId)) {
//                appointmentToUpdate = appointment;
//                break;
//            }
//        }
//
//        // If the appointment is found, update the customerName
//        if (appointmentToUpdate != null) {]''
//            appointmentToUpdate.setCustomer(Customer customer); // Assuming Appointment has a setCustomerName method
//            edit(appointmentToUpdate); // Call the edit method to save changes for the appointment
//        }
//
//        // Save the manager's state if any changes were made to the manager itself
//        edit(doctor);  // This is optional, depending on whether you modify anything on the manager itself
//
//        return doctor;  // Return the updated manager
//    }
}
