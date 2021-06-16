package com.example.myapplication.ui.msc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.MyStateAdapter;
import com.example.myapplication.EventBus.SheetClick;
import com.example.myapplication.R;
import com.example.myapplication.ui.btech.BtechViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MscFragment extends Fragment {


    Unbinder unbinder;
    private MscViewModel mscViewModel;
    private BottomSheetDialog yearBottomSheetDialog, semBottomSheetDialog;
    String[] yearArray = new String[]{
            "2010",
            "2011",
            "2012",
            "2013",
            "2014",
            "2015",
            "2016",
            "2017",
            "2018",
            "2019",
            "2020",
            "2021"
    };

    String[] semArray = new String[]{
            "Semester I",
            "Semester II",
            "Semester III",
            "Semester IV",
            "Semester V",
            "Semester VI"
    };

    int year, semester, type;

    //type 1 - semester long
    //type 2 - month long
    //type 3 - internship

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.year_text_input)
    TextInputLayout year_text_input;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_year)
    TextInputEditText edt_year;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.semester_text_input)
    TextInputLayout semester_text_input;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_semester)
    TextInputEditText edt_semester;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.search)
    MaterialButton search;

    RadioButton rdn_internship, rdn_semLong, rdn_monthLong;


    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.edt_year)
    void yearSheetOpen() {
        yearBottomSheetDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.edt_semester)
    void semesterSheetOpen() {
        semBottomSheetDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.search)
    void search() {
        check();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mscViewModel =
                new ViewModelProvider(this).get(MscViewModel.class);
        View root = inflater.inflate(R.layout.fragment_btech, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews(root);
        initYearDialog();

        initSemesterDialog();

        return root;
    }


    private void initViews(View root) {
        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle("M.Sc Projects");

        rdn_internship = root.findViewById(R.id.rdi_internship);
        rdn_monthLong = root.findViewById(R.id.rdi_month_long);
        rdn_semLong = root.findViewById(R.id.rdi_semester_long);

        textWatchers();
    }

    private void textWatchers() {
        edt_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (year_text_input.isErrorEnabled())
                    year_text_input.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_semester.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (semester_text_input.isErrorEnabled())
                    semester_text_input.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void check() {
        if (Objects.requireNonNull(edt_year.getText()).toString().length() < 1)
            year_text_input.setError("Provide your year");
        else if (Objects.requireNonNull(edt_semester.getText()).toString().length() < 1)
            semester_text_input.setError("Provide your semester");
        else {
            year = Integer.parseInt(edt_year.getText().toString());
            semester = convertSemesterToInt(edt_semester.getText().toString().replace("Semester ", ""));

            //type 1 - semester long
            //type 2 - month long
            //type 3 - internship
            if (rdn_semLong.isChecked())
                type = 1;
            else if (rdn_monthLong.isChecked())
                type = 2;
            else if (rdn_internship.isChecked())
                type = 3;

            String linkOperation = "" + year + semester + type;
            String driveLink = "https://google.com";
            switch (linkOperation) {
                case "201011":
                    driveLink = "https://github.com/";
                    break;
                //Similarly make cases
                //first four letters are the year, next letter is semester, and the next is type of project
                //type 1 - semester long
                //type 2 - month long
                //type 3 - internship
                //code should be exact 6 digit and should be string
            }

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(driveLink));
            startActivity(i);

        }
    }

    private int convertSemesterToInt(String charAt) {
        switch (charAt) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            default:
                return 0;
        }
    }

    private void initSemesterDialog() {
        semBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.DialogStyle);
        View layout_edit_display = getLayoutInflater().inflate(R.layout.fragment_sheet, null);
        RecyclerView recycler_sheet = layout_edit_display.findViewById(R.id.recycler_state);
        semBottomSheetDialog.setContentView(layout_edit_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler_sheet.setLayoutManager(layoutManager);
        recycler_sheet.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        MyStateAdapter stateAdapter = new MyStateAdapter(getContext(), semArray, 1);
        recycler_sheet.setAdapter(stateAdapter);

    }

    private void initYearDialog() {
        yearBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.DialogStyle);
        View layout_edit_display = getLayoutInflater().inflate(R.layout.fragment_sheet, null);
        RecyclerView recycler_sheet = layout_edit_display.findViewById(R.id.recycler_state);
        yearBottomSheetDialog.setContentView(layout_edit_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler_sheet.setLayoutManager(layoutManager);
        recycler_sheet.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        MyStateAdapter stateAdapter = new MyStateAdapter(getContext(), yearArray, 0);
        recycler_sheet.setAdapter(stateAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void sheetClick(SheetClick event) {
        if (event.isSuccess()) {
            if (event.getOperation() == 0) {
                yearBottomSheetDialog.dismiss();
                edt_year.setText(event.getString());
            } else if (event.getOperation() == 1) {
                semBottomSheetDialog.dismiss();
                edt_semester.setText(event.getString());
            }
        }
    }
}