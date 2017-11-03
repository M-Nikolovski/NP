import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;



abstract class Contact {

    protected String date;

    public Contact(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public boolean isNewerThan(Contact c) {
        return this.date.compareTo(c.getDate()) > 0;
    }

    public abstract String getType();

}


class EmailContact extends Contact {

    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return "Email";
    }
}


class PhoneContact extends Contact {

    private String phone;
    enum Operator {
        VIP("VIP"),
        ONE("ONE"),
        TMOBILE("TMOBILE");

        String value;

        Operator(String value) { this.value = value; }

        @Override
        public String toString() { return value; }
    }

    private Operator operator;

    public PhoneContact(String date, String phone) {
        super(date);
        String operator = phone.substring(0,3);
        switch (operator) {
            case "070" : case "071" : case "072" : this.operator = Operator.TMOBILE; break;
            case "075" : case "076" :              this.operator = Operator.ONE;     break;
            case "077" : case "078" :              this.operator = Operator.VIP;     break;
        }
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String getType() {
        return "Phone";
    }
}


class Student {

    private String firstName;
    private String lastName;
    private String city;
    private int age;
    private long index;
    private Contact [] contacts;
    private int numberOfContacts;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.contacts = new Contact[10];
        this.numberOfContacts = 0;
    }

    public void addEmailContact(String date, String email) {
        if (contacts.length == numberOfContacts) {
            Contact [] newContacts = new Contact[contacts.length * 2];
            System.arraycopy(contacts, 0, newContacts, 0, numberOfContacts);
            contacts = newContacts;
        }
        contacts[numberOfContacts++] = new EmailContact(date,email);
    }

    public void addPhoneContact(String date, String phone) {
        if (contacts.length == numberOfContacts) {
            Contact [] newContacts = new Contact[contacts.length * 2];
            System.arraycopy(contacts, 0, newContacts, 0, numberOfContacts);
            contacts = newContacts;
        }
        contacts[numberOfContacts++] = new PhoneContact(date,phone);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getCity() { return city; }

    public long getIndex() {
        return index;
    }

    public int getNumberOfContacts() { return numberOfContacts; }

    public Contact getLatestContact() {
        Contact latestContact = contacts[0];
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i].date.compareTo(latestContact.date) > 0)
                latestContact = contacts[i];
        }
        return latestContact;
    }

    public Contact[] getEmailContacts() {
        EmailContact [] tempEmailContacts = new EmailContact[numberOfContacts];
        int j = 0;
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i].getType().equals("Email"))
                tempEmailContacts[j++] = (EmailContact)contacts[i];
        }
        EmailContact[] emailContacts = new EmailContact[j];
        System.arraycopy(tempEmailContacts, 0, emailContacts, 0, j);
        return emailContacts;
    }

    public Contact[] getPhoneContacts() {
        PhoneContact [] tempPhoneContacts = new PhoneContact[numberOfContacts];
        int j = 0;
        for (int i = 0; i < numberOfContacts; i++) {
            if (contacts[i].getType().equals("Phone"))
                tempPhoneContacts[j++] = (PhoneContact)contacts[i];
        }
        PhoneContact[] phoneContacts = new PhoneContact[j];
        System.arraycopy(tempPhoneContacts, 0, phoneContacts, 0, j);
        return phoneContacts;
    }

    @Override
    public String toString() {

        String jsonString = String.format("{\"ime\":\"%s\", \"prezime\":\"%s\", \"vozrast\":%d, " +
                "\"grad\":\"%s\", \"indeks\":%d, ", firstName, lastName, age, city, index);

        PhoneContact [] phoneContacts = (PhoneContact[])getPhoneContacts();
        jsonString += "\"telefonskiKontakti\":[";
        if (phoneContacts.length != 0) {
            for (int i = 0; i < phoneContacts.length; i++) {
                if (i != phoneContacts.length - 1)
                    jsonString += String.format("\"%s\", ", phoneContacts[i].getPhone());
                else
                    jsonString += String.format("\"%s\"", phoneContacts[i].getPhone());
            }
        }
        jsonString += "]";

        EmailContact [] emailContacts = (EmailContact[])getEmailContacts();
        jsonString += (", \"emailKontakti\":[");
        if (emailContacts.length != 0) {
            for (int i = 0; i < emailContacts.length; i++) {
                if (i != emailContacts.length - 1)
                    jsonString += String.format("\"%s\", ", emailContacts[i].getEmail());
                else
                    jsonString += String.format("\"%s\"", emailContacts[i].getEmail());
            }
        }
        jsonString += "]}";

        return jsonString;
    }

}


class Faculty {

    private String name;
    private Student [] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        int numberOfStudents = 0;
        for (Student student : students) {
            if (student.getCity().equals(cityName))
                numberOfStudents++;
        }
        return numberOfStudents;
    }

    public Student getStudent(long index) {
        for (Student student : students) {
            if (student.getIndex() == index)
                return student;
        }
        return null;
    }

    public double getAverageNumberOfContacts() {
        double average = 0;
        for (Student student : students) {
            average += student.getNumberOfContacts();
        }
        return average / students.length;
    }

    public Student getStudentWithMostContacts() {
        Student maxContactsStudent = students[0];
        for (Student student : students) {
            if (student.getNumberOfContacts() >= maxContactsStudent.getNumberOfContacts()) {
                if (student.getNumberOfContacts() == maxContactsStudent.getNumberOfContacts()) {
                    if (student.getIndex() > maxContactsStudent.getIndex())
                        maxContactsStudent = student;
                }
                else
                    maxContactsStudent = student;
            }
        }
        return maxContactsStudent;
    }

    public String toString() {
        String jsonString = String.format("{\"fakultet\":\"%s\", \"studenti\":[", name);
        for (int i = 0; i < students.length; i++) {
            if (i != students.length - 1)
                jsonString += students[i].toString() + ", ";
            else
                jsonString += students[i].toString() + "]}";
        }
        return jsonString;
    }

}



class Testing {
    public static void main(String [] args) {

    }
}


public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
