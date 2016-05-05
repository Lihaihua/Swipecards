package com.lorentzos.swipecards;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/4.
 */
public class MyActivity1 extends Activity implements View.OnClickListener{
    private List<Map<String, Object>> mData;
    private MyAdapter myAdapter;
    private int i;
    private SwipeFlingAdapterView flingContainer;
    private Button leftBtn;
    private Button rightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my1);

        flingContainer = (SwipeFlingAdapterView)findViewById(R.id.frame1);
        leftBtn = (Button)findViewById(R.id.left);
        rightBtn = (Button)findViewById(R.id.right);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);

        mData = getData();

        final MyAdapter myAdapter = new MyAdapter(this, mData);
        flingContainer.setAdapter(myAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                mData.remove(0);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(MyActivity1.this, "Left");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(MyActivity1.this, "Right");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("img", R.drawable.ic_launcher);
                map.put("info", "eeeeeee " + i);
                mData.add(map);
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MyActivity1.this, "Clicked");
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> getData(){

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.ic_launcher);
        map.put("info", "跆拳道");
        list.add(map);

        map.put("img", R.drawable.ic_launcher);
        map.put("info", "跆拳道1");
        list.add(map);

        map.put("img", R.drawable.ic_launcher);
        map.put("info", "跆拳道2");
        list.add(map);

//        for(int i=0;i<5;i++)
//        {
//            map = new HashMap<String, Object>();
//            map.put("img", R.drawable.ic_launcher);
//            map.put("info", "跆拳道");
//            list.add(map);
//        }

        return list;
    }

    public void right(){
        flingContainer.getTopCardListener().selectRight();
    }

    public void left(){
        flingContainer.getTopCardListener().selectLeft();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left:
                left();
                break;
            case R.id.right:
                right();
                break;
            default:
                break;
        }
    }
}
