package uz.gita.task.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import uz.gita.task.R
import uz.gita.task.databinding.ScreenMainBinding
import uz.gita.task.presenter.MainViewModel
import uz.gita.task.presenter.impl.MainViewModelImpl
import uz.gita.task.ui.adapters.ContactAdapter

class MainScreen : Fragment(R.layout.screen_main) {
    private val adapter = ContactAdapter()
    private val binding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val navController by lazy { findNavController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.addLiveData.observe(this) {
            navController.navigate(R.id.action_mainScreen_to_addContactScreen)
        }
        viewModel.editLiveData.observe(this) {
            navController.navigate(
                R.id.action_mainScreen_to_editFragment,
                bundleOf(Pair("CONTACT", it))
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.container.adapter = adapter

        setClickListeners()
        subscribeDataObservers()

    }

    private fun setClickListeners() {
        binding.btnAdd.setOnClickListener {
            viewModel.add()
        }
        binding.btnRefresh.setOnClickListener {
            viewModel.updateSate()
        }

        binding.btnAdd.setOnClickListener {
            viewModel.add()
        }

        adapter.setEditListener {
            viewModel.edit(it)
        }
        adapter.setDeleteListener {
            viewModel.delete(it)
        }

    }

    private fun subscribeDataObservers() {
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            Timber.d(it.toString())
            adapter.submitList(it)
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner) {

        }
    }

}