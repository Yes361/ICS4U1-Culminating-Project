package Components;

import java.io.Serializable;

public record CharacterDialog(String dialogText, String character, String chrPath) implements Serializable {}
