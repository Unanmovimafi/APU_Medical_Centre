/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.counterstaff;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.AbstractFacade;
import model.customer.Customer;

/**
 *
 * @author zihao
 */
@Stateless
public class CounterStaffFacade extends AbstractFacade<CounterStaff> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CounterStaffFacade() {
        super(CounterStaff.class);
    }

    public CounterStaff findCounterStaffByUsername(String username) {
        try {
            TypedQuery<CounterStaff> counterStaff = em.createNamedQuery("CounterStaff.findByUsername", CounterStaff.class);
            counterStaff.setParameter("username", username);
            return counterStaff.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
