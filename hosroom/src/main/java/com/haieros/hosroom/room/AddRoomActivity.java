package com.haieros.hosroom.room;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haieros.hosroom.R;
import com.haieros.hosroom.base.BaseAnimActivity;
import com.haieros.hosroom.bean.RoomBean;
import com.haieros.hosroom.utils.Logger;

import butterknife.BindView;

public class AddRoomActivity extends BaseAnimActivity {

    private static final String TAG = AddRoomActivity.class.getSimpleName();
    private RoomDataRepo mRoomDataRepo;
    @BindView(R.id.kang_addroom_et)
    EditText kang_addroom_et;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_room;
    }

    @Override
    protected void initViews() {
        Logger.e(TAG, "AddRoomActivity--->initViews--->:");
        kang_title_left.setImageResource(R.drawable.icon_black_24dp);
        kang_title_menu.setImageResource(R.drawable.icon_confirm_24dp);
        kang_title_tabs.setVisibility(View.GONE);
        kang_title_title.setVisibility(View.VISIBLE);
        kang_title_title.setText(getResources().getString(R.string.kang_add_room));
        kang_title_title.setTextColor(Color.WHITE);
    }

    @Override
    protected void initData() {
        Logger.e(TAG, "AddRoomActivity--->initData--->:");
        mRoomDataRepo = RoomDataRepo.getInstance(this);
    }

    @Override
    protected void onClickOther(View view) {
        switch (view.getId()) {
            case R.id.kang_title_menu:
                String name = kang_addroom_et.getText().toString().trim();
                boolean result = mRoomDataRepo.checkNameForDuplicate(name);

                if(!result) {
                    Toast.makeText(AddRoomActivity.this, "房间名不能重复", Toast.LENGTH_SHORT).show();
                    return;
                }
                RoomBean roomBean = new RoomBean();
                roomBean.setRoomName(name);
                mRoomDataRepo.setRoomData(roomBean);
                this.setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
