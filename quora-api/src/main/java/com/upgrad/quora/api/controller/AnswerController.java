package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerBusinessService;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class AnswerController {

    @Autowired
    private AnswerBusinessService answerBusinessService;

    @Autowired
    private QuestionBusinessService questionBusinessService;

    private static String ANSWER_CREATED = "ANSWER CREATED";
    private static String ANSWER_EDITED = "ANSWER EDITED";
    private static String ANSWER_DELETED = "ANSWER DELETED";


    /**
     * This method creates answer
     * @param answerRequest
     * @param authorizationToken
     * @return
     * @throws AuthorizationFailedException
     */
    @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(AnswerRequest answerRequest, @PathVariable("questionId") final String questionId,
                                                       @RequestHeader("authorization") final String authorizationToken) throws AuthorizationFailedException, InvalidQuestionException {
        AnswerEntity answerEntity = answerBusinessService.performCreateAnswer(authorizationToken, questionId, answerRequest.getAnswer());
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.id(answerEntity.getUuid()).status(ANSWER_CREATED);
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.OK);
    }

    /**
     * This method edits the answer for the provided answer id
     * @param authorizationToken
     * @param answerId
     * @param answerEditRequest
     * @return
     * @throws AuthorizationFailedException
     * @throws InvalidQuestionException
     */

    @RequestMapping(method = RequestMethod.PUT, path = "/answer/edit/{answerId}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswerContent(@RequestHeader("authorization") final String authorizationToken,
                                                                @PathVariable("answerId") final String answerId, AnswerEditRequest answerEditRequest)
            throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerEditResponse answerEditResponse = new AnswerEditResponse();
        AnswerEntity answerEntity = answerBusinessService.performUpdateAnswer(authorizationToken, answerId, answerEditRequest.getContent());
        answerEditResponse.setId(answerEntity.getUuid());
        answerEditResponse.setStatus(ANSWER_EDITED);
        return new ResponseEntity<AnswerEditResponse>(answerEditResponse, HttpStatus.OK);
    }


    /**
     * This method retrieves all the answers by questionId
     * @param authorizationToken
     * @param questionId
     * @return
     * @throws AuthorizationFailedException
     * @throws UserNotFoundException
     */

    @RequestMapping(method = RequestMethod.GET, path = "answer/all/{questionId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<List<AnswerDetailsResponse>> getAllAnswersToQuestion (@RequestHeader("authorization") final String authorizationToken,
                                                                                @PathVariable("questionId") final String questionId) throws AuthorizationFailedException,
            InvalidQuestionException {
        List<AnswerDetailsResponse> answerDetailsResponseList = new ArrayList<AnswerDetailsResponse>();
        List<AnswerEntity> answerEntityList = answerBusinessService.performGetAllAnswersToQuestion(authorizationToken, questionId);
        if (answerEntityList != null && !answerEntityList.isEmpty()) {
            for (AnswerEntity answerEntity : answerEntityList) {
                answerDetailsResponseList.add(new AnswerDetailsResponse().id(answerEntity.getUuid())
                        .answerContent(answerEntity.getAnswer()).questionContent(answerEntity.getQuestion().getContent()));
            }
        }
        return new ResponseEntity<List<AnswerDetailsResponse>>(answerDetailsResponseList, HttpStatus.OK);
    }

    /**
     * This method deletes the answer for a given answer id
     * @param authorizationToken
     * @param answerId
     * @return
     * @throws AuthorizationFailedException
     * @throws InvalidQuestionException
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@RequestHeader("authorization") final String authorizationToken,
                                                             @PathVariable("answerId") final String answerId)
            throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse();
        answerBusinessService.performDeleteAnswer(authorizationToken, answerId);
        answerDeleteResponse.id(answerId).status(ANSWER_DELETED);
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);
    }
}
