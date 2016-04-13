package com.gravestristan.mypda;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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
        return true;
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem toggleNotifications = menu.findItem(R.id.action_toggle_notifications);
        if(ScheduleNotificationService.isServiceAlarmOn(this)){
            toggleNotifications.setTitle(R.string.action_disable_notify);
        }else{
            toggleNotifications.setTitle(R.string.action_enable_notify);
        }
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                Toast.makeText(this, "About page coming later", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_toggle_notifications:
                Boolean shouldStartAlarm = !ScheduleNotificationService.isServiceAlarmOn(this);
                ScheduleNotificationService.setServiceAlarm(this, shouldStartAlarm);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    this.invalidateOptionsMenu();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(v instanceof EditText){
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY())){
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
