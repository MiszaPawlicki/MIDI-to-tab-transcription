public class Note {
    public Note(long tick, String note, int note_octave) {
        this.tick = tick;
        this.note = note;
        this.note_octave = note_octave;
        this.full_note_name = note+note_octave;
    }

    public void setNote_octave(int note_octave) {
        this.note_octave = note_octave;
        this.full_note_name = note+note_octave;
    }

    public void setNote(String note) {
        this.note = note;
        this.full_note_name = note+note_octave;
    }

    long tick;
    String note;
    int note_octave;
    String full_note_name;





}
