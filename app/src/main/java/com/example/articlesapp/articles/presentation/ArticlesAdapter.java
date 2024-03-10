package com.example.articlesapp.articles.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.articlesapp.articles.data.Article;
import com.example.articlesapp.databinding.ItemArticleBinding;
import com.example.articlesapp.utils.ImageUtils;
import com.example.articlesapp.utils.Utils;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {
    private List<Article> articles;
    private final OnItemClickListener onItemClickListener;

    public ArticlesAdapter(List<Article> articles, OnItemClickListener onItemClickListener) {
        this.articles = articles;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArticleBinding binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticlesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {
        Article article = articles.get(position);
       holder.bind(article, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {
        ItemArticleBinding binding;

        public ArticlesViewHolder(@NonNull ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article article, OnItemClickListener onItemClickListener) {
            binding.itemArticleTitleTv.setText(article.title);
            String date = Utils.getInstance().simpleFormat.format(article.publishedAt);
            binding.itemArticleDateTv.setText(date);
            String url = article.urlToImage;
            ImageUtils.getInstance().loadImage(binding.getRoot().getContext(), binding.itemArticleIv, url);

            binding.getRoot().setOnClickListener(v -> {
                onItemClickListener.onUrlClick(article.url);
            });
        }
    }


    protected void publishResults(List<Article> results) {
        int size = getItemCount();
        articles.clear();
        articles.addAll(results);
        //notifyItemMoved(0, size - 1);
        notifyDataSetChanged();
    }

}