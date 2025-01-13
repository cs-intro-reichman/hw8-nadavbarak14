public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for(int i=0; i< this.users.length; i++){
            if(this.users[i] != null && this.users[i].getName() != null && this.users[i].getName().toLowerCase().equals(name.toLowerCase())){
                return this.users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (name == null) {
            return false;
        }
        if(this.userCount == this.users.length){
            return false;
        }
        if (this.getUser(name) != null) {
            return false;
        }
        User newUser = new User(name);
        for(int i = 0;i < this.users.length; i++){
            if(this.users[i] == null){
                this.users[i] = newUser;
                this.userCount++;
                return true;
            }
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
       if (name1 == null || name2 == null) {
        return false;
    }
    if (name1 == name2) {
        return false;
    }
    if (this.getUser(name1) == null || this.getUser(name2) == null) {
        return false;
        
    }

    if (this.getUser(name1).addFollowee(name2)) {
        return true;
    }
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        if (name == null) {
            return null;
        }
        int max =0;
        for( int i = 0; i < this.users.length; i++){
            if(this.users[i]!= null && !this.users[i].equals(getUser(name)) && getUser(name).countMutual(this.users[i]) > max){
                max = getUser(name).countMutual(this.users[i]);
            }
        }
        for( int i = 0; i < this.users.length; i++){
            if(this.users[i]!= null && !this.users[i].equals(getUser(name)) && getUser(name).countMutual(this.users[i]) == max){
               return this.users[i].getName();
            }
        }
        return null;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int max = 0;
        String mostPopular = null;
        for (int i = 0; i < this.users.length; i++) {
            if (this.users[i] != null) {
                int count = followeeCount(this.users[i].getName());
                if (count > max) {
                    max = count;
                    mostPopular = this.users[i].getName();
                }
            }
        }
        return mostPopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        if (getUser(name)  == null) {
            return 0;
        }
        for(int i = 0 ;i < this.users.length; i++){
            if (this.users[i] != null && this.users[i].follows(name)) {
                counter++;
            }

        }
       
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String all="Network:";
        for(int i = 0; i < this.users.length; i++){
            if (this.users[i] != null) {
                all += "\n";
                String ans = this.users[i].getName() + " -> ";
                for (int j = 0; j < this.users[i].getfCount(); j++) {
                    ans = ans + this.users[i].getfFollows()[j] + " ";
            }
            all += ans ;
        }
       
       }
      return all;
    }
}