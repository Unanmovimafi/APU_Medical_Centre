/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.manager;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.AbstractFacade;
import model.appointment.Appointment;

/**
 *
 * @author zihao
 */
@Stateless
public class ManagerFacade extends AbstractFacade<Manager> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManagerFacade() {
        super(Manager.class);
    }
    
    public Manager approveAppointment(Manager manager, Integer appointmentId, String newCustomerName) {
        // Find the appointment using the appointmentId
//        Appointment appointmentToUpdate = null;
//
//        for (Appointment appointment : manager.getAppointment()) {
//            if (appointment.getId().equals(appointmentId)) {
//                appointmentToUpdate = appointment;
//                break;
//            }
//        }
//
//        // If the appointment is found, update the customerName
//        if (appointmentToUpdate != null) {
//            appointmentToUpdate.setCustomerName(newCustomerName); // Assuming Appointment has a setCustomerName method
//            edit(appointmentToUpdate); // Call the edit method to save changes for the appointment
//        }
//
//        // Save the manager's state if any changes were made to the manager itself
//        edit(manager);  // This is optional, depending on whether you modify anything on the manager itself

        return manager;  // Return the updated manager
    }
}
