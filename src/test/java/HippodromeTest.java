import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class HippodromeTest {
    ///////////////////////////////////////////////////////////////////////////
    //A
    //Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException;
    //Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение "Horses cannot be null.";
    @Test
    void ConstructorNullNameTest() {
        try {
            Hippodrome h1 = new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //A
    //Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException;
    //Проверить, что при передаче в конструктор пустого списка, выброшенное исключение будет содержать сообщение "Horses cannot be empty.";
    @Test
    void ConstructorBlankNameTest() {
        List<Horse> listHorse = new ArrayList<>();
        try {
            Hippodrome h1 = new Hippodrome(listHorse);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //B
    //Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности, что и список который был передан в конструктор. При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;
    @Test
    void GetCorrect30HorsesTest() {
        List<Horse> listHorse = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Horse h1 = new Horse("Horse" + i, Math.random() * 10, Math.random() * 100);
            listHorse.add(h1);
        }
        Hippodrome h1 = new Hippodrome(listHorse);

        assertArrayEquals(listHorse.toArray(), h1.getHorses().toArray());
    }


    ///////////////////////////////////////////////////////////////////////////
    //D
    //Проверить, что метод возвращает лошадь с самым большим значением distance.
    @Test
    void GetWinnerTest() {
        int max = 0;

        List<Horse> listHorse = new ArrayList<>();
        Horse winner = new Horse("Пустышка", 1);
        for (int i = 0; i < 30; i++) {
            int speed = (int) (Math.random() * 10);
            int dist = (int) (Math.random() * 100);
            Horse h1 = new Horse("Horse" + i, speed, dist);
            listHorse.add(h1);
            if (dist > max) {
                max = dist;
                winner = h1;
            }
        }
        Hippodrome hip = new Hippodrome(listHorse);

        assertEquals(winner, hip.getWinner());
    }

    ///////////////////////////////////////////////////////////////////////////
    //2C
    //Проверить, что метод вызывает метод move у всех лошадей. При создании объекта Hippodrome передай в конструктор список из 50 моков лошадей и воспользуйся методом verify.
    List<Horse> listHorse = new ArrayList<>();

    @Test
    void InvokeMoveEach50HorseTest() {
        //создаем мок листа

        //Делаем 50 моков и помещаем в лист
        for (int i = 0; i < 50; i++) {
            Horse h1 = mock(Horse.class);
            listHorse.add(h1);
        }

        Hippodrome hip = new Hippodrome(listHorse);
        hip.move();

        for (Horse h1 : listHorse) {
            verify(h1).move();;
        }


    }
}
