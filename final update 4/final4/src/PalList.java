import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PalList {
    // list for Pal objects
    private List<Pal> pals;

    // constructor
    public PalList() {
        this.pals = new ArrayList<>();
    }

    // add pal to list
    public void addPal(Pal pal) {
        pals.add(pal);
    }

    // getter for list of pals
    public List<Pal> getPals() {
        return pals;
    }

    // sort pals by ID
    public void sortPalsByPalId() {
        Collections.sort(pals, Comparator.comparingInt(Pal::getPalNumber));
    }

    // sort pals by name
    public void sortPalsByPalName() {
        Collections.sort(pals, Comparator.comparing(Pal::getName));
    }

    // sort pals by element
    public void sortPalsByPalElement() {
        Collections.sort(pals, Comparator.comparing(Pal::getElementType));
    }

    // sort pals by capture count
    public void sortPalsByCapturedCount() {
        Collections.sort(pals, Comparator.comparingInt(Pal::getCapturedCount));
    }

    // populate the PalList from a text file allowing pals with multiple element
    // types
    public void populateFromTextFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+", 4); // Split into 4 parts at most
                if (parts.length == 4) {
                    int palNumber = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String elementType = parts[3];
                    int capturedCount = Integer.parseInt(parts[2]);
                    pals.add(new Pal(palNumber, name, elementType, capturedCount));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in file: " + filename);
        }
    }

}
