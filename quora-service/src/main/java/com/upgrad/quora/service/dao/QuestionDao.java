package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

  @PersistenceContext
  private EntityManager entityManager;


  public QuestionEntity createQuestion(QuestionEntity questionEntity) {
    try {
      entityManager.persist(questionEntity);
      return questionEntity;
    } catch (Exception e) {
      return null;
    }
  }

  public List<QuestionEntity> getAllQuestions() {
    try {
      return entityManager.createNamedQuery("getAllQuestions", QuestionEntity.class).getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }
}
