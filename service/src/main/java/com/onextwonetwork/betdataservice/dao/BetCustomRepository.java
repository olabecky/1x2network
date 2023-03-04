package com.onextwonetwork.betdataservice.dao;


import com.onextwonetwork.betdataservice.BetDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class BetCustomRepository {
    private static final Logger LOGGER = Logger.getLogger(BetCustomRepository.class.getName());
    @PersistenceContext
    private EntityManager entityManager;

    public BetCustomRepository() {
    }

    public List<BetDTO> searchBets(Pageable page, String game, Long clientId, Date startDate, Date endDate){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<BetDTO> query = cb.createQuery(BetDTO.class);
        Root<BetEntity> root = query.from(BetEntity.class);

        Path<String> gamePath = root.get("game");
        Path<Long> clientIdPath = root.get("clientId");
        Path<Date> datePath = root.get("betDate");

        Path<Long> idPath = root.get("id");
        Path<Long> numbetsPath = root.get("numbets");
        Path<BigDecimal> stakePath = root.get("stake");
        Path<BigDecimal> returnsPath = root.get("returns");


        List<Predicate> predicates = new ArrayList<>();

        if(game != null){
            predicates.add(cb.like(gamePath, game));
        }

        if(clientId != null){
            predicates.add(cb.equal(clientIdPath, clientId));
        }

        if(startDate != null && endDate != null){
            predicates.add(cb.between(datePath, startDate, endDate));
        }
        query.multiselect(idPath, numbetsPath, gamePath, stakePath, returnsPath, clientIdPath, datePath)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        return this.entityManager.createQuery(query)
                .setMaxResults(page.getPageSize())
                .setFirstResult(Long.valueOf(page.getOffset()).intValue())
                .getResultList();
    }
}
