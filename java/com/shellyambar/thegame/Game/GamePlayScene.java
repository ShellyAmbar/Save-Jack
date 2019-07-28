package com.shellyambar.thegame.Game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nex3z.flowlayout.FlowLayout;
import com.shellyambar.thegame.Interfaces.Scene;
import com.shellyambar.thegame.Models.QuestionAnswerModel;
import com.shellyambar.thegame.Obstacles.BombObstacle;
import com.shellyambar.thegame.Obstacles.CountriesObstacle;
import com.shellyambar.thegame.Obstacles.JellyFishObstacle;
import com.shellyambar.thegame.Obstacles.KnowledgeObstacle;
import com.shellyambar.thegame.Obstacles.Obstacle;
import com.shellyambar.thegame.Obstacles.ObstacleManager;
import com.shellyambar.thegame.Obstacles.OctopusObstacle;
import com.shellyambar.thegame.Questions.CountryQuestion;
import com.shellyambar.thegame.Questions.QuestionAmericanCountry;
import com.shellyambar.thegame.Questions.QuestionAmericanKnowledge;
import com.shellyambar.thegame.Questions.QuestionAmericanMath;
import com.shellyambar.thegame.Questions.QuestionCompleteCountry;
import com.shellyambar.thegame.Questions.QuestionCompleteMath;
import com.shellyambar.thegame.R;
import com.shellyambar.thegame.TextViewCelebrate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GamePlayScene implements Scene {


    private Rect r = new Rect();
    private Player player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean moovingPlayer = false;
    private boolean isMoovingObstcles = false;
    private boolean isGameOver = false;
    private boolean isNewQuestion = false;
    private long NewQuestionTime;
    private long gameOverTime;
    private OrientationData orientationData;
    private long frameTime;
    private int CurrentScore;
    private int numOfLevel;
    private boolean isAddedToScore = false;
    private boolean startNewLevel = false;
    private MediaPlayer mediaPlayer = null;
    private int maxPresses;
    private int currentNumOfPresses;




    public GamePlayScene(int numOfLevel) {
        this.numOfLevel = numOfLevel;
        player=new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));
        playerPoint= new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.Update(playerPoint);
        obstacleManager=new ObstacleManager(200,500,240,Color.GREEN,numOfLevel);
        CurrentScore = 0;
        orientationData = new OrientationData();
        orientationData.Register();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public int GetCurrentScore(){
        return CurrentScore;
    }
    private void resetGame() {
        playerPoint= new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.Update(playerPoint);
        obstacleManager=new ObstacleManager(200,500,240,Color.GREEN,numOfLevel);
        moovingPlayer = false;


    }

    @Override
    public void Terminate() {

        SceneManager.ActiveScene=0;
    }

    @Override
    public void ReceiveTouch(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(!isGameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()) && moovingPlayer)
                {

                    playerPoint.set((int)event.getX(),(int)event.getY()-200);
                    player.Update(playerPoint);
                }



                break;

            case MotionEvent.ACTION_MOVE:

                if(!isGameOver && moovingPlayer){
                    playerPoint.set((int)event.getX(),(int)event.getY());
                }

                break;



        }
    }

    @Override
    public void Update() {


        if(!isGameOver && !isNewQuestion){
            if(frameTime < Constants.INIT_TIME){
                frameTime = Constants.INIT_TIME;
            }

            int elapsedTime = 200;

            if(orientationData.getOrientation()!=null && orientationData.getStartOrientation()!=null){
                float pitch = orientationData.getOrientation()[1]- orientationData.getStartOrientation()[1];
                float roll  = orientationData.getOrientation()[2]- orientationData.getStartOrientation()[2];
                float xSpeed = 2*roll*Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch*Constants.SCREEN_HEIGHT/2000f;
                playerPoint.x += Math.abs(xSpeed*elapsedTime) > 2 ? xSpeed*elapsedTime: 0;
                playerPoint.y -= Math.abs(ySpeed*elapsedTime) > 5? ySpeed*elapsedTime: 0;

            }

            if(playerPoint.x<0){
                playerPoint.x=50;
            }
            else if(playerPoint.x> Constants.SCREEN_WIDTH){
                playerPoint.x =  Constants.SCREEN_WIDTH-50;
            }
            if(playerPoint.y<0){
                playerPoint.y=50;
            }
            else if(playerPoint.y> Constants.SCREEN_HEIGHT){
                playerPoint.y =  Constants.SCREEN_HEIGHT-100;
            }



            if (!isGameOver && (System.currentTimeMillis() -  NewQuestionTime)>5000 || !isNewQuestion ){

                isMoovingObstcles = true;
                isNewQuestion= false;
                moovingPlayer = true;
                isGameOver = false;


                player.Update(playerPoint);
                obstacleManager.Update();



            }else if(!isGameOver && isNewQuestion ){
                isMoovingObstcles = false;
                moovingPlayer = false;
            }




            if(obstacleManager.PlayerCollide(player)!=null  && !isNewQuestion && !isGameOver){
             final    Obstacle obstacleCollided = obstacleManager.PlayerCollide(player);

                if(obstacleCollided.getClass().equals(BombObstacle.class)){
                    isGameOver = true;
                    gameOverTime=System.currentTimeMillis();
                    CurrentScore-=3;

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            GameOverDialog();

                        };


                    });

                }else{


                    obstacleCollided.IncrementHeight(400);



                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {



                            NewQuestionTime = System.currentTimeMillis();
                            isNewQuestion = true;
                            Userdialog(obstacleCollided);

                        };


                    });
                }


            }
        }


    }

    @Override
    public void Draw(Canvas canvas) {


        BitmapFactory bf = new BitmapFactory();
        Bitmap bitmap =bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sea);
        //canvas.drawBitmap(bitmap,0,0,null);

        DisplayMetrics metrics = Constants.CURRENT_CONTEXT.getResources().getDisplayMetrics();
        Rect dstRect = new Rect();
        canvas.getClipBounds(dstRect);

        canvas.drawBitmap(bitmap,null,dstRect,null);


        Paint paint1 = new Paint();
        paint1.setTextSize(80);
        paint1.setColor(Color.WHITE);




        canvas.drawText("SCORE: " + CurrentScore, 50, 10 + paint1.descent() - paint1.ascent(), paint1);


        if(!isGameOver && !isNewQuestion){
            player.Draw(canvas);
            obstacleManager.Draw(canvas);
        }



    }

    private void drawCenter(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;

        canvas.drawText(text, x, y, paint);
    }

    private void Userdialog(Obstacle obstacleCollided){

         final QuestionAnswerModel questionAnswerModel  = new QuestionAnswerModel();

         QuestionAmericanMath questionAmericanMath = QuestionAmericanMath.getInstance();
         questionAmericanMath.getMathQuestion().setMaxInt(numOfLevel);
         QuestionAmericanCountry questionAmericanCountry = QuestionAmericanCountry.getInstance();
         QuestionAmericanKnowledge questionAmericanKnowledge = QuestionAmericanKnowledge.getInstance();
         QuestionCompleteCountry questionCompleteCountry  = QuestionCompleteCountry.getInstance();
         QuestionCompleteMath questionCompleteMath = QuestionCompleteMath.getInstance();
         questionCompleteMath.getMathQuestion().setMaxInt(numOfLevel);
         CountryQuestion countryQuestion = CountryQuestion.getInstance();





        if(obstacleCollided.getClass().equals(CountriesObstacle.class)){
            // countries complete or american
            Random random = new Random();
            int randChoice = random.nextInt(2);
            if(randChoice==0){
                //countries american

                questionAmericanCountry.CreateQuestion();


                List<String> listOfAnswers =questionAmericanCountry.getOptions();
                List<String> listOfAnswersTrimmed = new ArrayList<>();
                for(String country : listOfAnswers ){
                    listOfAnswersTrimmed.add(byIdName(Constants.CURRENT_CONTEXT,countryQuestion.getCountriesCapitals().get(country)));
                }
                int randChoice2 = random.nextInt(2);
                if(randChoice2 == 0){
                    CompleteLettersDialog(byIdName(Constants.CURRENT_CONTEXT,questionAmericanCountry.getAnswer()),Constants.CURRENT_CONTEXT.getString(R.string.question_country)+" "+byIdName(Constants.CURRENT_CONTEXT,questionAmericanCountry.getQuestion())+"?");

                } else if (randChoice2 == 1)
                {
                    AmericanDialog(listOfAnswersTrimmed,byIdName(Constants.CURRENT_CONTEXT,questionAmericanCountry.getAnswer()),Constants.CURRENT_CONTEXT.getString(R.string.question_country)+" "+byIdName(Constants.CURRENT_CONTEXT,questionAmericanCountry.getQuestion())+"?","country");

                }

            }else if (randChoice == 1){
                //countries complete

                questionCompleteCountry.CreateQuestion();

                List optionsList = new ArrayList();
                for(String option :  questionCompleteCountry.getOptions()){
                    optionsList.add(byIdName(Constants.CURRENT_CONTEXT,option));
                }

                int randChoice2 = random.nextInt(2);
                if(randChoice2 == 0){
                    CompleteLettersDialog(byIdName(Constants.CURRENT_CONTEXT,questionCompleteCountry.getAnswer()),byIdName(Constants.CURRENT_CONTEXT,questionCompleteCountry.getQuestion())+" "+ Constants.CURRENT_CONTEXT.getString(R.string.capital_of));

                } else if (randChoice2 == 1)
                {
                    AmericanDialog(optionsList,byIdName(Constants.CURRENT_CONTEXT,questionCompleteCountry.getAnswer()),byIdName(Constants.CURRENT_CONTEXT,questionCompleteCountry.getQuestion())+" "+ Constants.CURRENT_CONTEXT.getString(R.string.capital_of),"country");

                }

            }

        }else if(obstacleCollided.getClass().equals(KnowledgeObstacle.class)){
            //knowledge

            questionAmericanKnowledge.CreateQuestion();

            KnowledgeDialog(byIdName(Constants.CURRENT_CONTEXT,questionAmericanKnowledge.getQuestion()));


        }else if (obstacleCollided.getClass().equals(OctopusObstacle.class)){
            //math complete

            questionCompleteMath.CreateQuestion();
            AmericanDialog(questionCompleteMath.getOptions(),questionCompleteMath.getAnswer(),questionCompleteMath.getQuestion() +" ___ " + questionCompleteMath.getQuestionEnd(),"math");


        }else if (obstacleCollided.getClass().equals(JellyFishObstacle.class)){
            //math american


            questionAmericanMath.CreateQuestion();
            AmericanDialog(questionAmericanMath.getOptions(),questionAmericanMath.getAnswer(),questionAmericanMath.getQuestion(),"math");

        }






    }


    public void AmericanDialog(List<String> AllAnswers, final String RightAnswer, String Question, String kindOfQuestion){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer =MediaPlayer.create(Constants.CURRENT_CONTEXT,R.raw.smb_vine);
        mediaPlayer.start();
        isAddedToScore = false;
        final AlertDialog.Builder alertDialog=
                new AlertDialog.Builder(Constants.CURRENT_CONTEXT,R.style.AlertDialogCustom).setCancelable(false);


        final TextView textViewTitle =new TextView(Constants.CURRENT_CONTEXT);
        textViewTitle.setTextColor(Color.WHITE);
        textViewTitle.setTextSize(25);
        textViewTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        textViewTitle.setText(Question);

        LinearLayout.LayoutParams layoutParamsTitle = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT,0);
        layoutParamsTitle.setMargins(20,20,20,10);

        final TextView textView =new TextView(Constants.CURRENT_CONTEXT);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        final RadioGroup radioGroup = new RadioGroup(Constants.CURRENT_CONTEXT);

        radioGroup.setPadding(10,20,10,20);
        LinearLayout.LayoutParams layoutParamsGroup = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT,0);
        layoutParamsGroup.setMargins(30,5,30,20);

        radioGroup.setOrientation(RadioGroup.VERTICAL);
        final RadioButton[] radioButton = new RadioButton[4];
        for(int i=0;i<4;i++){
            radioButton[i] = new RadioButton(Constants.CURRENT_CONTEXT);

            radioButton[i].setText(AllAnswers.get(i));
            radioButton[i].setTextColor(Color.WHITE);
            radioButton[i].setTextSize(20);
            radioButton[i].setId(i);
            radioGroup.addView(radioButton[i]);
        }

        final LinearLayout linearLayout=new LinearLayout(Constants.CURRENT_CONTEXT);

        if(kindOfQuestion.equals("math")){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }

        }

        ViewGroup.LayoutParams layoutParams  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        linearLayout.addView(textViewTitle, layoutParamsTitle);
        linearLayout.addView(textView,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        ,ViewGroup.LayoutParams.WRAP_CONTENT,0));
        linearLayout.addView(radioGroup, layoutParamsGroup);
        alertDialog.setView(linearLayout);


        final AlertDialog alert = alertDialog.create();
        alert.show();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(!isAddedToScore) {
                    String userAnswer = radioButton[checkedId].getText().toString();
                    if (userAnswer.equals(RightAnswer)) {
                        isAddedToScore = true;
                        if(mediaPlayer!=null){
                            mediaPlayer.release();
                        }
                        mediaPlayer =MediaPlayer.create(Constants.CURRENT_CONTEXT,R.raw.smb_coin);
                        mediaPlayer.start();

                        CurrentScore += 1;
                        group.setBackgroundColor(Color.GREEN);


                        linearLayout.setBackgroundColor(Color.GREEN);

                        YoYo.with(Techniques.Tada)
                                .duration(800)
                                .repeat(3)
                                .playOn(group);

                        YoYo.with(Techniques.Flash)
                                .duration(800)
                                .repeat(3)
                                .playOn(radioButton[checkedId]);


                    } else {
                        isAddedToScore = true;
                        if(mediaPlayer!=null){
                            mediaPlayer.release();
                        }
                        mediaPlayer =MediaPlayer.create(Constants.CURRENT_CONTEXT,R.raw.smb_bowserfalls);
                        mediaPlayer.start();

                        CurrentScore -= 1;
                        group.setBackgroundColor(Color.RED);
                        linearLayout.setBackgroundColor(Color.RED);
                        YoYo.with(Techniques.Flash)
                                .duration(800)
                                .repeat(3)
                                .playOn(group);



                    }
                }


            }
        });


         new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished/1000+"");
            }

            @Override
            public void onFinish() {

                alert.dismiss();
                isGameOver = false;
                isNewQuestion = false;
                orientationData.NewGame();
            }
        }.start();





    }

    public void KnowledgeDialog(String KnowledgeSentence){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer =MediaPlayer.create(Constants.CURRENT_CONTEXT,R.raw.smb_coin);
        mediaPlayer.start();

        CurrentScore+=1;

        final AlertDialog.Builder alertDialog=
                new AlertDialog.Builder(Constants.CURRENT_CONTEXT,R.style.AlertDialogCustomText).setCancelable(false);


        final TextView textViewTitle =new TextView(Constants.CURRENT_CONTEXT);
        textViewTitle.setText(KnowledgeSentence);
        textViewTitle.setTextColor(Color.WHITE);
        textViewTitle.setTextSize(20);

        textViewTitle.setGravity(Gravity.CENTER_HORIZONTAL);


        final LinearLayout linearLayout=new LinearLayout(Constants.CURRENT_CONTEXT);
        ViewGroup.LayoutParams layoutParams  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        linearLayout.addView(textViewTitle,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        ,ViewGroup.LayoutParams.WRAP_CONTENT,0));


        alertDialog.setView(linearLayout);

        alertDialog.setPositiveButton(R.string.answer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //check answer and update score;



                dialog.dismiss();
                orientationData.NewGame();
                isNewQuestion=false;

            }
        });

        final AlertDialog alert = alertDialog.create();
        alert.show();



        alert.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundResource(R.drawable.button_light);
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);

    }

    public void GameOverDialog(){

        if(mediaPlayer!=null){
            mediaPlayer.release();
        }

        mediaPlayer =MediaPlayer.create(Constants.CURRENT_CONTEXT,R.raw.smb_warning);
        mediaPlayer.start();




       // Particle particle = new Particle(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2);
        //particle.move();

        final AlertDialog.Builder alertDialog=
                new AlertDialog.Builder(Constants.CURRENT_CONTEXT,R.style.AlertDialogGameOver).setCancelable(false);




        final TextView textView =new TextViewCelebrate(Constants.CURRENT_CONTEXT);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(Constants.CURRENT_CONTEXT.getResources().getString(R.string.boom));
        final TextView textView2 =new TextViewCelebrate(Constants.CURRENT_CONTEXT);
        textView2.setTextColor(Color.WHITE);
        textView2.setTextSize(40);
        textView2.setGravity(Gravity.CENTER_HORIZONTAL);
        textView2.setText(Constants.CURRENT_CONTEXT.getResources().getString(R.string.points_lost));

        final TextView textViewCounter =new TextViewCelebrate(Constants.CURRENT_CONTEXT);
        textViewCounter.setTextColor(Color.RED);
        textViewCounter.setTextSize(30);
        textViewCounter.setGravity(Gravity.CENTER_HORIZONTAL);



        final LinearLayout linearLayout=new LinearLayout(Constants.CURRENT_CONTEXT);
        ViewGroup.LayoutParams layoutParams  = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        linearLayout.addView(textView,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        ,ViewGroup.LayoutParams.WRAP_CONTENT,0));
        linearLayout.addView(textView2,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        ,ViewGroup.LayoutParams.WRAP_CONTENT,0));

        linearLayout.addView(textViewCounter,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        ,ViewGroup.LayoutParams.WRAP_CONTENT,0));

        alertDialog.setView(linearLayout);


        final AlertDialog alert = alertDialog.create();
        alert.show();
       new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewCounter.setText(""+ millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                    alert.dismiss();
                    isGameOver = false;
                    resetGame();
                    orientationData.NewGame();





            }
        }.start();

    }


    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }


    public void CompleteLettersDialog(final String RightAnswer, final String Question){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer =MediaPlayer.create(Constants.CURRENT_CONTEXT,R.raw.smb_vine);
        mediaPlayer.start();
        isAddedToScore = false;
        final AlertDialog.Builder alertDialog=
                new AlertDialog.Builder(Constants.CURRENT_CONTEXT,R.style.AlertDialogCustomComplete).setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) Constants.CURRENT_CONTEXT.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.layout_build_answer,null);

        TextView textViewQuestion = view.findViewById(R.id.question);
        TextView TextViewAnswer = view.findViewById(R.id.answer);
        FlowLayout linearLayoutOfLetters = view.findViewById(R.id.layout_letters);
        textViewQuestion.setText(Question);
        TextViewAnswer.setText("");
        List<String> arrayOfLetters = new ArrayList<>();

        maxPresses = RightAnswer.toCharArray().length;
        currentNumOfPresses = 0 ;
        for(char c : RightAnswer.toCharArray() ){

            arrayOfLetters.add(c+"");
        }
        Collections.shuffle(arrayOfLetters);

        for(String s : arrayOfLetters){

            AddView(linearLayoutOfLetters,s,TextViewAnswer,RightAnswer);
        }

        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isGameOver = false;
                isNewQuestion = false;
                orientationData.NewGame();
            }
        });

        alertDialog.setView(view);

        final AlertDialog alert = alertDialog.create();
        alert.show();

        alert.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundResource(R.drawable.button_light);
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);








    }

    private void AddView(final FlowLayout linearLayoutOfLetters, final String s, final TextView TextViewAnswer, final String RightAnswer) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.bottomMargin = 5;
        layoutParams.topMargin = 5;

        final TextView textView = new TextView(Constants.CURRENT_CONTEXT);
        textView.setLayoutParams(layoutParams);
        textView.setText(s);
        textView.setPadding(5,5,5,5);
        textView.setTextColor(Color.BLUE);
        textView.setTextSize(40);
        textView.setBackground(Constants.CURRENT_CONTEXT.getResources().getDrawable(R.drawable.button_letter));
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setFocusable(true);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(currentNumOfPresses < maxPresses ){
                    currentNumOfPresses+=1;
                    TextViewAnswer.setText(TextViewAnswer.getText().toString() + s);

                    textView.animate().alpha(0).setDuration(300);

                    if(currentNumOfPresses == maxPresses){
                        ValidateAnswer(RightAnswer, TextViewAnswer.getText().toString(), TextViewAnswer);
                    }
                }

            }
        });

        linearLayoutOfLetters.addView(textView);

    }

    private void ValidateAnswer(String RightAnswer, String UserAnswer, TextView TextViewAnswer) {

        YoYo.with(Techniques.Flash).repeat(3).duration(800).playOn(TextViewAnswer);
        if(RightAnswer.equals(UserAnswer)){

            TextViewAnswer.setTextColor(Color.GREEN);
            TextViewAnswer.setText(Constants.CURRENT_CONTEXT.getResources().getString(R.string.good));
            CurrentScore +=2;


        }else{

            TextViewAnswer.setTextColor(Color.RED);
            TextViewAnswer.setText(Constants.CURRENT_CONTEXT.getResources().getString(R.string.bad));
            CurrentScore -=1;
        }
    }


}
