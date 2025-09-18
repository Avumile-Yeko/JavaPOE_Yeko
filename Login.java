import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    
    // Constructor
    public Login() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhoneNumber = "";
    }

    // Check if username contains underscore and is no more than 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Check password complexity requirements 
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;

        boolean hasCapital = false; 
        boolean hasNumber = false; 
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }
        return hasCapital && hasNumber && hasSpecialChar;
    }

    // Check cell phone number format
    public boolean checkCellPhoneNumber() {
        // Expression to validate international phone number format
        // Starts with + followed by country code (1-3 digits) and 7-10 additional digits
        String regex = "^\\+[0-9]{1,3}[0-9]{7,10}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }
    // Register a new user
    public String registerUser () {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more five characters in length.";
        }
        
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an internnational code, please correct the number and try again.";
        }
        return "User registered successfully.";
    }
    // Verify Login credentials
    public boolean logicUser(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
    // Return login status message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + "," + lastName + " It is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    // Getters and Setters for all attributes
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName =  lastName;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Main method for testing the registration and login system
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        Login loginSystem =  new Login();

        System.out.print("=== User Registration ===");

        // Get user details
        System.out.print("Enter first name: ");
        loginSystem.setLastName(scanner.nextLine());

        System.out.print("Enter last name: ");
        loginSystem.setLastName(scanner.nextLine());

        // Get and validate username
        boolean validUsername =  false;
        while (!validUsername) {
            System.out.print("Enter username (must contain '_' and be no more than 5 characters): ");
            String username = scanner.nextLine();
            loginSystem.setUsername(username);

            if (loginSystem.checkUserName()) {
                System.out.println("Username successfully captured.");
                validUsername = true;
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // Get and validate password
        boolean validPassword = false;
        while (!validPassword) {
            System.out.print("Enter password (at least 8 characters, a capital letter, number, special char): ");
            String password = scanner.nextLine();
            loginSystem.setPassword(password);

            if (loginSystem.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.");
                validPassword = true;
            } else {
                System.out.println("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            }
        }

        // Get and validate call phone number
        boolean validPhone = false;
        while (!validPhone) {
            System.out.print("Enter cell phone number (with international code, e.g., +27831234567): ");
            String phone = scanner.nextLine();
            loginSystem.setCellPhoneNumber(phone);

            if (loginSystem.checkCellPhoneNumber()) {
                System.out.println("Cell number successfully captured.");
                validPhone = true;
            } else {
                System.out.println("Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
            }
        }

        // Complete registration
        String registrationResult = loginSystem.registerUser();
        System.out.println(registrationResult);

        System.out.println("\n=== User Login ===");

        // Login attempt
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        boolean loginSuccess = loginSystem.logicUser(inputUsername, inputPassword);
        String loginMessage = loginSystem.returnLoginStatus(loginSuccess);
        System.out.println(loginMessage);

        scanner.close();
    }
    
}