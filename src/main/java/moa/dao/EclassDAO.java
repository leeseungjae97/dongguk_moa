package moa.dao;

import java.util.ArrayList;

public class EclassDAO {
    ArrayList<EclassLectureDAO> eclassLectureDAOArrayList;
    String subjectName;
    ArrayList<String> weeks;

    public EclassDAO(String subjectName) {
        eclassLectureDAOArrayList = new ArrayList<>();
        this.subjectName = subjectName;
        this.weeks = new ArrayList<>();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setEclassLectureDAOArrayList(ArrayList<EclassLectureDAO> eclassLectureDAOArrayList) {
        this.eclassLectureDAOArrayList = eclassLectureDAOArrayList;
    }

    public ArrayList<String> getWeeks() {
        return weeks;
    }

    public void addWeek(String week) {
        this.weeks.add(week);
    }

    public ArrayList<EclassLectureDAO> getEclassLectureDAOArrayList() {
        return eclassLectureDAOArrayList;
    }

    public void addEclassLectureDAO(EclassLectureDAO eclassDAO) {
        eclassLectureDAOArrayList.add(eclassDAO);
    }
}
