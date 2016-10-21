package com.example.bai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bai.Fragment.Fragment2;
import com.example.bai.Fragment.Fragment3;
import com.example.bai.Fragment.Fragment4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager main_viewPager;
    List<Fragment> fragmentList=new ArrayList<Fragment>();
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Context mContext;
    ShareHelper sh;
    NavigationView navigationView;
    User user;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mContext=getApplicationContext();
        sh=new ShareHelper(mContext);
        Map<String,String> data = sh.read();
        System.out.println(data.get("username"));
        user= MyApplication.getLoginUser();
        if(data!=null)
        {
            if(data.get("name").equals("")||data.get("username").equals("")||data.get("passwd").equals(""))
            {
                text= (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
                if(user==null)
                {
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else
                {
                    user= MyApplication.getLoginUser();
                    text.setText(user.getPerson_name());
                }
            }
            else
            {
                user=new User(data.get("name"),data.get("username"),data.get("passwd"));
                System.out.println(data.get("name") + "3333333333");
                MyApplication.setLoginUser(user);
                text= (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
                text.setText(user.getPerson_name());
            }

        }
        else
        {
            TextView text= (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
            if(user==null)
            {
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else
            {
                user= MyApplication.getLoginUser();
                text.setText(user.getPerson_name());
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        navigationView.setNavigationItemSelectedListener(this);

        main_viewPager= (ViewPager) findViewById(R.id.main_viewPager);

        fragment2=new Fragment2();
        fragment3=new Fragment3();
        //fragment4=new Fragment4();

        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        //fragmentList.add(fragment4);

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());

        main_viewPager.setAdapter(fragmentAdapter);






    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            Intent intent = new Intent(MainActivity.this,AllActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_night) {
            Intent intent=new Intent(MainActivity.this,CreateActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_search) {
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            User user= MyApplication.getLoginUser();
            if(user==null)
            {
                Toast.makeText(MainActivity.this,"您尚未登录！",Toast.LENGTH_SHORT).show();
            }
            else
            {

                SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.remove("name");
                editor.remove("username");
                editor.remove("passwd");
                editor.commit();

                MyApplication.setLoginUser(null);
                Toast.makeText(MainActivity.this,"您已退出！请重新登录！",Toast.LENGTH_SHORT).show();
                finish();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);

            }

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class FragmentAdapter extends FragmentPagerAdapter
    {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
