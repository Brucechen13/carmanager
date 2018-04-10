package com.cc.carmanager.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.bean.QuestionListBean;

/**
 * Created by chenc on 2018/4/8.
 */

public class MyErrorQuestionDetailActivity extends BarBaseActivity {

    private ImageView left;
    private TextView title;

    private TextView questionTypeTV;
    private TextView questionNameTV;
    private LinearLayout layoutA;
    private LinearLayout layoutB;
    private LinearLayout layoutC;
    private LinearLayout layoutD;
    private LinearLayout layoutE;
    private ImageView ivA;
    private ImageView ivB;
    private ImageView ivC;
    private ImageView ivD;
    private ImageView ivE;
    private TextView tvA;
    private TextView tvB;
    private TextView tvC;
    private TextView tvD;
    private TextView tvE;
    private ImageView ivA_;
    private ImageView ivB_;
    private ImageView ivC_;
    private ImageView ivD_;
    private ImageView ivE_;
    private LinearLayout wrongLayout;
    private TextView explaindetailTv;

    private QuestionListBean.QuestionItemBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_error_question_detail);


        Bundle bundle = getIntent().getExtras();
        data = (QuestionListBean.QuestionItemBean)bundle.getSerializable("data");

        initView();

        setHeader("我的错题");
    }

    private void initView(){

        questionTypeTV=(TextView) findViewById(R.id.activity_prepare_test_no);
        questionNameTV=(TextView) findViewById(R.id.activity_prepare_test_question);
        layoutA=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_a);
        layoutB=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_b);
        layoutC=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_c);
        layoutD=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_d);
        layoutE=(LinearLayout) findViewById(R.id.activity_prepare_test_layout_e);
        ivA=(ImageView) findViewById(R.id.vote_submit_select_image_a);
        ivB=(ImageView) findViewById(R.id.vote_submit_select_image_b);
        ivC=(ImageView) findViewById(R.id.vote_submit_select_image_c);
        ivD=(ImageView) findViewById(R.id.vote_submit_select_image_d);
        ivE=(ImageView) findViewById(R.id.vote_submit_select_image_e);
        tvA=(TextView) findViewById(R.id.vote_submit_select_text_a);
        tvB=(TextView) findViewById(R.id.vote_submit_select_text_b);
        tvC=(TextView) findViewById(R.id.vote_submit_select_text_c);
        tvD=(TextView) findViewById(R.id.vote_submit_select_text_d);
        tvE=(TextView) findViewById(R.id.vote_submit_select_text_e);
        ivA_=(ImageView) findViewById(R.id.vote_submit_select_image_a_);
        ivB_=(ImageView) findViewById(R.id.vote_submit_select_image_b_);
        ivC_=(ImageView) findViewById(R.id.vote_submit_select_image_c_);
        ivD_=(ImageView) findViewById(R.id.vote_submit_select_image_d_);
        ivE_=(ImageView) findViewById(R.id.vote_submit_select_image_e_);
        wrongLayout=(LinearLayout) findViewById(R.id.activity_prepare_test_wrongLayout);
        explaindetailTv=(TextView) findViewById(R.id.activity_prepare_test_explaindetail);

        questionNameTV.setText(""+data.getQuestionContent());

        if(data.getC().equals("")){
            layoutC.setVisibility(View.GONE);
        }if(data.getD().equals("")){
            layoutD.setVisibility(View.GONE);
        }

        //文字题目
        ivA_.setVisibility(View.GONE);
        ivB_.setVisibility(View.GONE);
        ivC_.setVisibility(View.GONE);
        ivD_.setVisibility(View.GONE);
        ivE_.setVisibility(View.GONE);
        tvA.setVisibility(View.VISIBLE);
        tvB.setVisibility(View.VISIBLE);
        tvC.setVisibility(View.VISIBLE);
        tvD.setVisibility(View.VISIBLE);
        tvE.setVisibility(View.VISIBLE);
        tvA.setText("A." + data.getA());
        tvB.setText("B." + data.getB());
        tvC.setText("C." + data.getC());
        tvD.setText("D." + data.getD());

        if(data.getType() == 0){
            questionTypeTV.setText("(单选题)");
            //显示正确选项
            if(data.getRight().contains("A")){
                ivA.setImageResource(R.drawable.ic_practice_test_right);
                tvA.setTextColor(Color.parseColor("#61bc31"));
            }else if(data.getRight().contains("B")){
                ivB.setImageResource(R.drawable.ic_practice_test_right);
                tvB.setTextColor(Color.parseColor("#61bc31"));
            }else if(data.getRight().contains("C")){
                ivC.setImageResource(R.drawable.ic_practice_test_right);
                tvC.setTextColor(Color.parseColor("#61bc31"));
            }else if(data.getRight().contains("D")){
                ivD.setImageResource(R.drawable.ic_practice_test_right);
                tvD.setTextColor(Color.parseColor("#61bc31"));
            }

            if(data.getSelect().contains("A")){
                ivA.setImageResource(R.drawable.ic_practice_test_wrong);
                tvA.setTextColor(Color.parseColor("#d53235"));
            }else if(data.getSelect().contains("B")){
                ivB.setImageResource(R.drawable.ic_practice_test_wrong);
                tvB.setTextColor(Color.parseColor("#d53235"));
            }else if(data.getSelect().contains("C")){
                ivC.setImageResource(R.drawable.ic_practice_test_wrong);
                tvC.setTextColor(Color.parseColor("#d53235"));
            }else if(data.getSelect().contains("D")){
                ivD.setImageResource(R.drawable.ic_practice_test_wrong);
                tvD.setTextColor(Color.parseColor("#d53235"));
            }

        }else if(data.getType() == 1){
            questionTypeTV.setText("(判断题)");
            tvA.setText("A.对");
            tvB.setText("B.错");
            //显示正确选项
            if(data.getRight().contains("1")){
                ivA.setImageResource(R.drawable.ic_practice_test_right);
                tvA.setTextColor(Color.parseColor("#61bc31"));
            }else if(data.getRight().contains("0")){
                ivB.setImageResource(R.drawable.ic_practice_test_right);
                tvB.setTextColor(Color.parseColor("#61bc31"));
            }
            if(data.getSelect().contains("1")){
                ivA.setImageResource(R.drawable.ic_practice_test_wrong);
                tvA.setTextColor(Color.parseColor("#d53235"));
            }else if(data.getSelect().contains("0")){
                ivB.setImageResource(R.drawable.ic_practice_test_wrong);
                tvB.setTextColor(Color.parseColor("#d53235"));
            }

            layoutC.setVisibility(View.GONE);
            layoutD.setVisibility(View.GONE);
        }

        wrongLayout.setVisibility(View.GONE);
    }

}
