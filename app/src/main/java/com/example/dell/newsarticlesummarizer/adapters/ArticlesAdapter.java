package com.example.dell.newsarticlesummarizer.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dell.newsarticlesummarizer.R;
import com.example.dell.newsarticlesummarizer.models.Article;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private List<Article> articles = new ArrayList<>();
    private Context context;

    public ArticlesAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.llArticleView.setBackgroundColor(context.getResources().getColor(article.isSelected() ? R.color.light_grey : R.color.off_white));
        holder.tvArticleHeading.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(article.getArticleHeading()));
        holder.ivArticleImage.setImageURI(Uri.parse(article.getImageUrl()));
        holder.tvArticleDate.setText(article.getArticleDate());
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    class ArticlesViewHolder extends RecyclerView.ViewHolder {
        private View llArticleView;
        private TextView tvArticleHeading;
        private TextView tvArticleDate;
        private ImageView ivArticleImage;
        private VideoView vvArticleVideo;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            llArticleView = itemView.findViewById(R.id.llArticleView);
            tvArticleHeading = itemView.findViewById(R.id.tvArticleHeading);
            tvArticleDate = itemView.findViewById(R.id.tvArticleDate);
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);
            vvArticleVideo = itemView.findViewById(R.id.vvArticleVideo);
        }
    }
}
