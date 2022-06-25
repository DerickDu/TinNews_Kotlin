package link.jingweih.tinnews.ui.save

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import link.jingweih.tinnews.R
import link.jingweih.tinnews.databinding.SavedNewsItemBinding
import link.jingweih.tinnews.model.Article

class SaveAdapter : ListAdapter<Article, SaveAdapter.SavedNewsViewHolder>(Article.DIFF) {

    interface SaveArticleItemClickListener {
        fun onClickArticle(article: Article)
        fun onClickArticleHeartIcon(article: Article)
    }

    private var saveArticleItemClickListener: SaveArticleItemClickListener? = null
    fun setSaveArticleItemClickListener(saveArticleItemClickListener: SaveArticleItemClickListener?) {
        this.saveArticleItemClickListener = saveArticleItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_news_item, parent, false)
        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.authorTextView.text = article.author
        holder.descriptionTextView.text = article.description
        holder.favoriteIcon.setOnClickListener {
            saveArticleItemClickListener?.onClickArticleHeartIcon(article)
        }
        holder.itemView.setOnClickListener {
            saveArticleItemClickListener?.onClickArticle(
                article
            )
        }
    }

    class SavedNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var authorTextView: TextView
        var descriptionTextView: TextView
        var favoriteIcon: ImageView

        init {
            val binding = SavedNewsItemBinding.bind(itemView)
            authorTextView = binding.savedItemAuthorContent
            descriptionTextView = binding.savedItemDescriptionContent
            favoriteIcon = binding.savedItemFavoriteImageView
        }
    }
}