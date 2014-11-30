package nsccamp.sqliteplaying.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nsccamp.sqliteplaying.helper.DataBaseHelper;

public class Persistence {
	private static final String LOG_TAG = "Persistence";
    private static Persistence persistence;
    private static DataBaseHelper dbHelper;

	private Persistence() {
	}

    public static Persistence getPersistService(DataBaseHelper dbHelper) {
        persistence = persistence == null ? new Persistence() : persistence;
        Persistence.dbHelper = dbHelper;
        return persistence;
    }

    /**
     * query all records
     * @return list of string[]
     */
    public List<String[]> readRows() {
        String sql = "SELECT * FROM DEVELOPER ORDER BY _ID";
		try (   SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursorResult = db.rawQuery(sql,null)  ){

			List<String[]> list = new ArrayList<>();
			while (cursorResult.moveToNext()) {
                String[] columns = new String[2];
                columns[0] = cursorResult.getString(cursorResult.getColumnIndex("FIRSTNAME"));
                columns[1] = cursorResult.getString(cursorResult.getColumnIndex("LASTNAME"));

				list.add(columns);
			}

			return list;

		} catch (Exception ex) {
			Log.e(LOG_TAG, "Error in readRows", ex);
		}
		return null;
    }
}
