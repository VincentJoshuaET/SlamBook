package com.vjet.slambook.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vjet.slambook.R
import com.vjet.slambook.databinding.FragmentItemsBinding
import com.vjet.slambook.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsFragment : Fragment(R.layout.fragment_items) {

    private val binding by viewBinding(FragmentItemsBinding::bind)
    private val viewModel: RoomViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager2 = binding.viewPager2
        val fabCreate = binding.fabCreate
        val adapter = ItemAdapter { item, itemView ->
            val extras = FragmentNavigatorExtras(itemView to "sharedElementContainer")
            itemView.transitionName = "sharedElementContainer"
            viewModel.setItem(item)
            findNavController().navigate(ItemsFragmentDirections.actionItemsToSelected(item.fullName), extras)
        }
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        viewPager2.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            lifecycleScope.launch(Dispatchers.IO) {
                adapter.submitData(items)
            }
        }

        fabCreate.setOnClickListener {
            val extras = FragmentNavigatorExtras(fabCreate to "sharedElementContainer")
            fabCreate.transitionName = "sharedElementContainer"
            findNavController().navigate(R.id.action_items_to_edit, null, null, extras)
        }
    }
}