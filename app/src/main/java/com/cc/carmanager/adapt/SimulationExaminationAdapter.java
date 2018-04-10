package com.cc.carmanager.adapt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.AnswerQuestionActivity;
import com.cc.carmanager.activity.MyErrorQuestionActivity;
import com.cc.carmanager.bean.QuestionListBean;
import com.cc.carmanager.bean.QuestionResultListBean;
import com.cc.carmanager.fragment.NewsFragment;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenc on 2018/4/8.
 */

public class SimulationExaminationAdapter extends PagerAdapter {

    AnswerQuestionActivity mContext;
    // 传递过来的页面view的集合
    List<View> viewItems;
    // 每个item的页面view
    View convertView;
    // 传递过来的所有数据
    List<QuestionListBean.QuestionItemBean> dataItems;

    private Map<String, String > ans = new HashMap<>();

    private Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();


    boolean isNext = false;

    public SimulationExaminationAdapter(AnswerQuestionActivity context, List<View> viewItems, List<QuestionListBean.QuestionItemBean> dataItems) {
        mContext = context;
        this.viewItems = viewItems;
        this.dataItems = dataItems;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position));
    }
    /**
     * 监听
     */
    public Object instantiateItem(ViewGroup container, final int position) {
        final SimulationExaminationAdapter.ViewHolder holder = new SimulationExaminationAdapter.ViewHolder();
        convertView = viewItems.get(position);
        holder.questionType = (TextView) convertView.findViewById(R.id.activity_prepare_test_no);
        holder.question = (TextView) convertView.findViewById(R.id.activity_prepare_test_question);
        holder.previousBtn = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_upLayout);
        holder.nextBtn = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_nextLayout);
        holder.nextText = (TextView) convertView.findViewById(R.id.menu_bottom_nextTV);
        holder.errorBtn = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_errorLayout);
        holder.totalText = (TextView) convertView.findViewById(R.id.activity_prepare_test_totalTv);
        holder.nextImage = (ImageView) convertView.findViewById(R.id.menu_bottom_nextIV);
        holder.wrongLayout = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_wrongLayout);
        holder.explaindetailTv = (TextView) convertView.findViewById(R.id.activity_prepare_test_explaindetail);
        holder.layoutA = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_a);
        holder.layoutB = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_b);
        holder.layoutC = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_c);
        holder.layoutD = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_d);
        holder.layoutE = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_e);
        holder.ivA = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_a);
        holder.ivB = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_b);
        holder.ivC = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_c);
        holder.ivD = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_d);
        holder.ivE = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_e);
        holder.tvA = (TextView) convertView.findViewById(R.id.vote_submit_select_text_a);
        holder.tvB = (TextView) convertView.findViewById(R.id.vote_submit_select_text_b);
        holder.tvC = (TextView) convertView.findViewById(R.id.vote_submit_select_text_c);
        holder.tvD = (TextView) convertView.findViewById(R.id.vote_submit_select_text_d);
        holder.tvE = (TextView) convertView.findViewById(R.id.vote_submit_select_text_e);
        holder.ivA_ = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_a_);
        holder.ivB_ = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_b_);
        holder.ivC_ = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_c_);
        holder.ivD_ = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_d_);
        holder.ivE_ = (ImageView) convertView.findViewById(R.id.vote_submit_select_image_e_);

        holder.errertv = (TextView) convertView.findViewById(R.id.menu_bottom_errorTV);
        holder.totalText.setText(position + 1 + "/" + dataItems.size());
        // TODO: 2017/1/5

        //观看视频响应事件
        holder.errorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO: 2017/1/4
                Toast.makeText(mContext, "暂无视频！", Toast.LENGTH_SHORT).show();
            }
        });

