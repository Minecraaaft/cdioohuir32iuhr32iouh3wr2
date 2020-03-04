package ui;

import data.IUserDAO;

public interface IUI {
    void menu() throws IUserDAO.DALException;
    void opretBruger();
    void listBruger() throws IUserDAO.DALException;
    void retBruger();
    void sletBruger();
    void afslut();
}
