import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;

class HorseTest {
    ///////////////////////////////////////////////////////////////////////////
    //A
    //Проверить, что при передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException. Для этого нужно воспользоваться методом assertThrows;
    //Проверить, что при передаче в конструктор первым параметром null, выброшенное исключение будет содержать сообщение "Name cannot be null.". Для этого нужно получить сообщение из перехваченного исключения и воспользоваться методом assertEquals;
    @Test
    void NullNameOfHorseTest() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Horse(null, 5, 12);
                }
        );
        assertEquals("Name cannot be null", ex.getMessage());

    }

    ///////////////////////////////////////////////////////////////////////////
    //A
    //Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException. Чтобы выполнить проверку с разными вариантами пробельных символов, нужно сделать тест параметризованным;
    //Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), выброшенное исключение будет содержать сообщение "Name cannot be blank.";
    @ParameterizedTest
    @ValueSource(strings = {"", "\t", " ", "   ", "\t\t\t", " \t"})
    void BlankNameOfHorse_1Test(String arg) {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse(arg, 5, 12);
                }
        );
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

    ///////////////////////////////////////////////////////////////////////////
    //A
    //Проверить, что при передаче в конструктор вторым параметром отрицательного числа, будет выброшено IllegalArgumentException;
    //Проверить, что при передаче в конструктор вторым параметром отрицательного числа, выброшенное исключение будет содержать сообщение "Speed cannot be negative.";
    @Test
    void NegativeSecondParamTest() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse("Horse-1", -5, 12);
                }
        );
        assertEquals("Speed cannot be negative.", ex.getMessage());
    }

    ///////////////////////////////////////////////////////////////////////////
    //A
    //Проверить, что при передаче в конструктор третьим параметром отрицательного числа, будет выброшено IllegalArgumentException;
    //Проверить, что при передаче в конструктор третьим параметром отрицательного числа, выброшенное исключение будет содержать сообщение "Distance cannot be negative.";
    @Test
    void NegativeThirdParamTest() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse("Horse-1", 5, -12);
                }
        );
        assertEquals("Distance cannot be negative.", ex.getMessage());
    }

    ///////////////////////////////////////////////////////////////////////////
    //B
    //Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор;
    @Test
    void getNameTest() {
        String str = "horse-1";
        Horse h1 = new Horse(str, 5, 12);
        assertEquals(str, h1.getName());
    }

    ///////////////////////////////////////////////////////////////////////////
    //C
    //Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор;
    @Test
    void getSpeedTest() {
        int num = 5;
        Horse h1 = new Horse("horse-1", num, 12);
        assertEquals(num, h1.getSpeed());
    }

    ///////////////////////////////////////////////////////////////////////////
    //D
    //Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор;
    //Проверить, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами;
    @Test
    void getDistance() {
        int dist = 12;
        Horse h1 = new Horse("horse-1", 5, dist);
        Horse h2 = new Horse("horse-1", 5);
        assertAll("Проверяем 2 конструктора-с 3 и 2 параметрами",
                () -> assertEquals(dist, h1.getDistance()),
                () -> assertEquals(0, h2.getDistance())
        );
    }

    ///////////////////////////////////////////////////////////////////////////
    //E

    @Test
        //Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9. Для этого нужно использовать MockedStatic и его метод verify;
    void GetRandomDoubleParamTest() {
        Horse h1 = new Horse("Horse-1", 5, 12);

        try (MockedStatic<Horse> ms = Mockito.mockStatic(Horse.class)) {
            //      h2.when(Horse::getRandomDouble (0.2, 0.9)).thenReturn(0.5);

            h1.move();
            ms.verify(
                    () -> h1.getRandomDouble(eq(0.2), eq(0.9)));
        }
    }

    @Test
    //Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9. Для этого нужно использовать MockedStatic и его метод verify;
    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.15, 0.5, 0.999})
    void CorrectDistanceTest(double param) {


        try (MockedStatic<Horse> ms = Mockito.mockStatic(Horse.class)) {

            Horse h1 = new Horse("Horse-1", 5, 12);
            h1.move();
            ms.when(
                    () -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(param);
            h1.move();
            assertEquals(12 + 5 * param, h1.getDistance());

        }
    }

    @Test
        //Проверить, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9). Для этого нужно замокать getRandomDouble, чтобы он возвращал определенные значения, которые нужно задать параметризовав тест.
    void CorrectDistanceTest() {
        Horse h1 = new Horse("Horse-1", 5, 12);

        try (MockedStatic<Horse> ms = Mockito.mockStatic(Horse.class)) {
            //      h2.when(Horse::getRandomDouble (0.2, 0.9)).thenReturn(0.5);

            h1.move();
            ms.verify(
                    () -> h1.getRandomDouble(eq(0.2), eq(0.9)));
        }
    }

    @Disabled
    @Test
        //Работает, но надо сделать через MockedStatic
    void Test_GetRandomDouble_1() {
        Horse h1 = Mockito.mock(Horse.class);
        h1.move();
        Mockito.verify(h1, atLeastOnce()).getRandomDouble(0.2, 0.9);
    }

    @Test
    void Test_CorrectDistance() {
        Horse h1 = Mockito.mock(Horse.class);
        Mockito.doReturn(0.5).when(h1).getDistance();
        double dist = h1.getDistance();
        h1.move();
        assertEquals(dist + 0.5 * h1.getSpeed(), h1.getDistance());
    }

    //Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор;
//?
    ///////////////////////////////////////////////////////////////////////////
    //DISABLED!!!!-Решения задач только другими способами
    @Disabled
    @Test
    void NullNameOfHorse_1() {
        try {
            Horse h1 = new Horse(null, 2, 2);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }

    }

    @Disabled
    @Test
        //Введено пустое имя
    void BlankNameOfHorse_1() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse("", 5, 12);
                }
        );
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

    @Disabled
    @Test
        //2 Variant
        //Введено пустое имя
    void BlankNameOfHorse_1_1() {
        try {
            Horse h1 = new Horse("", 5, 12);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    @Disabled
    @Test
    //Введены символы табуляции
    void BlankNameOfHorse_2() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse("\t", 5, 12);
                }
        );
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

    //2 Variant
    @Disabled
    @Test
    void BlankNameOfHorse_2_1() {
        try {
            Horse h1 = new Horse("\t", 5, 12);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    @Disabled
    @Test
    //Введены пробелы
    void BlankNameOfHorse_3() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse("   ", 5, 12);
                }
        );
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

    ///////////////////////////////////////////////////////////////////////////
    @Disabled
    @Test
    //Введены символы табуляции
    void BlankNameOfHorse_4() {
        Throwable ex = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Horse h1 = new Horse("\t   \t", 5, 12);
                }
        );
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

}