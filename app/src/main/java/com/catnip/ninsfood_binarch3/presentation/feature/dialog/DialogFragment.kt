package com.catnip.ninsfood_binarch3.presentation.feature.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.catnip.ninsfood_binarch3.R

class DialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.fragment_dialog, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)
        val okButton = dialogView.findViewById<Button>(R.id.dialog_ok_button)
        val iconImageView = dialogView.findViewById<ImageView>(R.id.dialog_icon)

        titleTextView.text = "Pesanan Berhasil"
        messageTextView.text = "Terima kasih! Pesanan Anda telah berhasil diproses."
        iconImageView.setImageResource(R.drawable.ic_success)

        builder.setView(dialogView)
        val alertDialog = builder.create()

        okButton.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }
}