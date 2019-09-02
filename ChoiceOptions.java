package com.pointer.map;

public enum ChoiceOptions {
    MEMBER, PET, EXIT;

    static ChoiceOptions getOption() {
        try {
            System.out.println("Choose option: MEMBER, PET, EXIT");
            return ChoiceOptions.valueOf(Commands.in.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Option not found. Please enter another option");
            return getOption();
        }
    }
}
