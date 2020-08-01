package com.vjet.slambook.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.vjet.slambook.R
import com.vjet.slambook.databinding.FragmentSelectedBinding
import com.vjet.slambook.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SelectedFragment : Fragment(R.layout.fragment_selected) {
    
    private val binding by viewBinding(FragmentSelectedBinding::bind)
    
    private val viewModel: RoomViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatter =
            DateTimeFormatter.ofPattern("MMMM d, yyyy").withZone(ZoneId.systemDefault())
        
        binding.apply {
            viewModel.item.observe(viewLifecycleOwner) { item ->
                val birthday =
                    Instant.ofEpochMilli(item.birthday).atZone(ZoneId.systemDefault()).toLocalDate()
                root.setBackgroundColor(item.card)
                txtFullName.text = item.fullName
                txtNickname.text = item.nickname
                txtAddress.text = item.address
                txtMobile.text = item.mobile
                txtEmail.text = item.email
                txtGender.text = item.gender
                txtBirthday.text = birthday.format(formatter)
                txtAge.text = item.age.toString()
                txtOccupation.text = item.occupation
                txtLanguage.text = item.language
                txtColor.text = item.color
                txtMusician.text = item.musician
                txtMovie.text = item.movie
                txtTvShow.text = item.tvShow
                txtActor.text = item.actor
                txtBook.text = item.book
                txtAuthor.text = item.author
                txtGame.text = item.game
                txtFood.text = item.food
                txtPlace.text = item.place

                fabEdit.setOnClickListener {
                    val extras = FragmentNavigatorExtras(fabEdit to "sharedElementContainer")
                    root.transitionName = null
                    fabEdit.transitionName = "sharedElementContainer"
                    findNavController().navigate(SelectedFragmentDirections.actionSelectedToEdit("Edit"), extras)
                }
            }
        }
    }
}