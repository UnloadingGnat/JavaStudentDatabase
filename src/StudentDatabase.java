import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * StudentDatabase class
 * <p>
 *      Database uses of objects, sorting, searching and fileIO,
 *      and is optimized to read large amounts of data, and extensible to search with diffrent parameters
 *
 *      Database basic functions is searches,sorts and records the time they both take.
 *
 *      The database also implements utility functions like # of each gender and Total course enrollment,
 *      the latter uses Hashmaps, and handles which account for formatting errors
 *
 *      Finally, the database, can also search with multiple diffrent kinds of indetifiers for the students, like first/last names and studentID.
 * </p>
 *
 * @author UnloadingGnat
 * @date 2021/12/05
 */

class StudentDatabase {
    // Attributes
    protected ArrayList<Student> students;
    protected long bubbleSortTime = 0L;
    protected long insertSortTime = 0L;
    protected long binarySearchTime = 0L;
    protected long linearSearchTime = 0L;
    protected boolean studentIDSorted = false;


    /**
     * Constructor
     */
    public StudentDatabase() {
        this.students = new ArrayList<>();
    }

    /**
     * Bubble sort student database
     */
    public void bubbleSort() {
        // get current millisecond time
        long milliStartTime = System.currentTimeMillis();

        boolean sorted = false;
        Student temp;

        // bubble sort
        while(!sorted) {
            sorted = true;
            for(int i = 0; i < this.students.size() - 1; i++) {
                // check if adjacent students are not sorted
                if(this.students.get(i).compareTo(this.students.get(i+1)) > 0) {
                    // swap current and next student
                    temp = this.students.get(i);
                    this.students.set(i, this.students.get(i+1));
                    this.students.set(i+1, temp);

                    sorted = false;
                }
            }
        }

        // get time, and calculate difference for sortTime
        long milliEndTime = System.currentTimeMillis();
        this.bubbleSortTime = milliEndTime - milliStartTime;
    }

    /**
     * Insert Sort student database
     */
    public void insertSort() {
        // get timer
        long milliStartTime = System.currentTimeMillis();

        int key = 0;

        // Insert sort
        for(int i = 0; i < this.students.size(); i++) {
            // declare temp and key
            Student temp = this.students.get(i);
            key = i;

            // move key student element of studentDatabase to one index ahead, when greater than temp
            while(key>0 && this.students.get(key-1).compareTo(temp) > 0) {
                this.students.set(key, this.students.get(key-1));
                key--;
            }
            this.students.set(key, temp);
        }

        // get time, and calculate difference for sortTime
        long milliEndTime = System.currentTimeMillis();
        this.insertSortTime = milliEndTime - milliStartTime;

    }

    /**
     * Binary Search student database with studentID
     *
     * @param studentLookup studentID of the Student
     * @return the index of the student found on a studentID sorted db, or -1 if not found
     */
    public int binarySearch(String studentLookup) {
        // start timer
        long milliStartTime = System.currentTimeMillis();

        // student comparisons by ID
        Student.setCompareToKey("studentID");


        // declare search bounds, index, and found flag
        int min = 0;
        int max = this.students.size()-1;
        int mid = 0;
        int foundStudentIndex = -1;
        boolean found = false;

        // Binary Search
        while(min<=max && !found) {

            // calc middle index
            mid = (min+max) / 2;

            // if mid student is less, ignore students left of mid
            if(this.students.get(mid).compareTo(studentLookup) < 0) {
                min = mid + 1;
            }
            // if mid student is greater, ignore students right of mid
            else if (this.students.get(mid).compareTo(studentLookup) > 0) {
                max = mid - 1;
            }
            // student is present at mid
            else {
                foundStudentIndex = mid;
                found = true;
            }
        }

        // get time, and calculate difference for searchTime
        long milliEndTime = System.currentTimeMillis();
        this.binarySearchTime = milliEndTime - milliStartTime;

        return foundStudentIndex;
    }

