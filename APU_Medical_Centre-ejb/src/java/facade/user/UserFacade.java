/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facade.user;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import model.user.User;

/**
 *
 * @author zihao
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
        // Method to find a user by ID using the NamedQuery
    public User findUserByUsername(String username) {
        try {
            TypedQuery<User> user = em.createNamedQuery("User.findByUsername", User.class);
            user.setParameter("username", username);
            return user.getSingleResult();  // Returns the user or throws an exception if not found
        } catch (NoResultException e) {
            // Handle the case where the user is not found
            return null;
        }
    }
    
}
