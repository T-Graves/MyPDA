package com.gravestristan.mypda;

import android.support.v4.app.Fragment;

public class MyPDAMainActivity extends SingleFragmentActivity implements AppStatics{

    @Override
    protected Fragment createFragment(){
        return new MainMenuFragment();
    }
}
