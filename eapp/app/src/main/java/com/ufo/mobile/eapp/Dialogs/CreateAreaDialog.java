package com.ufo.mobile.eapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufo.mobile.eapp.R;

import java.util.ArrayList;
import java.util.List;

import ModelManager.Area;
import ModelManager.Section;

public class CreateAreaDialog extends Dialog {

    private Context mContext;
    private CreateAreaCallback callback;
    private List<Section> sections;
    private Section selectedSection;

    private EditText editName;
    private Spinner spnSections;
    private Button btnCancel,btnSave;

    public CreateAreaDialog(List<Section> sections,@NonNull Context context, final CreateAreaCallback callback) {
        super(context);
        this.sections = sections;
        mContext = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editName = (EditText) findViewById(R.id.edit_name);
        spnSections = (Spinner) findViewById(R.id.spn_section);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);

        populateSpinner();

        // Actions
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveArea();
            }
        });
        spnSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    selectedSection = null;
                }else{
                    selectedSection = sections.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateSpinner(){
        List<String> list = new ArrayList<>();
        list.add(mContext.getResources().getString(R.string.section));
        for (int i = 0; i < sections.size(); i++){
            list.add(sections.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.simple_spinner_item,list);
        spnSections.setAdapter(adapter);
    }

    private void saveArea(){
        String name = editName.getText().toString();

        if(name != null && !name.isEmpty() && selectedSection != null){
            Area area = new Area();
            area.setName(name);
            callback.createAreaCallback(area,selectedSection);
        }else{
            Toast.makeText(mContext,mContext.getResources().getString(R.string.fill_the_fields),Toast.LENGTH_LONG).show();
        }

    }
}
