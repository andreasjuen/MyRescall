package com.example.juena.myrescall02;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juena.myrescall02.data.NumberContract;
import com.example.juena.myrescall02.data.NumberDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NumberAdapter.ListItemClickListener {

    private EditText ps;
    private EditText kw;
    private EditText datum;
    private EditText woche;
    private Button del01;
    private Button del02;
    private Button speichern;
    private Button loeschen;

    private SQLiteDatabase mDb;
    private NumberAdapter mAdapter;

    Cursor gCursor;

    private Toast mToast;

    double psDou;
    double kwDou;
    String datumSt;
    int wocheInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView numberRecyclerView = (RecyclerView) this.findViewById(R.id.all_number_list_view);
        numberRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NumberDbHelper dbHelper = new NumberDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllNumber();

        mAdapter = new NumberAdapter(this, cursor, this);
        numberRecyclerView.setAdapter(mAdapter);

        ps = (EditText) findViewById(R.id.et_ps);
        ps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    psToKw();
                }
            }
        });
        kw = (EditText) findViewById(R.id.et_kw);
        kw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    kwToPs();
                }
            }
        });
        datum = (EditText) findViewById(R.id.et_datum);
        datum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    datumToWoche();
                }
            }
        });

        woche = (EditText) findViewById(R.id.et_woche);
        woche.setKeyListener(null);

        del01 = (Button)findViewById(R.id.bo_del01);
        del01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.d("Button del1", "löschen von ps und kw Textedit");
                deletePSKW();
            }
        });

        del02 = (Button)findViewById(R.id.bo_del02);
        del02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.d("Button del2", "löschen von ps und kw Textedit");
                deleteDatumWoche();
            }
        });

        speichern = (Button)findViewById(R.id.bo_speichern);
        speichern.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.d("Button speichern", "Speicherung der Daten");
                speichernNumbers();
                mAdapter.swapCursor(getAllNumber());
            }
        });

        loeschen = (Button)findViewById(R.id.bo_loeschen);
        loeschen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.d("Button loeschen", "Löschung der Daten");
                removeNumberAll();
                mAdapter.swapCursor(getAllNumber());

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeNumber(id);
                mAdapter.swapCursor(getAllNumber());
            }

        }).attachToRecyclerView(numberRecyclerView);

    }

    private long addNewNumber(String ps, String kw, String datum, String woche) {
        Log.d("neuer Eintrag", "");
        ContentValues cv = new ContentValues();
        cv.put(NumberContract.NumberEntry.COLUMN_ps, ps);
        cv.put(NumberContract.NumberEntry.COLUMN_kw, kw);
        cv.put(NumberContract.NumberEntry.COLUMN_datum, datum);
        cv.put(NumberContract.NumberEntry.COLUMN_woche, woche);;
        return mDb.insert(NumberContract.NumberEntry.TABLE_NAME, null, cv);
    }

    private Cursor getAllNumber(){
        return mDb.query(
                NumberContract.NumberEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NumberContract.NumberEntry.COLUMN_TIMESTAMP);
    }

    private void speichernNumbers()
    {
        String psSt = ps.getText().toString();
        String kwSt = kw.getText().toString();
        String datumSt = datum.getText().toString();
        String wocheSt = woche.getText().toString();

        addNewNumber(psSt, kwSt, datumSt, wocheSt);
    }

    private boolean removeNumberAll() {
        return mDb.delete(NumberContract.NumberEntry.TABLE_NAME, null, null) == 1;
    }

    private void deletePSKW()
    {
        ps.setText("");
        kw.setText("");
    }

    private void deleteDatumWoche()
    {
        datum.setText("");
        woche.setText("");
    }

    private void psToKw()
    {
        try{ psDou = Double.parseDouble(ps.getText().toString()); }
        catch(NumberFormatException e){ System.err.println(e.getMessage());}
        try{ kwDou = Double.parseDouble(kw.getText().toString()); }
        catch(NumberFormatException e){ System.err.println(e.getMessage());}
        double ergebnis = psDou * 0.73549875d;
        kw.setText("");
        kw.setText(String.format("%.2f", ergebnis));
    }

    private void kwToPs()
    {
        try{ psDou = Double.parseDouble(ps.getText().toString()); }
        catch(NumberFormatException e){ System.err.println(e.getMessage());}
        try{ kwDou = Double.parseDouble(kw.getText().toString()); }
        catch(NumberFormatException e){ System.err.println(e.getMessage());}
        double ergebnis = psDou * 1.359621625d;
        ps.setText("");
        ps.setText(String.format("%.2f", ergebnis));
    }

    private void datumToWoche(){
        datumSt = datum.getText().toString();

        if(datumSt.length() == 0)
        {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date d;
        try{
            d = format.parse(datumSt);
        } catch (java.text.ParseException e) {
            Log.d("datumToWoche", "Falsche Formateingabe");
            Toast.makeText(getApplicationContext(), "Falsches Datumformat", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        woche.setText("");
        woche.setText(Integer.toString(week));
    }

    private boolean removeNumber(long id) {
        return mDb.delete(NumberContract.NumberEntry.TABLE_NAME, NumberContract.NumberEntry._ID + "=" + id, null) > 0;
    }

    @Override
    public void onListItemClick(int clickedItemIndex, TextView ps, TextView kw, TextView datum, TextView woche)
    {
        if (mToast != null)
        {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " wurde hinzugefügt";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);


        this.ps.setText(ps.getText().toString());
        this.kw.setText(kw.getText().toString());
        this.datum.setText(datum.getText().toString());
        this.woche.setText(woche.getText().toString());

        mToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Log.d("Menu", "erstellt");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch(itemThatWasClickedId)
        {
            case R.id.action_info:
                Intent startRandomNumberListeActivity = new Intent(this, Main2Activity.class);
                startActivity(startRandomNumberListeActivity);
                Log.d("action", "Liste");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
