/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.feedback;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import model.AbstractFacade;
import model.appointment.Appointment;

/**
 *
 * @author khong
 */
@Stateless
public class FeedbackFacade extends AbstractFacade<Feedback> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackFacade() {
        super(Feedback.class);
    }

    public Feedback findByAppointment(Appointment appointment) {
        try {
            Query query = em.createQuery("SELECT f FROM Feedback f WHERE f.appointment = :appointment");
            query.setParameter("appointment", appointment);
            List<Feedback> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Feedback> findAllByAppointment(Appointment appointment) {
        try {
            Query query = em.createQuery(
                    "SELECT f FROM Feedback f WHERE f.appointment = :appointment ORDER BY f.creationDatetime DESC");
            query.setParameter("appointment", appointment);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Feedback> findByAppointmentId(Integer appointmentId) {
        try {
            Query query = em.createQuery(
                    "SELECT f FROM Feedback f WHERE f.appointment.id = :appointmentId ORDER BY f.creationDatetime DESC");
            query.setParameter("appointmentId", appointmentId);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
}
