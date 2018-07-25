package com.intej.laiws.banner;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.intej.laiws.intejdemo.R;


public class LocalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int[] images={
            R.drawable.am_02,
            R.drawable.am_03,
    };


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_over,parent,false)) ;
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
    public ViewHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click on :"+v.getTag(),Toast.LENGTH_LONG).show();
            }
        });
    }
}