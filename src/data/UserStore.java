package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserStore implements Serializable {
    private List<UserDTO> users = new ArrayList<>();

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public UserDTO getUser(int userID) {
        UserDTO userDTO = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userID) {
                userDTO = users.get(i);
            }
        }
        return userDTO;
    }

    public void addUser(UserDTO user) {
        users.add(user);
    }
}
