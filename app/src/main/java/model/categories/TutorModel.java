
package model.categories;

import com.google.gson.annotations.Expose;

public class TutorModel {

    @Expose
    private Tutor tutor;

    /**
     * 
     * @return
     *     The tutor
     */
    public Tutor getTutor() {
        return tutor;
    }

    /**
     * 
     * @param tutor
     *     The tutor
     */
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "TutorModel{" +
                "tutor=" + tutor +
                '}';
    }
}
