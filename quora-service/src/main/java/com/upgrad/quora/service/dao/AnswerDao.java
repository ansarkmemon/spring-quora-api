package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDao {
    @Autowired
    EntityManager entityManager;

    public AnswerDao() {
    }

    public AnswerEntity createAnswer(AnswerEntity answerEntity) {
        this.entityManager.persist(answerEntity);
        return answerEntity;
    }

    public AnswerEntity editAnswerContent(AnswerEntity answerEntity) {
        return entityManager.merge(answerEntity);
    }

    public void deleteAnswer(AnswerEntity answerEntity) {
        entityManager.remove(answerEntity);
    }

    public List<AnswerEntity> getAllAnswersToQuestion(int id) {
        try {
            return this.entityManager.createNamedQuery("getAnswersForQuestionId", AnswerEntity.class).setParameter("uuid", id).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AnswerEntity getAnswerForAnswerId(String uuid) {
        try {
            return this.entityManager.createNamedQuery("getAnswerForAnswerId", AnswerEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<AnswerEntity> getAnswersForUserId(Integer userId) {
        try {
            return this.entityManager.createNamedQuery("getAnswersByUserId", AnswerEntity.class).setParameter("userId", userId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<AnswerEntity> getAllAnswers() {
        try {
            return this.entityManager.createNamedQuery("getAllAnswers", AnswerEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
