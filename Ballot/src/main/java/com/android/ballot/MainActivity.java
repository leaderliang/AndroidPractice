package com.android.ballot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 一共7个单位，每个单位推荐1个班级参赛，
 * 其余班级抽选一个参赛（每个单位一共2个班级参赛），
 * 要求随机产生一个单位的推荐班级和抽选班级，
 * 并有相对的前后上场顺序
 *
 * 随后随机再把分好的组打乱，再生成七组数据，推荐和抽选可无序
 * <p>
 * <p>
 * 单位	推荐班级	抽选班级
 * -------------------------------------------
 * 1村	7班	    1班、2班、3班、4班、5班、6班、8班
 * 2村	1班	    2班、3班、4班、5班、6班
 * 3村	4班	    1班、2班、3班、5班、6班
 * 突	3班	    1班、2班
 * 火	3班	    1班、2班、4班、5班、6班
 * 保	10班	1班、2班、3班、6班、9班、11班、12班
 * 营	1班
 *
 * @author devliang
 */
public class MainActivity extends AppCompatActivity {
    public static final String IS_BALLOT = "IS_BALLOT";
    public static final String IS_RECOMMEND = "IS_RECOMMEND";
    private List<Unit.UnitBean> mUnitBeanList;
    List<Unit.ClassBean> recommendList;
    List<Unit.ClassBean> ballotList;
    private LinkedHashMap<String, List<Unit.ClassBean>> mLinkedHashMap = new LinkedHashMap<>();
    private TextView mTvShowAllClass, mTvShowRandomGroup, mTvShowRandomNextGrop;
    private List<StringBuilder>  mSingleList;
    int clickIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvShowAllClass = findViewById(R.id.tv_show_all_class);
        mTvShowRandomGroup = findViewById(R.id.tv_show_random_group);
        mTvShowRandomNextGrop = findViewById(R.id.tv_show_random_next_group);

