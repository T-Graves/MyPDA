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

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_new_task).setVisible(false);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
