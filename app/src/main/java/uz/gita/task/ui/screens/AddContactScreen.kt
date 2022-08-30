package uz.gita.task.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import uz.gita.task.R
import uz.gita.task.data.models.ContactRequestData
import uz.gita.task.databinding.ScreenAddContactBinding
import uz.gita.task.presenter.AddContactViewModel
import uz.gita.task.presenter.impl.AddContactViewModelImpl

class AddContactScreen : Fragment(R.layout.screen_add_contact) {

    private val viewModel: AddContactViewModel by viewModels<AddContactViewModelImpl>()
    private val viewBinding: ScreenAddContactBinding by viewBinding(ScreenAddContactBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.messageLiveData.observe(this) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnAdd.setOnClickListener {
            Timber.d("Clicked")
            viewModel.addContact(
                ContactRequestData(
                    viewBinding.inputName.text.toString(),
                    viewBinding.inputPhone.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }


}