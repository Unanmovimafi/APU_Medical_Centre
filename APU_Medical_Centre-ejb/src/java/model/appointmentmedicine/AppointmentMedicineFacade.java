/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.appointmentmedicine;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.AbstractFacade;
import java.util.List;

/**
 *
 * @author khong
 */
@Stateless
public class AppointmentMedicineFacade extends AbstractFacade<AppointmentMedicine> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppointmentMedicineFacade() {
        super(AppointmentMedicine.class);
    }

    public List<AppointmentMedicine> findByAppointmentId(Integer appointmentId) {
        TypedQuery<AppointmentMedicine> query = em.createQuery(
                "SELECT am FROM AppointmentMedicine am WHERE am.appointment.id = :appointmentId",
                AppointmentMedicine.class);
        query.setParameter("appointmentId", appointmentId);
        return query.getResultList();
    }

}
