package com.pointer.map;

public enum SaveOptions {
    WRITE, REWRITE, SERIALIZE, EXIT;

    static SaveOptions getOption() {
        try {
            System.out.println("Choose option: WRITE, SERIALIZE, EXIT");
            return SaveOptions.valueOf(Commands.in.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Option not found. Please enter another option");
            return getOption();
        }
    }
}
