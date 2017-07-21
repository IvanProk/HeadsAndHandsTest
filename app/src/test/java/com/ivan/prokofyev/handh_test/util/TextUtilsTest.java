package com.ivan.prokofyev.handh_test.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Shiraha on 7/14/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TextUtilsTest {

    @Test
    public void passwordMatches(){
        System.out.println("qwerty1".matches(TextUtils.PASSWORD_PATTERN));
        System.out.println("qwerty".matches(TextUtils.PASSWORD_PATTERN));
        System.out.println("Qwerty1".matches(TextUtils.PASSWORD_PATTERN));
        System.out.println("Qwerty 1".matches(TextUtils.PASSWORD_PATTERN));
    }

    @Test
    public void test(){
        RxTest.test();
    }
}
