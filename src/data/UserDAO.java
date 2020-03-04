package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO, Serializable {
    List<UserDTO> users = new ArrayList<>();
    String fileName = "data.txt";

    public UserDAO() {

    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        List<UserDTO> users = loadUsers().getUsers();
        return users.get(userId);
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        UserStore users = loadUsers();
        if (users == null) {
            throw new DALException("No users yet");
        }
        return users.getUsers();
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        UserStore users;
        try {
            users = loadUsers();

        } catch (Exception e) {
            saveUsers(new UserStore());
            users = loadUsers();
        }


        users.addUser(user);
        saveUsers(users);
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {

    }

    @Override
    public void deleteUser(int userId) throws DALException {
        UserStore userStore = loadUsers();
        List<UserDTO> users = userStore.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                users.remove(i);
            }
        }
        userStore.setUsers(users);
        saveUsers(userStore);
    }


    private UserStore loadUsers() throws DALException {
        UserStore userStore = new UserStore();
        ObjectInputStream oIS = null;
        try {
            FileInputStream fIS = new FileInputStream(fileName);
            oIS = new ObjectInputStream(fIS);
            Object inObj = oIS.readObject();
            if (inObj instanceof UserStore) {
                userStore = (UserStore) inObj;
            } else {
                throw new DALException("Wrong object in file");
            }
        } catch (FileNotFoundException e) {
            //No problem - just returning empty userstore
        } catch (IOException e) {
            throw new DALException("Error while reading disk!", e);
        } catch (ClassNotFoundException e) {
            throw new DALException("Error while reading file - Class not found!", e);
        } finally {
            if (oIS != null) {
                try {
                    oIS.close();
                } catch (IOException e) {
                    throw new DALException("Error closing pObjectStream!", e);
                }
            }
        }
        return userStore;
    }

    private void saveUsers(UserStore users) throws DALException {
        ObjectOutputStream oOS = null;
        try {
            FileOutputStream fOS = new FileOutputStream(fileName);
            oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(users);
        } catch (FileNotFoundException e) {
            throw new DALException("Error locating file", e);
        } catch (IOException e) {
            throw new DALException("Error writing to disk", e);
        } finally {
            if (oOS != null) {
                try {
                    oOS.close();
                } catch (IOException e) {
                    throw new DALException("Unable to close ObjectStream", e);
                }
            }
        }
    }


}
