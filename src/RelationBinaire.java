import java.util.*;
import java.lang.*;

public class RelationBinaire {

    // attributs

    private int n;           // n > 0, E = {0,1,2, ..., n-1}
    private boolean[][] matAdj;  // matrice d'adjacence de R
    private int m;           // cardinal de R
    private EE[] tabSucc;    // tableau des ensembles de successeurs


    // constructeurs

    /**
     * pré-requis : nb > 0
     * action : construit la relation binaire vide dans l'ensemble {0,1,2, ..., nb-1}
     */
    public RelationBinaire(int nb) {
        n = nb;
        m = 0;
        tabSucc = new EE[nb];
        matAdj = new boolean[nb][nb];
    }

    //______________________________________________


    /**
     * pré-requis : nb > 0 et 0 <= p <= 1
     * action : construit une relation binaire aléatoire dans l'ensemble {0,1,2, ..., nb-1}
     * à laquelle chaque couple a la probabilité p d'appartenir.
     * En particulier, construit la relation vide si p = 0 et la relation pleine si p = 1.
     * Indication : Math.random() retourne un réel de type double aléatoire de l'intervalle [0,1[
     */
    public RelationBinaire(int nb, double p) {
        n = nb;
        m = 0;
        tabSucc = new EE[nb];
        matAdj = new boolean[nb][nb];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double random = Math.random();
                if (random == p && p != 0) {
                    tabSucc[i].ajoutElt(j);
                }
            }
        }

        for (int i = 0; i < nb; i++) {
            for (int j = 0; j < nb; j++) {
                if (tabSucc[i].contient(j)) matAdj[i][j] = true;
            }
        }

        for (int i = 0; i < n; i++) {
            m += tabSucc[i].getCardinal();
        }
    }

    //______________________________________________


    /**
     * pré-requis : nb > 0 et 1 <= choix <= 3
     * action : construit la relation binaire dans l'ensemble {0,1,2, ..., nb-1}
     * '=' si egal a la valeur vrai et '<=' sinon
     */
    public RelationBinaire(int nb, boolean egal) {
        n = nb;
        m = 0;
        tabSucc = new EE[nb];

        /*
        if (egal) {
            '=' ????
        } else {
            '<=' ????
        }
        */
    }

    //______________________________________________


    /**
     * pré-requis : mat est une matrice carrée de dimension > 0
     * action : construit une relation binaire dont la matrice d'adjacence
     * est une copie de mat
     */
    public RelationBinaire(int[][] mat) {
        n = mat.length;
        m = 0;
        matAdj = new boolean[n][n];
        tabSucc = new EE[n];
        for (int i = 0; i < n; i++) {
            tabSucc[i] = new EE(n);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1){
                    m++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matAdj[i][j] = mat[i][j] != 0;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matAdj[i][j]) tabSucc[i].ajoutElt(j);
            }
        }
    }

    //______________________________________________


    /**
     * pré-requis : tab.length > 0 et pour tout i, les éléments de tab[i]
     * sont compris entre 0 et tab.length-1
     * action : construit une relation binaire dont le tableau des ensembles de successeurs
     * est une copie de tab
     */
    public RelationBinaire(EE[] tab) {
        n = tab.length;
        m = 0;
        for (int i = 0; i < tab.length; i++) {
            m += tab[i].getCardinal();
        }

        tabSucc = Arrays.copyOf(tab, tab.length);
        matAdj = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tabSucc[i].contient(j)) matAdj[i][j] = true;
            }
        }
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * action : construit une copie de r
     */
    public RelationBinaire(RelationBinaire r) {
        this.n = r.n;
        this.m = r.m;
        this.matAdj = new boolean[n][n];
        this.tabSucc = new EE[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matAdj[i][j] = r.matAdj[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            tabSucc[i] = new EE(r.tabSucc[i]);
        }
    }


    //______________________________________________


    // méthodes


    /**
     * pré-requis : aucun
     * résultat : une chaîne de caractères permettant d'afficher this par sa matrice d'adjacence
     * contenant des '0' et des '1' (plus lisibles que des 'V' et des 'F') et sa définition
     * en extension (ensemble de couples {(..,..),(..,..), ...})
     */
    @Override
    public String toString() {
        for (int i = 0; i < matAdj.length; i++) {
            System.out.print("(");
            for (int j = 0; j < matAdj[0].length; j++) {
                if (matAdj[i][j] == false) {
                    System.out.print(" 0 ");
                } else {
                    System.out.print(" 1 ");
                }
            }
            System.out.print(")\n");
        }

        System.out.print("Tableau des relations : (");
        for (int i = 0; i < n; i++) {
            System.out.print(tabSucc[i].toString());
        }
        System.out.println(")\n");

        return " ";
    }

    //______________________________________________


    // A) Logique et calcul matriciel
    //-------------------------------


    /**
     * pré-requis : m1 et m2 sont des matrices carrées de même dimension et 1 <= numConnecteur <= 5
     * résultat : la matrice obtenue en appliquant terme à terme le  connecteur de numéro numConnecteur
     * sur m1 si numConnecteur = 3 (dans ce cas le paramètre m2 n'est pas utilisé),
     * et sur m1 et m2 dans cet ordre sinon, sachant que les connecteurs "ou","et","non",
     * "implique"et "equivalent" sont numérotés de 1 à 5 dans cet ordre
     */

    public static boolean[][] opBool(boolean[][] m1, boolean[][] m2, int numConnecteur) {
        int length = m1.length;

        boolean[][] m3 = new boolean[length][length];

        switch (numConnecteur) {
            case (1): // OU
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        m3[i][j] = m1[i][j] || m2[i][j];
                    }
                }
            case (2): // ET
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        m3[i][j] = m1[i][j] && m2[i][j];
                    }
                }
            case (3): // NON
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        m3[i][j] = !m1[i][j];
                    }
                }
            case (4): // IMPLIQUE
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        m3[i][j] = !m1[i][j] || (m1[i][j] && m2[i][j]);
                    }
                }
            case (5): // EQUIVALENT
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length; j++) {
                        m3[i][j] = m1[i][j] == m2[i][j];
                    }
                }
        }

        return m3;
    }

    //______________________________________________


    /**
     * pré-requis : m1 et m2 sont des matrices carrées de même dimension
     * résultat : le produit matriciel de m1 et m2
     */
    public static boolean[][] produit(boolean[][] m1, boolean[][] m2) {
        int length = m1.length;

        boolean[][] mat = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    // soit laisse TRUE soit verifie que a l'indice de m1 et a l'indice de m2 il y a TRUE
                    mat[i][j] = mat[i][j] || (m1[i][k] && m2[k][j]);
                }
            }
        }
        return mat;
    }

    //______________________________________________


    /**
     * pré-requis : m est une matrice carrée
     * résultat : la matrice transposée de m
     */
    public static boolean[][] transposee(boolean[][] m) {
        int length = m.length;

        boolean[][] matriceTransposee = new boolean[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                matriceTransposee[i][j] = m[j][i];
            }
        }

        return matriceTransposee;
    }

    //______________________________________________


    // B) Théorie des ensembles
    //--------------------------


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est vide
     */
    public boolean estVide() {
        for (int i = 0; i < tabSucc.length; i++) {
            if (!tabSucc[i].estVide()) {
                return false;
            }
        }

        return true;

        /*
        for (int i = 0; i < matAdj.length; i++) {
            for (int j = 0; j < matAdj[i].length; j++) {
                if (matAdj[i][j] == true){
                    return false;
                }
            }
        }
         */
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est pleine (contient tous les couples d'éléments de E)
     */
    public boolean estPleine() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < tabSucc.length; j++) {
                if (!tabSucc[j].contient(i)) {
                    return false;
                }
            }
        }

        return true;

        /*
        for (int i = 0; i < matAdj.length; i++) {
            for (int j = 0; j < matAdj[i].length; j++) {
                if (matAdj[i][j] == false){
                    return false;
                }
            }
        }
         */
    }

    //______________________________________________

    /**
     * pré-requis : aucun
     * résultat : vrai ssi (x,y) appartient à this
     */
    public boolean appartient(int x, int y) {
        if (tabSucc[x].contient(y) || tabSucc[y].contient(x)) return true;

        return false;

        /*
        return matAjd[x][y];
         */
    }

    //______________________________________________


    /**
     * pré-requis : 0 <= x < this.n et 0 <= y < this.n
     * résultat : ajoute (x,y) à this s'il n'y est pas déjà
     */
    public void ajouteCouple(int x, int y) {
        if (!matAdj[x][y]) {
            matAdj[x][y] = true;
            tabSucc[x].ajoutElt(y);
        }
    }

    //______________________________________________


    /**
     * pré-requis : 0 <= x < this.n et 0 <= y < this.n
     * résultat : enlève (x,y) de this s'il y est
     */
    public void enleveCouple(int x, int y) {
        if (matAdj[x][y]) {
            matAdj[x][y] = false;
            tabSucc[x].retraitElt(y);
        }
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : une nouvelle relation binaire obtenue à partir de this en ajoutant
     * les couples de la forme (x, x) qui n'y sont pas déjà
     */
    public RelationBinaire avecBoucles() {
        RelationBinaire boucles = new RelationBinaire(this);

        for (int i = 0; i < n; i++) {
            boucles.ajouteCouple(i, i);
        }

        return boucles;
    }


    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : une nouvelle relation binaire obtenue à partir de this en enlèvant
     * les couples de la forme (x, x) qui y sont
     */
    public RelationBinaire sansBoucles() {
        RelationBinaire boucles = new RelationBinaire(this);

        for (int i = 0; i < n; i++) {
            boucles.enleveCouple(i, i);
        }

        return boucles;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : l'union de this et r
     */
    public RelationBinaire union(RelationBinaire r) {
        EE[] tabSuccUnion = new EE[n];

        for (int i = 0; i < n; i++) {
            tabSuccUnion[i] = tabSucc[i].union(r.tabSucc[i]);
        }

        return new RelationBinaire(tabSuccUnion);
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : l'intersection de this et r
     */
    public RelationBinaire intersection(RelationBinaire r) {
        EE[] tabSuccInter = new EE[n];

        for (int i = 0; i < n; i++) {
            tabSuccInter[i] = tabSucc[i].intersection(r.tabSucc[i]);
        }

        return new RelationBinaire(tabSuccInter);
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : la relation complémentaire de this
     */
    public RelationBinaire complementaire() {
        EE[] tabSuccComplementaire = tabSucc;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tabSuccComplementaire[i].contient(j)){
                    tabSuccComplementaire[i].retraitElt(j);
                } else {
                    tabSuccComplementaire[i].ajoutElt(j);
                }
            }
        }

        return new RelationBinaire(tabSuccComplementaire);
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : la différence de this et r
     */
    public RelationBinaire difference(RelationBinaire r) {
        EE[] tabSuccDifference = new EE[n];

        for (int i = 0; i < n; i++) {
            tabSuccDifference[i] = tabSucc[i].difference(r.tabSucc[i]);
        }

        return new RelationBinaire(tabSuccDifference);
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : vrai ssi this est incluse dans r
     */
    public boolean estIncluse(RelationBinaire r) {
        for (int i = 0; i < n; i++) {
            if (!tabSucc[i].estInclus(r.tabSucc[i])) return false;
        }

        return true;
    }

    //______________________________________________


    /**
     * pré-requis : this.n = r.n
     * résultat : vrai ssi this est égale à r
     */
    public boolean estEgale(RelationBinaire r) {
        for (int i = 0; i < n; i++) {
            if (!tabSucc[i].estEgal(r.tabSucc[i])){
                return false;
            }
        }

        return true;
    }

    //______________________________________________


    // C) Théorie des graphes orientés
    //---------------------------------

    /**
     * pré-requis : 0 <= x < this.n
     * résultat : l'ensemble des successeurs de x dans this, "indépendant"
     * (c'est-à-dire dans une autre zône mémoire) de l'attribut this.tabSucc
     */
    public EE succ(int x) {
        return tabSucc[0];
    }

    //______________________________________________


    /**
     * pré-requis : 0 <= x < this.n
     * résultat : l'ensemble des prédécesseurs de x dans this
     */
    public EE pred(int x) {
        return tabSucc[0];
    }

    //______________________________________________


    // D) Relation binaire
    //---------------------

    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est réflexive
     */
    public boolean estReflexive() {
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est antiréflexive
     */
    public boolean estAntireflexive() {
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est symétrique
     */
    public boolean estSymetrique() {
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est antisymétrique
     */
    public boolean estAntisymetrique() {
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est transitive
     */
    public boolean estTransitive() {
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : vrai ssi this est une relation d'ordre
     */
    public boolean estRelOrdre() {
        return true;
    }

    //______________________________________________


    /**
     * pré-requis : aucun
     * résultat : la relation binaire assiciée au diagramme de Hasse de this
     */
    public RelationBinaire hasse() {
        return this;
    }

    //______________________________________________

    /**
     * pré-requis : aucun
     * résultat : la fermeture transitive de this
     */
    public RelationBinaire ferTrans() {
        return this;
    }

    //______________________________________________

    /**
     * pré-requis : aucun
     * action : affiche this sous 2 formes (matrice et ensemble de couples), puis affiche ses propriétés
     * (réflexive, ..., relation d'ordre) et les relations binaires suivantes obtenues à partir de this :
     * Hasse, fermeture transitive de Hasse et fermeture transitive de Hasse avec boucles (sous 2 formes aussi)
     */
    public void afficheDivers() {

    }

    //______________________________________________


} // fin RelationBinaire