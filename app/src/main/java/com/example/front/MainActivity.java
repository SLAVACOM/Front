package com.example.front;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.front.CONST.CONST;
import com.example.front.data.Appeal;
import com.example.front.data.Data;
import com.example.front.data.Event;
import com.example.front.data.News;
import com.example.front.ui.Map.MapFragment;
import com.example.front.ui.User.UserFragment;
import com.example.front.ui.appeal.AppealFragment;
import com.example.front.ui.appeal.MyAppealFragment;
import com.example.front.ui.bus.FragmentBus;
import com.example.front.ui.event.EventFragment;
import com.example.front.ui.my_file.MyFileFragment;
import com.example.front.ui.news.NewsFragment;
import com.google.android.material.navigation.NavigationView;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CONST.API_SET) {
            MapKitFactory.setApiKey((String) BuildConfig.YandexAPIKey);
            MapKitFactory.initialize(this);
            CONST.API_SET=true;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new NewsFragment()).addToBackStack(null).commit();
        data();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout layout = findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)){
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private static void removeAllFragments(FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id){
            case R.id.nav_map:
                fragment =  new MapFragment();
                break;
            case R.id.nav_news:
                fragment = new NewsFragment();

                break;
            case R.id.nav_bus:
                fragment = new FragmentBus();
                break;
            case R.id.nav_event:
                fragment = new EventFragment();
                break;
            case R.id.nav_appeal:
                fragment = new AppealFragment();
                break;
            case R.id.nav_my_appeal:
                fragment = new MyAppealFragment();
                break;
            case R.id.nav_my_file:
                fragment= new MyFileFragment();
                break;
            case R.id.nav_user:
                fragment = new UserFragment();
                break;
            default:
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                return true;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        drawerLayout.closeDrawer(GravityCompat.START);
        removeAllFragments(fragmentManager);
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null).commit();
        return true;

    }


    private void data(){
        Appeal appeal = new Appeal("dfdsfsfsdfsdfsdfvcsdc ","vfsfdvcdvc sdvgsdvds ivfsdbhny vbfsdbvfsd sdasdsa dsad","SLAVACOM","10:54");
        News news = new News("sl[dpskdfghbfgbfchbnfhnfcvhnbfgvghnvnbhvghjngvh","sdjsojn","18:10" +
                "");
        Event event = new Event("shjngvh","sdhgjggggggggggggggggggggjsojn","18:10" +
                "");
        Data.NEWS_LIST.add(news);
        Data.NEWS_LIST.add(news);
        Data.NEWS_LIST.add(news);
        Data.NEWS_LIST.add(news);
        Data.EVENT_LIST.add(event);
        Data.EVENT_LIST.add(event);
        Data.EVENT_LIST.add(event);
        Data.EVENT_LIST.add(event);
        Data.APPEALS_LIST.add(appeal);
        Data.APPEALS_LIST.add(appeal);
        Data.APPEALS_LIST.add(appeal);
        Data.APPEALS_LIST.add(appeal);
    }


}
