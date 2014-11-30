package nsccamp.sqliteplaying.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @see <a
 *      href="http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/">www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications</a>
 * 
 * 
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DATABASE_PATH = null;	//contain DATABASE_NAME
	private static String DATABASE_NAME = "Sample.db";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 *
	 */
    public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		this.myContext = context;
		
		File outFile = context.getDatabasePath(DATABASE_NAME);
		DATABASE_PATH = outFile.getPath();
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		if (!isDatabaseExists()) {
			/* By calling this method and empty database will be created into
			 * the default system path
			 * of your application so we are gonna be able to overwrite that
			 * database with our database.
			 */
			this.getReadableDatabase();
			copyDataBase();
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean isDatabaseExists() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DATABASE_PATH;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database doesn't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
            return true;
		}
		return false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() {
		try {

			// Open your local db as the input stream
			InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

			// Path to the just created empty db
			String outFileName = DATABASE_PATH;

			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (IOException e) {
			Log.e("", "Error copying database");
		}
	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DATABASE_PATH;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}