        initData();



//        showRandomAllGroup();
    }


    private void initData() {
        if(mLinkedHashMap != null){
            mLinkedHashMap.clear();
        }
        mUnitBeanList = parseJson();
        recommendList = new ArrayList<>();
        ballotList = new ArrayList<>();
        for (int i = 0; i < mUnitBeanList.size(); i++) {
            // 单位名称
            String unitName = mUnitBeanList.get(i).getUnitName();
            List<Unit.ClassBean> classList = mUnitBeanList.get(i).getClassList();
            String recommendName;
            String ballotName;
            List<Unit.ClassBean> classBean = new ArrayList<>();
            for (int j = 0; j < classList.size(); j++) {
                if(classList.get(j).getBallotType().equals(IS_RECOMMEND)){
                    // 推荐类型的班级
//                   recommendName = classList.get(j).getClassName();
                    classBean.add(classList.get(j));

                    classList.get(j).setUnitName(unitName);
                    recommendList.add(classList.get(j));
                    classList.remove(j);
                    break;
                }
            }
            if(classList.size() > 0) {
                Random random = new Random();
                int randomNum = random.nextInt(classList.size());
//             ballotName = classList.get(randomNum).getClassName();
                classList.get(randomNum).setBallotType(IS_BALLOT);
                classBean.add(classList.get(randomNum));
                classList.get(randomNum).setUnitName(unitName);
                ballotList.add(classList.get(randomNum));
            }
            mLinkedHashMap.put(unitName, classBean);

        }

        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, List<Unit.ClassBean>> entry : mLinkedHashMap.entrySet()) {
            builder.append(entry.getKey());
            builder.append("\n");
            List<Unit.ClassBean> classBeanList = entry.getValue();
            for (int i = 0; i < classBeanList.size(); i++) {
                if(i == 0){
                    builder.append("推荐类型：");
                }else{
                    builder.append("抽选类型：");
                }
                builder.append(classBeanList.get(i).getClassName());
                builder.append("、");
                System.out.println("------key:" + entry.getKey() + "   value:" + classBeanList.get(i).getClassName());
            }
            builder.append("\n");
        }

        mTvShowAllClass.setText(builder.toString());


        Random random = new Random();
        Collections.shuffle(recommendList, random);
        Collections.shuffle(ballotList, random);
        System.out.println(mLinkedHashMap.toString());
        for (int i = 0; i < recommendList.size(); i++) {
            if (ballotList.size() <= i) {
                showRandomAllGroup();
                break;
            }
            if(recommendList.get(i).getUnitName().equals(ballotList.get(i).getUnitName())){
                initData();
                break;
            }
        }
    }


    public List<Unit.UnitBean> parseJson() {
        String mContentJson = getJson();
        try {
            List<Unit.UnitBean> unitLists = JSON.parseObject(mContentJson, Unit.class).getUnit();
            return unitLists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJson() {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = getAssets().open("unit.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void showAllClass(View view){
        initData();
    }

    /**
     * 查看打乱后产生的所有数据
     * @param view
     */
    public void showRandomAllGroup(View view){
//        ballotList
//        recommendList
        if(recommendList == null || recommendList.size() <= 0){
            Toast.makeText(this, "请先点击👆的查看全部分组", Toast.LENGTH_SHORT).show();
            return;
        }
        mTvShowRandomGroup.setText(showRandomAllGroup().toString());


/*        StringBuilder builder2 = new StringBuilder();
        Collections.shuffle(recommendList);
        for (int i = 0; i < recommendList.size(); i++) {
            builder2.append(recommendList.get(i).getClassName());
        }
        System.out.println(builder2.toString());*/
    }

    /**
     * @return
     */
    private StringBuilder showRandomAllGroup() {
        // 打印数据 start
        StringBuilder builder1 = new StringBuilder();
        for (int i = 0; i < recommendList.size(); i++) {
            builder1.append(recommendList.get(i).getClassName());
        }
        System.out.println(builder1.toString());

        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < ballotList.size(); i++) {
            builder1.append(ballotList.get(i).getClassName());
        }
        System.out.println(builder2.toString());
        // 打印数据 end

        mSingleList = new ArrayList<>();
        StringBuilder builderRandom = new StringBuilder();
        for (int i = 0; i < recommendList.size(); i++) {
            Random randomGroup = new Random();
            int randomNum = randomGroup.nextInt(2);
            StringBuilder singleBuild = new StringBuilder();
            for (int j = 0; j < 2 ; j++) {
                if(j == 1 && randomNum == 0){
                    randomNum = 1;
                }else if(j == 1 && randomNum == 1){
                    randomNum = 0;
                }
                if (randomNum == 0) {
                    // 0 取推荐集合里的数据
                    builderRandom.append(recommendList.get(i).getUnitName());
                    builderRandom.append("--");
                    builderRandom.append(recommendList.get(i).getClassName());
                    builderRandom.append("; ");
                    singleBuild.append(recommendList.get(i).getUnitName()).append("--").append(recommendList.get(i).getClassName()).append(";");
                }
                if (randomNum == 1) {
                    if (ballotList.size() > i) {
                        builderRandom.append(ballotList.get(i).getUnitName());
                        builderRandom.append("--");
                        builderRandom.append(ballotList.get(i).getClassName());
                        builderRandom.append("; ");
                        singleBuild.append(ballotList.get(i).getUnitName()).append("--").append(ballotList.get(i).getClassName()).append(";");
                    }
                }
            }
            builderRandom.append("\n");
            singleBuild.append("\n");
            mSingleList.add(singleBuild);
        }
        return builderRandom;
    }

    /**
     * 查看下组数据
     * @param view
     */
    public void showRandomNextGroup(View view){
//        mTvShowRandomNextGrop
//        showRandomAllGroup();
        if(clickIndex >= mSingleList.size()){
            Toast.makeText(this, "分组数据已展示完毕", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mSingleList.size() <= 0){
             return;
        }
//        mTvShowRandomNextGrop.setText(mSingleList.get(clickIndex));
//        clickIndex++;
        CourseOpeningBranchDialog dialog = new CourseOpeningBranchDialog(this, mSingleList.get(clickIndex));
        dialog.setClickBranchDialogLister(new CourseOpeningBranchDialog.ClickBranchDialogLister() {
            @Override
            public void branchDialogClick() {
//                mTvShowRandomNextGrop.setText(mSingleList.get(clickIndex));
                clickIndex++;
            }
        });
        dialog.showDialog();


    }

    /**
     * 复位
     * @param view
     */
    public void reSet(View view){
        clickIndex = 0;
        mTvShowRandomNextGrop.setText("");
        // 重新打乱，再从对应七组里面出数据
        initData();
        Toast.makeText(this, "复位成功~", Toast.LENGTH_SHORT).show();
    }
}