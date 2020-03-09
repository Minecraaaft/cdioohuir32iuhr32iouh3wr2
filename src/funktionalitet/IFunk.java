package funktionalitet;

import data.IUserDAO;

import java.util.List;

public interface IFunk {
    void makeUser(String username, String cpr, List<String> roles) throws AccountException, IUserDAO.DALException;
    void updateUsername(int id, String username) throws AccountException, IUserDAO.DALException;
    void addRole(int id, String role) throws IUserDAO.DALException, AccountException;
    void removeRole(int id, String role) throws IUserDAO.DALException, AccountException;
    void changeRole(int id, String oldRole, String newRole) throws IUserDAO.DALException, AccountException;
    List getUsers() throws IUserDAO.DALException;
    void deleteUser(int userID) throws IUserDAO.DALException;

    class AccountException extends Exception {

        public AccountException(String message) {
            super(message);
        }
        public String accountNotSuccessful() {
            return "Account creation not successful: " + getMessage();
        }
    }
}
