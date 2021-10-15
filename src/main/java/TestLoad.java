import java.util.Random;

public class TestLoad {
    public static void main(String[] args) {
        Random random = new Random();
        while (true){
            System.out.println(random.nextInt(1000000));
        }
    }
}
