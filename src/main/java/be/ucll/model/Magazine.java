package be.ucll.model;

import java.util.Objects;

public class Magazine extends Publication {

    private String editor;
    private String issn;

    public Magazine(String title, String editor, String issn, int pubYear, int availableCopies) {
        super(title, pubYear, availableCopies);
        setEditor(editor);
        setIssn(issn);

    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        if (editor == null || editor.isBlank()) {
            throw new RuntimeException("Editor is required");
        }

        this.editor = editor;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        if (issn == null || issn.isBlank()) {
            throw new RuntimeException("ISSN is required");
        }

        this.issn = issn;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "Magazine{" +
                "editor='" + editor + '\'' +
                ", issn='" + issn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return Objects.equals(editor, magazine.editor) && Objects.equals(issn, magazine.issn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), editor, issn);
    }
}
