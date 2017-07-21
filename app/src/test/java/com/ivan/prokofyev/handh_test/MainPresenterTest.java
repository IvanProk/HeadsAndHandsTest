package com.ivan.prokofyev.handh_test;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.features.main.MainMvpView;
import com.ivan.prokofyev.handh_test.features.main.MainPresenter;
import com.ivan.prokofyev.handh_test.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock MainMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadRibotsReturnsRibots() {
//        List<Ribot> ribots = TestDataFactory.makeListRibots(10);
//        when(mMockDataManager.getWeather())
//                .thenReturn(Observable.just(ribots));

//        mMainPresenter.loadWeather();
//        verify(mMockMainMvpView).showRibots(ribots);
//        verify(mMockMainMvpView, never()).showSignInScreen();
//        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadRibotsReturnsEmptyList() {
//        when(mMockDataManager.getWeather())
//                .thenReturn(Observable.just(Collections.<Ribot>emptyList()));

//        mMainPresenter.loadWeather();
//        verify(mMockMainMvpView).showSignInScreen();
//        verify(mMockMainMvpView, never()).showRibots(ArgumentMatchers.<Ribot>anyList());
//        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadRibotsFails() {
//        when(mMockDataManager.getWeather())
//                .thenReturn(Observable.<List<Ribot>>error(new RuntimeException()));

//        mMainPresenter.loadWeather();
//        verify(mMockMainMvpView).showError();
//        verify(mMockMainMvpView, never()).showSignInScreen();
//        verify(mMockMainMvpView, never()).showRibots(ArgumentMatchers.<Ribot>anyList());
    }

}
