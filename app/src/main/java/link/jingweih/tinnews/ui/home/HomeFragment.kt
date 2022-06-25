package link.jingweih.tinnews.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import link.jingweih.tinnews.databinding.FragmentHomeBinding
import link.jingweih.tinnews.model.Article

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding?  = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = HomeAdapter()
        binding.homeRecyclerView.apply {
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,
                RecyclerView.VERTICAL
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            viewModel.getTopHeadlines()
                        }, 3000)
                    }
                }
            })
        }
        binding.homeRecyclerView.itemAnimator?.changeDuration = 0
        homeAdapter.setHomeArticleItemClickListener(object : HomeAdapter.HomeArticleItemClickListener{
            override fun onClickArticle(article: Article) {
                val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(article)
                findNavController().navigate(direction)
            }

            override fun onClickArticleHeartIcon(article: Article) {
                viewModel.toggleFavoriteNews(article)
            }
        })

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.uiState.collect { uiState ->
                    // New value received
                    when (uiState) {
                        is TopHeadlinesUiState.Success -> homeAdapter.submitList(uiState.articles)
                        is TopHeadlinesUiState.Error -> Unit
                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}