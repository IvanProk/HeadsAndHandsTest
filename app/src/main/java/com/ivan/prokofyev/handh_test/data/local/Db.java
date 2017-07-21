package com.ivan.prokofyev.handh_test.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.ivan.prokofyev.handh_test.data.model.User;
import com.ivan.prokofyev.handh_test.data.model.Weather;
import com.ivan.prokofyev.handh_test.data.model.WeatherResponse;

public class Db {

    public Db() { }

    public abstract static class WeatherTable {
        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TEMPERATURE = "temperature";


        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_TEMPERATURE + " INTEGER NOT NULL" + " ); ";

        public static ContentValues toContentValues(WeatherResponse weather) {
            ContentValues values = new ContentValues();
            if(weather.weather()!= null && weather.weather().size()!=0)
                values.put(COLUMN_DESCRIPTION, weather.weather().get(0).description());
            if(weather.main() != null)
                values.put(COLUMN_TEMPERATURE, weather.main().temp());
            return values;
        }

        public static Weather parseCursor(Cursor cursor) {
            return Weather.builder()
                    .setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)))
                    .setTemperature(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEMPERATURE)))
                    .build();
        }
    }

    public abstract static class UserTable {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_TOKEN = "token";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_TOKEN + " TEXT" + " ); ";

        public static ContentValues toContentValues(User user) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, user.name());
            values.put(COLUMN_EMAIL, user.email());
            values.put(COLUMN_PASSWORD, user.password());
            values.put(COLUMN_TOKEN, user.token());
            return values;
        }

        public static User parseCursor(Cursor cursor) {
            return User.builder()
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
                    .setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)))
                    .setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)))
                    .setToken(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOKEN)))
                    .build();
        }
    }
}
