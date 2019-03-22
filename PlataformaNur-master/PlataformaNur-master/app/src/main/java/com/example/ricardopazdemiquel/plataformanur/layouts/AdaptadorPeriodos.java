package com.example.ricardopazdemiquel.plataformanur.layouts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ricardopazdemiquel.plataformanur.Objs.Periodo;
import com.example.ricardopazdemiquel.plataformanur.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPeriodos extends BaseAdapter {

    private Context context;
    private List<Periodo> periodos;

    public AdaptadorPeriodos(Context context, List<Periodo> periodos) {
        this.context = context;
        this.periodos = periodos;
    }

    public AdaptadorPeriodos(Context context, JSONArray periodosJson) {
        this.context = context;

        periodos = new ArrayList<>();

        try {
            for (int i = 0; i < periodosJson.length(); i++) {
                JSONObject periodoJson = periodosJson.getJSONObject(i);

                Periodo periodo = new Periodo();
                periodo.setLPERIODO_ID(periodoJson.getInt("LPERIODO_ID"));
                periodo.setSPERIODO_DSC(periodoJson.getString("SPERIODO_DSC"));

                periodos.add(periodo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return periodos.size();
    }

    @Override
    public Object getItem(int i) {
        return periodos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.textview);
        textView.setTextSize(22);
        textView.setTextColor(Color.WHITE);
        textView.setText(periodos.get(i).getSPERIODO_DSC());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);

        view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));

        return view;
    }
}
