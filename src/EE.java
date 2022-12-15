import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EE {
    private int cardinal;
    private int[] ensTab;

    public EE(int var1) {
        this.ensTab = new int[var1];
        this.cardinal = 0;
    }

    public EE(int[] var1, int var2) {
        this(var2);

        for(int var3 = 0; var3 < var1.length; ++var3) {
            this.ajoutElt(var1[var3]);
        }

    }

    public EE(EE var1) {
        this(var1.ensTab.length);

        for(int var2 = 0; var2 < var1.cardinal; ++var2) {
            this.ajoutElt(var1.ensTab[var2]);
        }

    }

    public EE(String var1, int var2) {
        this(var2);
        Scanner var3 = new Scanner(var1);

        while(var3.hasNext()) {
            this.ajoutElt(var3.nextInt());
        }

    }

    public EE(File var1, int var2) {
        this(var2);

        try {
            Scanner var3 = new Scanner(var1);

            while(var3.hasNext()) {
                this.ajoutElt(var3.nextInt());
            }
        } catch (FileNotFoundException var4) {
            System.out.println(var4);
            System.exit(0);
        }

    }

    public String toString() {
        String var1 = "{";

        for(int var2 = 0; var2 < this.cardinal - 1; ++var2) {
            var1 = var1 + this.ensTab[var2] + ",";
        }

        if (this.cardinal > 0) {
            var1 = var1 + this.ensTab[this.cardinal - 1];
        }

        var1 = var1 + "}";
        return var1;
    }

    public int getCardinal() {
        return this.cardinal;
    }

    private int contientPratique(int var1) {
        int var2;
        for(var2 = 0; var2 < this.cardinal && this.ensTab[var2] != var1; ++var2) {
        }

        return var2 == this.cardinal ? -1 : var2;
    }

    public boolean contient(int var1) {
        return this.contientPratique(var1) >= 0;
    }

    public void ajoutPratique(int var1) {
        this.ensTab[this.cardinal] = var1;
        ++this.cardinal;
    }

    private void retraitPratique(int var1) {
        this.ensTab[var1] = this.ensTab[this.cardinal - 1];
        --this.cardinal;
    }

    public boolean estVide() {
        return this.cardinal == 0;
    }

    public boolean deborde() {
        return this.cardinal == this.ensTab.length;
    }

    public boolean retraitElt(int var1) {
        int var2 = this.contientPratique(var1);
        if (var2 == -1) {
            return false;
        } else {
            this.retraitPratique(var2);
            return true;
        }
    }

    public int ajoutElt(int var1) {
        if (this.deborde()) {
            return -1;
        } else if (this.contient(var1)) {
            return 0;
        } else {
            this.ajoutPratique(var1);
            return 1;
        }
    }

    public int retraitUnElt() {
        --this.cardinal;
        return this.ensTab[this.cardinal];
    }

    public int retraitEltAleatoirement() {
        int var1 = Ut.randomMinMax(0, this.cardinal - 1);
        int var2 = this.ensTab[var1];
        this.retraitPratique(var1);
        return var2;
    }

    public boolean estInclus(EE var1) {
        int var2;
        for(var2 = 0; var2 < this.cardinal && var1.contient(this.ensTab[var2]); ++var2) {
        }

        return var2 == this.cardinal;
    }

    public boolean estEgal(EE var1) {
        return this.cardinal == var1.cardinal && this.estInclus(var1);
    }

    public boolean estDisjoint(EE var1) {
        int var2;
        for(var2 = 0; var2 < this.cardinal && !var1.contient(this.ensTab[var2]); ++var2) {
        }

        return var2 == this.cardinal;
    }

    public EE intersection(EE var1) {
        EE var2 = new EE(this.ensTab.length);

        for(int var3 = 0; var3 < this.cardinal; ++var3) {
            if (var1.contient(this.ensTab[var3])) {
                var2.ajoutPratique(this.ensTab[var3]);
            }
        }

        return var2;
    }

    public EE union(EE var1) {
        EE var2 = new EE(this);

        for(int var3 = 0; var3 < var1.cardinal; ++var3) {
            var2.ajoutElt(var1.ensTab[var3]);
        }

        return var2;
    }

    public EE difference(EE var1) {
        EE var2 = new EE(var1.ensTab.length);

        for(int var3 = 0; var3 < this.cardinal; ++var3) {
            if (!var1.contient(this.ensTab[var3])) {
                var2.ajoutPratique(this.ensTab[var3]);
            }
        }

        return var2;
    }

    public EE differenceBis(EE var1) {
        EE var2 = new EE(this);

        for(int var3 = 0; var3 < var1.cardinal; ++var3) {
            if (this.contient(var1.ensTab[var3])) {
                var2.retraitElt(var1.ensTab[var3]);
            }
        }

        return var2;
    }
}
