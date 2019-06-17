package com.olukoye.hannah.savicsmedic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class PatientsAdapter extends ArrayAdapter<Patient> {
    public PatientsAdapter(Context context, ArrayList<Patient> patients) {
        super(context, 0, patients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Patient patient = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.patient_list, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.patient_details);
        tvName.setText(patient.counter + " Patient { fullName:" + patient.fullname +" ,Age: "+ patient.age +
                " ,Gender: " +patient.gender +" }");
        return convertView;
    }

}
