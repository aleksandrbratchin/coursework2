package ru.bratchin.coursework2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//Тестовый класс добавлен ТОЛЬКО для покрытия вызова main(), не охваченного тестами приложения.
@SpringBootTest
class Coursework2ApplicationTests {

    @Test
    void contextLoads() {
        Coursework2Application.main(new String[]{});
    }

}
