import java.util.concurrent.Semaphore;
import java.util.random.RandomGenerator;

public class MainManager {
    public static final int DOOR_COUNT = 3;
    public static final int MAX_VISIT_PER_DOOR = 100;

    private static int accessCount; // informa de cuantas personas han accedido
    private static int doorFullCount; // informa de cuantas puertas estan llenas

    private static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        RandomGenerator random = RandomGenerator.getDefault();
        int doorVisits;
        for (int i = 0; i < DOOR_COUNT; i++) {
            // Se genera un numero aleatorio de personas para acceder por cada puerta, hasta un
            // maximo de MAX_VISIT_PER_DOOR
            doorVisits = random.nextInt(1, MAX_VISIT_PER_DOOR);
            System.out.printf("Van a entrar %d personas en la puerta %d\n", doorVisits, i);
            new DoorThread(doorVisits).start();
        }
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }

    public static void incrementAccessCount() {
        accessCount++;
    }

    public static void incrementDoorFullCount() {
        doorFullCount++;
        onDoorThreadFinish();
    }

    public static void onDoorThreadFinish() {
        // Se espera hasta que todas las puertas hayan terminado su proceso
        if (doorFullCount == DOOR_COUNT) {
            onExit();
        }
    }

    public static void onExit() {
        System.out.println("Todas las personas han entrado.\nTotal de personas que han accedido: " + accessCount);
    }
}