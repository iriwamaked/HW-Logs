package itstep.learning.ioc;

import com.google.inject.AbstractModule;

import com.google.inject.name.Names;
import itstep.learning.services.generator.*;
import itstep.learning.services.hash.HashService;
import itstep.learning.services.hash.Md5HashService;
import itstep.learning.services.hash.ShaHashService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(HashService.class).
                annotatedWith(Names.named("digest")).
                to(Md5HashService.class);
        bind(HashService.class).
                annotatedWith(Names.named("signature")).
                to(ShaHashService.class);
        bind(String.class).
                annotatedWith(Names.named("viewsFolder")).
                toInstance("views");
        bind(String.class).
                annotatedWith(Names.named("resourcesFolder")).
                toInstance("resources");
        bind(StringGeneratorService.class).annotatedWith(Names.named("fileName")).to(FileNameGeneratorService.class);
        bind(StringGeneratorService.class).annotatedWith(Names.named("otp")).to(OTPGeneratorService.class);
        bind(StringGeneratorService.class).annotatedWith(Names.named("salt")).to(SaltGeneratorService.class);
        bind(StringGeneratorService.class).annotatedWith(Names.named("password")).to(PasswordGeneratorService.class);
    }
}

/*Модуль регистрации сервисов (служб)
* */
