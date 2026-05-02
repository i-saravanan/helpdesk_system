package org.helpdesk;

import org.helpdesk.entity.Role;
import org.helpdesk.entity.Ticket;
import org.helpdesk.entity.TicketStatus;
import org.helpdesk.entity.User;
import org.helpdesk.service.AuthService;
import org.helpdesk.service.TicketService;

import java.util.List;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();
        TicketService ticketService = new TicketService();
        while(true){
            System.out.println("----- Helpdesk System -----");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.println("Enter your Choice");
            int choice = sc.nextInt();

            switch (choice){
                case 1:
                    System.out.println("Enter your name:");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.println("Enter Email");
                    String email = sc.next();
                    System.out.println("Enter new password");
                    String password = sc.next();
                    System.out.println("Enter your Role:\n" +
                            "1. ADMIN\n" +
                            "2. AGENT\n" +
                            "3. USER");
                    int rolechoice = sc.nextInt();
                    if (rolechoice == 1){
                        Role role = Role.ADMIN;
                        User newUser = authService.registerUser(name,email,password,role);
                        if(newUser == null){
                            System.out.println("Invalid credentials, try again");
                            continue;
                        }
                        else
                            adminMenu(newUser, sc, ticketService);
                    } else if (rolechoice == 2) {
                        Role role = Role.AGENT;
                        User newUser = authService.registerUser(name,email,password,role);
                        if(newUser == null){
                            System.out.println("Invalid credentials, try again");
                            continue;
                        }
                        else
                            agentMenu(newUser, sc, ticketService);
                    } else if (rolechoice == 3) {
                        Role role = Role.USER;
                        User newUser = authService.registerUser(name,email,password,role);
                        if(newUser == null){
                            System.out.println("Invalid credentials, try again");
                            continue;
                        }
                        else
                            userMenu(newUser, sc, ticketService);
                    }else System.out.println("Invalid Choice, Try Again");
                    break;
                case 2:
                    System.out.println("Enter your Email");
                    sc.nextLine();
                    String loginEmail = sc.nextLine();
                    System.out.println("Enter your Password");
                    String loginPassword = sc.next();
                    User currUser = authService.loginUser(loginEmail, loginPassword);
                    if(currUser == null) {
                        System.out.println("Invalid login, try again");
                        continue;
                    }
                    else if(currUser.getRole() == Role.ADMIN)
                        adminMenu(currUser, sc, ticketService);
                    else if(currUser.getRole() == Role.AGENT)
                        agentMenu(currUser, sc, ticketService);
                    else
                        userMenu(currUser, sc, ticketService);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
            }
        }
    }
    static void adminMenu(User currUser,Scanner sc, TicketService ticketService){
        while (true){
            System.out.println("Admin Menu\n1. View All Tickets\n2. Assign Tickets\n3. Logout");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    List<Ticket> tickets = ticketService.viewAllTickets(currUser);
                    for(Ticket t : tickets){
                        System.out.println("Ticket Id: " + t.getId() + "\nTitle: "+t.getTitle() + "\nDescription: " + t.getDescription() + "\nStatus: " + t.getStatus() + "\nCreated At: " + t.getCreatedAt() + "\nAssigned to:" + (t.getAssignedTo() != null ? t.getAssignedTo().getName() : "Not Assigned"));
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("Enter ticket Id:");
                    int ticketid = sc.nextInt();
                    System.out.println("Enter agent Email:");
                    String email = sc.next();
                    User agent = ticketService.findAgentByEmail(email);
                    boolean value = ticketService.assignTicketToAgent(currUser, ticketid, agent);
                    if(value) System.out.println("Assigned Successfully.");
                    else System.out.println("Try Again");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Choice ");
            }
        }
    }
    static void agentMenu(User currUser, Scanner sc, TicketService ticketService){
        while (true){
            System.out.println("Agent Menu\n1. View Assigned Tickets\n2. Update Ticket Status\n3. Logout");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    List<Ticket> tickets = ticketService.viewAssignedTickets(currUser);
                    for(Ticket t : tickets){
                        System.out.println("Ticket Id: " + t.getId() + "\nTitle: "+t.getTitle() + "\nDescription: " + t.getDescription() + "\nStatus: " + t.getStatus() + "\nCreated At: " + t.getCreatedAt());
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("Enter Ticket Id for update:");
                    int ticketid = sc.nextInt();
                    System.out.println("Enter the current status:\n" +
                            "1. OPEN,\n" +
                            "2. IN_PROGRESS,\n" +
                            "3. RESOLVED");
                    int statuschoice = sc.nextInt();
                    if (statuschoice == 1) {
                        TicketStatus status = TicketStatus.OPEN;
                        boolean updatestatus = ticketService.updateTicketStatus(currUser, ticketid, status);
                        if (updatestatus) System.out.println("Status updated successfully.");
                        else System.out.println("Error Occurred, Try Again.");
                    } else if(statuschoice == 2){
                        TicketStatus status = TicketStatus.IN_PROGRESS;
                        boolean updatestatus = ticketService.updateTicketStatus(currUser,ticketid, status);
                        if(updatestatus) System.out.println("Status updated successfully.");
                        else System.out.println("Error Occurred, Try Again.");
                    } else if (statuschoice == 3) {
                        TicketStatus status = TicketStatus.RESOLVED;
                        boolean updatestatus = ticketService.updateTicketStatus(currUser,ticketid, status);
                        if(updatestatus) System.out.println("Status updated successfully.");
                        else System.out.println("Error Occurred, Try Again.");
                    }
                    else
                        System.out.println("Invalid choice, Try Again");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
    static void userMenu(User currUser, Scanner sc, TicketService ticketService){
        while (true){
            System.out.println("User Menu\n1. Create ticket\n2. View Own Tickets\n3. Logout");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Enter Ticket's Title");
                    sc.nextLine();
                    String title = sc.nextLine();
                    System.out.println("Enter Ticket's Description");
                    String description = sc.nextLine();
                    Ticket newticket = ticketService.createTicket(currUser, title, description);
                    if (newticket != null) {
                        System.out.println("Ticket Created Successfully.");
                        System.out.println("Ticket Id: " + newticket.getId() + "\nTitle: " + newticket.getTitle() + "\nDescription: " + newticket.getDescription() + "\nStatus: " + newticket.getStatus() + "\nCreated At: " + newticket.getCreatedAt());
                    }
                    else System.out.println("Error in creating ticket, Try again");
                    break;

                case 2:
                    List<Ticket> tickets = ticketService.viewOwnTickets(currUser);
                    for(Ticket t : tickets){
                        System.out.println("Ticket Id: " + t.getId() + "\nTitle: "+t.getTitle() + "\nDescription: " + t.getDescription() + "\nStatus: " + t.getStatus() + "\nCreated At: " + t.getCreatedAt());
                        System.out.println();
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

}
