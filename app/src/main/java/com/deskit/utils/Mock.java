package com.deskit.utils;

import com.deskit.model.City;
import com.deskit.model.Comment;
import com.deskit.model.Faculty;
import com.deskit.model.FileType;
import com.deskit.model.ListItem;
import com.deskit.model.Resource;
import com.deskit.model.Subject;
import com.deskit.model.University;

import java.util.ArrayList;
import java.util.List;

public class Mock {

    public static List<ListItem> getListOfUniversities() {
        University universityOne = new University(1, "Universitatea Tehnica Cluj-Napoca", new City(1, "Cluj-Napoca"));
        University universityTwo = new University(2, "Universitatea Babes Bolyai", new City(1, "Cluj-Napoca"));
        University universityThree = new University(3, "Universitatea de Medicina si Farmacie Iuliu Hateganu", new City(1, "Cluj-Napoca"));
        University universityFour = new University(4, "Universitatea de Stiinte Agricole si Medicina Veterinara", new City(1, "Cluj-Napoca"));
        University universityFive = new University(5, "Universitatea Crestina Dimitrie Cantemir", new City(1, "Cluj-Napoca"));

        List<ListItem> universityList = new ArrayList<>();
        universityList.add(universityOne);
        universityList.add(universityTwo);
        universityList.add(universityThree);
        universityList.add(universityFour);
        universityList.add(universityFive);

        return universityList;
    }

    public static List<ListItem> getListOfFaculties() {
        List<ListItem> universityList = getListOfUniversities();

        Faculty facultyOne = new Faculty(1, "Facultatea de Constructii Civile", (University) universityList.get(0));
        Faculty facultyTwo = new Faculty(2, "Facultatea de Automatica si Calculatoare", (University) universityList.get(0));
        Faculty facultyThree = new Faculty(3, "Facultatea de Constructii de masini", (University) universityList.get(0));
        Faculty facultyFour = new Faculty(4, "Facultatea de Electronica", (University) universityList.get(0));
        Faculty facultyFive = new Faculty(5, "Facultatea de Telecomunicatii si Tehnologia Informatiei", (University) universityList.get(0));
        Faculty facultySix = new Faculty(6, "Facultatea de Ingineria Materialelor si a Mediului", (University) universityList.get(0));
        Faculty facultySeven = new Faculty(7, "Facultatea de Medicina", (University) universityList.get(2));
        Faculty facultyEight = new Faculty(8, "Facultatea de Medicina Dentara", (University) universityList.get(2));
        Faculty facultyNine = new Faculty(9, "Facultatea de Farmacie", (University) universityList.get(2));

        List<ListItem> facultyList = new ArrayList<>();
        facultyList.add(facultyOne);
        facultyList.add(facultyTwo);
        facultyList.add(facultyThree);
        facultyList.add(facultyFour);
        facultyList.add(facultyFive);
        facultyList.add(facultySix);
        facultyList.add(facultySeven);
        facultyList.add(facultyEight);
        facultyList.add(facultyNine);

        return facultyList;
    }

    public static List<ListItem> getListOfSubjects() {
        List<ListItem> facultyList = getListOfFaculties();

        Subject subject1 = new Subject("1", "Rezistenta Materialelor I", (Faculty) facultyList.get(0));
        Subject subject2 = new Subject("2", "Rezistenta Materialelor II", (Faculty) facultyList.get(0));
        Subject subject3 = new Subject("3", "Mecanica I", (Faculty) facultyList.get(0));
        Subject subject4 = new Subject("4", "Mecanica II", (Faculty) facultyList.get(0));
        Subject subject5 = new Subject("5", "Constructii civile", (Faculty) facultyList.get(0));

        Subject subject6 = new Subject("6", "Fundamentele programarii", (Faculty) facultyList.get(1));
        Subject subject7 = new Subject("7", "Algoritmica", (Faculty) facultyList.get(1));
        Subject subject8 = new Subject("8", "Programare orientata pe aspect", (Faculty) facultyList.get(1));
        Subject subject9 = new Subject("9", "Algebra", (Faculty) facultyList.get(1));
        Subject subject10 = new Subject("10", "Geometrie", (Faculty) facultyList.get(1));
        Subject subject11 = new Subject("11", "Calcul numeric", (Faculty) facultyList.get(1));
        Subject subject12 = new Subject("12", "Programare orientata pe obiect", (Faculty) facultyList.get(1));
        Subject subject13 = new Subject("13", "Baze de date I", (Faculty) facultyList.get(1));
        Subject subject14 = new Subject("14", "Baze de date II", (Faculty) facultyList.get(1));

        Subject subject15 = new Subject("15", "Protetica dentara", (Faculty) facultyList.get(7));
        Subject subject16 = new Subject("16", "Chirurgie maxilo-faciala", (Faculty) facultyList.get(7));
        Subject subject17 = new Subject("17", "Ortodontie", (Faculty) facultyList.get(7));
        Subject subject18 = new Subject("18", "Radiologie dentara", (Faculty) facultyList.get(7));
        Subject subject19 = new Subject("19", "Pedodontie", (Faculty) facultyList.get(7));
        Subject subject20 = new Subject("20", "Parodontologie", (Faculty) facultyList.get(7));
        Subject subject21 = new Subject("21", "Materiala dentare", (Faculty) facultyList.get(7));
        Subject subject22 = new Subject("22", "Ergonomie", (Faculty) facultyList.get(7));

        List<ListItem> subjectList = new ArrayList<>();
        subjectList.add(subject1);
        subjectList.add(subject2);
        subjectList.add(subject3);
        subjectList.add(subject4);
        subjectList.add(subject5);
        subjectList.add(subject6);
        subjectList.add(subject7);
        subjectList.add(subject8);
        subjectList.add(subject9);
        subjectList.add(subject10);
        subjectList.add(subject11);
        subjectList.add(subject12);
        subjectList.add(subject13);
        subjectList.add(subject14);
        subjectList.add(subject15);
        subjectList.add(subject16);
        subjectList.add(subject17);
        subjectList.add(subject18);
        subjectList.add(subject19);
        subjectList.add(subject20);
        subjectList.add(subject21);
        subjectList.add(subject22);

        return subjectList;
    }

