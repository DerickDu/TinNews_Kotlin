package link.jingweih.tinnews.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import link.jingweih.tinnews.databinding.FragmentSaveBinding
import link.jingweih.tinnews.model.Article

@AndroidEntryPoint
class SaveFragment : Fragment() {
    private val viewModel: SaveViewModel by viewModels()
    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val saveAdapter = SaveAdapter()

        binding.saveRecyclerView.apply {
            adapter = saveAdapter
            layoutManager = LinearLayoutManager(context)
        }
        saveAdapter.setSaveArticleItemClickListener(object :
            SaveAdapter.SaveArticleItemClickListener {
            override fun onClickArticle(article: Article) {
                val direction = SaveFragmentDirections.actionSaveFragmentToDetailsFragment(article)
                findNavController().navigate(direction)
            }

            override fun onClickArticleHeartIcon(article: Article) {
                viewModel.toggleFavorite(article)
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
                        is SaveUiState.Success -> saveAdapter.submitList(uiState.articles)
                        is SaveUiState.Error -> Toast.makeText(
                            context,
                            uiState.error,
                            Toast.LENGTH_SHORT
                        ).show()
                        else -> Unit
                    }
                }
            }
        }
    }

}