package com.zty.therapist.ui.activity.home;

import android.view.View;

import com.zty.therapist.R;
import com.zty.therapist.base.BaseActivity;

/**
 * Created by tianyu on 2017/1/2.
 */

public class SendHouseRentActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getContentView() {
        return R.layout.activity_send_house_rent;
    }

    @Override
    protected void initData() {
        title.setText("房屋租赁");
        left.setBackgroundResource(R.mipmap.ic_back);
        left.setOnClickListener(this);
        right.setText("发布");
        right.setOnClickListener(this);

    }

    @Override
    public void onFailureCallback(int requestCode, String errorMsg) {

    }

    @Override
    public void onSuccessCallback(int requestCode, String response) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleLeft:
                finish();
                break;
            case R.id.titleRight:
                break;
        }
    }
}
