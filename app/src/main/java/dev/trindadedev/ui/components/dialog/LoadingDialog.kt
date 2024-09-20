package dev.trindadedev.ui.components.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle

import com.geero.newpass.databinding.DialogLoadingBinding

class LoadingScreen(context: Context) : Dialog(context) {

    private lateinit var binding: DialogLoadingBinding
    private val c: Context = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
