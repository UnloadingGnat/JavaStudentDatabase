import java.util.Scanner;

/**
 * Student Database
 * <p>
 *      Summary of program: A studentDatabase program which will read a large amount of student data,
 *      sort it and make it searchable by different parameters. StudentData.txt is Random Test Data
 *
 *      Features:
 *      [1] : Bubble Sort by last Name - sets flag that it is lastname sorted
 *      [2] : Insert Sort by Student id - sets flag in db that idhasbeensorted
 *      [3] : Auto Search: OPTIMIZED SEARCH - by using sorted flags; implicitly searches based on how the db is sorted
 *      [4] : Time taken for the db to sort with each algorithm, and which is faster
 *      [5] : Number males and female students
 *      [6] : Linear search the db with last and first name - enter names comma seperated
 *      [7] : Binary Search the db with last and first - enter names comma seperated
 *      [8] : Get the total number of students for each course in db and output to file.
 * </p>
 *
 * @author UnloadingGnat
 * @date 2021/12/05
 */

public class Main {
    public static void main(String[] args) throws Exception {
        // open input for choice
        Scanner sc = new Scanner(System.in);

        // ArrayList<Student> students = new ArrayList<Student>();
        StudentDatabase students = new StudentDatabase();

        // Load the Student Database
        students.readFromFile("StudentData.txt");

        char choice = '0';

        System.out.println("Welcome to the Unloading Gnat Student Database!\n");

        // Main Program loop
        while (choice != 'q') {

            System.out.println("[1] Bubble Sort(Last Name)\n[2] Insert Sort(Student ID)\n[3] (AUTO) Search\n[4] Sort Time" +
                    "\n[5] # of each gender\n[6] Linear Search by Last and First Name\n[7] Binary Search by Last and" +
                    " First Name\n[8] Total Course Enrollment\n\n[Q] QUIT");

            // get user choice
            choice = sc.next().toLowerCase().charAt(0);

            if (choice == '1') {
                // bubble sort by last name to bubbleoutput
                Student.setCompareToKey("lastName");
                students.bubbleSort();
                // set flag in db that sort has changed
                students.setStudentIDSorted(false);

                // write data, and output message
                students.writeToFile("bubbleOutput", students.getStudents());
                System.out.println("Student data sorted at bubbleOuput.txt in outputs");

            }

            else if (choice == '2') {
                // insert sort by studentId to insertoutput
                Student.setCompareToKey("studentID");
                // set flag in database that data has been sorted by ID
                students.setStudentIDSorted(true);
                students.insertSort();

                // write data, and output message
                students.writeToFile("insertOutput", students.getStudents());
                System.out.println("Student data sorted at insertOuput.txt in outputs");

            }

            // Program decides wheter to use linear or binary, if DB sorted
            else if (choice == '3') {
                // Search studentDB by studentID
                System.out.println("Enter student ID: ");
                String studentIDSearch = sc.next();

                // if the studentDB is sorted binary search
                if(students.getStudentIDSorted()){
                    // binarySearch studentDB with studentID
                    int index = students.binarySearch(studentIDSearch);
                    // If found output student, And time taken to search
                    isBinaryStudentFound(students, index);
                }
                // Linear search if DB is not sorted
                else {
                    // linearSearch studentDB with studentID
                    int index = students.linearSearch(studentIDSearch);
                    // If found output student, And time taken to search
                    isLinearStudentFound(students, index);
                }
            }

            // output sortTimes, and faster sort algorithm
            else if (choice == '4') {
                System.out.println(students.sortTime());
            }

            // output number of male/female students
            else if (choice == '5') {
                int[] gender = students.getNumOfEachGender();
                System.out.println("There are " + gender[0] + " males and " + gender[1] + " females");
            }

            // Linear search for student by lastName, Firstname
            else if (choice == '6') {
                // search by first name last name
                System.out.println("Enter student Name CSV(lastName,firstName): ");
                // flush leftover newline and get first/Last Name
                sc.nextLine();
                String studentNameSearch = sc.nextLine();
                String[] lastFirstName = studentNameSearch.split(",");

                int index = students.linearSearch(lastFirstName[0], lastFirstName[1]);

                // if found output student, and time to search
                isLinearStudentFound(students, index);
            }

            // Binary search for student by lastName, Firstname
            else if (choice == '7') {
                // binary search
                // search by first name last name
                System.out.println("Enter student Name CSV(lastName,firstName) (binaryName): ");
                // flush leftover newline and get first/Last Name
                sc.nextLine();
                String studentNameSearch = sc.nextLine();
                String[] lastFirstName = studentNameSearch.split(",");

                Student.setCompareToKey("lastName");
                students.setStudentIDSorted(false);
                students.insertSort();

                int index = students.binarySearch(lastFirstName[0], lastFirstName[1]);

                // If found output student, And time taken to search
                isBinaryStudentFound(students, index);
            }

            // Get the total number of student enrolled in each course
            else if (choice == '8') {
                // write course enrollment info to file
                students.writeToFile("courseEnrollment", students.getNumStudentsByCourse());

            }
        }

        sc.close();
    }

    /**
     * check if a student was found and display time
     * @param students studentDB
     * @param index index found at, or -1
     */
    private static void isBinaryStudentFound(StudentDatabase students, int index) {
        if (index == -1) {
            System.out.println("Not Found!");
            System.out.println("0 result in " + students.getBinarySearchTIme() + " milliseconds");

        } else {
            System.out.println(students.getStudents().get(index));
            System.out.println("1 result in " + students.getBinarySearchTIme() + " milliseconds");
        }
    }

    /**
     * check if a student was found and display time
     * @param students studentDB
     * @param index index found at, or -1
     */
    private static void isLinearStudentFound(StudentDatabase students, int index) {
        if (index == -1) {
            System.out.println("Not Found!");
            System.out.println("0 result in " + students.getLinearSearchTIme() + " milliseconds");
        } else {
            System.out.println(students.getStudents().get(index));
            System.out.println("1 result in " + students.getLinearSearchTIme() + " milliseconds");
        }
    }
}
