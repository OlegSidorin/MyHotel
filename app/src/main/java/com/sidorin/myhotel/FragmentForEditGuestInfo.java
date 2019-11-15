package com.sidorin.myhotel;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentForEditGuestInfo extends Fragment {

    public FragmentForEditGuestInfo() {
        // Required empty public constructor
    }

    static int position;


    public void setPosition(int position) {
        this.position = position;
    }


    Calendar checkInDate, checkOutDate, today, tomorrow;

    {
        checkInDate = Calendar.getInstance();
        checkOutDate = Calendar.getInstance();
        today = Calendar.getInstance();
        tomorrow = Calendar.getInstance();
    }

    DatePickerDialog.OnDateSetListener datePickerIn = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            checkInDate.set(Calendar.YEAR, year);
            checkInDate.set(Calendar.MONTH, monthOfYear);
            checkInDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView tvCheckInDate = getActivity().findViewById(R.id.tv_date_check_in);
            tvCheckInDate.setText(DateUtils.formatDateTime(view.getContext(),
                    checkInDate.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    DatePickerDialog.OnDateSetListener datePickerOut = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            checkOutDate.set(Calendar.YEAR, year);
            checkOutDate.set(Calendar.MONTH, monthOfYear);
            checkOutDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView tvCheckOutDate = getActivity().findViewById(R.id.tv_date_check_out);
            tvCheckOutDate.setText(DateUtils.formatDateTime(view.getContext(),
                    checkOutDate.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    private void setDateCheckIn(View v) {
        new DatePickerDialog(v.getContext(), datePickerIn,
                checkInDate.get(Calendar.YEAR),
                checkInDate.get(Calendar.MONTH),
                checkInDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setDateCheckOut(View v) {
        new DatePickerDialog(v.getContext(), datePickerOut,
                checkOutDate.get(Calendar.YEAR),
                checkOutDate.get(Calendar.MONTH),
                checkOutDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_edit_guest_info, container, false);

        //находим все вьюшки
        final TextView tv_key = (TextView) view.findViewById(R.id.tv_key);
        final TextView tV_Name = (TextView) view.findViewById(R.id.inputFirstName);
        final TextView tv_SecondName = (TextView) view.findViewById(R.id.inputSecondName);
        final TextView tv_Phone = (TextView) view.findViewById(R.id.inputPhoneNumber);
        final TextView tv_CheckInDate = (TextView) view.findViewById(R.id.tv_date_check_in);
        final TextView tv_CheckOutDate = (TextView) view.findViewById(R.id.tv_date_check_out);
        final TextView tv_Room = (TextView) view.findViewById(R.id.idRoom);
        final Button button_save = view.findViewById(R.id.btn_save);
        final Button button_del = view.findViewById(R.id.btn_del);

        final int pos = AdapterForRV.selected_position; // определили позицию в адаптере

        if (pos != -1) { // блок редактирования данных гостя, позиция не -1

            System.out.println("pos == " + pos);
            DataItem dataItem = getGuestFromDatabase(MainActivity.currentData.get(pos).getCode());
            System.out.println("currentData.get(pos).getCode() == " + MainActivity.currentData.get(pos).getCode());

            tv_key.setText(dataItem.getCode());
            tV_Name.setText(dataItem.getName());
            tv_SecondName.setText(dataItem.getSurname());
            tv_Phone.setText(dataItem.getPhone());
            tv_Room.setText(dataItem.getRoom());

            checkInDate.set(Calendar.YEAR, Integer.parseInt(dataItem.getCheck_in_year()));
            checkInDate.set(Calendar.MONTH, Integer.parseInt(dataItem.getCheck_in_month()) - 1);
            checkInDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataItem.getCheck_in_day()));
            tv_CheckInDate.setText(DateUtils.formatDateTime(MyHotel.getAppContext(),
                    checkInDate.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
            System.out.println("записали дату заезда для " + dataItem.getSurname());

            checkOutDate.set(Calendar.YEAR, Integer.parseInt(dataItem.getCheck_out_year()));
            checkOutDate.set(Calendar.MONTH, Integer.parseInt(dataItem.getCheck_out_month()) - 1);
            checkOutDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataItem.getCheck_out_day()));
            tv_CheckOutDate.setText(DateUtils.formatDateTime(MyHotel.getAppContext(),
                    checkOutDate.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
            System.out.println("записали дату выезда для " + dataItem.getSurname());

            View.OnClickListener clickListenerOnIn = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDateCheckIn(view);
                }
            };

            View.OnClickListener clickListenerOnOut = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDateCheckOut(view);
                }
            };

            tv_CheckInDate.setOnClickListener(clickListenerOnIn);
            tv_CheckOutDate.setOnClickListener(clickListenerOnOut);

        } // конец блока редактирования данных гостя

        if (pos == -1) { // блок создания данных гостя и элемента в базе данных, позиция в адаптере -1
            tv_CheckInDate.setText(DateUtils.formatDateTime(MyHotel.getAppContext(),
                    today.getTimeInMillis(), DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
            tomorrow.getTime();
            tomorrow.add(Calendar.DATE, 1);
            tv_CheckOutDate.setText(DateUtils.formatDateTime(MyHotel.getAppContext(),
                    tomorrow.getTimeInMillis(), DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
            // создание уникального ключа бронирования
            if (today.get(Calendar.DAY_OF_MONTH) < 10 && today.get(Calendar.MONTH) < 9) {
                tv_key.setText((today.get(Calendar.YEAR)) +
                        "-0" + (today.get(Calendar.DAY_OF_MONTH)) +
                        "-0" + (today.get(Calendar.MONTH) + 1) +
                        "-" + "R" + (int) (1000 * (Math.random())));
            }
            if (today.get(Calendar.DAY_OF_MONTH) < 10 && today.get(Calendar.MONTH) >= 9) {
                tv_key.setText((today.get(Calendar.YEAR)) +
                        "-0" +(today.get(Calendar.DAY_OF_MONTH)) +
                        "-" + (today.get(Calendar.MONTH) + 1) +
                        "-" + "R" + (int) (1000 * (Math.random())));
            }
            if (today.get(Calendar.DAY_OF_MONTH) >= 10 && today.get(Calendar.MONTH) < 9) {
                tv_key.setText((today.get(Calendar.YEAR)) +
                        "-" + (today.get(Calendar.DAY_OF_MONTH)) +
                        "-0" + (today.get(Calendar.MONTH) + 1) +
                        "-" + "R" + (int) (1000 * (Math.random())));
            }
            if (today.get(Calendar.DAY_OF_MONTH) >= 10 && today.get(Calendar.MONTH) >= 9) {
                tv_key.setText((today.get(Calendar.YEAR)) +
                        "-" + (today.get(Calendar.DAY_OF_MONTH)) +
                        "-" + (today.get(Calendar.MONTH) + 1) +
                        "-" + "R" + (int) (1000 * (Math.random())));
            }

            checkInDate.set(Calendar.YEAR,today.get(Calendar.YEAR));
            checkInDate.set(Calendar.MONTH,today.get(Calendar.MONTH));
            checkInDate.set(Calendar.DAY_OF_MONTH,today.get(Calendar.DAY_OF_MONTH));

            checkOutDate.set(Calendar.YEAR,tomorrow.get(Calendar.YEAR));
            checkOutDate.set(Calendar.MONTH,tomorrow.get(Calendar.MONTH));
            checkOutDate.set(Calendar.DAY_OF_MONTH,tomorrow.get(Calendar.DAY_OF_MONTH));

            View.OnClickListener clickListenerOnIn = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDateCheckIn(view);
                }
            };

            View.OnClickListener clickListenerOnOut = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDateCheckOut(view);
                }
            };

            tv_CheckInDate.setOnClickListener(clickListenerOnIn);
            tv_CheckOutDate.setOnClickListener(clickListenerOnOut);

        } // конец блока создания бронирования

        // сохранение данных методом тыка в кнопку
        View.OnClickListener onClickListenerOnSave = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("запуск  обработки onclick()");
                // инициализируем пустой элемент
                DataItem dataItemToInsert = new DataItem(" ", " ", " ", " ",
                        " ", " ", " ", " ", " ", " ", " ");

                if (tv_SecondName.getText().toString().trim().isEmpty() || tV_Name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MyHotel.getAppContext(),"Заполните Имя и Фамилию", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataItemToInsert.setCode(tv_key.getText().toString());
                dataItemToInsert.setName(tV_Name.getText().toString());
                dataItemToInsert.setSurname(tv_SecondName.getText().toString());
                dataItemToInsert.setPhone(tv_Phone.getText().toString());
                dataItemToInsert.setCheck_in_year(String.valueOf(checkInDate.get(Calendar.YEAR)));
                dataItemToInsert.setCheck_in_month(String.valueOf(checkInDate.get(Calendar.MONTH) + 1));
                dataItemToInsert.setCheck_in_day(String.valueOf(checkInDate.get(Calendar.DAY_OF_MONTH)));
                dataItemToInsert.setCheck_out_year(String.valueOf(checkOutDate.get(Calendar.YEAR)));
                dataItemToInsert.setCheck_out_month(String.valueOf(checkOutDate.get(Calendar.MONTH) + 1));
                dataItemToInsert.setCheck_out_day(String.valueOf(checkOutDate.get(Calendar.DAY_OF_MONTH)));
                dataItemToInsert.setRoom(tv_Room.getText().toString());

                if (pos == -1) {
                    insertGuestInDatabase(dataItemToInsert); // вписали элемент в базу данных
                }
                if (pos != -1) {
                    updateGuestInDatabase(dataItemToInsert); // отредактировали
                }

                MainActivity.data = getAllDataFromDatabase(MyHotel.getAppContext()); // получаем данные из базы данных
                MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);

                FragmentWithRV newFRV = new FragmentWithRV();
                System.out.println("создали новый фрагмент по шаблону листа с данными");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                System.out.println("получили метод менеджера фрагментов");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, newFRV).commit();
                System.out.println("заменили старый фрагмент новым с новыми данными");

                // для альбомной ориентации
                View secondFragmentContainer = getActivity().findViewById(R.id.second_container);
                if (secondFragmentContainer == null) {
                    // nothing
                } else if (secondFragmentContainer != null) {
                    FramentPanda newFRMP = new FramentPanda();
                    FragmentTransaction fragmentTransactionSecondContainer = fragmentManager.beginTransaction();
                    fragmentTransactionSecondContainer.replace(R.id.second_container, newFRMP).commit();
                }

            }
        };

        button_save.setOnClickListener(onClickListenerOnSave);

        View.OnClickListener onClickListenerOnDel = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteGuestFromDatabase(tv_key.getText().toString()); // удалили элемент из базы данных

                MainActivity.data = getAllDataFromDatabase(MyHotel.getAppContext()); // получаем данные из базы данных
                MainActivity.currentData = DataTableItem.getCurrentData(MainActivity.data, MainActivity.currentDate);

                FragmentWithRV newFRV = new FragmentWithRV();
                System.out.println("создали новый фрагмент по шаблону листа с данными");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                System.out.println("получили метод менеджера фрагментов");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, newFRV).commit();
                System.out.println("заменили старый фрагмент новым с новыми данными");

                // специально для альбомной ориентации
                View secondFragmentContainer = getActivity().findViewById(R.id.second_container);
                if (secondFragmentContainer == null) {
                    // nothing
                } else if (secondFragmentContainer != null) {
                    FramentPanda newFRMP = new FramentPanda();
                    FragmentTransaction fragmentTransactionSecondContainer = fragmentManager.beginTransaction();
                    fragmentTransactionSecondContainer.replace(R.id.second_container, newFRMP).commit();
                }
            }
        };

        button_del.setOnClickListener(onClickListenerOnDel);

        return view;
    }
    // передача данных из базы данных в массив
    private ArrayList<DataItem> getAllDataFromDatabase(Context context) {

        SQLiteOpenHelper helper = new GuestDatabaseHelper(MyHotel.getAppContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query("GUESTS", new String[]{"_id", "NAME", "SURNAME", "PHONE", "CINY",
                "CINM", "CIND", "COUTY", "COUTM", "COUTD", "ROOM"}, null, null, null, null, null);

        ArrayList<DataItem> data = new ArrayList<>();

        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                DataItem dataItem = new DataItem(
                        " ", " ", " ", " ",
                        " ", " ", " ",
                        " ", " ", " ", " ");

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

    // вставка элемента в базу данных
    private static void insertGuestInDatabase(DataItem dataItem) {

        SQLiteOpenHelper helper = new GuestDatabaseHelper(MyHotel.getAppContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contactValues = new ContentValues();

        contactValues.put("_id", dataItem.getCode());
        contactValues.put("NAME", dataItem.getName());
        contactValues.put("SURNAME", dataItem.getSurname());
        contactValues.put("PHONE", dataItem.getPhone());
        contactValues.put("CINY", dataItem.getCheck_in_year());
        contactValues.put("CINM", dataItem.getCheck_in_month());
        contactValues.put("CIND", dataItem.getCheck_in_day());
        contactValues.put("COUTY", dataItem.getCheck_out_year());
        contactValues.put("COUTM", dataItem.getCheck_out_month());
        contactValues.put("COUTD", dataItem.getCheck_out_day());
        contactValues.put("ROOM", dataItem.getRoom());

        db.insert("GUESTS", null, contactValues);
        db.close();
    }
    //обновление инфо по бронированию гостя
    private static void updateGuestInDatabase(DataItem dataItem) {

        SQLiteOpenHelper helper = new GuestDatabaseHelper(MyHotel.getAppContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contactValues = new ContentValues();

        contactValues.put("_id", dataItem.getCode());
        contactValues.put("NAME", dataItem.getName());
        contactValues.put("SURNAME", dataItem.getSurname());
        contactValues.put("PHONE", dataItem.getPhone());
        contactValues.put("CINY", dataItem.getCheck_in_year());
        contactValues.put("CINM", dataItem.getCheck_in_month());
        contactValues.put("CIND", dataItem.getCheck_in_day());
        contactValues.put("COUTY", dataItem.getCheck_out_year());
        contactValues.put("COUTM", dataItem.getCheck_out_month());
        contactValues.put("COUTD", dataItem.getCheck_out_day());
        contactValues.put("ROOM", dataItem.getRoom());

        db.update("GUESTS",contactValues,"_id = ?", new String[] {dataItem.getCode()});
        db.close();
    }

    // получить элемент из базы данны по уникальному ключу бронирования
    private static DataItem getGuestFromDatabase(String code){

        SQLiteOpenHelper helper = new GuestDatabaseHelper(MyHotel.getAppContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        // инициализируем пустой элемент
        DataItem dataItem = new DataItem(" ", " ", " ", " ",
                " ", " ", " ", " ", " ", " ", " ");

        Cursor cursor = db.query("GUESTS", new String[]{"_id", "NAME", "SURNAME", "PHONE",
                        "CINY", "CINM", "CIND", "COUTY", "COUTM", "COUTD", "ROOM"},
                "_id = ?", new String[]{code}, null, null, null);

        if (cursor.moveToFirst()) {
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
            //Toast.makeText(MyHotel.getAppContext(),"Прочли данные из БД", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return dataItem;
    }

    //удаление элемента из базы данных по ключу бронирования
    public static void deleteGuestFromDatabase(String code){

        SQLiteOpenHelper helper = new GuestDatabaseHelper(MyHotel.getAppContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        db.delete("GUESTS","_id = ?", new String[] {code});
        db.close();
    }
}
