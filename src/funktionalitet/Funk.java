package funktionalitet;

import data.IUserDAO;
import data.IUserDTO;
import data.UserDTO;

import java.util.ArrayList;
import java.util.List;


public class Funk implements IFunk {
    IUserDAO dao;

    public Funk(IUserDAO dao) {
        this.dao = dao;
    }

    public void makeUser(String userName, String cpr, List<String> roles) throws AccountException, IUserDAO.DALException {
        List<UserDTO> brugere;

        brugere = dao.getUserList();
        AccountLogic accountLogic = new AccountLogic();
        int userID = accountLogic.findUserID(brugere);
        accountLogic.checkUsedUsername(userName, brugere);
        accountLogic.checkCPR(cpr);
        String ini = accountLogic.createIni(userName);

        String pass = "mangler";

        dao.createUser(new UserDTO(userID, userName, cpr, ini, roles, pass));
    }

    public List getUsers() throws IUserDAO.DALException {
        return dao.getUserList();
    }

    public void deleteUser(int userID) throws IUserDAO.DALException {
        dao.deleteUser(userID);
    }


    private class AccountLogic {
        public int findUserID(List<UserDTO> list) throws AccountException {
            int userID = 11;

            ArrayList<UserDTO> brugere = (ArrayList<UserDTO>) list;
            userID += brugere.size();
            for (int i = 11; i < 100; i++) {
                for (int j = 0; j < brugere.size(); j++) {
                    if (brugere.get(j).getUserId() == i) {
                        continue;
                    }
                    return i;
                }
            }
            throw new AccountException("Too many users.");
        }

        public boolean checkUsedUsername(String username, List<UserDTO> list) throws AccountException {
            char[] usernameChar = username.toCharArray();
            if (username.length() < 2)  {
                throw new AccountException("Username too short.");
            }
            if (username.length() > 20) {
                throw new AccountException("Username too long.");
            }
            for (UserDTO user : list) {
                if (user.getUserName().equals(username)) {
                    throw new AccountException("Username already used.");
                }
            }

            return true;
        }

        public boolean checkCPR(String cpr) throws AccountException {
            char[] cprChar = cpr.toCharArray();
            if (cprChar.length != 10) {
                throw new AccountException("CPR number not the correct length");
            }
            for (int i = 0; i < cprChar.length; i++) {
                if (cprChar[i] < 48 || cprChar[i] > 57) {
                    throw new AccountException("CPR number not corrrect");
                }
            }
            return true;
        }

        public String createIni(String username) {
            char[] usernameChar = username.toCharArray();
            int iniLength = usernameChar.length;
            if (iniLength > 4) {
                iniLength = 4;
            }

            String ini = "";
            for (int i = 0; i < iniLength; i++) {
                ini += usernameChar[i];
            }

            return ini;
        }

    }

}
