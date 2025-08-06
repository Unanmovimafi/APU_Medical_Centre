/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.doctor;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.AbstractFacade;

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
    
    public Doctor findDoctorByUsername(String username) {
        try {
            TypedQuery<Doctor> doctor = em.createNamedQuery("Doctor.findByUsername", Doctor.class);
            doctor.setParameter("username", username);
            return doctor.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
