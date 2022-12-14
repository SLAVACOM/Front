package com.example.front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.front.CONST.CONST;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;
import com.example.front.ui.Map.MapFragment;
import com.example.front.ui.User.UserProfileFragment;
import com.example.front.ui.User.UsersListFragment;
import com.example.front.ui.appeal.AppealFragment;
import com.example.front.ui.request.RequestTypesFragment;
import com.example.front.ui.bus.FragmentBus;
import com.example.front.ui.event.EventFragment;
import com.example.front.ui.hisory.HistoryFragment;
import com.example.front.ui.request.UserRequestsFragment;
import com.example.front.ui.news.NewsFragment;
import com.google.android.material.navigation.NavigationView;
import com.yandex.mapkit.MapKitFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    static MainActivity $instance;

    public static void toast(String toast) {
        if ($instance != null) Toast.makeText($instance, toast, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        DataBASE.token= userToken(getBaseContext());
    }
    public static String userToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.LOGIN_PREFS, 0);
        return sharedPreferences.getString(CONST.USER_TOKEN, null);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ($instance == null) $instance = this;
        if (!CONST.API_SET) {
            MapKitFactory.setApiKey((String) BuildConfig.YandexAPIKey);
            MapKitFactory.initialize(this);
            CONST.API_SET=true;
        }


        setContentView(R.layout.activity_main);

         toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateMenuItems(navigationView);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new NewsFragment()).addToBackStack(null).commit();
        toolbar.setTitle(R.string.news_fragment);


    }

    private void updateMenuItems(NavigationView navigationView) {
        navigationView.getMenu().findItem(R.id.nav_my_appeal).setVisible(!DataBASE.user.isAdmin()&& !DataBASE.user.isLibrarian());
        navigationView.getMenu().findItem(R.id.nav_request_types).setVisible(DataBASE.user.isAdmin());
//        navigationView.getMenu().findItem(R.id.nav_response_to_lib).setVisible(DataBASE.user.isLibrarian() || DataBASE.user.isAdmin());
        navigationView.getMenu().findItem(R.id.nav_response_to_admin).setVisible(DataBASE.user.isAdmin()||DataBASE.user.isUser());
        navigationView.getMenu().findItem(R.id.nav_user_list).setVisible(DataBASE.user.isAdmin() || DataBASE.user.isCurator());
        navigationView.getMenu().findItem(R.id.nav_history).setVisible(DataBASE.user.isUser());
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
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count>1){
            DrawerLayout layout = findViewById(R.id.drawer_layout);
            if (layout.isDrawerOpen(GravityCompat.START)){
                layout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
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

        Bundle bundle = new Bundle();
        switch (id){
            case R.id.nav_map:
                fragment =  new MapFragment();
                toolbar.setTitle(R.string.map_fragment);
                break;
            case R.id.nav_news:
                fragment = new NewsFragment();
                toolbar.setTitle(R.string.news_fragment);
                break;
            case R.id.nav_bus:
                fragment = new FragmentBus();
                toolbar.setTitle(R.string.bus_fragment);

                break;
            case R.id.nav_event:
                fragment = new EventFragment();
                toolbar.setTitle(R.string.event_fragment);

                break;
            case R.id.nav_appeal:
                fragment = new AppealFragment();
                toolbar.setTitle(R.string.appeal_fragment);
                break;
            case R.id.nav_my_appeal:
                fragment = new AppealFragment(AppealFragment.MODE_MY);
                toolbar.setTitle(R.string.appeal_my_fragment);
                break;
            case R.id.nav_request_types:
                fragment = new RequestTypesFragment();
                toolbar.setTitle(R.string.request_types);
                break;
            case R.id.nav_user_list:
                fragment= new UsersListFragment();
                toolbar.setTitle(R.string.user_list);
                break;
            case R.id.nav_response_to_lib:
                fragment = new UserRequestsFragment();
                bundle.putInt("role", CONST.LIBRARIAN_ROLE);
                toolbar.setTitle(R.string.response_to_lib);
                break;
            case R.id.nav_user:
                fragment = new UserProfileFragment();
                toolbar.setTitle(R.string.user_fragment);
                break;
            case R.id.nav_history:
                fragment = new HistoryFragment();
                toolbar.setTitle(R.string.event_his);
                break;
            case R.id.nav_response_to_admin:
                fragment = new UserRequestsFragment();
                bundle.putInt("role", CONST.ADMIN_ROLE);
                toolbar.setTitle(R.string.response_to_admin);
                break;
            case R.id.nav_exit:
                Call<ResponseBody> logout = Retrofit.getInstance().getApi().logout("Bearer "+ MainActivity.userToken(this));
                LoginActivity.saveUserToken(getBaseContext(), null);
                logout.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        finish();
                    }
                });
                return true;
            default:
                Toast.makeText(this, "???????????? ?? ????????????????????", Toast.LENGTH_SHORT).show();
                return true;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        drawerLayout.closeDrawer(GravityCompat.START);
        removeAllFragments(fragmentManager);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null).commit();
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
