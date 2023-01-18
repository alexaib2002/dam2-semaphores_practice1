import com.sun.tools.javac.Main;

public class DoorThread extends Thread {

    private int visitsRemaining;

    public DoorThread(int visitsRemaining) {
        this.visitsRemaining = visitsRemaining;
    }

    public void run() {
        try {
            for (int i = 0; i < visitsRemaining; i++) {
                try {
                    MainManager.getSemaphore().acquire();
                    MainManager.incrementAccessCount();
                    MainManager.getSemaphore().release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Se incrementa el contador de puertas llenas de forma segura
        try {
            MainManager.getSemaphore().acquire();
            MainManager.incrementDoorFullCount();
            MainManager.getSemaphore().release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
