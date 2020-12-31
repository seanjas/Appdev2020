package com.example.selection;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Activity5 extends BaseActivity {
    EditText txtPlace1,txtPlace2,txtPlace3,txtPlace4,txtPlace5,txtPlace6,txtStart1,txtStart2,txtStart3,txtStart4,txtStart5,txtStart6,txtName,txtxAmounts;
    Button btnPlace,btnStart;
    TextView lblPrize,lblWin;

    Random rnd;
    int[] lottobet = new int[6];
    int[] lottoresult = new int[6];
    EditText[] resultBalls;
    ArrayList<String> LottoBalls;
    InputFilterMinMaxInteger numfilter = new InputFilterMinMaxInteger(1,58);
    long startTime;

    Handler myhandler = new Handler();
    int lottoIndex =0;
    final double jackpot =50000000;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_randomizer;
    }

    @Override
    protected String getActivityName() {
        return "Activity 5";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LottoBalls = new ArrayList<>();

        rnd = new Random();

        txtName =findViewById(R.id.txtName);
        txtxAmounts=findViewById(R.id.txtAmount);
        btnStart=findViewById(R.id.btnStart);
        btnPlace=findViewById(R.id.btnPlace);
        txtPlace1=findViewById(R.id.txtPlace1);
        txtPlace2=findViewById(R.id.txtPlace2);
        txtPlace3=findViewById(R.id.txtPlace3);
        txtPlace4=findViewById(R.id.txtPlace4);
        txtPlace5=findViewById(R.id.txtPlace5);
        txtPlace6=findViewById(R.id.txtPlace6);
        lblPrize=findViewById(R.id.lblPrize);
        lblWin=findViewById(R.id.lblWin);

        txtStart1=findViewById(R.id.txtStart1);
        txtStart2=findViewById(R.id.txtStart2);
        txtStart3=findViewById(R.id.txtStart3);
        txtStart4=findViewById(R.id.txtStart4);
        txtStart5=findViewById(R.id.txtStart5);
        txtStart6=findViewById(R.id.txtStart6);

        // Add filter to place bet boxes
        txtPlace1.setFilters(new InputFilter[]{numfilter});
        txtPlace2.setFilters(new InputFilter[]{numfilter});
        txtPlace3.setFilters(new InputFilter[]{numfilter});
        txtPlace4.setFilters(new InputFilter[]{numfilter});
        txtPlace5.setFilters(new InputFilter[]{numfilter});
        txtPlace6.setFilters(new InputFilter[]{numfilter});

        resultBalls = new EditText[]{txtStart1,txtStart2,txtStart3,txtStart4,txtStart5,txtStart6};
        btnPlace.setOnClickListener(v-> {
            lottobet[0]=Integer.parseInt(txtPlace1.getText().toString());
            lottobet[1]=Integer.parseInt(txtPlace2.getText().toString());
            lottobet[2]=Integer.parseInt(txtPlace3.getText().toString());
            lottobet[3]=Integer.parseInt(txtPlace4.getText().toString());
            lottobet[4]=Integer.parseInt(txtPlace5.getText().toString());
            lottobet[5]=Integer.parseInt(txtPlace6.getText().toString());

        });

        btnStart.setOnClickListener(v->{
            if (lottoIndex==0){
                generateLottoBalls();
            }

            startTime =System.currentTimeMillis();
            btnStart.setEnabled(false);
            setRandomInteger(resultBalls[lottoIndex]);
        });

    }
    private void setRandomInteger(EditText et) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime <  2000) {
                    myhandler.postDelayed(this, 100);
                    et.setText(LottoBalls.get(randomNumber(0, LottoBalls.size() - 1)));
                }else{
                    myhandler.removeCallbacks(this);
                    removeLottoBall(et);
                    lottoIndex++;
                    processResult();
                    }
                }
        };
        myhandler.post(run);
    }


    private void generateLottoBalls(){
        LottoBalls.clear();
        for (int i = 1; i <= 58; i++) {
            LottoBalls.add(i + "");
        }
    }
    private void removeLottoBall(EditText et){
        String lottoNumber = et.getText().toString();
        for (int i=0;i<LottoBalls.size();i++){
            if (LottoBalls.get(i).equals(lottoNumber)){
                LottoBalls.remove(i);
                return;
            }

        }
    }
    private void showMessage(String m){
        Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
    }
    private  void  processResult(){
        btnStart.setEnabled(true);
        if (lottoIndex<resultBalls.length){
            btnStart.performClick();
        }else{
            lottoIndex=0;

            lottobet[0]= Integer.parseInt(txtStart1.getText().toString());
            lottobet[1]= Integer.parseInt(txtStart2.getText().toString());
            lottobet[2]= Integer.parseInt(txtStart3.getText().toString());
            lottobet[3]= Integer.parseInt(txtStart4.getText().toString());
            lottobet[4]= Integer.parseInt(txtStart5.getText().toString());
            lottobet[5]= Integer.parseInt(txtStart6.getText().toString());

            int count=0;
            for (int i=0;i<lottobet.length;i++){
                for (int j=0;j<lottoresult.length;j++){
                    if (lottobet[i]==lottoresult[j]){
                        count++;
                    }
                }
            }
            int win= randomNumber(0,5) + (count> 1 ? 1:0);
            lblWin.setText("WINNER: "+win);
            String message ="";
            switch (count){
                case 6:
                    message = String.format("YOUN WON %,3.2f",jackpot / win);
                    break;
                case 5:
                    message = String.format("YOUN WON %,3.2f",jackpot * 0.5 / win);
                    break;
                case 4:
                    message = String.format("YOUN WON %,3.2f",jackpot * .2 / win);
                    break;
                case 3:
                    message = String.format("YOUN WON %,3.2f",5000);
                    break;
                case 2:
                    message = "Play again with 5 lotto panels fixed!";
                    break;
                case 1:
                    message = String.format("YOUN WON %,3.2f",jackpot / win);
                    break;
                default:
                    message="Sorry , you did not win...";
            }
            lblPrize.setText(message);
        }

    }
}