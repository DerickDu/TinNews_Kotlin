package link.jingweih.tinnews.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import link.jingweih.tinnews.R
import link.jingweih.tinnews.databinding.HomeCardItemViewBinding
import link.jingweih.tinnews.model.Article
import link.jingweih.tinnews.model.Article.Companion.DIFF

class HomeAdapter : ListAdapter<Article, HomeAdapter.HomeCardViewHolder>(DIFF) {

    interface HomeArticleItemClickListener {
        fun onClickArticle(article: Article)
        fun onClickArticleHeartIcon(article: Article)
    }

    private var homeArticleItemClickListener: HomeArticleItemClickListener? = null
    fun setHomeArticleItemClickListener(homeArticleItemClickListener: HomeArticleItemClickListener) {
        this.homeArticleItemClickListener = homeArticleItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCardViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.home_card_item_view, parent, false)
        return HomeCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCardViewHolder, position: Int) {
        val article = getItem(position)
        holder.newsTitle.text = article.title
        holder.newsDescription.text = article.description
        if (article.imageUrl.isNullOrBlank()) {
            holder.newsImage.isVisible = false
        } else {
            holder.newsImage.isVisible = true
            Glide.with(holder.itemView)
                .load(article.imageUrl)
                .error(R.drawable.ic_error_outline_24)
                .fitCenter().into(holder.newsImage)
        }
        holder.newsHeartIcon.setImageResource(
            if (article.isFavorite) R.drawable.ic_favorite_24 else
                R.drawable.ic_unfavorite_24
        )
        holder.newsHeartIcon.setOnClickListener {
            homeArticleItemClickListener?.onClickArticleHeartIcon(article)
        }
        holder.itemView.setOnClickListener {
            homeArticleItemClickListener?.onClickArticle(article)
        }

    }

    class HomeCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView
        val newsTitle: TextView
        val newsDescription: TextView
        val newsHeartIcon: ImageView

        init {
            val binding = HomeCardItemViewBinding.bind(itemView)
            newsImage = binding.newsImage
            newsTitle = binding.newsTitle
            newsDescription = binding.newsDescription
            newsHeartIcon = binding.newsHeartIcon
        }
    }
}