    /**
     * Binary Search studentDB with last and first name
     *
     * <p>
     *     This function is preformed by first binary searching for the last name, once found it will linear search the
     *     min and max bounds for the first name.
     * </p>
     *
     * @param lastName students lastname
     * @param firstName student firstname
     * @return the index of the student found on a studentLastName sorted db, or -1 if not found
     */
    public int binarySearch(String lastName, String firstName) {

        // start timer
        long milliStartTime = System.currentTimeMillis();

        // declare search bounds, index, and found flag
        int min = 0;
        int max = this.students.size()-1;
        int mid = 0;
        int foundStudentIndex = -1;
        boolean done = false;

        // Binary Search
        while(min<=max && !done) {

            // calc middle index
            mid = (min+max) / 2;

            // student comparisons by lastName
            Student.setCompareToKey("lastName");

            // if mid student is less, ignore students left of mid
            if(this.students.get(mid).compareTo(lastName) < 0) {
                min = mid + 1;
            }
            // if mid student is greater, ignore students right of mid
            else if (this.students.get(mid).compareTo(lastName) > 0) {
                max = mid - 1;
            }
            // lastName is present at mid
            else {
                // linear search though min and max bounds for first name
                for(int i = min; i<=max ;i++) {
                    Student.setCompareToKey("firstName");
                    if (this.students.get(i).compareTo(firstName) == 0){
                        // index @ lower bound plus # of iterations
                        foundStudentIndex = min + (i-min);
                    }
                }
                done = true;
            }
        }

        // record time taken to search
        long milliEndTime = System.currentTimeMillis();
        this.binarySearchTime = milliEndTime - milliStartTime;

        return foundStudentIndex;
    }

    /**
     * Linear Search studentDB by ID
     *
     * @param studentLookup id NUm of student
     * @return found student index or -1
     */
    public int linearSearch(String studentLookup) {
        // start timer
        long milliStartTime = System.currentTimeMillis();

        // student comparisons by ID
        Student.setCompareToKey("studentID");


        // declare flags, and index
        boolean found = false;
        int foundStudentIndex = -1;

        int i = 0;
        // Linear Search
        while(!found && i < this.students.size()) {
            // if studentIDs are same, student is found
            if(this.students.get(i).compareTo(studentLookup) == 0) {
                foundStudentIndex = i;
                found = true;
            }
            i++;
        }

        // record time taken to search
        long milliEndTime = System.currentTimeMillis();
        this.linearSearchTime = milliEndTime - milliStartTime;

        return foundStudentIndex;
    }

    /**
     * Linear search by first and lastName
     *
     * @param lastName students last name
     * @param firstName students first name
     * @return found student index or -1
     */
    public int linearSearch(String lastName, String firstName) {

        // start timer
        long milliStartTime = System.currentTimeMillis();

        // declare flags, and index
        boolean found = false;
        int foundStudentIndex = -1;

        int i = 0;
        // Linear Search
        while(!found && i < this.students.size()) {
            // check if student lastName matches
            Student.setCompareToKey("lastName");
            if(this.students.get(i).compareTo(lastName) == 0) {
                // check if student first name matches
                Student.setCompareToKey("firstName");
                if(this.students.get(i).compareTo(firstName) == 0) {
                    foundStudentIndex = i;
                    found = true;
                }
            }
            i++;
        }

        // time taken to search
        long milliEndTime = System.currentTimeMillis();
        this.linearSearchTime = milliEndTime - milliStartTime;

        return foundStudentIndex;
    }

    /**
     * Number of males and females in Database
     *
     * @return number of males and females {M,F}
     */
    public int[] getNumOfEachGender() {
        // males and females {M,F}
        int[] numberOfGender = {0,0};

        for (Student student : this.students) {
            // if student is male increment 1st value
            if (student.getGender() == 'M') {
                numberOfGender[0]++;
            }
            // if student is female increment 2nd value
            else {
                numberOfGender[1]++;
            }
        }

        return numberOfGender;
    }

