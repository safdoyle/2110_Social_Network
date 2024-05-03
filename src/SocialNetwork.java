import java.util.*;

public class SocialNetwork implements SocialConnections{

    public static HashMap<String,Person> allUsers = new HashMap<String,Person>();

    /**
     * Constructor for creating new SocialNetwork Objects
     * */
    public SocialNetwork(){

    }

    /**
     * Implementation of addPerson method as specified in the
     * SocialConnections class.
     * */
    public boolean addPerson(String name){
        if(allUsers.containsKey(name)){
            return false;
        }

        Person newUser = new Person(name);
        allUsers.put(name, newUser);
        return true;
    }

    /**
     * Implementation of removePerson method as specified in the
     * SocialConnections class.
     * */
    public boolean removePerson(String name) throws PersonNotFoundException{

        if (!allUsers.containsKey(name)) {
            throw new PersonNotFoundException(name + " not found");
        }

        Person p = allUsers.get(name);
        allUsers.remove(name);
        for(String friend : p.getMyFriends()){
            Person friendPerson = allUsers.get(friend);
            friendPerson.getMyFriends().remove(name);
        }
        return true;

    }

    /**
     * Implementation of connectPeople method as specified in the
     * SocialConnections class.
     * */
    public void connectPeople(String name1, String name2) throws PersonNotFoundException{
        //Check that both individuals are there.
        if(!allUsers.containsKey(name1)){
            throw new PersonNotFoundException(name1 + " not found");
        } else if(!allUsers.containsKey(name2)){
            throw new PersonNotFoundException(name2 + " not found");
        }

        Person p = allUsers.get(name1);
        Person p1 = allUsers.get(name2);

        if(p == p1 || this.isFriend(p, p1)){
            return;
        }

        p.getMyFriends().add(name2);
        p1.getMyFriends().add(name1);
    }

    /**
     * Implementation of getConnections method as specified in the
     * SocialConnections class.
     * */
    public ArrayList<String> getConnections(String name) throws PersonNotFoundException{

        if(!allUsers.containsKey(name)){
            throw new PersonNotFoundException(name + " not found");
        }

        Collections.sort(allUsers.get(name).getMyFriends());
        return allUsers.get(name).getMyFriends();
    }

    /**
     * Implementation of getMinimumDegreeOfSeparation method
     * as specified in the SocialConnections class.
     * */
    public int getMinimumDegreeOfSeparation(String name1, String name2) throws PersonNotFoundException{
        //Check that both individuals are there.
        if(!(allUsers.containsKey(name1))){
            throw new PersonNotFoundException(name1 + " not found");
        } else if(!(allUsers.containsKey(name2))){
            throw new PersonNotFoundException(name2 + " not found");
        }

        ArrayList<String> shortestPath = shortestPath(name1, name2);
        int minDegreeOfSeparation = shortestPath.size();

        if(minDegreeOfSeparation == 0){
            return -1;
        }
        return minDegreeOfSeparation;
    }

    /**
     * Implementation of getConnectionsToDegree method
     * as specified in the SocialConnections class.
     * */
    public ArrayList<String> getConnectionsToDegree(String name1, int maxLevel) throws PersonNotFoundException{

        // I should have probably implemented each person's list of friends
        // differently because there are duplicates I don't have the time to
        // remove + it returns name1 in the list as well which I am not sure
        // how to stop :(

        if(!(allUsers.containsKey(name1))){
            throw new PersonNotFoundException(name1 + " not found");
        }

        ArrayList<String> connectionsToDegree = new ArrayList<>();

        if(maxLevel == 0){
            return connectionsToDegree;
        } if(maxLevel == 1){
            Collections.sort(allUsers.get(name1).getMyFriends());
            return allUsers.get(name1).getMyFriends();
        }

        while(maxLevel > 1){
            maxLevel--;
            for(String friend : allUsers.get(name1).getMyFriends()){
                ArrayList<String> furtherConnections = getConnectionsToDegree(friend, maxLevel);
                connectionsToDegree.addAll(furtherConnections);
            }
        }

        Collections.sort(connectionsToDegree);
        return connectionsToDegree;
    }

    /**
     * Method to conduct a Breadth-First Search from one
     * person to another. Here the parent and visited
     * attributes are updated as necessary to indicate
     * vertices we already visited during the search or
     * mutual friends respectively.
     *
     * @param name1 name of first person.
     * @param name2 name of second person.
     * */
    public void searchPathBFS(String name1, String name2){

        ArrayList<String> queue = new ArrayList<>();
        queue.add(name2);
        allUsers.get(name2).setVisitedTrue();

        while(!queue.isEmpty()){
            name2 = queue.remove(0);
            ArrayList<String> firstDegFriends = allUsers.get(name2).getMyFriends();
            for(String friend : firstDegFriends){
                if(!allUsers.get(friend).getVisited()){
                    allUsers.get(friend).setVisitedTrue();
                    allUsers.get(friend).setParent(name2);
                    if(name1.equals(friend)){
                        return;
                    }
                    queue.add(friend);
                }
            }
        }
    }

    /**
     * Method to find the shortest path between two users.
     * @param name1 name of first user
     * @param name2 name of second user
     * @return shortestPath ArrayList<String> that is the
     *                      shortest path between user 1
     *                      and user 2.
     * */

    public ArrayList<String> shortestPath(String name1, String name2){

        searchPathBFS(name1, name2);

        ArrayList<String> shortestPath = new ArrayList<>();

        while(!(name1 == null)){
           shortestPath.add(name1);
           name1 = allUsers.get(name1).getParent();
        }

        return shortestPath;
    }

    /**
     * Implementation of areWeAllConnected method
     * as specified in the SocialConnections class.
     * */
    public boolean areWeAllConnected(){
        if(allUsers.isEmpty()){
            return true;
        }

        LinkedList<String> allUserNames = new LinkedList<>();

        for(String name : allUsers.keySet()){
            allUserNames.add(name);
        }

        String firstUser = allUserNames.peek();
        int count = countVertices(allUsers.get(firstUser));

        return (count == allUserNames.size());
    }

    /**
     * Method to count the number of people in the Social
     * Network using each person's friendships.
     *
     * @param p The Person object we will be using as a root
     *         to count individuals in the network.
     * @return count The integer count of people in the network
     *               starting at person p.
     * */
    public int countVertices(Person p){
        int count = 0;

        if(!p.getVisited()){
            p.setVisitedTrue();
            count = 1;

            for(String friend : p.getMyFriends()){
                count = count + countVertices(allUsers.get(friend));
            }
        }

        return count;
    }

    /**
     * Method to indicate if two people are friends.
     * @param p first user
     * @param p1 second user
     * @return boolean value indicating friendship or amy lack thereof.
     * */
    public boolean isFriend(Person p, Person p1){
        return ((p.getMyFriends().contains(p1.getName())) && (p1.getMyFriends().contains(p.getName())));
    }
}
