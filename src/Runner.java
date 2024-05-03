import java.util.*;

public class Runner {
    public static void main(String[] args) throws PersonNotFoundException {

        SocialNetwork network = new SocialNetwork();

        network.addPerson("Hayley");
        network.addPerson("Jack");
        network.addPerson("Mary");

        network.connectPeople("Hayley", "Jack");
        network.connectPeople("Mary", "Jack");
        network.connectPeople("Mary", "Hayley");

        System.out.println(network.areWeAllConnected());
    }
}
