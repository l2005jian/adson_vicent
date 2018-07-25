package cn.com.lws.androidknowlogion.recycledemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.com.lws.androidknowlogion.R;

public class MeiziAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Meizi> meizis;

    public MeiziAdapter(Context context, List<Meizi> meizis) {
        this.context = context;
        this.meizis = meizis;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.meizi_item_big) {
            return new ViewHolderBig(inflater.inflate(R.layout.meizi_item_big, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.meizi_item_layout, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return R.layout.meizi_item_big;
        } else {
            return R.layout.meizi_item_layout;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Meizi meizi = meizis.get(position);
     /*   Glide.with(context)
                .load(meizi.getImageUrl())
                .into(holder.image);*/
        if (position % 3 == 0) {
            ViewHolderBig holderBig = (ViewHolderBig) holder;
            Picasso.get()
                    .load(meizi.getImageUrl())
                    .placeholder(R.drawable.mezi)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(holderBig.image);
            holderBig.title.setText(meizi.getTitle());
            holderBig.name.setText(meizi.getName());
            holderBig.favorites.setText(String.valueOf(meizi.getFavorites()));
            holderBig.comments.setText(String.valueOf(meizi.getComments()));

            holderBig.meiziItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context, "click.....", Toast.LENGTH_LONG).show();
                    startMeiziDetialActivity(meizi);
                }
            });

        } else {
            ViewHolder holderSmall = (ViewHolder) holder;

            Picasso.get()
                    .load(meizi.getImageUrl())
                    .placeholder(R.drawable.mezi)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(holderSmall.image);
            holderSmall.title.setText(meizi.getTitle());
            holderSmall.name.setText(meizi.getName());
            holderSmall.favorites.setText(String.valueOf(meizi.getFavorites()));
            holderSmall.comments.setText(String.valueOf(meizi.getComments()));

            holderSmall.meiziItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "click.....", Toast.LENGTH_LONG).show();
                    startMeiziDetialActivity(meizi);
                }
            });
        }
    }

    private void startMeiziDetialActivity(Meizi meizi) {
        Intent intent = new Intent(context, MeiziDetialActivity.class);
        intent.putExtra("image_url", meizi.getImageUrl());
        intent.putExtra("title", meizi.getTitle());
        intent.putExtra("name", meizi.getName());
        intent.putExtra("favorites", meizi.getFavorites());
        intent.putExtra("comments", meizi.getComments());
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return meizis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout meiziItem;
        ImageView image;
        TextView title;
        TextView name;
        TextView favorites;
        TextView comments;

        public ViewHolder(View itemView) {
            super(itemView);
            meiziItem = itemView.findViewById(R.id.meizi_item);
            image = itemView.findViewById(R.id.meizi_imageView);
            title = itemView.findViewById(R.id.meizi_item_title);
            name = itemView.findViewById(R.id.meizi_item_name);
            favorites = itemView.findViewById(R.id.meizi_favorites);
            comments = itemView.findViewById(R.id.meizi_item_comments);
        }
    }

    public class ViewHolderBig extends RecyclerView.ViewHolder {
        @BindView(R.id.meizi_item_big)
        ConstraintLayout meiziItem;
        @BindView(R.id.meizi_item_big_image)
        ImageView image;
        @BindViews({R.id.meizi_item_big_title, R.id.meizi_item_big_name, R.id.meizi_item_big_favorites,
                R.id.meizi_item_big_comments})
        TextView title, name, favorites, comments;

        public ViewHolderBig(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            meiziItem = itemView.findViewById(R.id.meizi_item_big);
//            image = itemView.findViewById(R.id.meizi_item_big_image);
//            title = itemView.findViewById(R.id.meizi_item_big_title);
//            name = itemView.findViewById(R.id.meizi_item_big_name);
//            favorites = itemView.findViewById(R.id.meizi_item_big_favorites);
//            comments = itemView.findViewById(R.id.meizi_item_big_comments);

        }
    }

}
