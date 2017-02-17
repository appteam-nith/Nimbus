package com.nith.appteam.nimbus.Utils;

import android.util.Log;

/**
 * Created by sukhbir
 */

public class ScoreCalculator {

    private static ScoreCalculator obj;
    private String answers[],selectedChoices[];
    private int totalScore=0;
    private int special;

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    private int question_type[];    // 1 => single   2 => multiple

    private ScoreCalculator(){
    }

    public static ScoreCalculator getInstance(){
        if(obj==null){
            obj=new ScoreCalculator();
        }

        return obj;
    }

    public void resetInstance(){
        obj=null;
    }

    public void setChoice(int qno, String choice){
        if(selectedChoices!=null)
       selectedChoices[qno-1]=choice;
       //Log.v("#######"+qno,"choice is "+choice+", answer is "+answers[qno-1]);
    }

    public void setChoice(int qno,String choice,boolean b){
        if(selectedChoices==null)return;

        if(b){
            selectedChoices[qno-1]=selectedChoices[qno-1]+choice;
        }else{
            selectedChoices[qno-1]=selectedChoices[qno-1].replaceAll(choice,"Z");
        }

        Log.v("testing-checkboxes",selectedChoices[qno-1]);

    }

    public void setQuestion_type(int question_type[]){  //initialization
        this.question_type=question_type;
    }

    public void setAnswers(String[] answers){   //initialization
        this.answers=answers;
    }

    public void setSelectedChoices(String[] selectedChoices){    //initialization
        this.selectedChoices=selectedChoices;
    }

    public int calculateScore(){
        if(answers==null) return 0;

        for(int i=0;i<answers.length;i++){
            Log.i("cal-score","#"+answers[i]+"# ----- #"+selectedChoices[i]+"#");

            if(question_type[i]==1){
                if(answers[i].trim().equals(selectedChoices[i].trim())){
                    Log.v("score1++","for i= "+i);
                    totalScore++;
                }

            }else{  // multiple choice
                int flag=1;

                for(int j=0;j<answers[i].length();j++){
                    char temp=answers[i].charAt(j);

                    if(selectedChoices[i].indexOf(temp)==-1){
                        flag=0;
                        break;
                    }
                }

                if(flag==1){
                    Log.v("score2++","for i= "+i);
                    totalScore++;
                }
            }

        }

        return totalScore;
    }

}
