package com.example.dell.newsarticlesummarizer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.dell.newsarticlesummarizer.interfaces.OnItemClickListener;
import com.example.dell.newsarticlesummarizer.models.Article;
import com.example.dell.newsarticlesummarizer.ui.WebViewActvity;

import java.util.ArrayList;
import java.util.List;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, final int position) {
        final Article article = articles.get(position);
        holder.llArticleView.setBackgroundColor(ContextCompat.getColor(context, article.isSelected() ? R.color.off_white : R.color.white));
        holder.tvArticleHeading.setText(article.getLinkTitle());
        holder.tvArticleSummary.setText(article.getLinkSummary());
//        holder.ivArticleImage.setImageURI(Uri.parse(article.getImageUrl()));
//        holder.tvArticleDate.setText(article.getArticleDate());

        holder.openArticleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActvity.class);
//                intent.putExtra(WebViewActvity.WEB_URL, article.getLinkURL());
//                context.startActivity(intent);
                new AlertDialog.Builder(context)
                        .setMessage(article.getLinkSummary())
                        .setCancelable(true)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
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
//        private TextView tvArticleDate;
//        private ImageView ivArticleImage;
//        private VideoView vvArticleVideo;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            llArticleView = itemView.findViewById(R.id.llArticleView);
            llArticleView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            tvArticleHeading = itemView.findViewById(R.id.tvArticleHeading);
            openArticleBtn = itemView.findViewById(R.id.open_article_btn);
            tvArticleSummary = itemView.findViewById(R.id.tvArticleSummary);
//            tvArticleDate = itemView.findViewById(R.id.tvArticleDate);
//            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);
//            vvArticleVideo = itemView.findViewById(R.id.vvArticleVideo);
        }
    }
}
