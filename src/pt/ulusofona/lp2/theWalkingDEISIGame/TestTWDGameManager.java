package pt.ulusofona.lp2.theWalkingDEISIGame;


import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static junit.framework.TestCase.*;


public class TestTWDGameManager {


    @Test
    public void test01MoveWrongTeam() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests.txt");
        gameManager.startGame(file);
        assertFalse(gameManager.move(3, 3, 3, 2));
    }

    @Test
    public void test02OutOfMap() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests 2.txt");
        gameManager.startGame(file);
        assertFalse(gameManager.move(3, 0, 3, -1));
    }

    @Test
    public void test03VampMoveDay() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests.txt");
        gameManager.startGame(file);
        assertTrue(gameManager.move(1, 1, 0, 2));
        assertFalse(gameManager.move(2, 2, 1, 2)); // vamp move during day
    }

    @Test
    public void test04ZombieKillsAlive() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests.txt");
        gameManager.startGame(file);
        assertTrue(gameManager.move(5, 5, 5, 4));
        assertTrue(gameManager.move(4, 4, 5, 4)); // zombie kills human
        assertFalse(gameManager.move(5, 4, 5, 3)); // fresh zombie tries to move as human
    }

    @Test
    public void test05ZombieTriesToKillAliveButDies() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests 2.txt");
        gameManager.startGame(file);
        assertTrue(gameManager.move(3, 0, 3, 2));
        assertTrue(gameManager.move(5, 5, 4, 5));
        assertTrue(gameManager.move(3, 2, 2, 3)); // human picks up sword
        assertTrue(gameManager.move(2, 2, 2, 3)); // zombie dies in combat
        assertFalse(gameManager.move(2,2,2,1)); // zombie aint there cause he dead
    }

    @Test
    public void test06KidTriesToKillAdultZombieWithSword() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests 2.txt");
        gameManager.startGame(file);
        assertTrue(gameManager.move(3, 3, 2, 3)); // kid gets sword
        assertTrue(gameManager.move(2, 2, 2, 3)); // kid tries do defend adult Z but dies
        assertFalse(gameManager.move(2, 3, 3, 3)); // kid tries to move as human but shes a zed
    }

    @Test
    public void test07GameEndsAllHumansSafe() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests 3.txt");
        gameManager.startGame(file);
        assertTrue(gameManager.move(3, 4, 5, 4));
        assertTrue(gameManager.move(2, 2, 3, 3)); // old man dies to Zed
        assertTrue(gameManager.move(5, 4, 6, 5));
        assertTrue(gameManager.move(5, 5, 5, 4));
        assertTrue(gameManager.move(6, 5, 6, 6)); // human to safeZone
        assertTrue(gameManager.gameIsOver());
    }

    @Test
    public void test08DogMoves() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests.txt");
        gameManager.startGame(file);
        assertTrue(gameManager.move(1, 1, 2, 0));
    }

    @Test
    public void test09MoveWrongTeamVampire() throws InvalidTWDInitialFileException, FileNotFoundException {

        TWDGameManager gameManager = new TWDGameManager();
        File file = new File("test-files/sampleForTests.txt");
        gameManager.startGame(file);
        assertFalse(gameManager.move(2, 2, 4, 2));
    }
    //end
}




