import data.IUserDAO;

import data.UserDAO;
import data.UserStore;
import funktionalitet.Funk;
import ui.IUI;
import ui.TUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IUserDAO.DALException {

        int counter = 0;
        UserDAO userDAO = new UserDAO();
        UserStore userStore = new UserStore();

        Funk funk = new Funk(userDAO);
        IUI ui = new TUI(funk);
        while(true) {
            ui.menu();
        }


    }
}
