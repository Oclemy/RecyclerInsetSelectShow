package com.tutorials.hp.recyclerinsetselectshow;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nameTxt,posTxt;
    RecyclerView rv;
    MyAdapter adapter;
    ArrayList<Player> players=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SHOW INPUT DIALOG
                showDialog();
            }
        });

        //recycler
       rv= (RecyclerView) findViewById(R.id.mRecycler);

        //SET PROPS
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        adapter=new MyAdapter(this,players);

        //RETRIEVE
        retrieve();


    }

    //SHOW INSERT DIALOG
    private void showDialog()
    {
        Dialog d=new Dialog(this);

        //NO TITLE
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        d.setContentView(R.layout.custom_layout);

        nameTxt= (EditText) d.findViewById(R.id.nameEditTxt);
        posTxt= (EditText) d.findViewById(R.id.posEditTxt);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        final Button retrievebtn= (Button) d.findViewById(R.id.retrieveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(nameTxt.getText().toString(),posTxt.getText().toString());
            }
        });


        retrievebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 retrieve();
            }
        });

        d.show();

    }

    private void save(String name,String pos)
    {
        DBAdapter db=new DBAdapter(this);

        //OPEN DB
        db.openDB();

        //COMMIT
        long result=db.add(name,pos);

        if(result>0)
        {
            nameTxt.setText("");
            posTxt.setText("");
        }else
        {
            Snackbar.make(nameTxt,"Unable To Save",Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();

        //REFRESH
        retrieve();
    }

    //RETREIEV
    private void retrieve()
    {
        players.clear();

        DBAdapter db=new DBAdapter(this);
        db.openDB();

        //RETRIEVE
        Cursor c=db.getAllPlayers();

        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String name=c.getString(1);
            String pos=c.getString(2);

            Player p=new Player(id,name,pos,R.id.playerImage);

            //ADD TO ARRAYLIS
            players.add(p);
        }

        //CHECK IF ARRAYLIST ISNT EMPTY
        if(!(players.size()<1))
        {
            rv.setAdapter(adapter);
        }

        db.closeDB();;

    }


}













