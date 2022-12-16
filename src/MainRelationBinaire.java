import java.util.*;
import java.lang.*;

public class MainRelationBinaire {


    /**
     * action : demande à l'utilisateur de saisir un entier nb > 0 et un réel p strictement compris entre 0 et 1 (avec vérification des saisie),
     * puis crée 5 relations binaires dans l'ensemble {0,1,2, ..., nb-1}, R0 vide, R1= '=', R2 = '<=', R3 une relation
     * binaire aléatoire à laquelle chaque couple a la probabilité p d'appartenir et R4 pleine, puis, pour i de 0 à 4,
     * affiche Ri sous 2 formes (matrice et ensemble de couples), puis affiche ses propriétés
     * (réflexive, ..., relation d'ordre) et les relations binaires suivantes obtenues à partir de Ri :
     * Hasse, fermeture transitive de Hasse et fermeture transitive de Hasse avec boucles (sous 2 formes aussi)
     */
    public static void main(String[] args) {
        boolean[][] tabBoolTest =
                (new boolean[][]{
                        {true, false, true},
                        {false, true, false},
                        {true, false, true}
                });

        int[][] tabIntTest =
                (new int[][]{
                        {1, 0, 0},
                        {0, 1, 0},
                        {1, 0, 1}
                });

        int[][] tabIntTest2 =
                (new int[][]{
                        {0, 1, 1},
                        {0, 0, 0},
                        {0, 0, 1}
                });

        RelationBinaire relationTest = new RelationBinaire(tabIntTest);
        relationTest.toString();

        RelationBinaire relationTest2 = new RelationBinaire(tabIntTest2);
        relationTest2.toString();


        System.out.println("AVEC BOUCLES");
        RelationBinaire relationAvecBoucles = relationTest2.avecBoucles();
        relationAvecBoucles.toString();

        System.out.println("SANS BOUCLES");
        RelationBinaire relationSansBoucles = relationTest.sansBoucles();
        relationSansBoucles.toString();

        System.out.println("UNION");
        RelationBinaire relationUnion = relationTest.union(relationTest2);
        relationUnion.toString();

        relationTest.toString();
        relationTest2.toString();

        System.out.println("INTERSECT");
        RelationBinaire relationIntersect = relationTest.intersection(relationTest2);
        relationIntersect.toString();

        System.out.println("COMPLEMENTAIRE");
        RelationBinaire relationComplementaire = relationTest.complementaire();
        relationComplementaire.toString();

        System.out.println("DIFFERENCE");
        RelationBinaire relationDifference = relationTest.difference(relationTest2);
        relationDifference.toString();
    }


} // fin MainRelationBinaire