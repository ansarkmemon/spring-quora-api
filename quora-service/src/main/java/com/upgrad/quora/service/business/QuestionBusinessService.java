package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionBusinessService {

  @Autowired
  private QuestionDao questionDao;

  @Transactional
  public QuestionEntity createQuestion(QuestionEntity questionEntity) {
    QuestionEntity createdQuestion = questionDao.createQuestion(questionEntity);
    return createdQuestion;
  }


  public List<QuestionEntity> getAllQuestions() {
    List<QuestionEntity> questions = questionDao.getAllQuestions();
    return questions;
  }

  public List<QuestionEntity> getAllQuestionsByUser(final UserEntity userId) {
    List<QuestionEntity> questionsList = questionDao.getAllQuestionsByUser(userId);

    return questionsList;
  }

  public QuestionEntity getQuestionById(String id) throws InvalidQuestionException {
    QuestionEntity question = questionDao.getQuestionById(id);

    if(question == null) {
      throw new InvalidQuestionException("QUES-001", "Entered question uuid does not exist");
    }

    return question;
  }


  public Boolean isQuestionOwner(UserAuthEntity userAuthEntity, QuestionEntity questionEntity) throws AuthorizationFailedException {
    if(questionEntity.getUserId().getUuid().equals(userAuthEntity.getUserId().getUuid())) {
      return true;
    } else {
      throw new AuthorizationFailedException("ATHR-003", "Only the question owner can edit the question");
    }
  }


  @Transactional
  public String editQuestion(String uuid, String questionContent) {
    questionDao.editQuestion(uuid, questionContent);
    return uuid;
  }

  @Transactional
  public String deleteQuestion(String uuid) {
    questionDao.deleteQuestion(uuid);
    return uuid;
  }





}
