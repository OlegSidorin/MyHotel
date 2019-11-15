package com.sidorin.myhotel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<DataItem> data;
    //static HashMap<String, DataItem> dataMap;
    static ArrayList<DataTableItem> currentData;
    static AdapterForRV adapter;
    private Toolbar toolbar;
    DataItem selectedItem;
    String selectedSurname, selectedName;
    private SQLiteDatabase db;
    private Cursor cursor;
    TextView currentDateView;
    static String currentYear, currentMonth, currentDay;
    static Calendar currentDate, varCheckInDate, varCheckOutDate, today;

    static {
        currentDate = Calendar.getInstance();
        varCheckInDate = Calendar.getInstance();
        varCheckOutDate = Calendar.getInstance();
        today = Calendar.getInstance();
    }
    // метод setDate установлен в кнопку
    public void setDate(View v) {
        new DatePickerDialog(MainActivity.this, datePicker,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        currentDateView = (TextView) findViewById(R.id.tvDate);
        currentDateView.setText(DateUtils.formatDateTime(this,
                currentDate.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        System.out.println("записали значение в окно даты (setInitialDateTime)");
    }

    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            currentDate.set(Calendar.YEAR, year);
            currentDate.set(Calendar.MONTH, monthOfYear);
            currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
            updateFragment();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_edit:
                MainActivity.data = getAllDataFromDatabase(MyHotel.getAppContext()); // получаем данные из базы данных
                MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);
                editGuestInfo();
                //Toast.makeText(getApplicationContext(),"Guest Info",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_home:
                MainActivity.data = getAllDataFromDatabase(MyHotel.getAppContext()); // получаем данные из базы данных
                MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);
                today.getTime();
                currentDate.set(Calendar.YEAR, today.get(Calendar.YEAR));
                currentDate.set(Calendar.MONTH, today.get(Calendar.MONTH));
                currentDate.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
                setInitialDateTime();
                updateFragment();
                //Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_update_fragment:
                MainActivity.data = getAllDataFromDatabase(MyHotel.getAppContext()); // получаем данные из базы данных
                MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);
                updateFragment();
                //Toast.makeText(getApplicationContext(),"Update",Toast.LENGTH_SHORT).show();
                break;
            default:
        } return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("string", "перевернулись и сохранились");
    }

    @Override // запуск приложения
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            selectedName = savedInstanceState.getString("string");
        }
        setContentView(R.layout.main_activity_layout);
        setInitialDateTime();
        System.out.println("установили начальную дату (MainActivity)");

        MainActivity.data = getAllDataFromDatabase(MyHotel.getAppContext()); // получаем данные из базы данных
        MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);

        System.out.println("создали сортированный массив данных (MainActivity)");
        System.out.println("запускаем начальный updateFragment()");
        updateFragment();

        TextView tv_onSave = findViewById(R.id.textOnSave);
        System.out.println("посчитали и записали количество данных (MainActivity)");
        tv_onSave.setTextColor(Color.RED);
        if (selectedName != null) tv_onSave.setText(selectedName);
    }

    private void editGuestInfo(){
            System.out.println("запуск фрагмента на создание / редактирование данных гостя");
            FragmentForEditGuestInfo newFEGI = new FragmentForEditGuestInfo();
            System.out.println("создали новый фрагмент по шаблону редактирвания");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            View secondFragmentContainer = findViewById(R.id.second_container);
            fragmentTransaction.addToBackStack(null);
            if (secondFragmentContainer == null) {
                fragmentTransaction.replace(R.id.main_container, newFEGI).commit();
            } else if (secondFragmentContainer != null) {
                fragmentTransaction.replace(R.id.second_container, newFEGI).commit();
            }

            System.out.println("заменили старый фрагмент новым с новыми данными");

    }

    private void updateFragment() {
        AdapterForRV.selected_position = -1;
        System.out.println("запуск метода updateFragment");
        MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);
        System.out.println("создали новый массив данных для адаптера на текущую дату в методе updateFragment");
        FragmentWithRV newFRV = new FragmentWithRV();
        System.out.println("создали новый фрагмент");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, newFRV).commit();
        System.out.println("заменили старый фрагмент новым с новыми данными");
    }

    private ArrayList<DataItem> getAllDataFromDatabase(Context context) {

        SQLiteOpenHelper helper = new GuestDatabaseHelper(MyHotel.getAppContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        // выборка всех данных из БД в курсор
        Cursor cursor = db.query("GUESTS", new String[] {"_id", "NAME", "SURNAME", "PHONE", "CINY",
                "CINM", "CIND", "COUTY", "COUTM", "COUTD", "ROOM"}, null, null, null, null, null);


        ArrayList<DataItem> data = new ArrayList<>();

        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                DataItem dataItem = new DataItem(
                        " ", " "," "," ",
                        " "," "," ",
                        " "," "," "," ");

                dataItem.setCode(cursor.getString(0));
                dataItem.setName(cursor.getString(1));
                dataItem.setSurname(cursor.getString(2));
                dataItem.setPhone(cursor.getString(3));
                dataItem.setCheck_in_year(cursor.getString(4));
                dataItem.setCheck_in_month(cursor.getString(5));
                dataItem.setCheck_in_day(cursor.getString(6));
                dataItem.setCheck_out_year(cursor.getString(7));
                dataItem.setCheck_out_month(cursor.getString(8));
                dataItem.setCheck_out_day(cursor.getString(9));
                dataItem.setRoom(cursor.getString(10));
                data.add(dataItem);

                i = i + 1;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return data;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //cursor.close();
        //db.close();
    }
}
