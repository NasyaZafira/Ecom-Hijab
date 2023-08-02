package com.fitri.jilbab.ui.admin

import android.app.DatePickerDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.FragmentReportAdminBinding
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ReportAdmin : Fragment() {

    private var _binding: FragmentReportAdminBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReportAdminBinding.bind(view)

        SharedPref.navAdmin = 3

        funAwal()
        funAkhir()
        btnDonwload()
    }

    private fun btnDonwload() {
        binding.btnSearch.setOnClickListener {
            val awal = binding.isDtst.text.toString().trim()
            Log.e("TAG", "btnDonwload: " + awal)
            val akhir = binding.isDtfn.text.toString().trim()
            Log.e("TAG", "btnDonwload: " + akhir)

            val url = "https://ecom-mobile.spdev.my.id/report/download?dt-st=$awal&dt-fn=$akhir"
            Log.e("TAG", "btnDonwload: " + url)
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("File")
                .setDescription("Sedang Mengunduh")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dm: DownloadManager =
                requireContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(requireContext(), "Mendownload PDF", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun funAkhir() {
        var cal = Calendar.getInstance()
        var textview_date: TextInputEditText? = null
        textview_date = this.binding.isDtfn
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textview_date.setText(sdf.format(cal.time))
                Log.e("TAG", "r_dateBirth: " + SimpleDateFormat(myFormat, Locale.US))
            }

        binding.isDtfn.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.e("TAG", "r_dateBirth: has focus if")
                binding.isDtfn.clearFocus()
                binding.isDtfn.clearFocus()
                textview_date.inputType = InputType.TYPE_NULL
                DatePickerDialog(
                    requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                Log.e("TAG", "r_dateBirth: has focus else")
            }
        }
    }

    private fun funAwal() {
        var cal = Calendar.getInstance()
        var textview_date: TextInputEditText? = null
        textview_date = this.binding.isDtst
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textview_date.setText(sdf.format(cal.time))
                Log.e("TAG", "r_dateBirth: " + SimpleDateFormat(myFormat, Locale.US))
            }

        binding.isDtst.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.e("TAG", "r_dateBirth: has focus if")
                binding.isDtst.clearFocus()
                binding.isDtst.clearFocus()
                textview_date.inputType = InputType.TYPE_NULL
                DatePickerDialog(
                    requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                Log.e("TAG", "r_dateBirth: has focus else")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}