/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.comment;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.AbstractFacade;
import model.doctor.Doctor;
import model.counterstaff.CounterStaff;

/**
 *
 * @author zihao
 */
@Stateless
public class CommentFacade extends AbstractFacade<Comment> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
    }

    public List<Comment> findByDoctor(Doctor doctor) {
        try {
            TypedQuery<Comment> query = getEntityManager().createQuery(
                    "SELECT c FROM Comment c WHERE c.doctor = :doctor ORDER BY c.creationDatetime DESC",
                    Comment.class);
            query.setParameter("doctor", doctor);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Comment> findByCounterStaff(CounterStaff counterStaff) {
        try {
            TypedQuery<Comment> query = getEntityManager().createQuery(
                    "SELECT c FROM Comment c WHERE c.counterStaff = :counterStaff ORDER BY c.creationDatetime DESC",
                    Comment.class);
            query.setParameter("counterStaff", counterStaff);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Comment> searchByDoctorAndKeyword(Doctor doctor, String column, String keyword) {
        try {
            String jpql = "SELECT c FROM Comment c WHERE c.doctor = :doctor";

            if (keyword != null && !keyword.trim().isEmpty()) {
                switch (column) {
                    case "customer_name":
                        jpql += " AND LOWER(c.customer.name) LIKE LOWER(:keyword)";
                        break;
                    case "content":
                        jpql += " AND LOWER(c.content) LIKE LOWER(:keyword)";
                        break;
                    case "rating":
                        try {
                            Integer ratingValue = Integer.parseInt(keyword.trim());
                            jpql += " AND c.rating = :rating";
                        } catch (NumberFormatException e) {
                            // If keyword is not a number, ignore rating search
                        }
                        break;
                }
            }

            jpql += " ORDER BY c.creationDatetime DESC";

            TypedQuery<Comment> query = getEntityManager().createQuery(jpql, Comment.class);
            query.setParameter("doctor", doctor);

            if (keyword != null && !keyword.trim().isEmpty()) {
                switch (column) {
                    case "customer_name":
                    case "content":
                        query.setParameter("keyword", "%" + keyword + "%");
                        break;
                    case "rating":
                        try {
                            Integer ratingValue = Integer.parseInt(keyword.trim());
                            query.setParameter("rating", ratingValue);
                        } catch (NumberFormatException e) {
                            // Return empty list if rating is not a valid number
                            return findByDoctor(doctor);
                        }
                        break;
                }
            }

            return query.getResultList();
        } catch (Exception e) {
            return findByDoctor(doctor); // Fallback to all comments
        }
    }

    public List<Comment> findAllComments() {
        try {
            TypedQuery<Comment> query = getEntityManager().createQuery(
                    "SELECT c FROM Comment c ORDER BY c.creationDatetime DESC",
                    Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Comment> searchAllCommentsAndKeyword(String column, String keyword) {
        try {
            String jpql = "SELECT c FROM Comment c";

            if (keyword != null && !keyword.trim().isEmpty()) {
                switch (column) {
                    case "customer_name":
                        jpql += " WHERE LOWER(c.customer.name) LIKE LOWER(:keyword)";
                        break;
                    case "doctor_name":
                        jpql += " WHERE LOWER(c.doctor.name) LIKE LOWER(:keyword)";
                        break;
                    case "staff_name":
                        jpql += " WHERE LOWER(c.counterStaff.name) LIKE LOWER(:keyword)";
                        break;
                    case "content":
                        jpql += " WHERE LOWER(c.content) LIKE LOWER(:keyword)";
                        break;
                    case "rating":
                        try {
                            Integer ratingValue = Integer.parseInt(keyword.trim());
                            jpql += " WHERE c.rating = :rating";
                        } catch (NumberFormatException e) {
                            // If keyword is not a number, ignore rating search
                        }
                        break;
                }
            }

            jpql += " ORDER BY c.creationDatetime DESC";

            TypedQuery<Comment> query = getEntityManager().createQuery(jpql, Comment.class);

            if (keyword != null && !keyword.trim().isEmpty()) {
                switch (column) {
                    case "customer_name":
                    case "doctor_name":
                    case "staff_name":
                    case "content":
                        query.setParameter("keyword", "%" + keyword + "%");
                        break;
                    case "rating":
                        try {
                            Integer ratingValue = Integer.parseInt(keyword.trim());
                            query.setParameter("rating", ratingValue);
                        } catch (NumberFormatException e) {
                            // Return all comments if rating is not a valid number
                            return findAllComments();
                        }
                        break;
                }
            }

            return query.getResultList();
        } catch (Exception e) {
            return findAllComments(); // Fallback to all comments
        }
    }

}
