package com.example.front;

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

import com.example.front.data.Appeal;
import com.example.front.data.Data;
import com.example.front.data.Event;
import com.example.front.data.News;
import com.example.front.ui.Map.MapFragment;
import com.example.front.ui.User.UserFragment;
import com.example.front.ui.appeal.AppealFragment;
import com.example.front.ui.bus.FragmentBus;
import com.example.front.ui.event.EventFragment;
import com.example.front.ui.news.NewsFragment;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment =new NewsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.commit();


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        data();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    private void getSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout layout = findViewById(R.id.nav_view);
        if (layout.isDrawerOpen(GravityCompat.START)){
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id){
            case R.id.nav_home:
                fragment =  new MapFragment();
                break;
            case R.id.nav_news:
                fragment = new NewsFragment();
                break;
            case R.id.nav_slideshow:
                fragment = new FragmentBus();
                break;
            case R.id.nav_event:
                fragment = new EventFragment();
                break;
            case R.id.nav_appeal:
                fragment = new AppealFragment();
                break;
            case R.id.nav_user:
                fragment = new UserFragment();
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.commit();
        return true;

    }

    private void data(){
        Appeal appeal = new Appeal("dfdsfsfsdfsdfsdfvcsdc vfsfdvcdvc sdvgsdvds ivfsdbhny vbfsdbvfsd ","sdasdsa dsad","10:54");
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

//    private void Json(){
//        try {
//            JSONObject object = new JSONObject(JsonDataFromAsset("user.json"));
//          /  JSONArray jsonArray = object.getJSONArray("users");
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject userData = jsonArray.getJSONObject(i);
//                name.add(userData.getString("name"));
//                email.add(userData.getString("email"));
//            }
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        HelperAdapter
//
//
//    }
//
//    private String JsonDataFromAsset(String fileName) throws IOException {
//        String json = null;
//        try {
//            InputStream inputStream = getAssets().open(fileName);
//            int sizeOFile = inputStream.available();
//            byte[] bufferData = new byte[sizeOFile];
//            inputStream.read(bufferData);
//            inputStream.close();
//            json = new String(bufferData,"UTF-8");
//        }catch (IOException e){
//            e.printStackTrace();
//            return null;
//       }
//        return json;
//
//    }
}
//
//