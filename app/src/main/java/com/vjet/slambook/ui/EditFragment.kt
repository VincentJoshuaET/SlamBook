package com.vjet.slambook.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.vjet.slambook.R
import com.vjet.slambook.database.Item
import com.vjet.slambook.databinding.FragmentEditBinding
import com.vjet.slambook.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
@ExperimentalStdlibApi
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding by viewBinding(FragmentEditBinding::bind)

    private val viewModel: RoomViewModel by activityViewModels()

    private val args: EditFragmentArgs by navArgs()

    @Inject
    lateinit var keyboard: Keyboard

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val formatter =
            DateTimeFormatter.ofPattern("MMMM d, yyyy").withZone(ZoneId.systemDefault())
        var birthday =
            LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val r = Random
        var card = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256))

        binding.apply {
            val genders = ArrayAdapter(
                context,
                R.layout.dropdown_menu_popup_item,
                arrayOf("Male", "Female", "Prefer not to say")
            )

            txtFullName.setErrorListener()
            txtNickname.setErrorListener()
            txtAddress.setErrorListener()
            txtMobile.setErrorListener()
            txtEmail.setErrorListener()
            txtGender.setErrorListener()
            txtBirthday.setErrorListener()
            txtAge.setErrorListener()

            txtGender.setAdapter(genders)
            txtGender.setOnClickListener {
                keyboard.hide(this@EditFragment)
            }

            txtBirthday.setOnClickListener {
                keyboard.hide(this@EditFragment)
                val constraints = CalendarConstraints.Builder().setOpenAt(birthday).build()
                val builder =
                    MaterialDatePicker.Builder.datePicker().apply {
                        setCalendarConstraints(constraints)
                        setSelection(birthday)
                        setTitleText("Birthday")
                    }
                val picker =
                    builder.build().apply {
                        addOnPositiveButtonClickListener { epochMilli ->
                            birthday = epochMilli
                            val date =
                                Instant.ofEpochMilli(birthday)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            txtBirthday.setText(date.format(formatter))
                        }
                    }
                picker.show(parentFragmentManager, picker.toString())
            }

            if (args.action == "Edit") {
                viewModel.item.observe(viewLifecycleOwner) { item ->
                    birthday = item.birthday
                    val birthdayLocalDate =
                        Instant.ofEpochMilli(birthday).atZone(ZoneId.systemDefault()).toLocalDate()
                    txtFullName.setText(item.fullName)
                    txtNickname.setText(item.nickname)
                    txtAddress.setText(item.address)
                    txtMobile.setText(item.mobile)
                    txtEmail.setText(item.email)
                    txtGender.setText(item.gender, false)
                    txtBirthday.setText(birthdayLocalDate.format(formatter))
                    txtAge.setText(item.age.toString())
                    txtOccupation.setText(item.occupation)
                    txtLanguage.setText(item.language)
                    txtColor.setText(item.color)
                    txtMusician.setText(item.musician)
                    txtMovie.setText(item.movie)
                    txtTvShow.setText(item.tvShow)
                    txtActor.setText(item.actor)
                    txtBook.setText(item.book)
                    txtAuthor.setText(item.author)
                    txtGame.setText(item.game)
                    txtFood.setText(item.food)
                    txtPlace.setText(item.place)
                    card = item.card

                    fabSave.setOnClickListener {
                        val updated = createItem(birthday, card) ?: return@setOnClickListener
                        updated.id = item.id
                        viewModel.updateItem(updated)
                        findNavController().popBackStack()
                    }
                }
            } else {
                fabSave.setOnClickListener {
                    val item = createItem(birthday, card) ?: return@setOnClickListener
                    viewModel.addItem(item)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun createItem(birthday: Long, card: Int): Item? =
        binding.run {
            keyboard.hide(this@EditFragment)

            val error = "Please answer this question!"

            val fullName = txtFullName.text.toString().capitalizeWords()
            val nickname = txtNickname.text.toString().capitalize(Locale.getDefault())
            val address = txtAddress.text.toString().capitalizeWords()
            val mobile = txtMobile.text.toString()
            val email = txtEmail.text.toString()
            val gender = txtGender.text.toString()
            val birthdayString = txtBirthday.text.toString()
            val age = txtAge.text.toString().toIntOrNull() ?: 0
            val occupation = txtOccupation.text.toString().capitalizeWords()
            val language = txtLanguage.text.toString().capitalizeWords()
            val color = txtColor.text.toString().capitalizeWords()
            val musician = txtMusician.text.toString().capitalizeWords()
            val movie = txtMovie.text.toString().capitalizeWords()
            val tvShow = txtTvShow.text.toString().capitalizeWords()
            val actor = txtActor.text.toString().capitalizeWords()
            val book = txtBook.text.toString().capitalizeWords()
            val author = txtAuthor.text.toString().capitalizeWords()
            val game = txtGame.text.toString().capitalizeWords()
            val food = txtFood.text.toString().capitalizeWords()
            val place = txtPlace.text.toString().capitalizeWords()
            var fail = false

            if (fullName.isEmpty()) {
                txtFullName.showError(error)
                fail = true
            }
            if (nickname.isEmpty()) {
                txtNickname.showError(error)
                fail = true
            }
            if (address.isEmpty()) {
                txtAddress.showError(error)
                fail = true
            }
            if (mobile.isEmpty()) {
                txtMobile.showError(error)
                fail = true
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.showError("Please enter valid email address!")
                fail = true
            }
            if (gender.isEmpty()) {
                txtGender.showError(error)
                fail = true
            }
            if (birthdayString.isEmpty()) {
                txtBirthday.showError(error)
                fail = true
            }
            if (age == 0) {
                txtAge.showError(error)
                fail = true
            }

            if (fail) return null

            return Item(
                fullName,
                nickname,
                address,
                mobile,
                email,
                gender,
                birthday,
                age,
                occupation,
                language,
                color,
                musician,
                movie,
                tvShow,
                actor,
                book,
                author,
                game,
                food,
                place,
                card
            )
        }


}