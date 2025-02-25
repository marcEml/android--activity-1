package fr.imt_atlantique.myfirstapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class User implements Parcelable {
    private final String firstName;
    private final String lastName;
    private final String birthCity;
    private final String birthDate;
    private final String department;
    private final List<String> phoneNumbers;

    public User(String firstName, String lastName, String birthCity, String birthDate, String department, List<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthCity = birthCity;
        this.birthDate = birthDate;
        this.department = department;
        this.phoneNumbers = phoneNumbers;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        birthCity = in.readString();
        birthDate = in.readString();
        department = in.readString();
        phoneNumbers = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(birthCity);
        dest.writeString(birthDate);
        dest.writeString(department);
        dest.writeStringList(phoneNumbers);
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBirthCity() { return birthCity; }
    public String getBirthDate() { return birthDate; }
    public String getDepartment() { return department; }
    public List<String> getPhoneNumbers() { return phoneNumbers; }
}
