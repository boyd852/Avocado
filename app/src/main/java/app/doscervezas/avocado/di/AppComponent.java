package app.doscervezas.avocado.di;

import android.app.Application;

import javax.inject.Singleton;

import app.doscervezas.avocado.vo.MyApp;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuildersModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(MyApp myApp);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}