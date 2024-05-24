package org.example.sae20102.Vue;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import org.example.sae20102.Controller;
import org.example.sae20102.Model.Entrepot;
import org.example.sae20102.Model.Mine;
import org.example.sae20102.Model.Robot;
import org.example.sae20102.Model.Secteur;


public class PageRobot extends Stage {
    private Scene scene;
    private Group root ;
    private Robot robot;
    private Rectangle rectangle;
    private Rectangle[] rectangles;
    private Controller controller;
    private Label type;
    private Label quantite;
    private Label capacite;
    private Label recolte;
    private Button buttonHaut;
    private Button buttonBas;
    private Button buttonGauche;
    private Button buttonDroite;
    private Button buttonRecolte;
    private Button buttonDepot;
    private PageJeu pageJeu;

    public PageRobot(Robot robot, Rectangle rectangle, Controller controller, Rectangle[] rectangles, PageJeu pageJeu) {
        this.pageJeu = pageJeu;
        this.root = new Group();
        this.robot = robot;
        this.rectangle = rectangle;
        this.controller = controller;
        this.rectangles = rectangles;
        this.type = new Label("Type: " + this.robot.getNature());
        this.quantite = new Label("Quantite: " + this.robot.getQuantite());
        this.capacite = new Label("Capacite: " + this.robot.getCapacite());
        this.recolte = new Label("Recolte: " + this.robot.getRecolte());

        this.buttonHaut = new Button("⇑");
        this.buttonBas = new Button("⇓");
        this.buttonGauche = new Button("⇐");
        this.buttonDroite = new Button("⇒");
        this.buttonRecolte = new Button("Recolte");
        this.buttonDepot = new Button("Depot");

        this.type.setLayoutX(10);
        this.type.setLayoutY(10);
        this.quantite.setLayoutX(10);
        this.quantite.setLayoutY(30);
        this.capacite.setLayoutX(10);
        this.capacite.setLayoutY(50);
        this.recolte.setLayoutX(10);
        this.recolte.setLayoutY(70);

        this.buttonHaut.setLayoutX(150);
        this.buttonHaut.setLayoutY(10);
        this.buttonBas.setLayoutX(150);
        this.buttonBas.setLayoutY(40);
        this.buttonGauche.setLayoutX(120);
        this.buttonGauche.setLayoutY(40);
        this.buttonDroite.setLayoutX(180);
        this.buttonDroite.setLayoutY(40);

        this.buttonRecolte.setLayoutX(95);
        this.buttonRecolte.setLayoutY(70);
        this.buttonDepot.setLayoutX(170);
        this.buttonDepot.setLayoutY(70);

        this.buttonHaut.setOnAction(e -> {
            Secteur secteurC = this.robot.getSecteur();
            if (this.controller.MoveRobot(this.robot, "H")){
                FillRectangle(secteurC);
                for (Rectangle rect : this.rectangles) {
                    if (rect.getX() == this.rectangle.getX() && rect.getY() == this.rectangle.getY() - 50) {
                        this.rectangle = rect;
                        this.rectangle.setFill(Color.GREY);
                        System.out.println(this.controller.getGrille().toString());
                        break;
                    }
                }
            }
        });

        this.buttonBas.setOnAction(e -> {
            Secteur secteurC = this.robot.getSecteur();
            if (this.controller.MoveRobot(this.robot, "B")){
                FillRectangle(secteurC);
                for (Rectangle rect : this.rectangles) {
                    if (rect.getX() == this.rectangle.getX() && rect.getY() == this.rectangle.getY() + 50) {
                        this.rectangle = rect;
                        this.rectangle.setFill(Color.GREY);
                        System.out.println(this.controller.getGrille().toString());
                        break;
                    }
                }
            }
        });

        this.buttonGauche.setOnAction(e -> {
            Secteur secteurC = this.robot.getSecteur();
            if (this.controller.MoveRobot(this.robot, "G")){
                FillRectangle(secteurC);
                for (Rectangle rect : this.rectangles) {
                    if (rect.getX() == this.rectangle.getX() - 50 && rect.getY() == this.rectangle.getY()) {
                        this.rectangle = rect;
                        this.rectangle.setFill(Color.GREY);
                        System.out.println(this.controller.getGrille().toString());
                        break;
                    }
                }
            }
        });

        this.buttonDroite.setOnAction(e -> {
            Secteur secteurC = this.robot.getSecteur();
            if (this.controller.MoveRobot(this.robot, "D")){
                FillRectangle(secteurC);
                for (Rectangle rect : this.rectangles) {
                    if (rect.getX() == this.rectangle.getX() + 50 && rect.getY() == this.rectangle.getY()) {
                        this.rectangle = rect;
                        this.rectangle.setFill(Color.GREY);
                        System.out.println(this.controller.getGrille().toString());
                        break;
                    }
                }
            }
        });

        this.buttonRecolte.setOnAction(e -> {
            Mine[] mines = this.controller.getMines();
            for (Mine mine : mines) {
                if (mine.getSecteur().equals(this.robot.getSecteur())) {
                    if (this.controller.FillRobot(this.robot, mine)) {
                        this.recolte.setText("Recolte: " + this.robot.getRecolte());
                        this.quantite.setText("Quantite: " + this.robot.getQuantite());
                    }
                }
            }
        });


        this.buttonDepot.setOnAction(e -> {
            Entrepot[] entrepots = this.controller.getEntrepots();
            for (Entrepot entrepot : entrepots) {
                if (entrepot.getSecteur().equals(this.robot.getSecteur())) {
                    if (this.controller.UnloadRobot(this.robot, entrepot)) {
                        this.quantite.setText("Quantite: " + this.robot.getQuantite());
                    }
                }
            }
        });

        this.root.getChildren().addAll(this.type, this.quantite, this.capacite, this.recolte, this.buttonHaut, this.buttonBas, this.buttonGauche, this.buttonDroite, this.buttonRecolte, this.buttonDepot);
        this.scene = new Scene(root, 300, 120);
        this.setTitle(STR."Robot \{this.robot.getNumR()}");
        this.setScene(scene);
    }

    public void FillRectangle(Secteur secteur){
        switch (secteur.toString()) {
            case " M":
                this.rectangle.setFill(Color.RED);
                break;
            case " E":
                this.rectangle.setFill(pageJeu.ImagePattern3);
                break;
            default:
                this.rectangle.setFill(pageJeu.ImagePattern1);
                break;
        }
    }
}
