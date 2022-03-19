/**
 * Student class
 * <p>
 *     Student type  holds 7 attributes, has compareTo is overridden and overloaded.
 *
 *     Uses setComparisonsKey to further alter the behaviour of the CompareTo function. overloaded fuctions also uses my favourite java statement
 * </p>
 *
 * @author UnloadingGnat
 * @date 2021/12/05
 */

class Student {
    // Attributes
    protected String lastName = "";
    protected String firstName = "";
    protected char gender = ' ';
    protected String studentID = "";
    protected String DOB = "";
    protected String courses = "";
    protected static String compareToKey = "";

    /**
     * Constructor
     *
     * @param lastName student last name
     * @param firstName student first name
     * @param gender student gender
     * @param studentID student's ID number
     * @param DOB Student Date of birth
     * @param courses Courses the student took
     */
    public Student(String lastName, String firstName, char gender, String studentID, String DOB, String courses) {

        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.studentID = studentID;
        this.DOB = DOB;
        this.courses = courses;
    }


    /**
     * overloaded compareTo function, compare Student type depending on STATIC key
     *
     * @param other other value is Students type
     * @return result 0 if equal, <0 if smaller, >0 if greater
     */
    public int compareTo(Student other) {
        int result = 0;

        if (compareToKey.equals("lastName")) {
            result = this.lastName.compareTo(other.getLastName());
        } else if (compareToKey.equals("studentID")) {
            result = this.studentID.compareTo(other.getStudentID());
        }

        return result;
    }

    /**
     * overloaded compareTo function, compare Student type to String depending on STATIC key
     *
     * @param other other value is String type
     * @return result 0 if equal, <0 if smaller, >0 if greater
     */
    public int compareTo(String other) {
        int result = 0;

        switch (compareToKey) {
            case "firstName":
                result = this.firstName.compareTo(other);
                // my favourite Java statement 😉
                break;
            case "lastName":
                result = this.lastName.compareTo(other);
                break;
            case "studentID":
                result = this.studentID.compareTo(other);
                break;
        }

        return result;
    }

    /**
     * set key to change the behaviour of the compareTo function
     * @param key types of keys: {firstName, lastName, studentID}
     */
    public static void setCompareToKey(String key) { compareToKey = key; }

    /**
     * get student's last Name
     * @return last name
     */
    public String getLastName() { return this.lastName; }

    /**
     * get student's ID
     * @return studentID
     */
    public String getStudentID() { return this.studentID; }

    /**
     * get student's gender
     * @return gender
     */
    public char getGender() { return this.gender; }

    public String getCourses() {return this.courses;}

    /**
     * toString
     */
    public String toString() { return this.lastName + "," + this.firstName + "," + this.gender + "," + this.studentID
            + "," + this.DOB + "," + this.courses; }

}
