package funktionalitet;

import data.IUserDAO;
import data.IUserDTO;
import data.UserDTO;

import java.util.ArrayList;
import java.util.List;


public class Funk implements IFunk {
    IUserDAO dao;
    AccountLogic accountLogic = new AccountLogic();

    public Funk(IUserDAO dao) {
        this.dao = dao;
    }

    public void makeUser(String userName, String cpr, List<String> roles) throws AccountException, IUserDAO.DALException {
        List<UserDTO> brugere;

        brugere = dao.getUserList();
        int userID = accountLogic.findUserID(brugere);
        accountLogic.checkUsedUsername(userName, brugere);
        accountLogic.checkCPR(cpr);
        String ini = accountLogic.createIni(userName);

        String pass = "mangler";

        dao.createUser(new UserDTO(userID, userName, cpr, ini, roles, pass));
    }

    public void updateUsername(int id, String username) throws AccountException, IUserDAO.DALException {
        accountLogic.checkUsedUsername(username, dao.getUserList());
        UserDTO userDTO = dao.getUser(id);
        userDTO.setUserName(username);

        String ini = accountLogic.createIni(username);
        userDTO.setIni(ini);

        dao.updateUser(userDTO);
    }

    public void addRole(int id, String role) throws IUserDAO.DALException, AccountException {
        accountLogic.checkRole(role);
        UserDTO userDTO = dao.getUser(id);
        userDTO.addRole(role);

        dao.updateUser(userDTO);
    }

    public void removeRole(int id, String role) throws IUserDAO.DALException, AccountException {
        accountLogic.checkRole(role);
        UserDTO userDTO = dao.getUser(id);
        userDTO.removeRole(role);

        dao.updateUser(userDTO);
    }

    public void changeRole(int id, String oldRole, String newRole) throws IUserDAO.DALException, AccountException {
        accountLogic.checkRole(newRole);
        UserDTO userDTO = dao.getUser(id);
        userDTO.getRoles().remove(oldRole);
        userDTO.addRole(newRole);

        dao.updateUser(userDTO);
    }

    public List getUsers() throws IUserDAO.DALException {
        return dao.getUserList();
    }

    public void deleteUser(int userID) throws IUserDAO.DALException {
        dao.deleteUser(userID);
    }


    private class AccountLogic {
        public int findUserID(List<UserDTO> list) throws AccountException {
            ArrayList<UserDTO> brugere = (ArrayList<UserDTO>) list;

            for (int i = 11; i < 100; i++) {
                for (int j = 0; j < brugere.size(); j++) {
                    if (brugere.get(j).getUserId() == i) {
                        continue;
                    }
                    return i;
                }
            }

            if (brugere.isEmpty()) {
                return 11;
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

        public void checkRole(String role) throws AccountException {
            if (role.equals("Admin") || role.equals("Foreman") || role.equals("Operator") || role.equals("Pharmacist")) {

            } else {
                throw new AccountException("Role missspelled");
            }
        }

    }



}
