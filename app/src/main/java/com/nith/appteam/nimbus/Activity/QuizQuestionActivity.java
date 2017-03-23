package com.nith.appteam.nimbus.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.nimbus.Fragment.QuizFragment;
import com.nith.appteam.nimbus.Model.QuizQuestionsModel;
import com.nith.appteam.nimbus.Model.SingleQuestionModel;
import com.nith.appteam.nimbus.R;
import com.nith.appteam.nimbus.Utils.ApiInterface;
import com.nith.appteam.nimbus.Utils.Connection;
import com.nith.appteam.nimbus.Utils.ScoreCalculator;
import com.nith.appteam.nimbus.Utils.SharedPref;
import com.nith.appteam.nimbus.Utils.UpdateScoreModel;
import com.nith.appteam.nimbus.Utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizQuestionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private ProgressBar progressBar;
    private Button back2home,finish;
    private LinearLayout staytuned_message;
    private TextView message,time_left;
    private FragmentManager manager;
    private static timer t;
    private ScoreCalculator scoreCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        manager=getSupportFragmentManager();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager=(ViewPager)findViewById(R.id.question_pager);
        back2home=(Button)findViewById(R.id.home_link);
        finish=(Button)findViewById(R.id.finish_button);
        staytuned_message=(LinearLayout)findViewById(R.id.stay_tuned_message);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        message=(TextView)findViewById(R.id.message);
        time_left=(TextView)findViewById(R.id.time_left);
        scoreCalculator= ScoreCalculator.getInstance();

        back2home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPref pref=new SharedPref(this);
        String userId=pref.getUserId();

        if(!userId.isEmpty()){
        loadQuizwithRetrofit(userId);
        t=new timer(this,time_left);}
        else {
            message.setText("Please Login to Play Quiz ");
        }

    }

    public void loadQuizwithRetrofit(String userId){

        ApiInterface apiservice= Util.getRetrofitService();
        Call<QuizQuestionsModel> call=apiservice.getQuiz(userId);

        Log.v("ronaldo","quiz get request for "+userId);

        call.enqueue(new Callback<QuizQuestionsModel>() {
            @Override
            public void onResponse(Call<QuizQuestionsModel> call, Response<QuizQuestionsModel> response) {
                progressBar.setVisibility(View.GONE);

                QuizQuestionsModel model=response.body();

                int status=response.code();

                if(model!=null && response.isSuccess()){
                    if(model.isSuccess()){
                        // set viewpager

                        ScoreCalculator sc=ScoreCalculator.getInstance();
                        sc.setSpecial(1);

                        final List<SingleQuestionModel> questions=model.getQuestions();

                        pager.setVisibility(View.VISIBLE);

                        if(questions!=null){
                            for(int i=0;i<questions.size();i++){
                                Log.i("first-option "+i,"#"+questions.get(i).getOptionsA()+"#");
                            }

                            t.execute();

                            //initialize scoreCalculator
                            //ScoreCalculator sc=ScoreCalculator.getInstance();

                            String answers[]=new String[questions.size()];
                            String selectedChoices[]=new String[questions.size()];
                            int question_type[]=new int[questions.size()];

                            for(int i1=0;i1<questions.size();i1++){
                                SingleQuestionModel ques=questions.get(i1);

                                answers[i1]=ques.getAnswer()+"";
                                selectedChoices[i1]=" ";

                                if(ques.isSingleChoice()){
                                    question_type[i1]=1;
                                }else{
                                    question_type[i1]=2;
                                }

                            }

                            // array initialization
                            sc.setAnswers(answers);
                            sc.setSelectedChoices(selectedChoices);
                            sc.setQuestion_type(question_type);

                            FragmentManager fragmentManager=getSupportFragmentManager();
                            pager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

                                @Override
                                public Fragment getItem(int position) {
                                    Bundle b=new Bundle();
                                    b.putInt("num",position+1);
                                    b.putSerializable("ques",questions.get(position));

                                    if(position==questions.size()-1){
                                        b.putBoolean("finish",true);
                                    }else{
                                        b.putBoolean("finish",false);
                                    }

                                    QuizFragment fragment=new QuizFragment();
                                    fragment.setArguments(b);

                                    return fragment;
                                }

                                @Override
                                public int getCount() {
                                    return questions.size();
                                }
                            });

                        }else{
                            staytuned_message.setVisibility(View.VISIBLE);
                        }

                    }else{
                        staytuned_message.setVisibility(View.VISIBLE);
                        message.setText("Some error occurred !! \nPlease try again later..");

                        if (status == 503) {
                            Toast.makeText(QuizQuestionActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                        }

                    }

                    Toast.makeText(QuizQuestionActivity.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();

                }else{
                    staytuned_message.setVisibility(View.VISIBLE);
                    message.setText("Some error occurred !! \nPlease try again later..");

                    if (status == 503) {
                        Toast.makeText(QuizQuestionActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<QuizQuestionsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                staytuned_message.setVisibility(View.VISIBLE);
                message.setText("Some error occurred !! \nPlease try again later..");

            }
        });

    }

    @Override
    public void onBackPressed() {

        if(staytuned_message.getVisibility()==View.GONE || progressBar.getVisibility()==View.VISIBLE){
            QuizDialog dialog=new QuizDialog();
            dialog.show(manager,"dialog");

        }else {
            super.onBackPressed();
        }

    }

    public void submitScore(){
        progressBar.setVisibility(View.VISIBLE);

        if(finish!=null)
        finish.setEnabled(false);

        SharedPref pref=new SharedPref(this);

        ScoreCalculator sc=ScoreCalculator.getInstance();

        int score_calculated=sc.calculateScore();
        Log.v("calculated_score is ",score_calculated+"");

        sc.resetInstance();     //Important

        finishAndUpdateScore(pref.getUserId(),score_calculated);
    }

    public void finishAndUpdateScore(String id, final int score){

        ApiInterface service= Util.getRetrofitService();
        Call<UpdateScoreModel> call=service.updateScore(id,score);

        call.enqueue(new Callback<UpdateScoreModel>() {
            @Override
            public void onResponse(Call<UpdateScoreModel> call, Response<UpdateScoreModel> response) {

                if(finish!=null){
                    finish.setEnabled(true);
                }

                progressBar.setVisibility(View.GONE);

                int status=response.code();
                UpdateScoreModel model=response.body();

                if(model!=null && response.isSuccess()){
                    if(model.isSuccess()){
                        scoreCalculator.setSpecial(0);
                        Toast.makeText(QuizQuestionActivity.this,model.getMsg() ,Toast.LENGTH_SHORT);

                        finish();
                        Intent in=new Intent(QuizQuestionActivity.this,QuizScoreActivity.class);
                        in.putExtra("score",score);

                        startActivity(in);
                        t.cancel(true);
                    }else{
                        Toast.makeText(QuizQuestionActivity.this,model.getMsg() ,Toast.LENGTH_SHORT);
                    }

                }else{
                    Toast.makeText(QuizQuestionActivity.this,"Some error occurred !!",Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<UpdateScoreModel> call, Throwable t) {
                Toast.makeText(QuizQuestionActivity.this,"Some error occurred !!",Toast.LENGTH_SHORT);
                progressBar.setVisibility(View.GONE);
                if(finish!=null){
                    finish.setEnabled(true);
                }
            }
        });

    }

    public static class QuizDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle saveInstanceState) {
            android.app.AlertDialog.Builder builder= new android.app.AlertDialog.Builder(getActivity());

            builder.setTitle("Are you sure?");
            builder.setMessage("Note that these questions will not appear again, so better to attempt it now. " +
                    "Do you still want to exit from quiz?");

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dismiss();
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if(t!=null) t.cancel(true);
                    getActivity().finish();

                }
            });

            Dialog d=builder.create();

            return d;
        }
    }

    private static class timer extends AsyncTask<Void, Pair<String, String>, Void>{

        private TextView timer_text=null;
        private Context context;

        public timer(Context context, TextView timer_text) {
            this.context=context;
            this.timer_text = timer_text;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for(int i=1;i>=0;i--){  // 1 minutes
                for(int j=59;j>=0;j--){ // 59 seconds

                    if(isCancelled()){
                        Log.v("cancel","called");
                        return null;
                    }

                    //Log.v("timer",i+" mins "+j+" seconds");
                    publishProgress(new Pair<String, String>(i+"",j+""));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Pair<String, String>... values) {
            String str="";

            if(values[0].first.length()==1){
                str+="0";
            }
            str+=values[0].first+" mins ";

            if(values[0].second.length()==1){
                str+="0";
            }
            str+=values[0].second+" seconds ";

            timer_text.setText(str);

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(new Connection(context).isInternet()){
                Log.v("internet","available");

                ScoreCalculator sc=ScoreCalculator.getInstance();

                if(sc.getSpecial()==1)
                ((QuizQuestionActivity)context).submitScore();

            }else{
                Log.v("internet","not available");
                Toast.makeText(context.getApplicationContext(),"No internet Connection",Toast.LENGTH_SHORT);

                ((AppCompatActivity)context).finish();
                Intent in=new Intent(context,QuizScoreActivity.class);
                in.putExtra("score",0);

                context.startActivity(in);
            }

        }
    }

}
