package link.jingweih.tinnews.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import link.jingweih.tinnews.databinding.FragmentDetailsBinding
import link.jingweih.tinnews.model.Article

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding?  = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article: Article = DetailsFragmentArgs.fromBundle(arguments!!).article
        binding.detailsTitleTextView.text = article.title
        binding.detailsAuthorTextView.text = article.author
        binding.detailsContentTextView.text = article.content
        binding.detailsDateTextView.text = article.publishedAt
        binding.detailsDescriptionTextView.text = article.description
        Glide.with(this).load(article.imageUrl).into(binding.detailsImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}