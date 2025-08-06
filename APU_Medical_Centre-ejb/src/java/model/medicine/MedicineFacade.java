/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.medicine;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.AbstractFacade;

/**
 *
 * @author khong
 */
@Stateless
public class MedicineFacade extends AbstractFacade<Medicine> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedicineFacade() {
        super(Medicine.class);
    }
    
}
