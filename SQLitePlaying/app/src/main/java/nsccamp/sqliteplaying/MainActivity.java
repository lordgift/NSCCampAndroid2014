package nsccamp.sqliteplaying;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import nsccamp.sqliteplaying.helper.DataBaseHelper;
import nsccamp.sqliteplaying.persistence.Persistence;


public class MainActivity extends Activity {
    private DataBaseHelper myDbHelper;
    private Persistence persistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*------------------ Copy db from asset to android system ------------------*/
        myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.createDataBase();
            myDbHelper.openDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
		/*------------------ Copy db from asset to android system ------------------*/
        persistence = Persistence.getPersistService(myDbHelper);


        //TODO Use persistence to access database













    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
