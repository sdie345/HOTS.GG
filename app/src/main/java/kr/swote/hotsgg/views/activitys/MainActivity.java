package kr.swote.hotsgg.views.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.views.fragments.HeroFragment;
import kr.swote.hotsgg.views.fragments.SearchFragment;
import kr.swote.hotsgg.views.fragments.StaticFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    int backKeyPressedTime = 200;
    Fragment registedFragment[] = new Fragment[3];
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registedFragment[0] = new SearchFragment();
        registedFragment[1] = new HeroFragment();
        registedFragment[2] = new StaticFragment();
        replaceFragment(0);
        toast = Toast.makeText(this, R.string.press_back_button, Toast.LENGTH_SHORT);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_search :
                    replaceFragment(0);
                    return true;
                case R.id.nav_hero :
                    replaceFragment(1);
                    return true;
                case R.id.nav_static :
                    replaceFragment(2);
                    return true;
            }
            return false;
        }
    };

    private void replaceFragment(int idx) {
        FragmentTransaction fragmnTrans = getSupportFragmentManager().beginTransaction();
        fragmnTrans.replace(R.id.main_container, registedFragment[idx]).commit();
    }

    @Override
    public void onBackPressed() {
        int delay = 500;

        if (System.currentTimeMillis() > backKeyPressedTime + delay) {
            toast.show();
        } else {
            toast.cancel();
            this.finish();
        }
    }
}
