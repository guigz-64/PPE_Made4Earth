package com.jordan_remi.app.codescan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joplagne on 29/09/17.
 */

public class MonAdapteur extends RecyclerView.Adapter<MonAdapteur.ViewHolder> {

    private static ArrayList<Code> lesCodes = new ArrayList<>();
    private static Context context;
    private int lastPosition = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Code code = lesCodes.get(position);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int taille;
        if (size.x >= size.y){
            taille = Math.round(size.x*0.1f);
        }else{
            taille = Math.round(size.y*0.1f);
        }
        holder.imageCode.setImageBitmap(code.getImage(taille));
        holder.leCode.setText(code.getCode());
        holder.typeDeCode.setText(code.getTypeString());
        holder.layout.setBackgroundResource(R.drawable.corners_arrounded);
        GradientDrawable tmp = (GradientDrawable) holder.layout.getBackground();
        tmp.setColor(Color.parseColor(code.getColor()));

        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(200);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return lesCodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageCode;
        public TextView leCode;
        public TextView typeDeCode;
        public FrameLayout layout;

        public ViewHolder(View view) {
            super(view);
            leCode = ((TextView) itemView.findViewById(R.id.leCode));
            imageCode = itemView.findViewById(R.id.imageCode);
            typeDeCode = ((TextView) itemView.findViewById(R.id.typeDeCode));

            layout = (FrameLayout) itemView.findViewById(R.id.layout_item);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Code code = lesCodes.get(pos);
                        Intent intent = new Intent();
                        intent.setClass(context, DetailActivity.class);
                        intent.putExtra("code", code);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


    public MonAdapteur(ArrayList<Code> listeDeCodes, Context context){
        lesCodes = listeDeCodes;
        this.context = context;
    }


}
