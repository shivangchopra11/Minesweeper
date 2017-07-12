package com.example.shivang.minesweeperv2;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static int n=10;
    public static LinearLayout[] rowLayout;
    public static MyButton[][] buttons;
    public static LinearLayout main_layout;
    public static boolean game_over = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_layout = (LinearLayout)findViewById(R.id.main_layout);
        setUpGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.reset){
            setUpGame();
        }
        return true;
    }

    public void resetBoard() {
        int maxBombs = (n*n)/6;
        for(int i=0;i<maxBombs;i++) {
            int x = 0 + (int)(Math.random() * n-1);
            int y = 0 + (int)(Math.random() * n-1);
            buttons[x][y].value = -1;
            if(x!=0) {
                if(y!=0 && buttons[x-1][y-1].value!=-1)
                    buttons[x-1][y-1].value++;
                if(y!=n-1 && buttons[x-1][y+1].value!=-1)
                    buttons[x-1][y+1].value++;
                if(buttons[x-1][y].value!=-1)
                    buttons[x-1][y].value++;
            }
            if(x!=n-1) {
                if(y!=n-1 && buttons[x+1][y+1].value!=-1)
                    buttons[x+1][y+1].value++;
                if(buttons[x+1][y].value!=-1)
                    buttons[x+1][y].value++;
                if(y!=0 && buttons[x+1][y-1].value!=-1 )
                    buttons[x+1][y-1].value++;
            }
            if(y!=n-1 && buttons[x][y+1].value!=-1)
                buttons[x][y+1].value++;
            if(y!=0 && buttons[x][y-1].value!=-1)
                buttons[x][y-1].value++;
        }


    }

    public void setUpGame() {
        main_layout.removeAllViews();
        game_over = false;
        rowLayout = new LinearLayout[n];
        buttons = new MyButton[n][n];
        for(int i=0;i<n;i++) {
            rowLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 1f);
            params.setMargins(1,1,1,1);
            rowLayout[i].setLayoutParams(params);
            rowLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            main_layout.addView(rowLayout[i]);
        }
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                buttons[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(1,1,1,1);
                buttons[i][j].setLayoutParams(params);
                //buttons[i][j].setBackgroundResource(R.drawable.circle);
                buttons[i][j].setOnClickListener(this);
                if (i % 2 == 0) {
                    if(j%2==0)
                        buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.black));
                    else
                        buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.gray));
                }
                else {
                    if(j%2!=0)
                        buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.black));
                    else
                        buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.gray));
                }
                buttons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        MyButton b = (MyButton)v;
                        if(b.flagged==true) {
                            return true;
                        }
                        else {
                            b.setBackgroundResource(R.drawable.flag);
                        }
                        return true;
                    }
                });
                buttons[i][j].setTextColor(ContextCompat.getColor(this,R.color.red));
                buttons[i][j].flagged = false;
                buttons[i][j].x = i;
                buttons[i][j].y = j;
                rowLayout[i].addView(buttons[i][j]);
            }
        }
        resetBoard();
    }

    @Override
    public void onClick(View v) {
        MyButton cur = (MyButton)v;
        if(cur.value!=-1 && game_over==false && cur.visited==false) {
            cur.setText(cur.value +"");
            cur.visited=true;
            if(cur.value==0) {
                cur.setBackgroundColor(ContextCompat.getColor(this,R.color.silver));
                cur.setText(" ");
                recfn(cur.x,cur.y);
            }
        }
        else if(game_over==false && cur.visited==false && cur.value==-1){
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    if(buttons[i][j].value==-1) {
                        buttons[i][j].setBackgroundResource(R.drawable.minesweeper);
                    }
                }
            }
            cur.setBackgroundResource(R.drawable.minesweeper);
            game_over=true;
            Toast.makeText(this,"Game Over!!",Toast.LENGTH_SHORT).show();
        }
        else if(cur.visited==true){
            return;
        }
        else {
            Toast.makeText(this,"Game Over!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void recfn(int x, int y) {
        if(x>0) {
            if(y>0) {
                buttons[x-1][y-1].performClick();
            }
            if(y<n-1) {
                buttons[x-1][y+1].performClick();
            }
            buttons[x-1][y].performClick();
        }
        if(x<n-1) {
            if(y<n-1) {
                buttons[x+1][y+1].performClick();
            }
            buttons[x+1][y].performClick();
            if(y>0) {
                buttons[x+1][y-1].performClick();
            }

        }
        if(y<n-1) {
            buttons[x][y+1].performClick();
        }
        if(y>0) {
            buttons[x][y-1].performClick();
        }
        return;
    }

}
