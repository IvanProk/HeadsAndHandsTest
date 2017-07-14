package com.ivan.prokofyev.handh_test;

import com.ivan.prokofyev.handh_test.data.local.DatabaseHelper;
import com.ivan.prokofyev.handh_test.data.local.DbOpenHelper;
import com.ivan.prokofyev.handh_test.util.DefaultConfig;
import com.ivan.prokofyev.handh_test.util.RxSchedulersOverrideRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    private final DatabaseHelper mDatabaseHelper =
            new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application));

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Test
    public void setRibots() {
//        Ribot ribot1 = TestDataFactory.makeRibot("r1");
//        Ribot ribot2 = TestDataFactory.makeRibot("r2");
//        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);

//        TestSubscriber<Ribot> result = new TestSubscriber<>();
//        mDatabaseHelper.setWeather(ribots).subscribe(result);
//        result.assertNoErrors();
//        result.assertReceivedOnNext(ribots);
//
//        Cursor cursor = mDatabaseHelper.getBriteDb()
//                .query("SELECT * FROM " + Db.WeatherTable.TABLE_NAME);
//        assertEquals(2, cursor.getCount());
//        for (Ribot ribot : ribots) {
//            cursor.moveToNext();
//            assertEquals(ribot.profile(), Db.WeatherTable.parseCursor(cursor));
//        }
    }

    @Test
    public void getRibots() {
//        Ribot ribot1 = TestDataFactory.makeRibot("r1");
//        Ribot ribot2 = TestDataFactory.makeRibot("r2");
//        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);

//        mDatabaseHelper.setWeather(ribots).subscribe();
//
//        TestSubscriber<List<Ribot>> result = new TestSubscriber<>();
//        mDatabaseHelper.getWeather().subscribe(result);
//        result.assertNoErrors();
//        result.assertValue(ribots);
    }

}