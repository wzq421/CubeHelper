package com.example.myapplication.utils;

import com.example.myapplication.bean.rxbus.New_Formulation;
import com.example.myapplication.callback.SharedViewModel;

import java.util.ArrayList;
import java.util.Random;

import me.goldze.mvvmhabit.bus.RxBus;

public class ShufflingFormulation {
    private volatile static ShufflingFormulation shufflingFormulation;
    private ShufflingFormulation(){getFormulation();
    }
    public static ShufflingFormulation getShufflingFormulation(){
        if(shufflingFormulation==null){
            synchronized (SharedViewModel.class){
                if(shufflingFormulation==null){
                    shufflingFormulation=new ShufflingFormulation();
                }
            }
        }
        return shufflingFormulation;
    }
    private String mShufflingFormulation;
    public String formulation="formulation";
    String[] letters0=new String[]{"R","U","F"};
    String[] letters1=new String[]{"L","D","B"};
    private int[] remainders;
    //得到0到33之间整数，如果得到0就跳过。然后用得到的数除以三以余数得到操作字母，保存余数，如果是2则操作两次，如果是1则变成小写。
    public int getRandom(){
        Random random=new Random();
        int r=random.nextInt(34);
        return r;
    }
    public void getFormulation(){
        remainders=new int[15];
        for(int i=0;i<15;i++){
            int r=getRandom();
            int num=r==0?new Random().nextInt(33)+1:r;
            int index=num%3;
            remainders[i]=index;
        }
        for(int i=0;i<15;i++){
            int r=getRandom();
            int num=r==0?new Random().nextInt(33)+1:r;
            int index=num%3;
            String s=num%2==0?letters0[index]:letters1[index];
            if(remainders[i]==1){
                s=s.toLowerCase();
            }else if(remainders[i]==2){
                s+="2";
            }
            mShufflingFormulation+=s;
        }
        formulation=mShufflingFormulation;
        mShufflingFormulation="";
        RxBus.getDefault().post(new New_Formulation(formulation));
    }
}

