package com.ivan.prokofyev.handh_test.data.local;

import android.database.sqlite.SQLiteDatabase;

import com.ivan.prokofyev.handh_test.data.model.User;
import com.ivan.prokofyev.handh_test.data.model.Weather;
import com.ivan.prokofyev.handh_test.data.model.WeatherResponse;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Weather> setWeather(final WeatherResponse newWeather) {
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()) return;
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                mDb.delete(Db.WeatherTable.TABLE_NAME, null);
                long result = mDb.insert(Db.WeatherTable.TABLE_NAME,
                        Db.WeatherTable.toContentValues(newWeather),
                        SQLiteDatabase.CONFLICT_REPLACE);
                if (result >= 0) subscriber.onNext(Weather.create(newWeather));
                transaction.markSuccessful();
                subscriber.onCompleted();
            } finally {
                transaction.end();
            }
        });
    }

    public Observable<Weather> getWeather() {
        return mDb.createQuery(Db.WeatherTable.TABLE_NAME,
                "SELECT * FROM " + Db.WeatherTable.TABLE_NAME)
                .mapToOne(cursor ->
                        Weather.create(Db.WeatherTable.parseCursor(cursor)));
    }


    public Observable<User> addUser(User user){
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()) return;
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                long result = mDb.insert(Db.UserTable.TABLE_NAME,
                        Db.UserTable.toContentValues(user),
                        SQLiteDatabase.CONFLICT_REPLACE);
                if (result >= 0) subscriber.onNext(user);
                transaction.markSuccessful();
                subscriber.onCompleted();
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                transaction.end();
            }
        });
    }

    public Observable<User> getUser(String email) {
        Observable<User> user =  mDb.createQuery(Db.UserTable.TABLE_NAME,
                "SELECT * FROM " + Db.UserTable.TABLE_NAME + " WHERE " +
                        Db.UserTable.COLUMN_EMAIL + " = '" + email + "'")
                .mapToOneOrDefault(Db.UserTable::parseCursor, null);
    return user;
    }

    public Observable<User> getUserByToken(String token) {
        Observable<User> user =  mDb.createQuery(Db.UserTable.TABLE_NAME,
                "SELECT * FROM " + Db.UserTable.TABLE_NAME + " WHERE " +
                        Db.UserTable.COLUMN_TOKEN + " = '" + token + "'")
                .mapToOneOrDefault(Db.UserTable::parseCursor, null);
        return user;
    }


}
