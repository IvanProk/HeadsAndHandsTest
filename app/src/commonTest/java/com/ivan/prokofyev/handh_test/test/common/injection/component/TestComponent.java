package com.ivan.prokofyev.handh_test.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.ivan.prokofyev.handh_test.injection.component.ApplicationComponent;
import com.ivan.prokofyev.handh_test.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
