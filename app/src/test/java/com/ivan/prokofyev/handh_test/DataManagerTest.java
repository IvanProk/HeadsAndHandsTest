package com.ivan.prokofyev.handh_test;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.data.local.DatabaseHelper;
import com.ivan.prokofyev.handh_test.data.local.PreferencesHelper;
import com.ivan.prokofyev.handh_test.data.remote.ApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock DatabaseHelper mMockDatabaseHelper;
    @Mock PreferencesHelper mMockPreferencesHelper;
    @Mock
    ApiService mMockApiService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockApiService, mMockPreferencesHelper,
                mMockDatabaseHelper);
    }

    @Test
    public void syncRibotsEmitsValues() {
//        List<Ribot> ribots = Arrays.asList(TestDataFactory.makeRibot("r1"),
//                TestDataFactory.makeRibot("r2"));
//        stubSyncRibotsHelperCalls(ribots);

//        TestSubscriber<Ribot> result = new TestSubscriber<>();
//        mDataManager.syncWeather().subscribe(result);
//        result.assertNoErrors();
//        result.assertReceivedOnNext(ribots);
    }

    @Test
    public void syncRibotsCallsApiAndDatabase() {
//        List<Ribot> ribots = Arrays.asList(TestDataFactory.makeRibot("r1"),
//                TestDataFactory.makeRibot("r2"));
//        stubSyncRibotsHelperCalls(ribots);

//        mDataManager.syncWeather().subscribe();
        // Verify right calls to helper methods
//        verify(mMockApiService).getRibots();
//        verify(mMockDatabaseHelper).setWeather(ribots);
    }

    @Test
    public void syncRibotsDoesNotCallDatabaseWhenApiFails() {
//        when(mMockApiService.getRibots())
//                .thenReturn(Observable.<List<Ribot>>error(new RuntimeException()));

//        mDataManager.syncWeather().subscribe(new TestSubscriber<Ribot>());
//        // Verify right calls to helper methods
//        verify(mMockApiService).getRibots();
//        verify(mMockDatabaseHelper, never()).setWeather(ArgumentMatchers.<Ribot>anyList());
    }

//    private void stubSyncRibotsHelperCalls(List<Ribot> ribots) {
        // Stub calls to the prokofyev service and database helper.
//        when(mMockApiService.getRibots())
//                .thenReturn(Observable.just(ribots));
//        when(mMockDatabaseHelper.setWeather(ribots))
//                .thenReturn(Observable.from(ribots));
//    }

}
