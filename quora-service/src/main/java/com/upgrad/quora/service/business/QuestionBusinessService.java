package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.QuestionEntity;
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
}
