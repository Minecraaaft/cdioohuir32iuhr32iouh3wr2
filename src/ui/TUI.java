package ui;

import data.IUserDAO;
import data.UserDTO;
import funktionalitet.Funk;
import funktionalitet.IFunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TUI implements IUI {
    private Scanner scanner = new Scanner(System.in);
    Funk funk;
    public TUI(Funk funk) {
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
        System.out.println("Choose username: ");
        String name = scanner.next();
        System.out.println("please enter CPR :");
        String cpr = scanner.next();

        System.out.println("" +
                "Choose your role: " +
                "1: Pharmacist" +
                "2: Foreman" +
                "3: Operator" +
                "4: Admin");
        List<String> roles = new ArrayList<>();
        String role = scanner.next();
        while (!role.equals("Pharmacist") && !role.equals("Foreman") && !role.equals("Operator") && !role.equals("Admin")) {
            role = scanner.next();
        }
        roles.add(role);


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
            List<UserDTO> users = funk.getUsers();

            for (int i = 0; i < users.size(); i++) {
                System.out.print("Username: " + users.get(i).getUserName() + ", ID: " + users.get(i).getUserId() + ", roles: ");
                for (int j = 0; j < users.get(i).getRoles().size(); j++) {
                    System.out.print(users.get(i).getRoles().get(j) + ", ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        } catch (IUserDAO.DALException e) {
            System.out.println(e.getMessage());
            System.out.println();
            System.out.println();
        }

    }

    public void retBruger() {


    }

    public void sletBruger() {
        System.out.println("Please enter userID you wish to delete: ");

        int userToDelete = scanner.nextInt();
        try {
            funk.deleteUser(userToDelete);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }

    }

    public void afslut() {
        System.out.println("goodbye");
        System.exit(0);
    }
}