    /**
     * how long each sort took, and which is faster
     *
     * @return string of time taken, and faster
     */
    public String sortTime() {

        // determine which sort was faster
        String fasterSort = "";
        if(this.bubbleSortTime < insertSortTime) {
            fasterSort = "Bubble Sort is faster";
        } else {
            fasterSort = "Insert Sort is faster";
        }

        return "Bubble Sort Time took " + bubbleSortTime +  " milliseconds\n" + "Insert Sort Time took " +
                insertSortTime + " milliseconds\n" + fasterSort;
    }

    /**
     *  get Num students by Course - get number on students enrolled in course
     *
     *  <p>
     *      Hashmap is very similar to the python dictionary datastructures. Handled formatting errors by Adding
     *      check to see if any courses are "" / " " whitespace
     *  </p>
     *
     * @return A hashmap of courses, and students enrolled - (KEY - Course, Value - Number of enrolled students)
     */
    public HashMap<String,Integer> getNumStudentsByCourse() {

        // init Hashmap with string keys, integer values
        HashMap<String, Integer> coursesByEnrollment = new HashMap<>();

        // parse every student in student DB
        for (Student student:this.students) {
            String[] currentStudentCourses = student.getCourses().split(" ");

            for (String studentCourse : currentStudentCourses) {
                if (coursesByEnrollment.containsKey(studentCourse)) {
                    // increment # of enrolled by 1
                    coursesByEnrollment.put(studentCourse, coursesByEnrollment.get(studentCourse) + 1);
                }
                // handle formating errors, add new course
                else if (studentCourse.compareTo("") != 0 && studentCourse.compareTo(" ") != 0) {
                    coursesByEnrollment.put(studentCourse, 1);
                }
            }
        }
        return coursesByEnrollment;
    }

    /**
     * file writer utility function
     *
     * @param fileName name of file
     * @param sortedStudentDB sorted student database
     * @throws IOException output to file has failed
     */
    public void writeToFile(String fileName, ArrayList<Student> sortedStudentDB) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("outputs/" + fileName + ".txt"));

        // print all students in the sorted student DB
        for(Student student:sortedStudentDB) {
            pw.println(student);
            pw.flush();
        }
        pw.close();
    }

    /**
     * overloaded file writer utility function
     *
     * @param fileName name of file
     * @param numStudentsCourses HashMap of courses and num of students (key:courses, value: enrollment)
     * @throws IOException output to file has failed
     */
    public void writeToFile(String fileName, HashMap<String, Integer> numStudentsCourses) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("outputs/" + fileName + ".txt"));

        // iterate through all the keys, and print to file
        for(String course : numStudentsCourses.keySet()) {
            pw.println("Course: " + course + " - Enrollment: " + numStudentsCourses.get(course));
            pw.flush();
        }
        pw.close();
    }

    /**
     * Read data from file and load to student DB
     *
     * @param fileName name of the file to read
     * @throws FileNotFoundException file is not found
     */
    public void readFromFile(String fileName) throws FileNotFoundException {
        // open data for parsing, then loading
        Scanner sc = new Scanner(new FileReader(fileName));

        // load students data into studentDatabase
        while(sc.hasNextLine()) {
            // loaded in main, so you can easily modify to load manually in console.
            String currentStudent = sc.nextLine();
            String[] currentStudentCSV = currentStudent.split(",");

            // init new student and add to studentDB
            this.students.add(new Student(currentStudentCSV[0], currentStudentCSV[1], currentStudentCSV[2].charAt(0),
                    currentStudentCSV[3] , currentStudentCSV[4], currentStudentCSV[5]));
        }
    }

    /**
     * get the student database
     * @return the student database as arraylist
     */
    public ArrayList<Student> getStudents() { return this.students; }

    /**
     * set if database is sorted by ID
     * @param sorted is sorted
     */
    public void setStudentIDSorted(boolean sorted) { this.studentIDSorted = sorted; }

    /**
     * get if database is sorted by ID
     * @return is database sorted
     */
    public boolean getStudentIDSorted() { return this.studentIDSorted; }

    /**
     * get time binary search took
     * @return time
     */
    public long getBinarySearchTIme() { return this.binarySearchTime; }

    /**
     * get time linear search took
     * @return time
     */
    public long getLinearSearchTIme() { return this.linearSearchTime; }
}
