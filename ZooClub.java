package com.pointer.map;

import java.io.*;
import java.rmi.NoSuchObjectException;
import java.util.*;

public class ZooClub implements Serializable{

    private static Scanner in = new Scanner(System.in);
    private Map<Member, List<Pet>> zooMap;
    private int idCounter;

    public ZooClub() {
        this.zooMap = new HashMap<>();
        this.idCounter = 0;
    }

    @Override
    public String toString() {
        return "ZooClub{" +
                "zooMap=" + zooMap +
                '}';
    }

    public Map<Member, List<Pet>> getZooMap() {
        return zooMap;
    }

    void startMenu() throws Exception{
        boolean flg = true;
        while (flg) {
            switch (Commands.getCommand()) {
                case ADD:
                    add();
                    break;
                case REMOVE:
                    remove();
                    break;
                case REMOVE_PET_FROM_CLUB:
                    removePetFromAllMembers(new Pet(in.next(), in.nextInt()));
                    break;
                case SHOW:
                    show();
                    break;
                case SAVE:
                    save();
                    break;
                case LOAD:
                    break;
                case EXIT:
                    System.out.println("Exiting...");
                    flg = false;
                    break;
            }
        }
    }

    private void add() {
        switch (ChoiceOptions.getOption()) {
            case MEMBER:
                System.out.println("Enter member");
                addMember(new Member(in.next(), idCounter));
                break;
            case PET:
                System.out.println("Enter member");
                addPetToMember(new Member(in.next(), in.nextInt()));
                break;
            case EXIT:
                return;
        }
    }

    private void addMember(Member member) {
        if (zooMap.containsKey(member)) {
            System.out.println("This member is already present. Returning");
        } else {
            zooMap.put(member, new LinkedList<Pet>());
            idCounter++;
        }
    }

    private void addPetToMember(Member member) {
        if (zooMap.containsKey(member)) {
            System.out.println("Enter pet: Name, Age");
            zooMap.get(member).add(new Pet(in.next(), in.nextInt()));
        } else {
            System.out.println("Member not found. Returning");
        }
    }

    private void remove() {
        switch (ChoiceOptions.getOption()) {
            case MEMBER:
                System.out.println("Enter member");
                removeMember(new Member(in.next(), in.nextInt()));
                break;
            case PET:
                System.out.println("Enter member");
                removePetFromMember(new Member(in.next(), in.nextInt()));
                break;
            case EXIT:
                return;
        }
    }


    private void removeMember(Member member) {
        if (zooMap.containsKey(member)) {
            zooMap.remove(member);
        } else {
            System.out.println("Member not found. Returning");
        }
    }

    private void removePetFromMember(Member member) {
        if (zooMap.containsKey(member)) {
            System.out.println("Enter pet: Name, Age");
            try {
                zooMap.get(member).remove(new Pet(in.nextLine(), in.nextInt()));
            } catch (IllegalArgumentException e) {
                System.out.println("Pet not found");
            }
        } else {
            System.out.println("Member not found. Returning");
        }
    }

    private void removePetFromAllMembers(Pet pet) {
        for (Map.Entry<Member, List<Pet>> entry : zooMap.entrySet()) {
            entry.getValue().remove(pet);
        }
    }

    void show() {
        System.out.println(getZooMap());
    }

    void save() throws Exception{
        switch (SaveOptions.getOption()) {
            case WRITE:
                writeToTxt(new File("E:\\ZooClubReport"),true);
                break;
            case REWRITE:
                writeToTxt(new File("E:\\ZooClubReport"),false);
                break;
            case SERIALIZE:
                serialize(new File("E:\\ZooClubSaveFile.ser"),this);
                break;
            case EXIT:
                return;
        }
    }

    void writeToTxt(File file, boolean append) throws IOException{
        try {
            FileOutputStream fos=new FileOutputStream(file,append);
            fos.write(getZooMap().toString().getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            file.createNewFile();
            writeToTxt(file,append);
        }
    }

    void serialize(File file, Serializable obj) throws IOException{
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(obj);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    void load() throws Exception{
        switch (LoadOptions.getOption()) {
            case READ:
                readFromTxt("E:\\ZooClubReport");
                break;
            case DESERIALIZE:
                deSerialize("E:\\ZooClubSaveFile.ser");
                break;
            case EXIT:
                return;
        }
    }

    void readFromTxt(String path) throws IOException{
        try {
            FileInputStream fis =new FileInputStream(new File(path));
            String str = new String(fis.readAllBytes());
            System.out.println(str);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    ZooClub deSerialize(String path) throws Exception{
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (ZooClub)objectInputStream.readObject();
    }


}

