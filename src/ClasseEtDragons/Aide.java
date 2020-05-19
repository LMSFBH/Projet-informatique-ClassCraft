/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClasseEtDragons;


import javax.swing.*;

/**
 *
 * @author erwin
 */
public class Aide extends JFrame {
    JPanel panneauAide= new JPanel();
    JTextArea aide ;
    JScrollPane bareDefilante;
    String texte;
    
    public Aide(){
        int x = 500;
        int y = 500;
        
        setTitle("Aide");
        setSize(x,y);
        texte =" La première colonne correspond au prénom et nom d'un étudiant. En cliquant sur leur nom, vous pouvez voir les informations de cet étudiant et même changer ces informations au besoin.\n" +
                "\n" +
                "La deuxième colonne correspond au pseudo donné à l'étudiant.\n" +
                "\n" +
                "La troisième colonne correspond au rôle donné à l'étudiant. Ce rôle détermine les pouvoirs que l'étudiant aura, ainsi que son maximmum de point de vie.\n" +
                "\n" +
                "La quatrième colonne correspond aux points de vie de l'étudiant. Son maximum de point de vie est déterminé par sa classe, son rôle. Il est possible d'augmenter ou diminuer ses points de vie de 1 grâçe aux boutons adjacents.\n" +
                "\n" +
                "La cinquième colonne correspond à l'expérience de l'étudiant. Il est possible de donner ou d'enlever un demi-niveau, 0.5 expérience, grâçe aux boutons adjacents. Le niveau de l'étudiant permet de déterminer quel pouvoir est disponible et utilisable.\n" +
                "\n" +
                "La sixième colonne correspond aux pouvoirs de l'étudiant. C'est à vous de décider l'action du pouvoir de chaque classe en influancant les points de vie ou expériences des autres étudiants. Il y a 4 couleurs différentes:\n" +
                "-Le noir: l'élève n'a pas le niveau requis pour activer son pouvoir\n" +
                "-Le vert: l'élève a le niveau requis pour activer son pouvoir\n" +
                "-Le jaune: l'élève a déjà activer son pouvoir\n" +
                "-Le rouge: l'élève n'a plus de point de vie et ne peut donc pas utilisé de pouvoirs\n" +
                "\n" +
                "____________________________________________________________________________________________________________________________________________________________________\n" +
                "\n" +
                "Cette application a été programmé par des étudiants de SIM au collège Rosemont pour leur projet d'intégration: Nour Asmani, Arthur Frennette, Brecht Erwin Kegoum Tchantchet et Pierre Moyne-bressand\n" +
                "\n" +
                "Si vous ne trouvez pas l'information que vous cherchiez ici, veuillez aller chercher dans le document texte \"Utilisation\" si votre information s'y trouve.voici l'aides              asdfasdfasdfsadfsafd";
        
        aide = new JTextArea(texte);
        aide.setSize(x,y);
        aide.setEditable(false);
        
        panneauAide.add(aide);
        bareDefilante = new JScrollPane(panneauAide);
        add(bareDefilante);
    }
    
     public Aide(String aPropos){
        int x = 400;
        int y = 400;
        setTitle(aPropos);
        setSize(x,y);
        
        texte = "Ceci est une application pour professeur qui lui permet de gérer sa classe d'étudiant à travers un petit jeu vidéo.\n" +
                "\n" +
                "Les images des étudiants sont tous répertoriées dans un dossier \"Image\" et les tableaux Excel devront être rangés dans le dossier \"Classe\".\n" +
                "\n" +
                "Nous vous avons mis dans le dossier \"Classe\" un exemple de tableau Excel que vous devez suivre pour créer votre propre classe à partir du tableaux Excel pour éviter des erreurs. \n" +
                "Vous n'aurez plus besoin de toucher ce tableau Excel après cela.";
        
        aide = new JTextArea(texte);
        aide.setSize(x,y);
        aide.setEditable(false);
        
        panneauAide.add(aide);
        bareDefilante = new JScrollPane(panneauAide);
        add(bareDefilante);
     }

}
