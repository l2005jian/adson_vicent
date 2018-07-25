package cn.com.lws.androidknowlogion.recycledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.com.lws.androidknowlogion.R;

public class RecylerActivity extends AppCompatActivity {
    private List<Meizi> meizis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_main_layout);
                Toolbar toolbar=findViewById(R.id.toobar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RecylerActivity.this,"yahoooo",Toast.LENGTH_LONG).show();;
            }
        });
        this.setSupportActionBar(toolbar);
        meizis=MeiziFactory.createMeizi(100);
        MeiziAdapter adapter=new MeiziAdapter(this,meizis);
        //线性布局
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MeiziItemDecoration(2));
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_settings){
            Toast.makeText(RecylerActivity.this,"Settrings",Toast.LENGTH_LONG).show();
            return true;
        }else if(id == R.id.action_search) {
            Toast.makeText(RecylerActivity.this,"Search",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
