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

  public QuestionEntity getQuestionById(final String id) {
    try {
      return entityManager.createNamedQuery("getQuestionById", QuestionEntity.class)
              .setParameter("uuid", id)
              .getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public void editQuestion(String uuid, String content) {
    try {
      entityManager.createNamedQuery("editQuestionById")
              .setParameter("uuid", uuid)
              .setParameter("content", content)
              .executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
