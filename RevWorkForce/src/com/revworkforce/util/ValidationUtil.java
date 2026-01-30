package com.revworkforce.util;

import com.revworkforce.exception.ValidationException;

public class ValidationUtil {

	//1. Email Validation
    public static boolean isValidEmail(String email) throws ValidationException{

        if (email == null || email.isEmpty())
        	throw new ValidationException("Email must not be empty and null");

        if (!email.contains("@") || !email.contains("."))
        	throw new ValidationException("Email must must contains '@' and '.'");

        if (email.startsWith("@") || email.endsWith("@"))
        	throw new ValidationException("Email must not start and end with '@'");

        return true;
    }

    // 2. Password Validation
    public static void validatePassword(String password) throws ValidationException {

        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must contain at least 8 characters");
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {

            if (Character.isUpperCase(ch))
            	hasUpper = true;
            else if (Character.isLowerCase(ch)) 
            	hasLower = true;
            else if (Character.isDigit(ch))
            	hasDigit = true;
            else 
            	hasSpecial = true;
        }

        if (!(hasUpper && hasLower && hasDigit && hasSpecial)) {
            throw new ValidationException(
                "Password must contain Uppercase, Lowercase, Digit and Special character");
        }
    }

    // 3. Phone Validation (10 Digits)
    public static boolean validatePhone(String phone) throws ValidationException {

        if (!phone.matches("\\d{10}")) {
            throw new ValidationException("Phone number must contain exactly 10 digits");
        }
        
        for (char ch : phone.toCharArray()) {
            if (!Character.isDigit(ch))
                return false;
        }
        return true;
    }

    // 3. Name Validation
    public static void validateName(String name) throws ValidationException {

        if (name == null || name.trim().length() < 3) {
            throw new ValidationException("Name must contain at least 3 characters");
        }
    }

    //4. Role Validation
    public static void validateRole(String role) throws ValidationException {

        if (!(role.equalsIgnoreCase("ADMIN") ||
              role.equalsIgnoreCase("MANAGER") ||
              role.equalsIgnoreCase("EMPLOYEE"))) {

            throw new ValidationException("Role must be ADMIN / MANAGER / EMPLOYEE");
        }
    }

    //5. LeaveType Validation
    public static void validateLeaveType(String type)
            throws ValidationException {

        if (!(type.equalsIgnoreCase("CL") ||
              type.equalsIgnoreCase("SL") ||
              type.equalsIgnoreCase("PL") ||
              type.equalsIgnoreCase("PRIVILEGE"))) {

            throw new ValidationException(
                "Leave Type must be CL / SL / PL / PRIVILEGE");
        }
    }

    //6. Date Range Validation
    public static void validateDateRange(java.sql.Date from, java.sql.Date to)
            throws ValidationException {

        if (from.after(to)) {
            throw new ValidationException("From Date cannot be after To Date");
        }
    }

    //7. Rating Validation
    public static void validateRating(int rating) throws ValidationException {

        if (rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5");
        }
    }

    //8.Progress Validation
    public static void validateProgress(int progress) throws ValidationException {

        if (progress < 0 || progress > 100) {
            throw new ValidationException("Progress must be between 0 and 100");
        }
    }

    //9. Salary Validation
    public static void validateSalary(double salary) throws ValidationException {

        if (salary <= 0) {
            throw new ValidationException("Salary must be greater than zero");
        }
    }
    
}
