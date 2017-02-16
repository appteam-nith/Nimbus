package com.nith.appteam.nimbus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sukhbir on 11/10/16.
 */

public class QuizQuestionsModel {

    @SerializedName("questions")
    private List<SingleQuestionModel> questions;

    @SerializedName("success")
    private boolean success;

    @SerializedName("msg")
    private String msg;

    public List<SingleQuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SingleQuestionModel> questions) {
        this.questions = questions;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

/*
{
  "questions": [
    {
      "_id": "57fbc901f36d28111030add9",
      "qNo": 1,
      "question": "what is the full form of HTML",
      "optionsA": "Hyper Text Language",
      "optionsB": "Markup Language",
      "optionsC": "Hyper Text Markup Language",
      "optionsD": "HTM Language",
      "answer": "optionC",
      "isSingleChoice": true
    },
    {
      "_id": "57fbca8bf36d28111030ae41",
      "qNo": 2,
      "question": "what is the full form of HTML",
      "optionsA": "Hyper Text Language",
      "optionsB": "Markup Language",
      "optionsC": "Hyper Text Markup Language",
      "optionsD": "HTM Language",
      "answer": "optionC",
      "isSingleChoice": true
    }
  ],
  "success": true,
  "msg": "successfull"
}
*/