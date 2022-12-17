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
//        boolean[][] tabBoolTest1 =
//                (new boolean[][]{
//                        {false, true, true},
//                        {false, true, false},
//                        {true, false, true}
//                });
//
//        boolean[][] tabBoolTest2 =
//                (new boolean[][]{
//                        {false, false, true},
//                        {false, true, false},
//                        {false, false, false}
//                });
//
//        int[][] tabIntTest1 =
//                (new int[][]{
//                        {1, 0, 0},
//                        {0, 1, 0},
//                        {1, 0, 1}
//                });
//
//        int[][] tabIntTest2 =
//                (new int[][]{
//                        {0, 0, 0},
//                        {0, 1, 0},
//                        {1, 0, 1}
//                });
//
//        int[][] tabIntTest3 =
//                (new int[][]{
//                        {1, 1, 1},
//                        {0, 0, 0},
//                        {0, 1, 1}
//                });
//
//        System.out.println("RELATION TEST 1");
//        RelationBinaire relationTest1 = new RelationBinaire(tabIntTest1);
//        relationTest1.toString();
//
//        System.out.println("RELATION TEST 2");
//        RelationBinaire relationTest2 = new RelationBinaire(tabIntTest2);
//        relationTest2.toString();
//
//        System.out.println("RELATION TEST 3");
//        RelationBinaire relationTest3 = new RelationBinaire(tabIntTest3);
//        relationTest3.toString();
//
//        System.out.println("PRODUIT");
//        boolean[][] matriceProduit = RelationBinaire.produit(tabBoolTest1, tabBoolTest2);
//        for (int i = 0; i < matriceProduit.length; i++) {
//            System.out.print("(");
//            for (int j = 0; j < matriceProduit.length; j++) {
//                if (matriceProduit[i][j] == false) {
//                    System.out.print(" 0 ");
//                } else {
//                    System.out.print(" 1 ");
//                }
//            }
//            System.out.print(")\n");
//        }
//
//        System.out.println("MATRICE NON TRANSPOSEE");
//        for (int i = 0; i < tabBoolTest1.length; i++) {
//            System.out.print("(");
//            for (int j = 0; j < tabBoolTest1.length; j++) {
//                if (tabBoolTest1[i][j] == false) {
//                    System.out.print(" 0 ");
//                } else {
//                    System.out.print(" 1 ");
//                }
//            }
//            System.out.print(")\n");
//        }
//
//        System.out.println("MATRICE TRANSPOSEE");
//        boolean[][] matriceTranposee = RelationBinaire.transposee(tabBoolTest1);
//        for (int i = 0; i < matriceTranposee.length; i++) {
//            System.out.print("(");
//            for (int j = 0; j < matriceTranposee.length; j++) {
//                if (matriceTranposee[i][j] == false) {
//                    System.out.print(" 0 ");
//                } else {
//                    System.out.print(" 1 ");
//                }
//            }
//            System.out.print(")\n");
//        }
//
//        System.out.println("OpBool (1=ou / 2=et / 3=non / 4=implique / 5=equivalent)");
//        Scanner scanner = new Scanner(System.in);
//        int numConnecteur = scanner.nextInt();
//
//        boolean[][] matriceOpBool = RelationBinaire.opBool(tabBoolTest1, tabBoolTest2, numConnecteur);
//        for (int i = 0; i < matriceOpBool.length; i++) {
//            System.out.print("(");
//            for (int j = 0; j < matriceOpBool.length; j++) {
//                if (matriceOpBool[i][j] == false) {
//                    System.out.print(" 0 ");
//                } else {
//                    System.out.print(" 1 ");
//                }
//            }
//            System.out.print(")\n");
//        }
//
//        System.out.println("\nAVEC BOUCLES");
//        RelationBinaire relationAvecBoucles = relationTest2.avecBoucles();
//        relationAvecBoucles.toString();
//
//        System.out.println("SANS BOUCLES");
//        RelationBinaire relationSansBoucles = relationTest1.sansBoucles();
//        relationSansBoucles.toString();
//
//        System.out.println("UNION");
//        RelationBinaire relationUnion = relationTest1.union(relationTest2);
//        relationUnion.toString();
//
//        System.out.println("INTERSECT");
//        RelationBinaire relationIntersect = relationTest1.intersection(relationTest2);
//        relationIntersect.toString();
//
//        System.out.println("COMPLEMENTAIRE");
//        RelationBinaire relationComplementaire = relationTest1.complementaire();
//        relationComplementaire.toString();
//
//        System.out.println("DIFFERENCE");
//        RelationBinaire relationDifference = relationTest1.difference(relationTest2);
//        relationDifference.toString();
//
//        relationTest1.toString();
//        relationTest2.toString();
//
//        System.out.println("estVide");
//        System.out.println(relationTest1.estVide());
//
//        System.out.println("estPlein");
//        System.out.println(relationTest1.estPleine());
//
//        System.out.println("estInclus");
//        System.out.println(relationTest2.estIncluse(relationTest1));
//
//        System.out.println("estEgale");
//        System.out.println(relationTest2.estEgale(relationTest1));
//
//        System.out.println("EstReflexive");
//        System.out.println(relationTest1.estReflexive());
//
//        System.out.println("EstAntiReflexive");
//        System.out.println(relationTest1.estAntireflexive());
//
//        System.out.println("EstSymetrique");
//        System.out.println(relationTest1.estSymetrique());
//        System.out.println("EstAntiSymetrique");
//        System.out.println(relationTest1.estAsymetrique());

//        new RelationBinaire(new int[][] {{1, 1, 1, 1, 1},
//                {0, 1, 1, 1, 1},
//                {0, 0, 1, 1, 0},
//                {0, 0, 0, 1, 0},
//                {0, 0, 0, 0, 1},
//        }).afficheDivers();

        new RelationBinaire(new int[][] {{0,1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}}).afficheDivers();
    }


} // fin MainRelationBinaire