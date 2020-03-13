package funktionalitet;

import data.IUserDAO;
import data.IUserDTO;
import data.UserDTO;

import java.util.ArrayList;
import java.util.Comparator;
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
        String pass = "";
        //Random password generator (2 first are capital letters, next 2 are lower case, next 4 are numbers)
        char randomHolder;
        for (int i = 0; i < 2; i++) {
            randomHolder = (char)((int) (Math.random()*26) + 97);
            pass +=randomHolder;
        }
        for (int i = 0; i < 2; i++) {
            randomHolder = (char)((int) (Math.random()*26) + 65);
            pass +=randomHolder;
        }

        pass +=(int)(Math.random()*8999 + 1000);

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
        //Check if role is the same as the user
        for (int i = 0; i < role.length(); i++) {
            if(userDTO.getRoles().get(i).equals(role)){
                throw new AccountException("Role already assigned to you!");
            }
        }
        userDTO.addRole(role);
        dao.updateUser(userDTO);
    }

    public void removeRole(int id, String role) throws IUserDAO.DALException, AccountException {
        accountLogic.checkRole(role);
        UserDTO userDTO = dao.getUser(id);
        //Check if role is the same as the user
        for (int i = 0; i < role.length(); i++) {
            if(!userDTO.getRoles().get(i).equals(role)){
                throw new AccountException("Cannot remove a role, which is not assigned to you!");
            }
        }
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
        List users = dao.getUserList();
        users.sort(new Comparator<UserDTO>() {
            @Override
            public int compare(UserDTO o1, UserDTO o2) {
                if (o1.getUserId() > o2.getUserId()) {
                    return 1;
                }else
                    return -1;
            }
        });
        return users;
    }

    public void deleteUser(int userID) throws IUserDAO.DALException {
        dao.deleteUser(userID);
    }


    private class AccountLogic {
        public int findUserID(List<UserDTO> list) throws AccountException {
            ArrayList<UserDTO> brugere = (ArrayList<UserDTO>) list;

            brugere.sort(new Comparator<UserDTO>() {
                @Override
                public int compare(UserDTO o1, UserDTO o2) {
                    if (o1.getUserId() > o2.getUserId()) {
                        return 1;
                    }else
                        return -1;
                }
            });


            for (int i = 11; i < 100; i++) {
                boolean idUsed = true;
                for (int j = 0; j < brugere.size(); j++) {
                    if (brugere.get(j).getUserId() == i) {
                        idUsed = false;
                    }

                }
                if (idUsed) {
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
            int dag = Integer.parseInt(cpr.substring(0,2));
            int maaned = Integer.parseInt(cpr.substring(2,4));
            String month="";
            if (cprChar.length != 10) {
                throw new AccountException("CPR number not the correct length");
            }
            else if(maaned > 12 || maaned < 1 ){
                throw new AccountException("CPR birth month is incorrect");
            }

            switch (maaned){
                case 1: month = "januar"; break;
                case 2: month = "februar"; break;
                case 3: month = "marts"; break;
                case 4: month = "april"; break;
                case 5: month = "maj"; break;
                case 6: month = "juni"; break;
                case 7: month = "juli"; break;
                case 8: month = "august"; break;
                case 9: month = "september"; break;
                case 10: month = "oktober"; break;
                case 11: month = "november"; break;
                case 12: month = "december"; break;
            }
            switch (month){
                case "januar": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "februar": if (dag > 28 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "marts": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "april": if (dag > 30 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "maj": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "juni": if (dag > 30 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "juli": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "august": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "september": if (dag > 30 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "oktober": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "november": if (dag > 30 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
                case "december": if (dag > 31 || dag < 1) throw new AccountException("The day does not correspond to the month"); break;
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
                throw new AccountException("Role misspelled");
            }
        }

    }



}
