package cl.telematica.tareamultimedios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LikesSQLiteHelper extends SQLiteOpenHelper {
	
	String sqlCreate = "CREATE TABLE Likes (id INTEGER PRIMARY KEY, name TEXT)";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "LikeDB";
	public LikesSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Likes");
		onCreate(db);
	}

}
