package funktionalitet;

public interface IFunk {
    class AccountException extends Exception {

        public AccountException(String message) {
            super(message);
        }
        public String accountNotSuccessful() {
            return "Account creation not successful: " + getMessage();
        }
    }
}