    public static List<ListItem> getListOfSubjects(Faculty currentFaculty) {
        List<ListItem> totalSubjects = getListOfSubjects();
        if (currentFaculty == null) {
            return totalSubjects;
        }

        List<ListItem> filteredSubjects = new ArrayList<>();
        for (ListItem listItem : totalSubjects) {
            if (((Subject) listItem).getFaculty().getId() == currentFaculty.getId()) {
                filteredSubjects.add(listItem);
            }
        }

        return filteredSubjects;
    }

    public static List<ListItem> getListOfFaculties(University currentUniversity) {
        List<ListItem> totalFaculties = getListOfFaculties();
        if (currentUniversity == null) {
            return totalFaculties;
        }

        List<ListItem> filteredFaculties = new ArrayList<>();
        for (ListItem listItem : totalFaculties) {
            if (((Faculty) listItem).getUniversity().getId() == currentUniversity.getId()) {
                filteredFaculties.add(listItem);
            }
        }
        return filteredFaculties;
    }

    public static List<Resource> getListOfResources(Subject subject) {
        List<Resource> resourcesList = new ArrayList<>();

        Resource resource1 = new Resource("1", "Resources 1", "anonim", "Vasilache", FileType.JPEG, 10, "Taken in 2016", 1000, subject);
        resource1.getCommentList().add(new Comment());
        resource1.getCommentList().add(new Comment());
        resource1.getCommentList().add(new Comment());
        resource1.getCommentList().add(new Comment());
        Resource resource2 = new Resource("2", "Resources 2", "Ion Andronescu", "Vasilache", FileType.JPEG, 13, "Taken in 2015 and 2016", 1400, subject);
        resource2.getCommentList().add(new Comment());
        resource2.getCommentList().add(new Comment());
        resource2.getCommentList().add(new Comment());
        Resource resource3 = new Resource("3", "Resources 3", "Strmtorel Irimie", "Vasilache", FileType.PDF, 14, "Real subjects", 11000, subject);
        Resource resource4 = new Resource("4", "Resources 4", "anonim", "Ursescu Florea", FileType.PDF, 17, "Received from previous years", 1200, subject);
        Resource resource5 = new Resource("5", "Resources 5", "anonim", "Vasilache", FileType.JPEG, 20, "Don't know if these subjects were given in the last 4 years, but I found them in some group", 1250, subject);
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        resource5.getCommentList().add(new Comment());
        Resource resource6 = new Resource("6", "Resources 6", "Ancu Pop", "Vasilache", FileType.DOC, 34, "Taken in 2015 in overdue session", 2300, subject);
        resource6.getCommentList().add(new Comment());
        Resource resource7 = new Resource("7", "Resources 7", "anonim", "Ursescu Florea", FileType.JPEG, 34, "Taken in 2014, partial exam", 3245, subject);
        Resource resource8 = new Resource("8", "Resources 8", "Fla Molodvan", "Vasilache", FileType.DOC, 36, "Taken in 2016, winter session", 3400, subject);
        Resource resource9 = new Resource("9", "Resources 9", "anonim", "", FileType.PDF, 40, "Taken in 2016", 5608, subject);
        resource9.getCommentList().add(new Comment());
        resource9.getCommentList().add(new Comment());
        resource9.getCommentList().add(new Comment());
        resource9.getCommentList().add(new Comment());
        resource9.getCommentList().add(new Comment());
        resource9.getCommentList().add(new Comment());

        resourcesList.add(resource1);
        resourcesList.add(resource2);
        resourcesList.add(resource3);
        resourcesList.add(resource4);
        resourcesList.add(resource5);
        resourcesList.add(resource6);
        resourcesList.add(resource7);
        resourcesList.add(resource8);
        resourcesList.add(resource9);

        return resourcesList;
    }
}