//        if (dataItems.get(position).getA().equals("")) {
//            holder.layoutA.setVisibility(View.GONE);
//        }
//        if (dataItems.get(position).getB().equals("")) {
//            holder.layoutB.setVisibility(View.GONE);
//        }
        if (dataItems.get(position).getC().equals("")) {
            holder.layoutC.setVisibility(View.GONE);
        }
        if (dataItems.get(position).getD().equals("")) {
            holder.layoutD.setVisibility(View.GONE);
        }
        holder.layoutE.setVisibility(View.GONE);
        holder.errertv.setText("观看视频");

        //判断是否文字图片题目
        //文字题目
        holder.ivA_.setVisibility(View.GONE);
        holder.ivB_.setVisibility(View.GONE);
        holder.ivC_.setVisibility(View.GONE);
        holder.ivD_.setVisibility(View.GONE);
        holder.ivE_.setVisibility(View.GONE);
        holder.tvA.setVisibility(View.VISIBLE);
        holder.tvB.setVisibility(View.VISIBLE);
        holder.tvC.setVisibility(View.VISIBLE);
        holder.tvD.setVisibility(View.VISIBLE);
        holder.tvE.setVisibility(View.VISIBLE);
        holder.tvA.setText("A." + dataItems.get(position).getA());
        holder.tvB.setText("B." + dataItems.get(position).getB());
        holder.tvC.setText("C." + dataItems.get(position).getC());
        holder.tvD.setText("D." + dataItems.get(position).getD());
        //判断题型
        if (dataItems.get(position).getType()== 0) {
            //单选题
            // TODO: 2017/1/2
            holder.question.setText("(单选题)" + dataItems.get(position).getQuestionContent());

            View.OnClickListener singleClick = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String answer = "";
                    holder.ivA.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvA.setTextColor(Color.parseColor("#9a9a9a"));
                    holder.ivB.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvB.setTextColor(Color.parseColor("#9a9a9a"));
                    holder.ivC.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvC.setTextColor(Color.parseColor("#9a9a9a"));
                    holder.ivD.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvD.setTextColor(Color.parseColor("#9a9a9a"));
                    holder.ivE.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvE.setTextColor(Color.parseColor("#9a9a9a"));
                    switch (arg0.getId()){
                        case R.id.activity_prepare_test_layout_a:
                            answer = "A";
                            holder.ivA.setImageResource(R.drawable.ic_practice_test_select);
                            holder.tvA.setTextColor(Color.parseColor("#2b89e9"));
                            break;
                        case R.id.activity_prepare_test_layout_b:
                            answer = "B";
                            holder.ivB.setImageResource(R.drawable.ic_practice_test_select);
                            holder.tvB.setTextColor(Color.parseColor("#2b89e9"));
                            break;
                        case R.id.activity_prepare_test_layout_c:
                            answer = "C";
                            holder.ivC.setImageResource(R.drawable.ic_practice_test_select);
                            holder.tvC.setTextColor(Color.parseColor("#2b89e9"));
                            break;
                        case R.id.activity_prepare_test_layout_d:
                            answer = "D";
                            holder.ivD.setImageResource(R.drawable.ic_practice_test_select);
                            holder.tvD.setTextColor(Color.parseColor("#2b89e9"));
                            break;
                        case R.id.activity_prepare_test_layout_e:
                            answer = "E";
                            holder.ivE.setImageResource(R.drawable.ic_practice_test_select);
                            holder.tvE.setTextColor(Color.parseColor("#2b89e9"));
                            break;
                    }
                    ans.put(""+dataItems.get(position).getId(), answer);
                    map.put(position, true);
                }
            };
            holder.layoutA.setOnClickListener(singleClick);
            holder.layoutB.setOnClickListener(singleClick);
            holder.layoutC.setOnClickListener(singleClick);
            holder.layoutD.setOnClickListener(singleClick);
            holder.layoutE.setOnClickListener(singleClick);
        } else {
            //判断题
            // TODO: 2017/1/2
            holder.question.setText("(判断题)" + dataItems.get(position).getQuestionContent());
            holder.tvA.setText("A.对");
            holder.tvB.setText("B.错");

            holder.layoutA.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ans.put(""+dataItems.get(position).getId(), "1");
                    map.put(position, true);
                    holder.ivA.setImageResource(R.drawable.ic_practice_test_select);
                    holder.tvA.setTextColor(Color.parseColor("#2b89e9"));
                    holder.ivB.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvB.setTextColor(Color.parseColor("#9a9a9a"));
                }
            });
            holder.layoutB.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ans.put(""+dataItems.get(position).getId(), "0");
                    map.put(position, true);
                    holder.ivA.setImageResource(R.drawable.ic_practice_test_normal);
                    holder.tvA.setTextColor(Color.parseColor("#9a9a9a"));
                    holder.ivB.setImageResource(R.drawable.ic_practice_test_select);
                    holder.tvB.setTextColor(Color.parseColor("#2b89e9"));
                }
            });
        }

        // 最后一页修改"下一步"按钮文字
        if (position == viewItems.size() - 1) {
            holder.nextText.setText("提交");
            holder.nextImage.setImageResource(R.drawable.vote_submit_finish);
        }
        holder.previousBtn.setOnClickListener(new SimulationExaminationAdapter.LinearOnClickListener(position - 1, false, position, holder));
        holder.nextBtn.setOnClickListener(new SimulationExaminationAdapter.LinearOnClickListener(position + 1, true, position, holder));
        container.addView(viewItems.get(position));
        return viewItems.get(position);
    }

    /**
     * @author 设置上一步和下一步按钮监听
     */
    class LinearOnClickListener implements View.OnClickListener {

        private int mPosition;
        private int mPosition1;
        private boolean mIsNext;
        private SimulationExaminationAdapter.ViewHolder viewHolder;

        public LinearOnClickListener(int position, boolean mIsNext, int position1, SimulationExaminationAdapter.ViewHolder viewHolder) {
            mPosition = position;
            mPosition1 = position1;
            this.viewHolder = viewHolder;
            this.mIsNext = mIsNext;
        }

        @Override
        public void onClick(View v) {
            if (mPosition == viewItems.size()) {
                // TODO: 2017/1/5

                JSONObject jsonObj = new JSONObject(ans);
                HashMap<String, String> res = new HashMap<>();
                res.put("answer", jsonObj.toString());
                VolleyInstance.getVolleyInstance().startJsonObjectPost(NetUrlsSet.URL_QUESTION_COMMIT, res, new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        Log.e("car", resultStr);
                        Gson gson=new Gson();
                        final QuestionResultListBean mRecommendBean=gson.fromJson(resultStr,QuestionResultListBean.class);
                        if(mRecommendBean.isSuccess()){
                            final Dialog dialog = new Dialog(mContext, R.style.dialog);
                            dialog.setContentView(R.layout.dialog_layout);
                            dialog.show();
                            TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
                            TextView content = (TextView) dialog.findViewById(R.id.dialog_content);
                            title.setText("你的提交结果");

                            int wrongSize = 0;
                            final List<QuestionListBean.QuestionItemBean> wrongDatas = new ArrayList<>();
                            for(QuestionResultListBean.QuestionResItemBean res : mRecommendBean.getData().subList(0, mRecommendBean.getData().size()-1)){
                                for(QuestionListBean.QuestionItemBean que : dataItems){
                                    if (!res.isCorrect() && que.getId() == Integer.parseInt(res.getId())) {
                                        que.setRight(res.getAnswer());
                                        que.setSelect(ans.get(""+que.getId()));
                                        wrongDatas.add(que);
                                        wrongSize++;
                                        break;
                                    }
                                }
                            }
                            int score = mRecommendBean.getData().get(mRecommendBean.getData().size()-1).getScore();
                            content.setText(String.format("你一共答错了%d道题，得分%d", wrongSize, score));
                            final Button confirm_btn = (Button) dialog
                                    .findViewById(R.id.dialog_sure);
                            Button cancel_btn = (Button) dialog.findViewById(R.id.dialog_cancle);
                            // TODO: 2017/1/1
                            confirm_btn.setText("查看错题");
                            cancel_btn.setText("保存退出");
                            cancel_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    mContext.finish();
                                }
                            });
                            if(wrongSize > 0){
                                //查看错题
                                confirm_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, MyErrorQuestionActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putSerializable("data", (Serializable)wrongDatas);
                                        intent.putExtras(bundle);
                                        mContext.startActivity(intent);
                                    }
                                });
                            }
                        }else{
                            ToastUtils.makeShortText("新闻加载失败", mContext);
                        }
                    }

                    @Override
                    public void failure() {

                    }
                });
            } else {
                if (mPosition == -1) {
                    Toast.makeText(mContext, "已经是第一页", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //单选
                    if (dataItems.get(mPosition1).getType()==0) {
                        if (mIsNext) {
                            if (!map.containsKey(mPosition1)) {
                                Toast.makeText(mContext, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        isNext = mIsNext;
                        mContext.setCurrentView(mPosition);
                    }  else {
                        if (mIsNext) {
                            if (!map.containsKey(mPosition1)) {
                                Toast.makeText(mContext, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        isNext = mIsNext;
                        mContext.setCurrentView(mPosition);
                    }
                }
            }

        }

    }

    @Override
    public int getCount() {
        if (viewItems == null)
            return 0;
        return viewItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public class ViewHolder {
        TextView errertv;
        TextView questionType;
        TextView question;
        LinearLayout previousBtn, nextBtn, errorBtn;
        TextView nextText;
        TextView totalText;
        ImageView nextImage;
        LinearLayout wrongLayout;
        TextView explaindetailTv;
        LinearLayout layoutA;
        LinearLayout layoutB;
        LinearLayout layoutC;
        LinearLayout layoutD;
        LinearLayout layoutE;
        ImageView ivA;
        ImageView ivB;
        ImageView ivC;
        ImageView ivD;
        ImageView ivE;
        TextView tvA;
        TextView tvB;
        TextView tvC;
        TextView tvD;
        TextView tvE;
        ImageView ivA_;
        ImageView ivB_;
        ImageView ivC_;
        ImageView ivD_;
        ImageView ivE_;
    }

}
