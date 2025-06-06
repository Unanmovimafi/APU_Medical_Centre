/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facade.codevalue;

import facade.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.codevalue.CodeValue;

/**
 *
 * @author zihao
 */
@Stateless
public class CodeValueFacade extends AbstractFacade<CodeValue> {

    @PersistenceContext(unitName = "APU_Medical_Centre-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodeValueFacade() {
        super(CodeValue.class);
    }

    public CodeValue findActiveCodeValueByCodeSetAndCodeValue(String codeCodeSet, String codeCodeValue) {
        TypedQuery<CodeValue> codeValue = em.createNamedQuery("CodeValue.findByCodeAndStatus", CodeValue.class);
        codeValue.setParameter("code", codeCodeValue);
        codeValue.setParameter("status", "ACTIVE");
        List<CodeValue> listCodeValue = codeValue.getResultList();
        for (CodeValue cv : listCodeValue) {
            if (codeCodeSet.equals(cv.getCodeSet().getCode()) && "ACTIVE".equals(cv.getCodeSet().getStatus())) {
                return cv;
            }
        }
        return null;
    }

}
