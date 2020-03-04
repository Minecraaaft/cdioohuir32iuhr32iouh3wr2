package funktionalitet;

import data.IUserDAO;
import data.IUserDTO;
import data.UserDTO;

import java.util.ArrayList;
import java.util.List;


public class Funk {
    IUserDAO dao;

    public Funk(IUserDAO dao) {
        this.dao = dao;
    }

    public void makeUser(String userName, String cpr, List<String> roles) throws IUserDAO.DALException {
        List<UserDTO> brugere;

        int userID = 11;
        try {
            brugere = dao.getUserList();
            userID += brugere.size();

        } catch (IUserDAO.DALException e) {
        }


        char[] usernameCharArr = userName.toCharArray();
        char[] arr = new char[3];
        for (int i = 0; i < 3; i++) {
            arr[i] = usernameCharArr[i];
        }

        String pass = "mangler";

        dao.createUser(new UserDTO(userName, cpr, new String(arr), roles, pass));
    }

    public List<UserDTO> getUsers() throws IUserDAO.DALException {
        return dao.getUserList();
    }

    public void deleteUser(int userID) throws IUserDAO.DALException {
        dao.deleteUser(userID);
    }

    public static class FunkException extends Exception {

    }

}
