package devlight.io.sample.components;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DbContentProvider extends ContentProvider {
    private SQLiteDbOpenHelper dbHelper;
    private final String dbName = "TODO.db";

    @Override
    public boolean onCreate() {
        this.dbHelper = new SQLiteDbOpenHelper(getContext(), "TODO.db", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return this.dbHelper.getWritableDatabase().query(this.dbName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return ContentUris.withAppendedId(uri, this.dbHelper.getWritableDatabase().insert(this.dbName, null, values));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return this.dbHelper.getWritableDatabase().delete(this.dbName, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return this.dbHelper.getWritableDatabase().update(this.dbName, values, selection, selectionArgs);
    }
}
