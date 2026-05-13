package epaw.lab3.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import epaw.lab3.model.User;
import epaw.lab3.repository.UserRepository;
import jakarta.servlet.http.Part;

public class UserService {

    private static UserService instance;
    private UserRepository userRepository;

    private static final String NAME_RE     = "^[A-Za-zÀ-ÿ\\s\\-']{2,60}$";
    private static final String EMAIL_RE    = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    private static final String PHONE_RE    = "^\\d{7,15}$";
    private static final String DNI_RE      = "^\\d{8}[A-Za-z]$";
    private static final String ZIP_RE      = "^\\d{5}$";
    private static final String USERNAME_RE = "^[A-Za-z0-9_]{4,30}$";
    private static final String PASSWORD_RE = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,}$";

    private static final Set<String> BLOCKED_USERNAMES = Set.of("admin", "root", "administrator");

    private UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Map<String, String> register(User user, String passwordConfirm) {
        Map<String, String> errors = new LinkedHashMap<>();

        // --- Format validations ---
        String name = user.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Full name is required.");
        } else if (!name.matches(NAME_RE)) {
            errors.put("name", "Name must be 2–60 letters (accents, hyphens and apostrophes allowed).");
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required.");
        } else if (!email.matches(EMAIL_RE)) {
            errors.put("email", "Enter a valid email address.");
        }

        String phone = user.getPhone();
        if (phone != null && !phone.trim().isEmpty() && !phone.matches(PHONE_RE)) {
            errors.put("phone", "Phone must be 7–15 digits (no spaces or dashes).");
        }

        String dni = user.getDni();
        if (dni == null || dni.trim().isEmpty()) {
            errors.put("dni", "DNI is required.");
        } else if (!dni.matches(DNI_RE)) {
            errors.put("dni", "DNI must be 8 digits followed by an uppercase letter (e.g. 12345678A).");
        }

        String zip = user.getZip();
        if (zip == null || zip.trim().isEmpty()) {
            errors.put("zip", "ZIP code is required.");
        } else if (!zip.matches(ZIP_RE)) {
            errors.put("zip", "ZIP code must be exactly 5 digits.");
        }

        String city = user.getCity();
        if (city == null || city.trim().isEmpty()) {
            errors.put("city", "City is required.");
        }

        String country = user.getCountry();
        if (country == null || country.trim().isEmpty()) {
            errors.put("country", "Country code is required.");
        } else if (country.length() != 2) {
            errors.put("country", "Country must be a 2-letter ISO code (e.g. ES, US, FR).");
        }

        String username = user.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Username is required.");
        } else if (!username.matches(USERNAME_RE)) {
            errors.put("username", "Username must be 4–30 characters (letters, digits, underscores).");
        } else if (BLOCKED_USERNAMES.contains(username.toLowerCase())) {
            errors.put("username", "That username is reserved. Please choose another.");
        }

        String password = user.getPassword();
        if (password == null || !password.matches(PASSWORD_RE)) {
            errors.put("password", "Password needs at least 8 characters, one uppercase, one digit, and one special character (!@#$%^&*).");
        } else if (!password.equals(passwordConfirm)) {
            errors.put("confirmPassword", "Passwords do not match.");
        }

        if (!errors.isEmpty()) return errors;

        // --- Uniqueness checks ---
        if (userRepository.existsByEmail(email)) {
            errors.put("email", "This email address is already registered.");
        }
        if (userRepository.existsByDni(dni)) {
            errors.put("dni", "This DNI is already registered.");
        }
        if (userRepository.existsByUsername(username)) {
            errors.put("username", "This username is already taken.");
        }

        if (!errors.isEmpty()) return errors;

        // --- Bubble lookup ---
        int bubbleId = userRepository.findBubbleId(zip, city, country);
        if (bubbleId == -1) {
            errors.put("zip", "No Bubble found for the given ZIP / city / country combination.");
            return errors;
        }

        // --- Determine status and assign bubble ---
        user.setBubbleId(bubbleId);
        user.setStatus(userRepository.isBubbleOpen(bubbleId) ? "APPROVED" : "PENDING");
        user.setRole("USER");
        user.setDni(dni.toUpperCase());
        user.setCountry(country.toUpperCase());
        user.setPassword(DigestUtils.sha256Hex(password));

        userRepository.save(user);
        return errors;
    }

    public Map<String, String> login(User user) {
        Map<String, String> errors = new LinkedHashMap<>();
        String hashed = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(hashed);
        if (!userRepository.checkLogin(user)) {
            errors.put("password", "Username or password is incorrect.");
        }
        return errors;
    }

    public String saveProfilePicture(Part filePart, String username) {
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }
        try {
            String fileName = filePart.getSubmittedFileName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = username + extension;
            String resourcesDir = "EXTERNAL_RESOURCES";
            Files.createDirectories(Paths.get(resourcesDir));
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(resourcesDir, newFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
