package com.android.week7_exercise1_currency

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var input1: EditText
    lateinit var input2: EditText
    lateinit var spinner1: Spinner
    lateinit var spinner2: Spinner
    private var isSourceInput = true
    private var isUpdating = false

    private val currencyRates = mapOf(
        "United States - Dollar" to 1.0,
        "Vietnam - Dong" to 24575.0,
        "European Union - Euro" to 0.92,
        "Japan - Yen" to 151.0,
        "China - Yuan" to 7.32,
        "South Korea - Won" to 1367.0,
        "United Kingdom - Pound" to 0.77,
        "Australia - Dollar" to 1.50,
        "Canada - Dollar" to 1.38,
        "Singapore - Dollar" to 1.37,
        "Switzerland - Franc" to 0.91,
        "Thailand - Baht" to 36.55,
        "Malaysia - Ringgit" to 4.79,
        "India - Rupee" to 83.20,
        "Russia - Ruble" to 93.7,
        "Brazil - Real" to 5.16,
        "Indonesia - Rupiah" to 15700.0,
        "Mexico - Peso" to 18.02,
        "New Zealand - Dollar" to 1.68,
        "Hong Kong - Dollar" to 7.84,
        "Sweden - Krona" to 10.63,
        "Norway - Krone" to 10.86,
        "Denmark - Krone" to 6.88,
        "Philippines - Peso" to 56.80,
        "Taiwan - Dollar" to 32.12
    )

    private val currencySymbols = mapOf(
        "United States - Dollar" to "$",
        "Vietnam - Dong" to "đ",
        "European Union - Euro" to "€",
        "Japan - Yen" to "¥",
        "China - Yuan" to "¥",
        "South Korea - Won" to "₩",
        "United Kingdom - Pound" to "£",
        "Australia - Dollar" to "$",
        "Canada - Dollar" to "$",
        "Singapore - Dollar" to "$",
        "Switzerland - Franc" to "CHF",
        "Thailand - Baht" to "฿",
        "Malaysia - Ringgit" to "RM",
        "India - Rupee" to "₹",
        "Russia - Ruble" to "₽",
        "Brazil - Real" to "R$",
        "Indonesia - Rupiah" to "Rp",
        "Mexico - Peso" to "$",
        "New Zealand - Dollar" to "$",
        "Hong Kong - Dollar" to "$",
        "Sweden - Krona" to "kr",
        "Norway - Krone" to "kr",
        "Denmark - Krone" to "kr",
        "Philippines - Peso" to "₱",
        "Taiwan - Dollar" to "NT$"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupSystemBars()
        initializeViews()
        setupSpinners()
        setupTextWatchers()
        setupFocusListeners()
    }

    private fun setupSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeViews() {
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
    }

    private fun setupSpinners() {
        val currencyList = currencyRates.toSortedMap().keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        val initialValue = 0

        spinner1.adapter = adapter
        spinner2.adapter = adapter


        input1.setText(initialValue.toString())
        spinner2.setSelection(currencyList.indexOf("Vietnam - Dong"))
        spinner1.setSelection(currencyList.indexOf("United States - Dollar"))

        setupSpinnerListeners()
    }

    // Thay đổi tiền tệ (Giá trị Spinner)
    private fun setupSpinnerListeners() {
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    updateConversion()
                }

                val selectedCurrency1 = spinner1.selectedItem?.toString() ?: ""
                val selectedCurrency2 = spinner2.selectedItem?.toString() ?: ""

                findViewById<TextView>(R.id.sign1).text = currencySymbols[selectedCurrency1] ?: ""
                findViewById<TextView>(R.id.sign2).text = currencySymbols[selectedCurrency2] ?: ""
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner1.onItemSelectedListener = spinnerListener
        spinner2.onItemSelectedListener = spinnerListener
    }


    // Thay đổi giá trị EditText
    private fun setupTextWatchers() {
        val maxDigits = 10
        val numberFormat: NumberFormat = DecimalFormat.getInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating) {
                    val currentInput = if (isSourceInput) input1 else input2

                    currentInput.filters = arrayOf(InputFilter.LengthFilter(maxDigits))

                    if (s != null && s.isNotEmpty()) {
                        val cleanString = s.toString().replace(",", "")
                        try {
                            val formatted = numberFormat.format(cleanString.toLong())
                            isUpdating = true
                            currentInput.setText(formatted)
                            currentInput.setSelection(formatted.length)
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        } finally {
                            isUpdating = false
                        }
                    }

                    if (s.isNullOrEmpty()) {
                        currentInput.setText("0")
                        currentInput.selectAll()
                    } else if (s.toString() == "0") {
                        currentInput.selectAll()
                    }
                    updateConversion()
                }
            }
        }

        input1.addTextChangedListener(textWatcher)
        input2.addTextChangedListener(textWatcher)
    }



    // Thay đổi input nguồn, đích khi focus vào EditText tương ứng
    private fun setupFocusListeners() {
        input1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isSourceInput = true
                input1.selectAll()
            }
        }

        input2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isSourceInput = false
                input2.selectAll()
            }
        }
    }

    // Hàm cập nhật dữ liệu, gọi mỗi khi thay đổi EditText hoặc Spinner
    private fun updateConversion() {
        try {
            isUpdating = true

            val sourceInput = if (isSourceInput) input1 else input2
            val targetInput = if (isSourceInput) input2 else input1
            val sourceSpinner = if (isSourceInput) spinner1 else spinner2
            val targetSpinner = if (isSourceInput) spinner2 else spinner1


            val sourceText = sourceInput.text.toString().replace(",", "")
            if (sourceText.isEmpty()) {
                input1.setText("0")
                input2.setText("0")
                return
            }

            val sourceCurrency = sourceSpinner.selectedItem?.toString() ?: return
            val targetCurrency = targetSpinner.selectedItem?.toString() ?: return

            val sourceValue = sourceText.toDoubleOrNull() ?: return

            val sourceRate = currencyRates[sourceCurrency] ?: return
            val targetRate = currencyRates[targetCurrency] ?: return
            val result = sourceValue * targetRate / sourceRate

            val formattedResult = NumberFormat.getNumberInstance(Locale.US).format(result)

            targetInput.setText(formattedResult)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isUpdating = false
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}