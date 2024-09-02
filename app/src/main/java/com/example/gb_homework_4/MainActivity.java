package com.example.gb_homework_4;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gb_homework_4.databinding.ActivityMainBinding;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateProgress();

        binding.getRoot().setOnTouchListener((v, event) -> {
            hideKeyboard(binding.getRoot());
            return false;
        });

        binding.switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.checkBox1.setEnabled(isChecked);
            binding.checkBox2.setEnabled(isChecked);
            validateInputs();
        });

        binding.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateInputs();
            }
        });

        binding.numberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateInputs();
            }
        });

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            validateInputs();
        });

        binding.saveButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void validateInputs() {
        boolean isNameValid = !Objects.requireNonNull(binding.nameEditText.getText()).toString().trim().isEmpty();
        boolean isPhoneValid = !Objects.requireNonNull(binding.numberEditText.getText()).toString().trim().isEmpty();
        boolean isGenderSelected = binding.radioGroup.getCheckedRadioButtonId() != -1;

        boolean isAllFieldsValid = isNameValid && isPhoneValid && isGenderSelected;

        binding.saveButton.setEnabled(isAllFieldsValid);
    }

    private void updateProgress() {
        int progress = new Random().nextInt(101); // Значение от 0 до 100
        binding.pointsProgressBar.setProgress(progress);
        binding.pointsText.setText(progress + " баллов");
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}