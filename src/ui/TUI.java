package ui;

import data.IUserDAO;
import funktionalitet.IFunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TUI implements IUI {
    private Scanner scanner = new Scanner(System.in);
    private IFunk funk;
    public TUI(IFunk funk) {
        this.funk = funk;
    }

    public void menu() {
        System.out.println();
        System.out.println("" +
                "Press 1-5:\n" +
                "1.    Opret ny bruger\n" +
                "2.    List Brugere\n" +
                "3.    Ret bruger\n" +
                "4.    Slet bruger\n" +
                "5.    Afslut program\n");

        switch (scanner.next()) {
            case "1":
                opretBruger();
                break;
            case "2":
                listBruger();
                break;
            case "3":
                retBruger();
                break;
            case "4":
                sletBruger();
                break;
            case "5":
                afslut();
                break;
        }
    }

    public void opretBruger() {
        System.out.println();
        System.out.println("Choose username (no spaces): ");
        String name = scanner.next();
        System.out.println("please enter CPR :");
        String cpr = scanner.next();

        System.out.println("" +
                "Choose your role: " +
                "\n1: Pharmacist" +
                "\n2: Foreman" +
                "\n3: Operator" +
                "\n4: Admin");
        List<String> roles = new ArrayList<>();
        int role = 0;
        String input = "";

        input = scanner.next();
        while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
            System.out.println("Wrong input, please type a number from 1 - 4:");
            input = scanner.next();
        }

        role = Integer.parseInt(input);

        if (role == 1) {
            roles.add("Pharmacist");
        } else if (role == 2) {
            roles.add("Foreman");
        } else if (role == 3) {
            roles.add("Operator");
        } else if (role == 4) {
            roles.add("Admin");
        }

        try {
            funk.makeUser(name, cpr, roles);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        } catch (IFunk.AccountException e) {
            System.out.println(e.accountNotSuccessful());
        }

    }

    public void listBruger() {
        try {
            List users = funk.getUsers();

            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i));
            }
            System.out.println();
        } catch (IUserDAO.DALException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

    }

    public void retBruger() {
        listBruger();
        System.out.println("Please enter userID from the user you wish to edit: ");
        String idString = scanner.next();
        int id = 0;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e ) {

        }

        while (!(id >= 11 && id <= 99)) {
            System.out.println("Wrong input, please type a valid id:");
            idString = scanner.next();
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e ) {

            }
        }

        System.out.println("" +
                "\nPress 1-3: " +
                "\n1. edit username" +
                "\n2. add role" +
                "\n3. remove role" +
                "\n4. change role");
        String optionString = scanner.next();
        while (!optionString.equals("1") && !optionString.equals("2") && !optionString.equals("3") && !optionString.equals("4")) {
            System.out.println("Wrong input, please type a number from 1 - 4:");
            optionString = scanner.next();
        }
        int option = Integer.parseInt(optionString);

        if (option == 1) {
            System.out.println("Type new username: ");
            String newUsername = scanner.next();
            try {
                funk.updateUsername(id, newUsername);
            } catch (IFunk.AccountException e) {
                System.out.println(e.getMessage());
            } catch (IUserDAO.DALException e) {
                System.out.println(e.getMessage());
            }

        } else if (option == 2) {
            System.out.println("Type role to add: ");
            String role = scanner.next();
            try {
                funk.addRole(id, role);
            } catch (IUserDAO.DALException e) {
                System.out.println(e.getMessage());
            } catch (IFunk.AccountException e) {
                System.out.println(e.getMessage());
            }
        } else if (option == 3) {
            System.out.println("Type role to remove: ");
            String role = scanner.next();
            try {
                funk.removeRole(id, role);
            } catch (IUserDAO.DALException e) {
                System.out.println(e.getMessage());
            } catch (IFunk.AccountException e) {
                System.out.println(e.getMessage());
            }
        } else if (option == 4) {
            System.out.println("Type role you want to change: ");
            String role = scanner.next();
            System.out.println("Type new role: ");
            String newRole = scanner.next();
            try {
                funk.changeRole(id, role, newRole);
            } catch (IUserDAO.DALException e) {
                System.out.println(e.getMessage());
            } catch (IFunk.AccountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void sletBruger() {
        listBruger();
        System.out.println("Please enter userID you wish to delete: ");

        String userToDeleteString = scanner.next();
        int userToDelete = 0;
        try {
            userToDelete = Integer.parseInt(userToDeleteString);
        } catch (NumberFormatException e ) {

        }

        while (!(userToDelete >= 11 && userToDelete <= 99)) {
            System.out.println("Wrong input, please type a valid id:");
            userToDeleteString = scanner.next();
            try {
                userToDelete = Integer.parseInt(userToDeleteString);
            } catch (NumberFormatException e ) {

            }
        }

        try {
            funk.deleteUser(userToDelete);
        } catch (IUserDAO.DALException e) {
            System.out.println(e.getMessage());
        }

    }

    public void afslut() {
        System.out.println("goodbye");
        System.exit(0);
    }
}
