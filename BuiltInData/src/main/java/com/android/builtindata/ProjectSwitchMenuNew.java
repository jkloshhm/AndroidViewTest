package com.android.builtindata;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class ProjectSwitchMenuNew extends Activity {

    private String[] Animation = new String[]{"welcome","android","solking","fotola","vivo",
            "oppo","lg","huawei","sony","samung","htc"};
    private String[] ROM = new String[]{"normal", "128G", "64G", "32G", "16G", "8G", "4G"};
    private String[] RAM = new String[]{"normal", "4G", "3G", "2G", "1G", "512M"};
    private String[] MODEL = new String[]{"5X"};
    private String[] SINGLE = new String[]{"normal", "双3G", "双4G LTE", "单4G LTE"};
    private String[] PLATFORM = new String[]{"MT6580"};

    //private MyGridViewAdapter mAdapter;
    /*private MyGridView myGridView;
    private MyGridView animationGridView, romGridView,
            ramGridView, modelGridView, singleGridView,
            platformGridView;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_switch_menu_new);


        /*myGridView = (MyGridView)findViewById(R.id.switch_animation);
        mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(Animation, 2);//传数组
        myGridView.setAdapter(mAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });*/

        initAnimation();
        initRom();
        initRam();
        initModel();
        initSingle();
        initPlatform();
    }


    private void initAnimation(){


        MyGridView animationGridView = (MyGridView)findViewById(R.id.single_choice_view_animation);

        final  MyGridViewAdapter mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(Animation, 2);//传数组
        animationGridView.setAdapter(mAdapter);
        animationGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });

        Log.i("jkl","animation==");
    }

    private void initRom(){
        MyGridView romGridView = (MyGridView)findViewById(R.id.single_choice_view_rom);
        final  MyGridViewAdapter mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(ROM, 0);//传数组
        romGridView.setAdapter(mAdapter);
        romGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });
        Log.i("jkl","animation==");
    }
    private void initRam(){
        MyGridView ramGridView = (MyGridView)findViewById(R.id.single_choice_view_ram);
        final  MyGridViewAdapter mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(RAM, 0);//传数组
        ramGridView.setAdapter(mAdapter);
        ramGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });
        Log.i("jkl","animation==");
    }


    private void initModel(){
        MyGridView modelGridView = (MyGridView)findViewById(R.id.single_choice_view_model);
        final  MyGridViewAdapter mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(MODEL,0);//传数组
        modelGridView.setAdapter(mAdapter);
        modelGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });
        Log.i("jkl","animation==");
    }


    private void initSingle(){
        MyGridView singleGridView = (MyGridView)findViewById(R.id.single_choice_view_single);
        final  MyGridViewAdapter mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(SINGLE, 0);//传数组
        singleGridView.setAdapter(mAdapter);
        singleGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });
        Log.i("jkl","animation==");
    }


    private void initPlatform(){
        MyGridView platformGridView = (MyGridView)findViewById(R.id.single_choice_view_platform);
        final  MyGridViewAdapter mAdapter = new MyGridViewAdapter(this);
        mAdapter.setData(PLATFORM, 0);//传数组
        platformGridView.setAdapter(mAdapter);
        platformGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                Log.i("jkl","position==="+position);
            }
        });
        Log.i("jkl","animation==");
    }
}
