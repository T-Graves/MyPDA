package com.gravestristan.mypda;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

/**
 *
 */
public class MyPDAMainActivity extends SingleFragmentActivity implements AppStatics{

    /**
     *
     * @return
     */
    @Override
    protected Fragment createFragment(){
        return new MainMenuFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
