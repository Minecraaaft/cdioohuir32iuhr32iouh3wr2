//import data.IUserDAO;
//import data.UserDAO;
//import data.UserDTO;
//
//import java.util.List;
//
//public class DBTester {
//    //TODO refactor as JUnit test???
//    public static void main(String[] args) {
//        IUserDAO iDAO = new UserDAO();
//        UserDTO newUser = new UserDTO();
//        printUsers(iDAO);
//        //TODO test new fields...
//        newUser.setIni("test");
//        newUser.addRole("Admin");
//        newUser.setUserName("max");
//        newUser.setUserId(0);
//        try {
//            iDAO.createUser(newUser);
//        } catch (IUserDAO.DALException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            iDAO.createUser(newUser);
//        } catch (IUserDAO.DALException e1) {
//            System.out.println("User already existed - OK");
//        }
//
//        newUser.setUserId(1);
//        newUser.setUserName("max");
//        try {
//            iDAO.createUser(newUser);
//        } catch (IUserDAO.DALException e1) {
//            e1.printStackTrace();
//        }
//        printUsers(iDAO);
//        newUser.setUserId(0);
//        newUser.setUserName("ModifiedName");
//        try {
//            iDAO.updateUser(newUser);
//        } catch (IUserDAO.DALException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        printUsers(iDAO);
//
//        try {
//            iDAO.deleteUser(1);
//        } catch (IUserDAO.DALException e) {
//            e.printStackTrace();
//        }
//
//        printUsers(iDAO);
//
//
//    }
//
//    private static void printUsers(IUserDAO iDAO) {
//        try {
//            System.out.println("Printing users...");
//            List<UserDTO> userList = iDAO.getUserList();
//            for (UserDTO userDTO : userList) {
//                System.out.println(userDTO);
//            }
//
//        } catch (IUserDAO.DALException e) {
//            e.printStackTrace();
//        }
//    }
//
//}