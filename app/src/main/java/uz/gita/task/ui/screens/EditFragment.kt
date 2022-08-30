package uz.gita.task.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.task.R
import uz.gita.task.data.models.ContactData
import uz.gita.task.data.models.ContactRequestData
import uz.gita.task.databinding.ScreenEditContactBinding
import uz.gita.task.presenter.EditViewModel
import uz.gita.task.presenter.impl.EditViewModelImpl

class EditFragment : Fragment(R.layout.screen_edit_contact) {
    private val viewModel: EditViewModel by viewModels<EditViewModelImpl>()
    private val navController: NavController by lazy { findNavController() }
    private val binding: ScreenEditContactBinding by viewBinding(ScreenEditContactBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact = requireArguments().getSerializable("CONTACT") as ContactData
        binding.inputName.setText(contact.name)
        binding.inputPhone.setText(contact.phone)
        binding.btnEdit.setOnClickListener {
            val name: String = binding.inputName.text.toString()
            val number: String = binding.inputPhone.text.toString()
            viewModel.edit(contact.localId, ContactRequestData(name, number))
            findNavController().popBackStack()
        }
    }
}