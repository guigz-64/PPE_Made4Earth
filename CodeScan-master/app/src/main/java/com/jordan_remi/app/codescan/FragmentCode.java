package com.jordan_remi.app.codescan;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.jordan_remi.app.codescan.Ressource.db;

/**
 * Created by joplagne on 29/09/17.
 */

public class FragmentCode extends Fragment {

    private EditText informations;
    private TextView type, code;
    private ImageView img;
    private FloatingActionButton date;
    private Button valider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.presentation_code, container, false);
        img = view.findViewById(R.id.image);
        type = view.findViewById(R.id.type);
        date = view.findViewById(R.id.button_date);
        code = view.findViewById(R.id.code);
        valider = view.findViewById(R.id.valider);
        informations = view.findViewById(R.id.informations);

        final Code codebar = (Code) getActivity().getIntent().getExtras().get("code");

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int taille;
        if (size.x >= size.y){
            taille = Math.round(size.x*0.5f);
        }else{
            taille = Math.round(size.y*0.5f);
        }
        if (codebar != null && codebar.getImage(taille) != null)
            img.setImageBitmap(codebar.getImage(taille));
        type.setText(codebar.getTypeString());
        informations.setText(codebar.getInformations());
        code.setText(codebar.getCode());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Date de création du code : " + codebar.getDate().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codebar.setInformations(informations.getText().toString());
                db.updateCode(codebar);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
