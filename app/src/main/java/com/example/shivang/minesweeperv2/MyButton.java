package com.example.shivang.minesweeperv2;

import android.content.Context;
import android.widget.Button;

/**
 * Created by shivang on 12/07/17.
 */

public class MyButton extends Button {
    int value;
    boolean visited;
    int x;
    int y;
    boolean flagged;
    public MyButton(Context context) {
        super(context);
        value = 0;
        visited = false;
    }
}
