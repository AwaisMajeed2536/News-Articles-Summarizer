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
import android.widget.TextView;

import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.models.News;
import com.example.dell.newsarticlesummarizer.ui.ArticlesActivity;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, final int position) {
        final News singleNews = articles.get(position);
        holder.llArticleView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.tvArticleHeading.setText(singleNews.getNewsName());
//        holder.ivArticleImage.setImageURI(Uri.parse(article.getImageUrl()));
//        holder.tvArticleDate.setText(article.getArticleDate());

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
        private TextView tvArticleHeading;
        private Button openArticleBtn;
        private TextView tvArticleSummary;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            llArticleView = itemView.findViewById(R.id.llArticleView);
            llArticleView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            tvArticleHeading = itemView.findViewById(R.id.tvArticleHeading);
            openArticleBtn = itemView.findViewById(R.id.open_article_btn);
            tvArticleSummary = itemView.findViewById(R.id.tvArticleSummary);
            openArticleBtn.setVisibility(View.GONE);
        }
    }
}
