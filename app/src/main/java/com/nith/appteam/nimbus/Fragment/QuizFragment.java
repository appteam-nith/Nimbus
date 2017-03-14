package com.nith.appteam.nimbus.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nith.appteam.nimbus.Activity.QuizQuestionActivity;
import com.nith.appteam.nimbus.Model.SingleQuestionModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ScoreCalculator;
import com.nith.appteam.nimbus.Utils.SharedPref;

/**
 * Created by sukhbir
 */

public class QuizFragment extends Fragment {

    private int qnumber=1;
    private boolean show_finish=false;
    private Button finish;
    private TextView next_text,question_number_text;

    private ProgressBar progressBar;

    private TextView question_text;
    private CardView cardA,cardB, cardC, cardD;
    private TextView optionA,optionB,optionC,optionD;

    private ScoreCalculator sc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.single_question, container, false);
        qnumber=getArguments().getInt("num",1);
        show_finish=getArguments().getBoolean("finish");

        sc= ScoreCalculator.getInstance();

        final SingleQuestionModel ques= (SingleQuestionModel) getArguments().getSerializable("ques");

        init(view);

        String previous_choice=sc.getSelectedChoice(qnumber);

        if(previous_choice!=null){
            if(previous_choice.indexOf("A")!=-1){
                setCardBgColor(0);

            }else if(previous_choice.indexOf("B")!=-1){
                setCardBgColor(1);

            }else if(previous_choice.indexOf("C")!=-1){
                setCardBgColor(2);

            }else if(previous_choice.indexOf("D")!=-1){
                setCardBgColor(3);
            }

        }

        question_number_text.setText("Question "+qnumber+".");

        if(ques!=null){
            question_text.setText(ques.getQuestion());

            optionA.setText(ques.getOptionsA());
            optionB.setText(ques.getOptionsB());
            optionC.setText(ques.getOptionsC());
            optionD.setText(ques.getOptionsD());

            setCardClickListeners();

            if(show_finish){
                next_text.setVisibility(View.GONE);
                finish.setVisibility(View.VISIBLE);

                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        finish.setEnabled(false);

                        ScoreCalculator sc=ScoreCalculator.getInstance();

                        if(sc.getSpecial()==1){
                            progressBar.setVisibility(View.VISIBLE);

                            SharedPref pref=new SharedPref(getContext());

                            int score_calculated=sc.calculateScore();
                            Log.v("calculated_score is ",score_calculated+"");

                            sc.resetInstance();     //Important

                            ((QuizQuestionActivity)getActivity()).finishAndUpdateScore(pref.getUserId(),score_calculated);

                        }

                    }
                });

            }else{
                next_text.setVisibility(View.VISIBLE);
                finish.setVisibility(View.GONE);
            }

        }

        return view;

    }

    public void init(View view){
        question_text=(TextView)view.findViewById(R.id.question_text);

        optionA=(TextView)view.findViewById(R.id.optionA);
        optionB=(TextView)view.findViewById(R.id.optionB);
        optionC=(TextView)view.findViewById(R.id.optionC);
        optionD=(TextView)view.findViewById(R.id.optionD);

        cardA=(CardView) view.findViewById(R.id.card_a);
        cardB=(CardView) view.findViewById(R.id.card_b);
        cardC=(CardView) view.findViewById(R.id.card_c);
        cardD=(CardView) view.findViewById(R.id.card_d);

        progressBar=(ProgressBar)view.findViewById(R.id.progress);

        finish=(Button) view.findViewById(R.id.finish_button);
        next_text=(TextView) view.findViewById(R.id.next_instruction);
        question_number_text=(TextView) view.findViewById(R.id.question_number_text);



    }

    public void setCardClickListeners(){
        cardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBgColor(0);
                sc.setChoice(qnumber,"A");
            }
        });

        cardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBgColor(1);
                sc.setChoice(qnumber,"B");
            }
        });

        cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBgColor(2);
                sc.setChoice(qnumber,"C");
            }
        });

        cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCardBgColor(3);
                sc.setChoice(qnumber,"D");
            }
        });

    }

    public void setCardBgColor(int position){
        cardA.setBackgroundColor(Color.parseColor("#ffffff"));
        cardB.setBackgroundColor(Color.parseColor("#ffffff"));
        cardC.setBackgroundColor(Color.parseColor("#ffffff"));
        cardD.setBackgroundColor(Color.parseColor("#ffffff"));

        if(position==0){
            cardA.setBackgroundColor(Color.parseColor("#8ae9de"));
        }else if(position==1){
            cardB.setBackgroundColor(Color.parseColor("#8ae9de"));
        }else if(position==2){
            cardC.setBackgroundColor(Color.parseColor("#8ae9de"));
        }else{
            cardD.setBackgroundColor(Color.parseColor("#8ae9de"));
        }
    }

}
