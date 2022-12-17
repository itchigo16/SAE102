import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class EE {

    private int cardinal; // stockage du cardinal de l'objet
    private int [] ensTab; // stockage des valeurs

    public EE(int max) { // creation d'un ensemble vide
        this.ensTab = new int[max];
        this.cardinal=0;
    }

    public EE (int [] mesValeurs, int max) {
        this(max); // invoque le constructeur vide
        for(int i=0; i < mesValeurs.length; i++) {
            this.ajoutElt(mesValeurs[i]);
        }
    }

    public EE (EE e) {
        this(e.ensTab.length); // constructeur par recopie
        for(int i=0; i < e.cardinal; i++) {
            this.ajoutElt(e.ensTab[i]);
        }
    }

    /*
    public EE (String mesValeurs) {
	this(); // invoque le constructeur vide
	String str[] = mesValeurs.split(" ");
	for (int i = 0; i < str.length; i++) {
	    this.ajoutElt(Integer.parseInt(str[i]));
	}
    }
    */

    public EE (String mesValeurs, int max) {
        this(max); // invoque le constructeur vide
        Scanner sc = new Scanner(mesValeurs);
        while(sc.hasNext()) {
            this.ajoutElt(sc.nextInt());
        }
    }

    public EE (File fichier, int max) {
        this(max); // invoque le constructeur vide
        // File fichier=new File(nomFic);
        try {
            Scanner sc = new Scanner(fichier);
            while(sc.hasNext()) {
                this.ajoutElt(sc.nextInt());
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(0);
        }
    }

    public String toString () {
        String s="{";
        for (int i=0; i < this.cardinal-1; i++) {
            s = s + this.ensTab[i] + ",";
        }
        if (this.cardinal > 0) {
            s = s + this.ensTab[this.cardinal-1];
        }
        s = s + "}";
        return s;
    }

    /* ==================================================================== */

    public int getCardinal() {
        return this.cardinal;
    }

    /** @param x est l'element a chercher
     *  @return l'indice de l'element si positif ; -1 si absent
     */
    private int contientPratique (int x) {
        int i = 0;
        while (i < this.cardinal && this.ensTab[i] != x) {
            i++;
        }
        if (i == this.cardinal)
            return -1;
        else
            return i;
    }

    public boolean contient (int x) {
        return ( (contientPratique(x)) >= 0 );
    }

    /**
     * @param x est un element a ajouter
     * ajoute x a l'objet courant
     */
    public void ajoutPratique(int x) {
        this.ensTab[this.cardinal]=x;
        this.cardinal++;
    }

    /**
     * @param i et pre-requis : 0 <= i < this.cardinal
     * @action enleve l'elt this.ensTab[i] de l'ensemble courant
     */
    private void retraitPratique(int i) {
        this.ensTab[i]=this.ensTab[this.cardinal - 1]; // on bouche le trou
        this.cardinal--;
    }

    /**
     *  @return booleen indiquant si l'ensemble est vide
     */
    public boolean estVide() {
        return (this.cardinal == 0);
    }

    /**
     *  @return booleen indiquant si le tableau permettant de stocker l'ensemble est plein
     */
    public boolean deborde() {
        return (this.cardinal == this.ensTab.length);
    }

    /* ==================================================================== */

    /**
     * @param x est l'entier a retirer l'ensemble
     * @return false si this ne contenait pas x ; true si x present et est retire
     */
    public boolean retraitElt (int x) {
        int res = this.contientPratique(x);
        if (res == -1)
            return false;
        else {
            this.retraitPratique(res);
            return true;
        }
    }

    /**
     * @param x est l'entier a chercher dans l'ensemble
     * @return -1 si this est plein ; 0 si x deja present et pas ajoute ; 1 si x ajoute
     */
    public int ajoutElt(int x) {
        if (this.deborde())
            return -1;
        else if (this.contient(x))
            return 0;
        else {
            this.ajoutPratique(x);
            return 1;
        }
    }

    public int retraitUnElt() {
        // Pre-requis : ensemble this est non vide
        // Action/resultat : enleve un element de this (le dernier, par commodite)
        //                   et le retourne
        this.cardinal--;
        return this.ensTab[this.cardinal];
    }

    public int retraitEltAleatoirement() {
        // Pre-requis : ensemble this est non vide
        // Resultat/action : enleve un element de this (aleatoirement)
        //                   et le retourne
        int i = Ut.randomMinMax(0,this.cardinal - 1);
        int select = this.ensTab[i];
        this.retraitPratique(i);
        return select;
    }

    /**
     * @param e est un ensemble
     * @return vrai si this est inclus dans l'ensemble e
     */
    public boolean estInclus (EE e) {
        // parcours partiel
        int i=0;
        while (i < this.cardinal && e.contient(this.ensTab[i])) {
            i++;
        }
        return (i == this.cardinal);
    }

    /**
     * @param e est un ensemble
     * @return vrai si les ensembles ont le meme nombre d'elements et que tous
     * les elements sont egaux
     */
    public boolean estEgal (EE e) {
        return this.cardinal == e.cardinal && this.estInclus(e);
    }

    /**
     * @param e est un ensemble
     * @return vrai si l'objet courant est disjoint de e
     */
    public boolean estDisjoint (EE e) {
        //
        // principe: on verifie si chaque elt de l'objet courant n'appartient pas a e;
        // parcours partiel car des qu'un element existe dans les 2, on s'arrete en
        // retournant faux
        //
        int i=0;
        while (i < this.cardinal && ! e.contient(this.ensTab[i])) {
            i++;
        }
        return i == this.cardinal;
    }

    /**
     *
     * @param e est un ensemble
     * @return un ensemble representant l'intersection entre l'objet courant et f
     */
    public EE intersection(EE e) {
        //
        // Principe: pour chaque x de l'objet courant on regarde s'il est present dans e,
        // si oui on l'ajoute dans l'ensemble retourne
        //
        EE ens = new EE(this.ensTab.length);
        for (int i=0; i < this.cardinal; i++) {
            if (e.contient(this.ensTab[i])) { // ensTab[i] appartient aux deux ensembles
                ens.ajoutPratique(this.ensTab[i]);
            }
        }
        return ens;
    }

    /**
     *
     * @param e est un ensemble
     * @return un ensemble representant l'union entre l'objet courant et f
     */
    public EE union (EE e) {
        //
        // Principe: recopie l'objet courant puis lui ajoute (si besoin) les elements de e
        //
        EE ens = new EE(this);

        for (int i=0; i < e.cardinal; i++) {
            ens.ajoutElt(e.ensTab[i]);
        }
        return ens;
    }

    /**
     * @param e est un ensemble
     * @return un ensemble representant la difference entre l'objet courant et f
     */
    public EE difference (EE e) {
        //
        // retourne un ensemble dans lequel figurent les elements qui existe dans
        // l'ensemble courant mais pas dans e
        //
        EE ens = new EE(e.ensTab.length);
        for (int i=0; i < this.cardinal; i++) {
            if (! e.contient(this.ensTab[i])) {
                ens.ajoutPratique(this.ensTab[i]);
            }
        }
        return ens;
    }

    /**
     * @param e est un ensemble
     * @return un ensemble representant la difference entre l'ensemble courant et e
     */
    public EE differenceBis (EE e) {
        //
        // retourne un ensemble dans lequel figure les elements qui existe dans l'objet courant
        // mais pas dans f
        //
        EE ens = new EE(this);
        for (int i=0; i < e.cardinal; i++) {
            if (this.contient(e.ensTab[i])) {
                ens.retraitElt(e.ensTab[i]);
            }
        }
        return ens;
    }
}