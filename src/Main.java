import data.IUserDAO;

import data.UserDAO;
import funktionalitet.Funk;
import funktionalitet.IFunk;
import ui.IUI;
import ui.TUI;

public class Main {
    public static void main(String[] args) {
        IUserDAO userDAO = new UserDAO();

        IFunk funk = new Funk(userDAO);
        IUI ui = new TUI(funk);
        while(true) {
            ui.menu();
        }

    }
}
