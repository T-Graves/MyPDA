package com.gravestristan.mypda;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity that first runs and holds all other fragments used in the app.
 * This class extends FragmentActivityStarter
 */
public class MyPDAMainActivity extends FragmentActivityStarter implements AppStatics{

    /**
     * This method returns a new MainMenuFragment which creates the main fragment that is used.
     * @return The MainMenuFragment.
     */
    @Override
    protected Fragment createFragment(){
        return new MainMenuFragment();
    }

    /**
     * This method inflates the main menu for the app.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method toggles the text of the toggleNotifications action depending on a preference
     * saved by the user.
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
     * This method handles what to do when an option is pressed.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                AboutAndHelpFragment aboutAndHelpFragment = new AboutAndHelpFragment();
                transaction.replace(R.id.fragmentContainer, aboutAndHelpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            case R.id.action_toggle_notifications:
                Boolean shouldStartAlarm = !ScheduleNotificationService.isServiceAlarmOn(this);
                ScheduleNotificationService.setServiceAlarm(this, shouldStartAlarm);
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("notify", shouldStartAlarm);
                editor.commit();

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    this.invalidateOptionsMenu();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is used to hide the keyboard when the user clicks out of an edit text view.
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
