package com.example.dell.newsarticlesummarizer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.models.News;
import com.example.dell.newsarticlesummarizer.ui.ArticlesActivity;
import com.example.dell.newsarticlesummarizer.utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticlesViewHolder> {

    private List<News> articles = new ArrayList<>();
    private Context context;

    public NewsAdapter(Context context, List<News> articles) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticlesViewHolder holder, final int position) {
        final News singleNews = articles.get(position);
        holder.llArticleView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.newsNameTv.setText(singleNews.getNewsName());
        if (AppPreferences.isAddedToFavorites(context, singleNews.getNewsName())) {
            holder.addBtn.setImageResource(R.drawable.ic_favorite_full);
        } else {
            holder.addBtn.setImageResource(R.drawable.ic_favorite);
        }
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.isAddedToFavorites(context, singleNews.getNewsName())) {
                    holder.addBtn.setImageResource(R.drawable.ic_favorite);
                    AppPreferences.removeFromFavorites(context, singleNews.getNewsName());
                } else {
                    holder.addBtn.setImageResource(R.drawable.ic_favorite_full);
                    AppPreferences.addToFavorites(context, singleNews.getNewsName());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticlesActivity.class);
                intent.putExtra(ArticlesActivity.MODEL_NAME, singleNews.getNewsName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    class ArticlesViewHolder extends RecyclerView.ViewHolder {
        private View llArticleView;
        private TextView newsNameTv;
        private ImageView addBtn;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            llArticleView = itemView.findViewById(R.id.llArticleView);
            llArticleView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            newsNameTv = itemView.findViewById(R.id.newsNameTv);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
