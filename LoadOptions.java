package com.pointer.map;

public enum LoadOptions {

    READ, DESERIALIZE, EXIT;

    static LoadOptions getOption() {
        try {
            System.out.println("Choose option: READ, DESERIALIZE, EXIT");
            return LoadOptions.valueOf(Commands.in.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Option not found. Please enter another option");
            return getOption();
        }
    }
}
