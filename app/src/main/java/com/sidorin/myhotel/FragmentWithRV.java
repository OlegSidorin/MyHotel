package com.sidorin.myhotel;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;

import static com.sidorin.myhotel.MainActivity.adapter;


public class FragmentWithRV extends Fragment {
    private static RecyclerView myRecyclerView;
    private Context viewContext;

    public FragmentWithRV() {
        // Нужен пустой конструктор (или не нужен?)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_rv, container, false);
        myRecyclerView = view.findViewById(R.id.rv);
        myRecyclerView.setHasFixedSize(true);
        System.out.println("создали recyclerView во фрагменте");

        MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);
        System.out.println("создали массив данных на текущую дату");
        adapter = new AdapterForRV(MainActivity.currentData);
        System.out.println("создали новый адаптер");
        myRecyclerView.setAdapter(adapter);
        System.out.println("установили адаптер в recyclerView");
        myRecyclerView.setLayoutManager(new LinearLayoutManager(MyHotel.getAppContext()));
        System.out.println("назначили стиль отображения");
        System.out.println("начало списка гостей: (из метода onCreateView фрагмента)");
        for (int k = 0; k < MainActivity.currentData.size(); k++) {
            System.out.println(" -- > " + MainActivity.currentData.get(k).getSurnameOfGuest());
        }
        System.out.println("конец списка гостей");

        TextView tv_info = getActivity().findViewById(R.id.tv_info_one);
        if (tv_info != null && adapter != null) tv_info.setText("" + adapter.getItemCount());
        return view;
    }

}
