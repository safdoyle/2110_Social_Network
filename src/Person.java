/**
 * Author: Safiya Doyle | B00915654
 * CSCI 2110
 * Assignment 4 | 16 April 2024
 * Objects of this class are users of a particular Social Network.
 * They each have a name (String) and a list of their 1st degree
 * friendships, myFriends (ArrayList<String>). The parent and
 * (String) and visited (boolean) values are references utilized
 * in methods necessary for this class.
 * */
import java.util.*;
public class Person{

    // Declaring/initializing necessary attributes for each person.

    private String name;

    private String parent;

    private boolean visited = false;

    private ArrayList<String> myFriends = new ArrayList<>();

    /**
     * Constructor for creating new Person objects.
     * @param name String value representing the person's
     *             name
     */
    public Person(String name){
        this.name = name;
    }

    // All necessary getters and setters for attributes of Person objects
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent){
        this.parent = parent;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisitedTrue() {
        this.visited = true;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getMyFriends() {
        return this.myFriends;
    }

}
